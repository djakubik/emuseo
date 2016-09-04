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
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import me.uni.emuseo.EMuseoUtil;
import me.uni.emuseo.exception.PasswordChangeException;
import me.uni.emuseo.model.users.PasswordDTO;
import me.uni.emuseo.model.users.UserDTO;
import me.uni.emuseo.service.AuthManager;
import me.uni.emuseo.service.UserService;
import me.uni.emuseo.view.common.ExpandingPanel;
import me.uni.emuseo.view.common.form.FormBuilder;
import me.uni.emuseo.view.common.form.FormWithButtonsLayout;
import me.uni.emuseo.view.common.form.InvalidBeanException;
import me.uni.emuseo.view.settings.form.MyAccountFormLayout;
import me.uni.emuseo.view.settings.password.PasswordPopUpWindow;

public class MyAccountView extends VerticalLayout implements View {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1336543483525792809L;
	private AuthManager authManager;
	private UserService userService;

	public MyAccountView() {

		authManager = EMuseoUtil.getAppContext().getBean(AuthManager.class);
		userService = EMuseoUtil.getAppContext().getBean(UserService.class);

		final Long userId = authManager.getLoggedUserId();
		UserDTO user = userService.getUser(userId);

		FormWithButtonsLayout<UserDTO> formLayout = new FormWithButtonsLayout<UserDTO>(user) {

			private static final long serialVersionUID = -1826989504302110056L;

			@Override
			protected void onSave(UserDTO bean) {
				userService.editUser(bean);
			}

			@Override
			protected FormBuilder<UserDTO> buildForm(UserDTO bean) {
				return new MyAccountFormLayout(bean);
			}
		};

		ExpandingPanel myAccountPanel = new ExpandingPanel();
		myAccountPanel.setCaption("Moje dane");
		myAccountPanel.setContent(formLayout);
		myAccountPanel.setExpanded(true);

		Button addButton = new Button("Zmień hasło");
		addButton.setIcon(FontAwesome.LOCK);
		addButton.addStyleName("emuseo-button-icon");
		addButton.addStyleName("emuseo-button-margin");
		addButton.setWidth(100, Unit.PERCENTAGE);

		addComponent(myAccountPanel);
		addComponent(addButton);

//		setComponentAlignment(formLayout, Alignment.MIDDLE_CENTER);

		addButton.addClickListener(new ClickListener() {

			private static final long serialVersionUID = -263205007196895260L;

			@Override
			public void buttonClick(ClickEvent event) {
				PasswordPopUpWindow exhibitPopUpWidow = new PasswordPopUpWindow("Zmień hasło") {
					private static final long serialVersionUID = 3776311059670953583L;

					@Override
					protected boolean onSave(PasswordDTO bean) throws InvalidBeanException {
						try {
							userService.changePassword(userId, bean);
							return true;
						} catch (PasswordChangeException e) {
							new Notification("Błąd", e.getReason(), Type.ERROR_MESSAGE, true).show(Page.getCurrent());
						}
						return false;
					}
				};
				UI.getCurrent().addWindow(exhibitPopUpWidow);
			}
		});
	}

	@Override
	public void enter(ViewChangeEvent event) {
	}

}
