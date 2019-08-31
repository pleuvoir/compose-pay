package io.github.pleuvoir.manager.common.util;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;

public final class PasswordEncrypt {

	public static String encode(String password, String salt){
		byte[] sha1 = Sha1.bin((password + salt).getBytes());
		return Md5.str(sha1);
	}
	
	public static String encode(String password, Date date){
		String salt = "";
		if(date!=null){
			salt = DateFormatUtils.format(date, "yyyyMMddHHmmss");
		}
		return encode(password, salt);
	}
}
