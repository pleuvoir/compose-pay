package io.github.pleuvoir.pay;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import freemarker.template.TemplateException;
import io.github.pleuvoir.sql.script.DBScriptRunner;
import io.github.pleuvoir.sql.tookit.DataModel;

public class Generator  {

	private static Logger logger = LoggerFactory.getLogger(Generator.class);

	@Autowired
	private DBScriptRunner dBScriptRunner;

	public void generateVO(String sql, String voName) throws FileNotFoundException, IOException, TemplateException {
		DataModel dataModel = dBScriptRunner.excute(sql).asDataModel();
		logger.info("根据sql生成 VO 元数据：{}", dataModel.toJSON());
		// 待写入的文件位置
		String file = LazyKit.javaAbsoluteFilePath(voName);
		logger.info("根据sql生成 VO {} ，文件{}", voName, file);
		// 根据 freemark 生成文件
		dataModel.addData("entityName", voName).write("vo.ftl", file);
	}

	public String getAllColumnsBySql(String sql) {
		StringBuffer sb = new StringBuffer();
		dBScriptRunner.excute(sql).getColumnExtendList().forEach(colunm -> {
			sb.append(colunm.getColumnName().toLowerCase()).append(",");
		});
		if (sb.length() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

	public void singleTabelePO(String tableName, String showName)
			throws FileNotFoundException, IOException, TemplateException {
		DataModel dataModel = dBScriptRunner.excuteSingleTableQuery(tableName).asDataModel();
		logger.info("生成单表实体文件元数据：{}", dataModel.toJSON());
		// 实体名称
		String entityName = showName;
		// 待写入的文件位置
		String file = LazyKit.javaAbsoluteFilePath(entityName);
		logger.info("生成单表实体{}，文件{}", entityName, file);
		// 根据 freemark 生成文件
		dataModel.addData("entityName", entityName).addData("tableName", tableName).write("po.ftl", file);
	}


	public void CRUD(String tableName) throws FileNotFoundException, IOException, TemplateException{
		
		// p_pay_type  PayType
		String name = LazyKit.toCapitalizeCamelCase(StringUtils.substringAfter(tableName, "p_"));
		
		DataModel dataModel = dBScriptRunner.excuteSingleTableQuery(tableName).asDataModel();
		dataModel.addData("POName", name + "PO"); //PO名称
		dataModel.addData("tableName", tableName); //表名
		dataModel.addData("name", name);  //PayType这样的
		
		//生成PO
		dataModel.write("crud/po.ftl",  LazyKit.javaAbsoluteFilePath(name + "PO"));	
		
		//生成formDTO
		dataModel.write("crud/formDTO.ftl",  LazyKit.javaAbsoluteFilePath(name + "FormDTO"));	
		
		//生成listVO
		dataModel.write("crud/ListVO.ftl",  LazyKit.javaAbsoluteFilePath(name + "ListVO"));	
		
		//生成DAO
		dataModel.write("crud/dao.ftl",  LazyKit.javaAbsoluteFilePath(name + "Dao"));		
		
		
		//生成MAPPER
		
		String allColumnsBySql = this.getAllColumnsBySql("select * from " + tableName);
		dataModel.addData("allColumnsBySql", allColumnsBySql);
		dataModel.write("crud/Mapper.ftl",  LazyKit.defaultFolder() + name + "Mapper.xml");	
		
		//生成service
		dataModel.write("crud/service.ftl",  LazyKit.javaAbsoluteFilePath(name + "Service"));	
		
		//生成serviceImpl
		dataModel.write("crud/serviceImpl.ftl",  LazyKit.javaAbsoluteFilePath(name + "ServiceImpl"));	
		
		
		//生成controller
		dataModel.write("crud/Controller.ftl",  LazyKit.javaAbsoluteFilePath(name + "Controller"));	
		
		
		//生成页面
		dataModel.write("crud/list.ftl", LazyKit.defaultFolder() + "list.jsp");	
		
		
		//生成权限sql
	}

}
