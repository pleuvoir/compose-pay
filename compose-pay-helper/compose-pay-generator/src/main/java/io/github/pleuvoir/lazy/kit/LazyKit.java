package io.github.pleuvoir.lazy.kit;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class LazyKit {

	/**
	 * 默认文件夹下生成的 java 文件路径
	 */
	public static String javaAbsoluteFilePath(String javaFileName) {
		return LazyKit.defaultFolder().concat(javaFileName).concat(".java");
	}

	/**
	 * 驼峰
	 */
	public static String toCapitalizeCamelCase(String s) {
		if (s == null) {
			return null;
		}
		s = toCamelCase(s);
		return firstToUpper(s);
	}

	/**
	 * 首字母大写
	 */
	public static String firstToUpper(String s) {
		return s.substring(0, 1).toUpperCase() + s.substring(1);
	}

	/**
	 * _ 风格数据库命名转驼峰
	 */
	public static String toCamelCase(String s) {
		final char SEPARATOR = '_';
		if (s == null) {
			return null;
		}
		s = s.toLowerCase();
		StringBuilder sb = new StringBuilder(s.length());
		boolean upperCase = false;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c == SEPARATOR) {
				upperCase = true;
			} else if (upperCase) {
				sb.append(Character.toUpperCase(c));
				upperCase = false;
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	/**
	 * 生成文件默认的文件夹位置
	 */
	public static String defaultFolder() {
		String codeFolder = getTarget().getPath().concat("/code/");
		File file = new File(codeFolder);
		if (!file.exists()) {
			file.mkdirs();
		}
		return codeFolder;
	}

	/**
	 * target 文件夹的位置
	 */
	public static File getTarget() {
		String path = (String.valueOf(Thread.currentThread().getContextClassLoader().getResource("")))
				.replaceAll("file:/", "").replaceAll("%20", " ").trim();
		try {
			path = URLDecoder.decode(path, "utf-8");
			if (path.indexOf(":") != 1) {
				path = File.separator + path;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return new File(path).getParentFile();
	}
}
