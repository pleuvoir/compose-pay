package io.github.pleuvoir.manager.model.vo;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 由此对象将page对象转换成json对象，传到前台处理
 * 由于jqgrid框架定义的page对象里面的字段和mybatisplus的不一样
 * 所以这个由这个中间对象来转换
 * @param <T>
 */
public abstract class AbstractJqGridVO<T> {
	//当前页
    private long page;

    //总页数
    private long total;
    
    //总条数
    private long records;

    //包含实际数据的数组
    private List<T> rows = new ArrayList<>();
    
    public AbstractJqGridVO(Pagination pagination){
        this.page = pagination.getCurrent();
        this.records = pagination.getTotal();
        this.total = pagination.getPages();
    }
    
    public AbstractJqGridVO() {
		super();
	}

	/**
     * 增加行数据
     * @param row
     */
    public void add(T row) {
    	rows.add(row);
    }
    
	public long getPage() {
		return page;
	}

	public void setPage(long page) {
		this.page = page;
	}

	public long getRecords() {
		return records;
	}

	public void setRecords(long records) {
		this.records = records;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

}
