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
package me.uni.emuseo;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.ui.SingleComponentContainer;
import com.vaadin.ui.UI;

import me.uni.emuseo.service.AuthManager;
import me.uni.emuseo.service.Permissions;

public final class EMuseoNavigator extends Navigator {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3630835193893311802L;
	protected AuthManager authenticationManager;

	public EMuseoNavigator(UI ui, SingleComponentContainer container, AuthManager authenticationManager) {
		super(ui, container);
		this.authenticationManager = authenticationManager;
	}

	@Override
	protected void navigateTo(View view, String viewName, String parameters) {
		if (view == null || viewName == null) {
			return;
		}
		boolean authorized = isAuthorizedToView(viewName, parameters);
		if (!authorized) {
			Navigator navigator = UI.getCurrent().getNavigator();
			authorized = authenticationManager.isAuthorizedTo(Permissions.MENU_VIEW);
			if (authorized) {
				navigator.navigateTo(Permissions.MENU_VIEW);
			} else {
				navigator.navigateTo(Permissions.LOGIN_VIEW);
			}
			return;
		}
		super.navigateTo(view, viewName, parameters);
	}

	protected boolean isAuthorizedToView(String viewName, String parameters) {
		boolean authorized = false;

		if (viewName.isEmpty()) {
			authorized = true;
		} else {
			authorized = authenticationManager.isAuthorizedTo(viewName, parameters);
		}
		return authorized;
	}
}