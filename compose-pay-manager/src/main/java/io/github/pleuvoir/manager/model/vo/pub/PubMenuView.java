package io.github.pleuvoir.manager.model.vo.pub;

import java.io.Serializable;
import java.util.List;

import io.github.pleuvoir.manager.model.po.pub.PubMenuPO;

/**
 * 菜单的视图模型
 * @author LaoShu
 *
 */
public class PubMenuView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3938906170751889528L;
	
	private List<PubMenuView> children;
	
	private PubMenuPO menu;

	public List<PubMenuView> getChildren() {
		return children;
	}

	public void setChildren(List<PubMenuView> children) {
		this.children = children;
	}

	public PubMenuPO getMenu() {
		return menu;
	}

	public void setMenu(PubMenuPO menu) {
		this.menu = menu;
	}
	
}
