package io.github.pleuvoir.pay.common.builder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPConnectionClosedException;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FtpUploadBuilder {

    private static Logger logger = LoggerFactory.getLogger(FtpUploadBuilder.class);

    private FTPClient client;

    //ftp服务器地址
    private String host;

    //ftp 端口号 默认21
    private int port = 21;

    //ftp服务器用户名
    private String username;

    //ftp服务器密码
    private String password;

    //ftp远程目录
    private String remoteDir;

    /**
     * 创建实例
     *
     * @param host 主机名或IP
     * @param port 端口号
     */
    public static FtpUploadBuilder create(String host, int port) {
        FtpUploadBuilder builder = new FtpUploadBuilder();
        builder.host = host;
        builder.port = port;
        return builder;
    }

    /**
     * 设置登录用户
     *
     * @param username 用户名
     * @param password 密码
     */
    public FtpUploadBuilder setUser(String username, String password) {
        this.username = username;
        this.password = password;
        return this;
    }

    /**
     * 设置远程目录
     */
    public FtpUploadBuilder setRemoteDirectory(String remoteDir) {
        this.remoteDir = remoteDir;
        return this;
    }


    private void connect() throws IOException {
        client = new FTPClient();
        //设置超时时间
        client.setConnectTimeout(30000);

        // 1、连接服务器
        if (!client.isConnected()) {
            // 如果采用默认端口，可以使用client.connect(host)的方式直接连接FTP服务器
            client.connect(host, port);
            // 登录
            client.login(username, password);
            // 获取ftp登录应答码 ，验证是否登陆成功
            if (!FTPReply.isPositiveCompletion(client.getReplyCode())) {
                logger.error("未连接到FTP，可能是用户名或密码错误。replyString={}", client.getReplyString());
                disconnect();
                throw new IllegalArgumentException("未连接到FTP，用户名或密码错误。");
            } else {
                logger.info("FTP连接成功。IP:{}, PORT:{}", host, port);
            }
            // 2、设置连接属性
            String encoding = "utf-8";
            client.setControlEncoding(encoding);
            // 设置以二进制方式传输
            client.setFileType(FTPClient.BINARY_FILE_TYPE);
            client.enterLocalPassiveMode();
        } else {
            logger.error("连接FTP服务器失败。IP:{}, PORT:{}", host, port);
            throw new FTPConnectionClosedException("连接FTP服务器失败!");
        }
    }


    private void uploadFile(FTPClient client, File localFile) throws IOException {
        OutputStream os = null;
        FileInputStream fis = null;
        try {
            os = client.storeFileStream(new String(localFile.getName().getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
            if (os == null) {
                logger.warn("上传失败，请检查是否有上传权限。");
                throw new IOException("上传失败，请检查是否有上传权限。");
            }
            fis = new FileInputStream(localFile);
            IOUtils.copy(fis, os);
        } finally {
            IOUtils.closeQuietly(os);
            IOUtils.closeQuietly(fis);
        }
    }

    private void uploadFile(FTPClient client, InputStream input, String filename) throws IOException {
        OutputStream os = null;
        try {
            os = client.storeFileStream(new String(filename.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
            if (os == null) {
                logger.warn("上传失败，请检查是否有上传权限。");
                throw new IOException("上传失败，请检查是否有上传权限。");
            }
            IOUtils.copy(input, os);
        } finally {
            IOUtils.closeQuietly(os);
        }
    }


    private void changeOrMkdir(FTPClient client, String remoteDir) throws IOException {
        //尝试切换目录
        boolean isChanged = client.changeWorkingDirectory(remoteDir);
        //切换目录失败时，进行创建目录
        if (!isChanged) {
            //循环多次目录
            for (Path path : Paths.get(remoteDir)) {
                //每一层的目录名
                String pathName = path.toString();
                //尝试切换进入本层的目录中
                isChanged = client.changeWorkingDirectory(pathName);
                //切换本层的目录失败，进行创建本层目录
                if (!isChanged) {
                    boolean isMaked = client.makeDirectory(pathName);
                    if (isMaked) {
                        logger.debug("创建子目录：{}", pathName);
                        //创建本层目录成功后，尝试进入创建的目录
                        isChanged = client.changeWorkingDirectory(pathName);
                    }
                }
                if (logger.isDebugEnabled()) {
                    logger.debug("切换至工作子目录{}：{}", isChanged ? StringUtils.EMPTY : "失败", pathName);
                }
            }
        }
        if (isChanged) {
            logger.debug("切换至工作目录：{}", remoteDir);
        } else {
            logger.error("切换至工作目录失败：{}", remoteDir);
        }
    }

    /**
     * 上传文件
     */
    public void upload(File file) throws IOException {
        // 2、取本地文件
        if (file == null) {
            logger.warn("没有找到可上传的文件。");
            throw new IllegalArgumentException("没有找到可上传的文件。");
        }
        logger.debug("准备上传文件：{}", file.getAbsolutePath());
        // 3、上传到FTP服务器
        this.connect();
        try {
            //设置远程FTP目录
            changeOrMkdir(client, remoteDir);
            //上传文件
            uploadFile(client, file);
            logger.info("文件上传成功,上传文件路径：{}", remoteDir);
        } finally {
            disconnect();
        }
    }

    /**
     * 批量上传文件
     */
    public void batchUpload(List<File> files) throws IOException {
        if (files == null || files.size() == 0) {
            logger.warn("文件数为0，没有找到可上传的文件。");
            throw new IllegalArgumentException("文件数为0，没有找到可上传的文件。");
        }
        logger.debug("准备上传{}个文件", files.size());
        // 3、上传到FTP服务器
        try {
            this.connect();
            //设置远程FTP目录
            changeOrMkdir(client, remoteDir);
            for (File file : files) {
                //上传文件
                uploadFile(client, file);
            }
            logger.info("文件上传成功,上传文件路径：{}", remoteDir);
        } finally {
            disconnect();
        }
    }

    /**
     * 上传文件
     *
     * @param input    待上传的文件流
     * @param filename 保存在服务器的文件名
     */
    public void upload(InputStream input, String filename) throws IOException {
        if (input == null) {
            logger.warn("没有找到可上传的文件。");
            throw new IllegalArgumentException("没有找到可上传的文件。");
        }
        logger.debug("准备上传文件：{}", filename);
        // 3、上传到FTP服务器
        try {
            this.connect();
            //设置远程FTP目录
            changeOrMkdir(client, remoteDir);
            //上传文件
            uploadFile(client, input, filename);
            logger.info("文件上传成功,上传文件路径：{}", remoteDir);
        } finally {
            disconnect();
        }
    }


    /**
     * 关闭连接
     */
    private void disconnect() {
        if (client != null && client.isConnected()) {
            try {
                client.disconnect();
                logger.debug("关闭FTP连接。");
            } catch (IOException e) {
                logger.error("关闭FTP连接失败。", e);
            }
        }
    }
}
