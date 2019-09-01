package io.github.pleuvoir.lazy.payload;

import java.io.FileNotFoundException;
import java.io.IOException;

import freemarker.template.TemplateException;

public interface Generator {

	
	void CRUD(String tableName, String showName) throws Exception;
	
	
	/**
	 * 生成单表实体文件
	 */
	void singleTabelePO(String tableName,String showName) throws FileNotFoundException, IOException, TemplateException;

	/**
	 * 根据 sql 生成 VO
	 * @param sql	待执行的 sql
	 * @param voName	vo 名称
	 */
	void generateVO(String sql, String voName) throws FileNotFoundException, IOException, TemplateException;
	
	/**
	 * 获取所有的列名，并以 , 分割拼接
	 * @param sql	待执行的 sql
	 * @return	拼接后所有的列名小写
	 */
	String getAllColumnsBySql(String sql) ;
	
	

}
