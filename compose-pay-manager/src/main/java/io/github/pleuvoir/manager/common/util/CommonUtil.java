package io.github.pleuvoir.manager.common.util;

import java.lang.reflect.Field;
import com.baomidou.mybatisplus.toolkit.ReflectionKit;

/**
 * @author pleuvoir
 *
 */
public class CommonUtil {

	/**
	 * 对对象中所有String类型字段统一去除空格
	 * @param clazz
	 * @param entity
	 */
	public static void trimAll(Class<?> clazz, Object entity) {
		for (Field field : ReflectionKit.getFieldList(clazz)) {
			field.setAccessible(true);
			if (field.getType().equals(String.class)) {
				String methodValue = String.valueOf(ReflectionKit.getMethodValue(clazz, entity, field.getName()));
				try {
					field.set(entity, org.apache.commons.lang3.StringUtils.trim(methodValue));
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}
}
