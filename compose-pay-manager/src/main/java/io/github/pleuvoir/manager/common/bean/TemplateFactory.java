package io.github.pleuvoir.manager.common.bean;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.output.StringBuilderWriter;
import org.springframework.core.io.Resource;

import freemarker.log.Logger;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

/**
 * 模板处理工厂，解析ftl模板或者ftl格式字符串
 * @author abeir
 *
 */
@SuppressWarnings("deprecation")
public class TemplateFactory {
	public static Configuration config = new Configuration(Configuration.VERSION_2_3_23);
	static {
		try {
			Logger.selectLoggerLibrary(Logger.LIBRARY_SLF4J);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		config.setDefaultEncoding(StandardCharsets.UTF_8.name());
		config.setOutputEncoding(StandardCharsets.UTF_8.name());
	}

	/**
	 * 设置模板的根目录
	 * @param ftlLocation 模板路径
	 * @throws IOException
	 */
	public void setLocation(Resource ftlLocation) throws IOException {
		config.setDirectoryForTemplateLoading(ftlLocation.getFile());
	}
	
	/**
	 * 获取操作模板的对象，该方法必须首先设置{{@link #setLocation(Resource)}
	 * @param name 模板名
	 * @return
	 * @throws TemplateNotFoundException
	 * @throws IOException
	 */
	public Template getTemplate(String name) throws TemplateNotFoundException, IOException {
		return config.getTemplate(name, StandardCharsets.UTF_8.name());
	}
	
	/**
	 * 处理指定的模板，将解析后的内容返回到流中，该方法必须首先设置{{@link #setLocation(Resource)}
	 * @param name 模板名
	 * @param dataModel 模板内变量，可以是一个Map<String,Object>或者JavaBean
	 * @param out 输出解析后的模板内容
	 * @throws TemplateException
	 * @throws IOException
	 */
	public void process(String name, Object dataModel, Writer out) throws TemplateException, IOException {
		getTemplate(name).process(dataModel, out);
	}
	
	/**
	 * 处理指定的模板，将解析后的内容转换为字符串，该方法必须首先设置{{@link #setLocation(Resource)}
	 * @param name 模板名
	 * @param dataModel 模板内变量，可以是一个Map<String,Object>或者JavaBean
	 * @return 返回解析后的字符串
	 * @throws TemplateException
	 * @throws IOException
	 */
	public String processToString(String name, Object dataModel) throws TemplateException, IOException {
		StringBuilderWriter writer = new StringBuilderWriter();
		getTemplate(name).process(dataModel, writer);
		return writer.toString();
	}
	/**
	 * 处理字符串内容，返回解析后的字符串
	 * @param ftlContent 待解析的原始字符串
	 * @param dataModel 字符串内变量，可以是一个Map<String,Object>或者JavaBean
	 * @return 返回解析后的字符串
	 * @throws TemplateException
	 * @throws IOException
	 */
	public String stringToString(String ftlContent, Object dataModel) throws TemplateException, IOException {
		Template template = new Template(null, new StringReader(ftlContent), config);
		StringBuilderWriter writer = new StringBuilderWriter();
		template.process(dataModel, writer);
		return writer.toString();
	}
	/**
	 * 处理字符串内容，返回解析后的字符串
	 * @param input 待解析的原始字符串流
	 * @param dataModel 字符串内变量，可以是一个Map<String,Object>或者JavaBean
	 * @return 返回解析后的字符串
	 * @throws IOException
	 * @throws TemplateException
	 */
	public String readerToString(Reader input, Object dataModel) throws IOException, TemplateException {
		Template template = new Template(null, input, config);
		StringBuilderWriter writer = new StringBuilderWriter();
		template.process(dataModel, writer);
		return writer.toString();
	}
	/**
	 * 处理字符串内容，将解析后的字符串输出到流中
	 * @param ftlContent 待解析的原始字符串
	 * @param dataModel 字符串内变量，可以是一个Map<String,Object>或者JavaBean
	 * @param out 解析后的输出的字符串流
	 * @throws IOException
	 * @throws TemplateException
	 */
	public void stringToWriter(String ftlContent, Object dataModel, Writer out) throws IOException, TemplateException {
		Template template = new Template(null, new StringReader(ftlContent), config);
		template.process(dataModel, out);
	}
	/**
	 * 处理字符串内容
	 * @param input 待解析的原始字符串流
	 * @param dataModel 字符串内变量，可以是一个Map<String,Object>或者JavaBean
	 * @param out 解析后的输出的字符串流
	 * @throws IOException
	 * @throws TemplateException
	 */
	public void readerToWriter(Reader input, Object dataModel, Writer out) throws IOException, TemplateException {
		Template template = new Template(null, input, config);
		template.process(dataModel, out);
	}
	
}
