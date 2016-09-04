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

import java.util.Arrays;
import java.util.List;

import me.uni.emuseo.model.users.UserType;

public class ViewPermission {

	private List<String> adminViews;
	private List<String> modViews;
	private List<String> userViews;

	public ViewPermission() {
		adminViews = Arrays.asList(Permissions.MENU_VIEW, Permissions.MENU_USERS_VIEW, Permissions.MENU_EXHIBIT_VIEW,
				Permissions.MENU_CATEGORIES_VIEW, Permissions.MENU_RESOURCES_VIEW, Permissions.MENU_MY_ACCOUNT_VIEW,
				Permissions.MENU_SETTINGS_VIEW);
		modViews = Arrays.asList(Permissions.MENU_VIEW, Permissions.MENU_EXHIBIT_VIEW, Permissions.MENU_CATEGORIES_VIEW,
				Permissions.MENU_MY_ACCOUNT_VIEW, Permissions.MENU_SETTINGS_VIEW);
		userViews = Arrays.asList(Permissions.MENU_VIEW, Permissions.MENU_EXHIBIT_VIEW,
				Permissions.MENU_MY_ACCOUNT_VIEW, Permissions.MENU_SETTINGS_VIEW);
	}

	public boolean isAuthorizedTo(String authority, String viewName, String parameters) {
		String desired = viewName + ((parameters == null || parameters.isEmpty()) ? "" : "/" + parameters);
		return isAuthorizedTo(authority, desired);
	}

	public boolean isAuthorizedTo(String authority, String fullViewName) {
		UserType userType = UserType.valueOf(authority);
		if (userType == null) {
			return false;
		}
		switch (userType) {
		case ADMINISTRATOR:
			return adminViews.contains(fullViewName);
		case MODERATOR:
			return modViews.contains(fullViewName);
		case USER:
			return userViews.contains(fullViewName);
		default:
			break;
		}
		return false;
	}
}
