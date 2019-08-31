package io.github.pleuvoir.manager.common.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5算法
 * @author abeir
 *
 */
public class Md5 {
	/**
	 * 字节的输入与字节的输出
	 * @param input
	 * @return
	 */
	public static byte[] bin(byte[] input){
		return bin(input, 0, input.length);
	}
	/**
	 * 字节的输入与字节的输出
	 * @param input
	 * @param offset
	 * @param len
	 * @return
	 */
	public static byte[] bin(byte[] input, int offset, int len){
		MessageDigest m;
		try {
			m = MessageDigest.getInstance("md5");
			m.update(input, offset, len);
			return m.digest();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 将输入转换成UTF-8编号后进行MD5计算，并返回16进制的大写字符串
	 * @param input
	 * @return
	 */
	public static String utf8(String input){
		try {
			return str(input, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 输入字节数组后，返回16进制的大写字符串
	 * @param input
	 * @return
	 */
	public static String str(byte[] input){
		byte[] md5 = bin(input);
		return io.github.pleuvoir.manager.common.util.StringUtil.hex(md5);
	}
	
	/**
	 * 输入的字符串被转换成指定的编码后，再进行MD5的计算，并返回16进制的大写字符串
	 * @param input
	 * @param charset
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String str(String input, String charset) throws UnsupportedEncodingException{
		byte[] md5 = bin(input.getBytes(charset));
		return io.github.pleuvoir.manager.common.util.StringUtil.hex(md5);
	}
	
	/**
	 * 计算对象的md5，对象必须实现{@link Serializable}
	 * @param obj 
	 * @return 返回md5的字节数组
	 */
	public static byte[] object2Bin(Object obj){
		try(
			ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
			ObjectOutputStream objOut = new ObjectOutputStream(byteOut);
		){
			objOut.writeObject(obj);
			return bin(byteOut.toByteArray());
		} catch(IOException e){
			return new byte[0];
		}
	}
	
	/**
	 * 计算对象的md5，对象必须实现{@link Serializable}
	 * @param obj
	 * @return 返回md5的字符串
	 */
	public static String object2Str(Object obj){
		try(
			ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
			ObjectOutputStream objOut = new ObjectOutputStream(byteOut);
		){
			objOut.writeObject(obj);
			return str(byteOut.toByteArray());
		} catch(IOException e){
			return "";
		}
	}
}

