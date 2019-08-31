package io.github.pleuvoir.manager.common.util.excel;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * 执行简单的excel导入<br>
 * 如下示例：
 * <pre><code>
 * ExcelReader.builder()
 *     .setRowBeginIndex(1)	
 *     .setColumnBeginIndex(0)
 *     .setColumMaxTotal(4)	
 *     .setSheetMaxTotal(1)	
 *     .setDatePattren("yyyy-MM-dd HH:mm:ss")	
 *     .build()		
 *     .read(new File("D:\\aaa.xlsx"), ls -> {	
 *         t.save(ls);	
 *     });
 * </code></pre>
 * 
 * @author abeir
 *
 */
public class ExcelReader {
	private static Logger logger = LoggerFactory.getLogger(ExcelReader.class);
	//起始的行号
	private int rowBeginIndex;
	//起始的列号
	private int columnBeginIndex;
	//最大的列数量
	private int columMaxTotal;
	//最大的sheet数量
	private int sheetMaxTotal;
	//日期格式化
	private SimpleDateFormat fmt;
	
	private ExcelReader() {}
	
	/**
	 * 读取excel，注意，该方法不会关闭输入流
	 * @param input excel输入流
	 * @param save 保存每行数据
	 */
	public void read(InputStream input, Consumer<List<String>> save) throws InvalidFormatException, IOException {
		Workbook wk = WorkbookFactory.create(input);
			
		int sheetNum = getSheetMaxNumber(wk);
		for (int i = 0; i < sheetNum; i++) {
			Sheet sheet = wk.getSheetAt(i);
			for (Row row : sheet) {
				if (row.getRowNum() < rowBeginIndex)
					continue;
				processRow(row, save);
			}
		}
	}
	
	/**
	 * 读取excel
	 * @param file excel文件
	 * @param save 保存每行数据
	 */
	public void read(File file, Consumer<List<String>> save) throws InvalidFormatException, IOException {
		try(
				FileInputStream input = new FileInputStream(file);
				){
			read(input, save);
		}
	}
	
	private int getSheetMaxNumber(Workbook wk) {
		int sheetNum = wk.getNumberOfSheets();
		logger.debug("当前excel sheet数量：{}", sheetNum);
		if(sheetNum > sheetMaxTotal) {
			logger.debug("当前excel sheet数量 [{}大于{}] 最大sheet数量，使用：{}", sheetNum, sheetMaxTotal, sheetMaxTotal);
			return sheetMaxTotal;
		}
		logger.debug("当前excel sheet数量 [{}小于等于{}] 最大sheet数量，使用：{}", sheetNum, sheetMaxTotal, sheetNum);
		return sheetNum;
	}
	
	private void processRow(Row row, Consumer<List<String>> save) {
		if (columMaxTotal < 1) {
			logger.debug("最大的列数量小于1");
			return;
		}
		//记录一行的数据
		List<String> rowList = new ArrayList<String>();
		//处理一行
		for (int i = columnBeginIndex; i < columMaxTotal; i++) {
			Cell cell = row.getCell(i);
			// 遇见空的单元格，将赋予null
			if (cell == null) {
				logger.debug("遇见空单元格，row:{}, column:{}", row.getRowNum(), i);
				rowList.add(null);
				continue;
			}
			switch (cell.getCellTypeEnum()) {
			case BOOLEAN:
				rowList.add(String.valueOf(cell.getBooleanCellValue()));
				break;
			case NUMERIC:
				if (DateUtil.isCellDateFormatted(cell)) {
					rowList.add(this.fmt.format(cell.getDateCellValue()));
				} else {
					rowList.add(NumberToTextConverter.toText(cell
							.getNumericCellValue()));
				}
				break;
			case STRING:
				rowList.add(cell.getRichStringCellValue().getString());
				break;
			default:
				logger.debug("未知的单元格格式，按空处理，row:{}, column:{}", row.getRowNum(), i, cell.getCellTypeEnum().toString());
				rowList.add(null);
				break;
			}
		}
		logger.debug("行：[{}] >>> {}", row.getRowNum(), rowList);
		if(save!=null) {
			save.accept(rowList);
		}
	}
	
	/**
	 * 创建构造器
	 * @return
	 */
	public static Builder builder() {
		return new Builder();
	}
	
	/**
	 * 构建excel读取的配置
	 */
	public static class Builder{
		
		private int rowBeginIndex = 0;
		private int columnBeginIndex = 0;
		private int columMaxTotal = Integer.MAX_VALUE;
		private int sheetMaxTotal = Integer.MAX_VALUE;
		private String datePattren;
		
		public ExcelReader build() {
			ExcelReader reader = new ExcelReader();
			reader.rowBeginIndex = rowBeginIndex;
			reader.columnBeginIndex = columnBeginIndex;
			reader.columMaxTotal = columMaxTotal;
			reader.sheetMaxTotal = sheetMaxTotal;
			reader.fmt = new SimpleDateFormat(datePattren);
			return reader;
		}
		/**
		 * 设置读取的起始行号
		 */
		public Builder setRowBeginIndex(int rowBeginIndex) {
			if(rowBeginIndex < 0) {
				throw new IllegalArgumentException("起始的行号小于0");
			}
			this.rowBeginIndex = rowBeginIndex;
			return this;
		}
		/**
		 * 设置读取的起始列号
		 */
		public Builder setColumnBeginIndex(int columnBeginIndex) {
			if(columnBeginIndex < 0) {
				throw new IllegalArgumentException("起始的列号小于0");
			}
			this.columnBeginIndex = columnBeginIndex;
			return this;
		}
		/**
		 * 设置最大的列数量
		 */
		public Builder setColumMaxTotal(int columMaxTotal) {
			if(columMaxTotal < 1) {
				throw new IllegalArgumentException("最大的列数量小于1");
			}
			this.columMaxTotal = columMaxTotal;
			return this;
		}
		/**
		 * 设置最大的sheet数量
		 */
		public Builder setSheetMaxTotal(int sheetMaxTotal) {
			if(sheetMaxTotal < 1) {
				throw new IllegalArgumentException("最大的sheet数量小于1");
			}
			this.sheetMaxTotal = sheetMaxTotal;
			return this;
		}
		/**
		 * 设置日期格式
		 */
		public Builder setDatePattren(String datePattren) {
			this.datePattren = datePattren;
			return this;
		}
	}
	
}
