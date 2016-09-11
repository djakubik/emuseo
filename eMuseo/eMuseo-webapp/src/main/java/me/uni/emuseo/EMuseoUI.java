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

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import me.uni.emuseo.service.AuthManager;
import me.uni.emuseo.service.Permissions;
import me.uni.emuseo.view.login.LoginView;
import me.uni.emuseo.view.menu.MenuView;

import org.springframework.context.ApplicationContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.WrappedHttpSession;
import com.vaadin.server.WrappedSession;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;

@Theme(EMuseoUtil.DEFAULT_THEME)
@PreserveOnRefresh
public class EMuseoUI extends UI {

	private static final String PAGE_TITLE = "eMuseo";
	private static final long serialVersionUID = -297303412065890467L;

	// -> http://localhost:8080/eMuseo/
	private Navigator navigator;
	private ApplicationContext applicationContext;
	private SecurityContext securityContext;
	private AuthManager authenticationManager;

	@Override
	protected void init(VaadinRequest request) {

		WrappedSession session = request.getWrappedSession();
		HttpSession httpSession = ((WrappedHttpSession) session).getHttpSession();
		ServletContext servletContext = httpSession.getServletContext();
		applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
		securityContext = SecurityContextHolder.getContext();
		authenticationManager = applicationContext.getBean(AuthManager.class);

		getPage().setTitle(PAGE_TITLE);
		Responsive.makeResponsive(this);
		addStyleName(ValoTheme.UI_WITH_MENU);

		navigator = new Navigator(this, this) {

			/**
			 * 
			 */
			private static final long serialVersionUID = -3630835193893311802L;

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

		};
		navigator.addView(Permissions.LOGIN_VIEW, LoginView.class);
		navigator.addView(Permissions.MENU_VIEW, MenuView.class);
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public SecurityContext getSecurityContext() {
		return securityContext;
	}

	private boolean isAuthorizedToView(String viewName, String parameters) {
		boolean authorized = false;

		if (viewName.isEmpty()) {
			authorized = true;
		} else {
			authorized = authenticationManager.isAuthorizedTo(viewName, parameters);
		}
		return authorized;
	}

}
