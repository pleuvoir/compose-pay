package io.github.pleuvoir.manager.common.util.excel;
/**
 * Excel单元格格式化
 * @author abeir
 *
 */
public interface CellFormater {
	/**
	 * 执行格式化<br>
	 * 注意，每一次单元格写入时都会调用该方法
	 * @param columnName 列名，是结果集中javabean的属性名或者map的key
	 * @param value 
	 * @return 返回格式化后的单元格内容。若返回null，则使用默认的格式写入单元格
	 */
	String format(String columnName, Object value);
}
