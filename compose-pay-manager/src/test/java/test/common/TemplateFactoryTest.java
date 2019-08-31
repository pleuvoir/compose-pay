package test.common;

import freemarker.template.TemplateException;
import io.github.pleuvoir.manager.common.bean.TemplateFactory;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import test.BaseTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TemplateFactoryTest extends BaseTest{
	
	@Autowired
	private TemplateFactory templateFactory ;
	
	@Test
	public void test() throws TemplateException, IOException {
		Map<String,Object> m = new HashMap<>();
		m.put("name", "hahaha");
		
		List<String> ls = new ArrayList<>();
		ls.add("000");
		ls.add("111");
		m.put("nameList", ls);
		
		
		String s = templateFactory.processToString("aaa.ftl", m);
		System.out.println(StringUtils.repeat(">", 30));
		System.out.println(s);
	}
}
