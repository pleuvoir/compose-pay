package io.github.pleuvoir.manager.common.util;


import io.github.pleuvoir.manager.exception.AssertException;

/**
 * 断言工具，判断失败时抛出{@link AssertException}
 * @author abeir
 *
 */
public class AssertUtil {

	/**
	 * 判断执行结果数量是否为1，为空时也认为非1
	 * @param rs 结果数量
	 * @param errorMsg 错误信息
	 * @throws AssertException
	 */
	public static void assertOne(Integer rs, String errorMsg) throws AssertException {
		if(rs==null || rs.intValue()!=1) {
			throw new AssertException(errorMsg);
		}
	}
	/**
	 * 判断执行结果数量是否为1，为空时也认为非1
	 * @param rs 结果数量
	 * @param errorMsg 错误信息
	 * @throws AssertException
	 */
	public static void assertOne(int rs, String errorMsg) throws AssertException {
		if(rs!=1) {
			throw new AssertException(errorMsg);
		}
	}
	
	/**
	 * 判断执行结果是否是预期的数量，为空时也认为非预期
	 * @param rs 结果数量
	 * @param expected 预期的数量
	 * @param errorMsg 错误信息
	 * @throws AssertException
	 */
	public static void assertMulti(Integer rs, int expected, String errorMsg) throws AssertException {
		if(rs==null || rs.intValue()!=expected) {
			throw new AssertException(errorMsg);
		}
	}
	
}
