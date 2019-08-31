package io.github.pleuvoir.manager.model.dto;

/**
 * form分页参数
 * @author abeir
 *
 */
public abstract class AbstractFormPageDTO {
	
	//当前页
	private Integer page;
	//每页行数
	private Integer rows;
	//排序字段
	private String sidx;
	//升序或降序
	private String sord;

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public String getSidx() {
		return sidx;
	}

	public void setSidx(String sidx) {
		this.sidx = sidx;
	}

	public String getSord() {
		return sord;
	}

	public void setSord(String sord) {
		this.sord = sord;
	}
}
