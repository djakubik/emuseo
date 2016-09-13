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
package me.uni.emuseo.view.settings;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;

import me.uni.emuseo.EMuseoUtil;
import me.uni.emuseo.model.users.UserDetailsDTO;
import me.uni.emuseo.service.AuthManager;
import me.uni.emuseo.view.common.ExpandingPanel;
import me.uni.emuseo.view.common.form.FormBuilder;
import me.uni.emuseo.view.common.form.FormWithButtonsLayout;
import me.uni.emuseo.view.settings.form.UserDetailsFormLayout;

public class SettingsView extends ExpandingPanel implements View {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1336543483525792423L;
	private AuthManager authManager;

	public SettingsView() {
		setCaption("Ustawienia");
		authManager = EMuseoUtil.getAppContext().getBean(AuthManager.class);
		UserDetailsDTO userDetails = authManager.getUserDetails();

		FormWithButtonsLayout<UserDetailsDTO> mainLayout = new FormWithButtonsLayout<UserDetailsDTO>(userDetails) {

			private static final long serialVersionUID = 123523643637L;

			@Override
			protected FormBuilder<UserDetailsDTO> buildForm(UserDetailsDTO bean) {
				return new UserDetailsFormLayout(bean);
			}

			@Override
			protected void onSave(UserDetailsDTO bean) {
				authManager.setUserDetails(bean);
				refreshTheme();
			}

			private void refreshTheme() {
				UserDetailsDTO userDetails = authManager.getUserDetails();
				EMuseoUtil.refreshTheme(userDetails);
			}
		};

		setContent(mainLayout);
		setExpanded(true);
	}

	@Override
	public void enter(ViewChangeEvent event) {
	}

}
