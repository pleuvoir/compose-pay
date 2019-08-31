package io.github.pleuvoir.manager.common.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;

public class StringUtil {
	
	private static final char[] HEX = new char[] { '0', '1', '2', '3', '4',
			'5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
	
	/**
	 * 系统换行符
	 */
	public static final String LINE_SEPARATOR;
	
	static{
		LINE_SEPARATOR = getLineSeparator();
	}
	
	private static String getLineSeparator(){
		@SuppressWarnings("deprecation")
		String lineSeparator = SystemUtils.LINE_SEPARATOR;
		return StringUtils.isBlank(lineSeparator) ? "\r\n" : lineSeparator;
	}
	
	/**
	 * 将byte数组转化为String类型的十六进制编码格式 本方法实现的思路是： 1）每位byte数组转换为2位的十六进制数
	 * 2）将字节左移4位取得高四位字节数值，获取对应的char类型数组编码 3）将字节与0x0F按位与，从而获取第四位的字节，同样获取编码
	 */
	public static String hex(byte[] b) {
		char[] rs = new char[b.length * 2];
		for(int i=0; i<b.length; i++){
			rs[i*2] = HEX[b[i] >> 4 & 0x0F];	// &0x0F的目的是为了转换负数
			rs[i*2+1] = HEX[b[i] & 0x0F];
		}
		return new String(rs);
	}
	/**
	 * 将byte数组中指定的位数范围转为String类型的十六进制格式，指定的范围包前不包后
	 * @param data 需要转换的数字
	 * @param begin 开始的数组下标
	 * @param end 截止的数组下标，不包含
	 * @return
	 */
	public static String hex(byte[] b, int begin, int end){
		StringBuilder accum = new StringBuilder();
		for (int i=begin; i<end; i++) {
			accum.append(HEX[b[i] >> 4 & 0x0F]);// &0x0F的目的是为了转换负数
			accum.append(HEX[b[i] & 0x0F]);
		}
		return accum.toString();
	}
	
    public static String of(String s1, String s2){
    	StringBuilder b = new StringBuilder();
    	b.append(s1);
    	b.append(s2);
    	return b.toString();
    }
    
    public static String of(String s1, String s2, String s3){
    	StringBuilder b = new StringBuilder();
    	b.append(s1);
    	b.append(s2);
    	b.append(s3);
    	return b.toString();
    }
    
    public static String of(String s1, String s2, String s3, String s4){
    	StringBuilder b = new StringBuilder();
    	b.append(s1);
    	b.append(s2);
    	b.append(s3);
    	b.append(s4);
    	return b.toString();
    }
    
    public static String of(String s1, String s2, String s3, String s4, String s5){
    	StringBuilder b = new StringBuilder();
    	b.append(s1);
    	b.append(s2);
    	b.append(s3);
    	b.append(s4);
    	b.append(s5);
    	return b.toString();
    }
    
    public static String of(String s1, String s2, String s3, String s4, String s5, String s6){
    	StringBuilder b = new StringBuilder();
    	b.append(s1);
    	b.append(s2);
    	b.append(s3);
    	b.append(s4);
    	b.append(s5);
    	b.append(s6);
    	return b.toString();
    }
    
    public static String of(String s1, String s2, String s3, String s4, String s5, String s6, String s7){
    	StringBuilder b = new StringBuilder();
    	b.append(s1);
    	b.append(s2);
    	b.append(s3);
    	b.append(s4);
    	b.append(s5);
    	b.append(s6);
    	b.append(s7);
    	return b.toString();
    }
    
    public static String of(String s1, String s2, String s3, String s4, String s5, String s6, String s7, String s8){
    	StringBuilder b = new StringBuilder();
    	b.append(s1);
    	b.append(s2);
    	b.append(s3);
    	b.append(s4);
    	b.append(s5);
    	b.append(s6);
    	b.append(s7);
    	b.append(s8);
    	return b.toString();
    }
    
    public static String of(String...strs){
    	StringBuilder b = new StringBuilder();
    	for(String s : strs){
    		b.append(s);
    	}
    	return b.toString();
    }
    
    public static String of(Object...array){
    	StringBuilder b = new StringBuilder();
    	for(Object o : array){
    		b.append(String.valueOf(o));
    	}
    	return b.toString();
    }
    
    /**
     * 首字母转小写
     * @return
     */
    public static String lowerCaseInitial(String val){
    	if(val==null){
    		return val;
    	}
    	char[] chars = val.toCharArray();
    	char lowerChar = Character.toLowerCase(chars[0]);
    	chars[0] = lowerChar;
    	return new String(chars);
    }

	/**
	 * 拼接地址
	 * @param url
	 * @param addurl
	 * @return
	 */
	public static String contactUrl(String url, String addurl) {
		StringBuilder b = new StringBuilder();
		if(StringUtils.isNotBlank(url)) {
			b.append(url);
			if(url.endsWith("/")) {
				b.deleteCharAt(url.length()-1);
			}
		}
		if(StringUtils.isNotBlank(addurl)) {
			if(!addurl.startsWith("/")) {
				b.append("/");
			}
			b.append(addurl);
		}
		return b.toString();
	}
}

