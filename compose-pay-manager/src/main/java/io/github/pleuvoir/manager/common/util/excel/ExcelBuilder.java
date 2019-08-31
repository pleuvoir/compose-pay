package io.github.pleuvoir.manager.common.util.excel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

/**
 * 构建excel，并按照指定的方式写入文件或者文件流<br>
 * 注意，若写入文件流时，不会主动关闭流
 * @author abeir
 *
 */
public class ExcelBuilder {
	
	//所有excel导出分页每页的行数
	public static final int ROW_LEN = 65535;

	private OutputStream outputStream;
	private File outputFile;
	
	private String[] header;
	private String[] columns;
	private int[] widths;
	private CellFormater formater;
	private List<?> list;
	
	//写入excel时在内存中保留的行数
	private int windowSize = 1000;
	
	private ExcelBuilder(){}
	
	/**
	 * 构建一个新的对象
	 * @return
	 */
	public static ExcelBuilder create(){
		ExcelBuilder builder = new ExcelBuilder();
		return builder;
	}
	/**
	 * 设置写入excel时在内存中保留的行数，默认为1000行
	 * @param windowSize
	 * @return
	 */
	public ExcelBuilder setWindowSize(int windowSize) {
		this.windowSize = windowSize;
		return this;
	}
	/**
	 * 设置按流的方式输出
	 * @param outputStream
	 * @return
	 */
	public ExcelBuilder setOutput(OutputStream outputStream){
		this.outputStream = outputStream;
		return this;
	}
	/**
	 * 设置输出至指定的文件
	 * @param outputFile
	 * @return
	 */
	public ExcelBuilder setOutput(File outputFile){
		if(outputFile!=null && outputFile.isDirectory()){
			throw new IllegalArgumentException("输出位置是一个目录，必须指定一个文件位置");
		}
		this.outputFile = outputFile;
		return this;
	}
	/**
	 * 设置excel的表头内容
	 * @param header
	 * @return
	 */
	public ExcelBuilder setHeader(String[] header){
		this.header = header;
		return this;
	}
	/**
	 * 设置结果集中的对象的属性名，或者map的key<br>
	 * 长度应该与excel表头保持一致
	 * @param columns
	 * @return
	 */
	public ExcelBuilder setColumns(String[] columns){		
		this.columns = columns;
		return this;
	}
	/**
	 * 设置单元格的宽度
	 * @param widths
	 * @return
	 */
	public ExcelBuilder setWidths(int[] widths){
		this.widths = widths;
		return this;
	}
	/**
	 * 设置单元格格式化
	 * @param formater
	 * @return
	 */
	public ExcelBuilder setFormater(CellFormater formater){
		this.formater = formater;
		return this;
	}
	/**
	 * 设置结果集，结果集的list内部可以是javabean或map
	 * @param list
	 * @return
	 */
	public ExcelBuilder setResults(List<?> list){
		this.list = list;
		return this;
	}
	/**
	 * 进行excel的自动构建，并按照设置的输出方式进行输出
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws IOException
	 */
	public void build() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, IOException{
		check();
		writeToExcel();
	}
	
	private void check() throws IllegalArgumentException{
		if(this.outputStream != null && this.outputFile != null){
			throw new IllegalArgumentException("输出的类型过多，只可使用一种输出方式");
		}
		if(this.outputStream == null && this.outputFile == null){
			throw new IllegalArgumentException("未设置输出方式");
		}
	}
	
	
	private void writeToExcel() throws IOException, IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		try(
				SXSSFWorkbook wb = new SXSSFWorkbook(windowSize);
				){
			// 若数据过多需要分拆成多个sheet容纳
			List<?>[] partitionResult = partition();
			//输出一个空的excel
			if(ArrayUtils.isEmpty(partitionResult)){
				Sheet sheet = wb.createSheet();
				setSheetColumnWidths(sheet);
				createHead(sheet, wb);
				createBody(null, sheet, 1);
			}else{
				for (List<?> subResult : partitionResult) {
					Sheet sheet = wb.createSheet();
					setSheetColumnWidths(sheet);
					createHead(sheet, wb);
					createBody(subResult, sheet, 1);
				}
			}
			write(wb);
		}
	}
	
	/**
	 * 拆分结果集，将按照excel每个sheet的最大值进行拆分
	 */
	private List<?>[] partition() {
		if(CollectionUtils.isEmpty(this.list)){
			return null;
		}
		int size = this.list.size();
		int partNo = 0;
		if (size % ROW_LEN == 0)
			partNo = 1;
		else
			partNo = size / ROW_LEN + 1;
		List<?>[] list = new List[partNo];
		for (int i = 0; i < partNo; i++) {
			if (i + 1 == partNo) {
				list[i] = this.list.subList(i * ROW_LEN, size);
			} else {
				list[i] = this.list.subList(i * ROW_LEN, (i + 1) * ROW_LEN);
			}
		}
		return list;
	}
	
	
	/**
	 * 设置每个单元格长度，小于等于0的宽度会被忽略
	 */
	private void setSheetColumnWidths(Sheet sheet) {
		if(ArrayUtils.isEmpty(this.widths))
			return ;
		for(int i=0; i<widths.length; i++){
			if(widths[i] > 0){
				sheet.setColumnWidth(i, widths[i] * 256);
			}
		}
	}
	
	/**
	 * 创建excel表头
	 */
	private void createHead(Sheet sheet, Workbook wb) {
		if(ArrayUtils.isEmpty(this.header)){
			return;
		}
		// 字体加粗
		Font font = wb.createFont();
		font.setBold(true);
		CellStyle style = wb.createCellStyle();
		style.setFont(font);
		
		Row headerRow = sheet.createRow(0);
		for (int i = 0; i < header.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(header[i]);
			cell.setCellStyle(style);
		}
	}
	
	/**
	 * 创建表内容 
	 */
	private void createBody(List<?> subList, Sheet sheet, int offset) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		if(CollectionUtils.isEmpty(subList)){
			return;
		}
		for(int i=0; i<subList.size(); i++){
			Row row = sheet.createRow(i + offset);
			createBodyCell(subList.get(i), row);
		}
	}
	/**
	 * 创建单元格内容 
	 */
	private void createBodyCell(Object rowObj, Row row) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		if(ArrayUtils.isEmpty(this.columns)){
			return;
		}
		for (int i = 0; i < columns.length; i++) {
			
			Object value = PropertyUtils.getProperty(rowObj, columns[i]);
			
			if (value != null) {
				Cell cell = row.createCell(i);
				//调用格式化
				if(this.formater!=null){
					String fmtValue = this.formater.format(columns[i], value);
					if(StringUtils.isNotBlank(fmtValue)){
						cell.setCellValue(fmtValue);
					}else{
						setCellValue(cell, value);
					}
				}else{
					setCellValue(cell, value);
				}
			}
		}
	}
	
	private void setCellValue(Cell cell, Object value){
		Class<?> clzz = value.getClass();
		if(clzz == String.class){
			cell.setCellValue((String)value);
			
		}else if(clzz == BigDecimal.class){
			cell.setCellValue(((BigDecimal)value).doubleValue());
			
		}else{
			cell.setCellValue(String.valueOf(value));
		}
	}
	
	
	/**
	 * 输出excel 
	 */
	private void write(Workbook wb) throws IOException{
		if(this.outputStream!=null){
			wb.write(this.outputStream);
			
		}else if(this.outputFile!=null){
			try(
				FileOutputStream out = new FileOutputStream(this.outputFile);
					){
				wb.write(out);
			}
		}
	}
}
