package io.github.pleuvoir.manager.common.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 * SHA-1算法
 * @author abeir
 *
 */
public class Sha1 {
	/**
	 * 对字节数组的指定部分进行sha-1的计算
	 * @param input
	 * @param offset
	 * @param len
	 * @return
	 */
	public static byte[] bin(byte[] input, int offset, int len){
		try {
			MessageDigest m = MessageDigest.getInstance("sha-1");
			m.update(input, offset, len);
			return m.digest();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 对字节数组进行sha-1计算
	 * @param input
	 * @return
	 */
	public static byte[] bin(byte[] input){
		return bin(input, 0, input.length);
	}
	/**
	 * 将输入的字符串按指定的编码转换后进行sha-1的计算，并返回16进制的字符串
	 * @param input
	 * @param charset
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String str(String input, String charset) throws UnsupportedEncodingException{
		byte[] sha1 = bin(input.getBytes(charset));
		return io.github.pleuvoir.manager.common.util.StringUtil.hex(sha1);
	}
	/**
	 * 将输入的字节数组进行sha-1计算后，返回16进制的字符串
	 * @param input
	 * @return
	 */
	public static String str(byte[] input){
		byte[] sha1 = bin(input);
		return io.github.pleuvoir.manager.common.util.StringUtil.hex(sha1);
	}
	/**
	 * 将输入的字符串使用utf-8编码转换后进行sha-1的计算，并返回16进制的字符串
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
	
}

