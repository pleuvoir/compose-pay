package io.github.pleuvoir.manager.model;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import io.github.pleuvoir.manager.model.dto.AbstractFormPageDTO;

import org.apache.commons.lang3.StringUtils;

/**
 * 修改Pagination属性值
 */
public class PageCondition extends Pagination {

	private static final long serialVersionUID = 8326886038782148041L;
	
	public PageCondition() {
		super();
	}
	
	public static PageCondition create(Integer page, Integer rows) {
    	PageCondition pageCondition = new PageCondition();
    	if(page==null) {
    		pageCondition.setCurrent(1);
    	}else {
    		pageCondition.setCurrent(page);
    	}
    	if(rows==null) {
    		pageCondition.setSize(20);
    	}else {
    		pageCondition.setSize(rows);
    	}
    	return pageCondition;
    }
	
	
	public static PageCondition create(AbstractFormPageDTO form) {
		PageCondition pageCondition = new PageCondition();
		if(form.getPage()==null) {
			pageCondition.setCurrent(1);
		}else {
			pageCondition.setCurrent(form.getPage());
		}
		if(form.getRows()==null) {
			pageCondition.setSize(20);
		}else {
			pageCondition.setSize(form.getRows());
		}
		//按指定字段排序
		if(StringUtils.isNotBlank(form.getSidx())) {
			pageCondition.setOpenSort(true);
			pageCondition.setOrderByField(form.getSidx());
			pageCondition.setAsc(StringUtils.equalsIgnoreCase(form.getSord(), "asc") ? true : false);
		}
    	return pageCondition;
	}

}
