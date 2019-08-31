package io.github.pleuvoir.manager.common.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CollectionUtil {

	public static boolean isEmpty(Collection<?> collec){
		return collec==null || collec.isEmpty();
	}
	
	public static boolean isEmpty(List<?> ls){
		return ls==null || ls.isEmpty();
	}
	
	public static boolean isEmpty(Set<?> set){
		return set==null || set.isEmpty();
	}
	
	public static boolean isEmpty(Map<?,?> map){
		return map==null || map.isEmpty();
	}
	
	public static boolean isNotEmpty(Collection<?> collec){
		return !isEmpty(collec);
	}
	
	public static boolean isNotEmpty(List<?> ls){
		return !isEmpty(ls);
	}
	
	public static boolean isNotEmpty(Set<?> set){
		return !isEmpty(set);
	}
	
	public static boolean isNotEmpty(Map<?,?> map){
		return !isEmpty(map);
	}
	
	
	public static <T> List<T> ofList(T elem){
		List<T> list = new ArrayList<>();
		list.add(elem);
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> List<T> ofList(T...elems){
		List<T> list = new ArrayList<>();
		for(T elem : elems){
			list.add(elem);
		}
		return list;
	}
}
