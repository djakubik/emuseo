/*******************************************************************************
 * Copyright (c) 2016 Darian Jakubik.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v2.1
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Darian Jakubik - initial API and implementation
 ******************************************************************************/
package me.uni.emuseo.service;

public interface Permissions {
	public static final String LOGIN_VIEW = "";
	public static final String MENU_VIEW = "menu";

	public static final String USERS_VIEW = "users";
	public static final String EXHIBITS_VIEW = "exhibits";
	public static final String CATEGORIES_VIEW = "categories";
	public static final String RESOURCES_VIEW = "resources";
	public static final String MY_ACCOUNT_VIEW = "myAccountView";
	public static final String SETTINGS_VIEW = "settingsView";

	public static final String MENU_USERS_VIEW = MENU_VIEW + "/" + USERS_VIEW;
	public static final String MENU_EXHIBIT_VIEW = MENU_VIEW + "/" + EXHIBITS_VIEW;
	public static final String MENU_CATEGORIES_VIEW = MENU_VIEW + "/" + CATEGORIES_VIEW;
	public static final String MENU_RESOURCES_VIEW = MENU_VIEW + "/" + RESOURCES_VIEW;
	public static final String MENU_MY_ACCOUNT_VIEW = MENU_VIEW + "/" + MY_ACCOUNT_VIEW;
	public static final String MENU_SETTINGS_VIEW = MENU_VIEW + "/" + SETTINGS_VIEW;

}
