package io.github.pleuvoir.manager.common.taglib;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

import io.github.pleuvoir.manager.common.Const;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.DynamicAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 分页标签
 * @author abeir
 *
 */
public class PaginationTag extends RequestContextAwareTag implements
		DynamicAttributes {
	private static final long serialVersionUID = 1264342546463546393L;
	//页面跳转的URL
	private String url;
	//总记录数
	private Integer total = 0;
	//是否显示完整的分页信息
	private String full = "true";
	//分页参数名
	private String pageName = "page";
	
	private Map<String, String> attrs = new HashMap<>();
	
	protected int doStartTagInternal() throws Exception {

		JspWriter out = this.pageContext.getOut();
		out.print(createTag());
		return 6;
	}

	public void setDynamicAttribute(String uri, String localName, Object value)
			throws JspException {
		this.attrs.put(localName, String.valueOf(value));
	}

	private String getUrlWithParams() {
		Map<String, String[]> params = this.pageContext.getRequest()
				.getParameterMap();
		StringBuffer buffer = new StringBuffer(this.url);
		buffer.append("?");
		for (Map.Entry<String, String[]> entry : params.entrySet()) {
			String name = (String) entry.getKey();
			if (!pageName.equals(name)) {
				String[] values = (String[]) entry.getValue();
				for (String val : values)
					buffer.append(name + "=" + val + "&");
			}
		}
		buffer.append(pageName);
		buffer.append("=");
		return  buffer.toString();
	}

	private int getCurrentPage(int pageCount) {
		String page = this.pageContext.getRequest().getParameter(pageName);
		if (StringUtils.isBlank(page)) {
			return 1;
		}
		int p = 1;
		try {
			p = Integer.parseInt(page);
			if (p < 1)
				p = 1;
			if (p > pageCount)
				p = pageCount;
		} catch (NumberFormatException e) {
			p = 1;
		}
		return p;
	}

	private int getPageCount() {
		int defaultRows = Const.DEFAULT_PAGE_ROWS;
		if (this.total.intValue() == 0) {
			return 1;
		}
		if (this.total.intValue() % defaultRows == 0) {
			return this.total.intValue() / defaultRows;
		}
		return this.total.intValue() / defaultRows + 1;
	}

	private boolean isFull() {
		if (("true".equals(this.full)) || ("yes".equals(this.full))) {
			return true;
		}
		return false;
	}

	private boolean hasPrevPage(int currPage, int pageCount) {
		if (pageCount <= 1) {
			return false;
		}
		return (currPage > 1) && (currPage <= pageCount);
	}

	private boolean hasNextPage(int currPage, int pageCount) {
		if (pageCount <= 1) {
			return false;
		}
		return currPage < pageCount;
	}

	private boolean hasFirstPage(int currPage) {
		return currPage > 1;
	}

	private boolean hasLastPage(int currPage, int pageCount) {
		return currPage < pageCount;
	}

	private int getPrevPage(int currPage) {
		currPage--;
		if (currPage < 1)
			currPage = 1;
		return currPage;
	}

	private int getNextPage(int currPage, int pageCount) {
		currPage++;
		if (currPage > pageCount) {
			currPage = pageCount;
		}
		return currPage;
	}

	private ArrayList<Integer> getPageNums(int currPage, int pageCount) {
		ArrayList<Integer> nums = new ArrayList<Integer>();

		if (pageCount <= 5) {
			for (int i = 0; i < pageCount; i++)
				nums.add(Integer.valueOf(i + 1));
		} else {
			int first = currPage - 3;
			int last = currPage + 2;
			if (first < 1) {
				first = 0;
				last = 5;
			} else if (last > pageCount) {
				first = pageCount - 5;
				last = pageCount;
			}
			for (int i = first; i < last; i++) {
				nums.add(Integer.valueOf(i + 1));
			}
		}
		return nums;
	}

	private String createPageInfoDiv(int currPage, int pageCount) {
		return String.format(
				"<div>共%s条&nbsp;&nbsp;共%s页&nbsp;&nbsp;第%s页 </div>",
				new Object[] { Integer.valueOf(total),Integer.valueOf(pageCount),Integer.valueOf(currPage)});
	}

	private String createEnableLi(String href, Object inner, String cls) {
		if (href == null) {
			href = "javascript:;";
		}
		href = StringUtils.replace(href, "'", "&apos;");
		href = StringUtils.replace(href, "\"", "&quot;");
		if (cls == null) {
			cls = "";
		}
		return String.format("<li class='%s'><a href=\"%s\">%s</a></li>",
				new Object[] { cls, href, inner });
	}
	
	private String createDisableLi(String href, Object inner, String cls) {
		if (href == null) {
			href = "javascript:;";
		}
		href = StringUtils.replace(href, "'", "&apos;");
		href = StringUtils.replace(href, "\"", "&quot;");
		if (cls == null) {
			cls = "";
		}
		return String.format("<li class='%s'><span>%s</span></li>",
				new Object[] { cls, inner });
	}

	private String createDivAttrs() {
		StringBuffer buffer = new StringBuffer();
		if (!this.attrs.containsKey("class")) {
			this.attrs.put("class", "pull-right");
		}
		for (Map.Entry<String, String> entry : this.attrs.entrySet()) {
			buffer.append(" ");
			buffer.append((String) entry.getKey());
			buffer.append("=");
			buffer.append("'");
			buffer.append((String) entry.getValue());
			buffer.append("'");
		}
		return buffer.toString();
	}

	private String createTag() {
		String href = getUrlWithParams();
		int pageCount = getPageCount();
		int currPage = getCurrentPage(pageCount);

		int prevPage = getPrevPage(currPage);
		int nextPage = getNextPage(currPage, pageCount);

		StringBuffer buffer = new StringBuffer();

		buffer.append("<div class='row'>");
		buffer.append("<div class='col-md-5'>");
		if (isFull()) {
			buffer.append(createPageInfoDiv(currPage, pageCount));
		}
		buffer.append("</div>");

		buffer.append("<div class='col-md-7'>");
		buffer.append("<div");
		buffer.append(createDivAttrs());
		buffer.append(">");
		buffer.append("<ul class='pagination'>");

		if (hasFirstPage(currPage))
			buffer.append(createEnableLi(href + 1, "首页", null));
		else {
			buffer.append(createDisableLi(null, "首页", "previous disabled"));
		}

		if (hasPrevPage(currPage, pageCount))
			buffer.append(createEnableLi(href + prevPage, "上一页", null));
		else {
			buffer.append(createDisableLi(null, "上一页", "disabled"));
		}

		ArrayList<?> pageNums = getPageNums(currPage, pageCount);
		for (Iterator<?> localIterator = pageNums.iterator(); localIterator
				.hasNext();) {
			int i = ((Integer) localIterator.next()).intValue();
			if (currPage == i)
				buffer.append(createDisableLi(href + i, Integer.valueOf(i), "active"));
			else {
				buffer.append(createEnableLi(href + i, Integer.valueOf(i), null));
			}
		}

		if (hasNextPage(currPage, pageCount))
			buffer.append(createEnableLi(href + nextPage, "下一页", null));
		else {
			buffer.append(createDisableLi(null, "下一页", "disabled"));
		}

		if (hasLastPage(currPage, pageCount))
			buffer.append(createEnableLi(href + pageCount, "尾页", null));
		else {
			buffer.append(createDisableLi(null, "尾页", "next disabled"));
		}

		buffer.append("</ul>");
		buffer.append("</div>");
		buffer.append("</div>");

		buffer.append("</div>");
		return buffer.toString();
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = (this.pageContext.getServletContext().getContextPath() + url);
	}

	public Integer getTotal() {
		return this.total;
	}

	public void setTotal(Integer total) {
		if(null == total){
			this.total = 0;
		}else{
			this.total = total;
		}
	}

	public String getFull() {
		return this.full;
	}

	public void setFull(String full) {
		this.full = full;
	}

	public Map<String, String> getAttrs() {
		return this.attrs;
	}

	public void setAttrs(Map<String, String> attrs) {
		this.attrs = attrs;
	}

	public String getPageName() {
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}
}
