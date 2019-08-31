package test.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import io.github.pleuvoir.manager.model.po.pub.PubRolePO;

import java.util.*;

public class JsonTest {
	
	public static void main(String[] args) {
		PubRolePO po = new PubRolePO();
		po.setId("123");
		po.setName("qwe");
		System.out.println(JSON.toJSON(po));
		List<PubRolePO> list = new ArrayList<>();
		list.add(po);
		list.add(po);
		System.out.println(JSON.toJSON(list));
		Map<String, PubRolePO> map = new HashMap<>();
		map.put("key", po);
		map.put("key1", po);
		System.out.println(JSON.toJSON(map));
		
		String json = "[{\"id\":1},{\"id\":2},{\"id\":3,\"children\":[{\"id\":4},{\"id\":5}]}]";
		
		JSONArray jarr = JSONArray.parseArray(json);
		for(Iterator<Object> iterator = jarr.iterator(); iterator.hasNext();) {
			JSONObject job = (JSONObject) iterator.next();
			String idStr = job.get("id").toString();
			System.out.println(idStr);
			if(job.get("children")!=null) {
				JSONArray jarr2 = JSONArray.parseArray(job.get("children").toString());
				for(Iterator<Object> iterator2 = jarr2.iterator(); iterator2.hasNext();) {
					JSONObject job2 = (JSONObject) iterator2.next();
					String idStr2 = job2.get("id").toString();
					System.out.println(idStr2);
				}
			}
		}
	}
	
}
