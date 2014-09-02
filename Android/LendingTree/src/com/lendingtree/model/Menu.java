package com.lendingtree.model;

public class Menu {
	
	private String menuTitle;
	private int iconId;
	
	public Menu(String menuTitle, int iconId) {
		this.menuTitle = menuTitle;
		this.iconId = iconId;
	}
	public String getMenuTitle() {
		return menuTitle;
	}
	public void setMenuTitle(String menuTitle) {
		this.menuTitle = menuTitle;
	}
	public int getIconId() {
		return iconId;
	}
	public void setIconId(int iconId) {
		this.iconId = iconId;
	}

}
