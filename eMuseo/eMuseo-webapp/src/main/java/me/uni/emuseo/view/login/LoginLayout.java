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
package me.uni.emuseo.view.login;

import me.uni.emuseo.EMuseoUtil;
import me.uni.emuseo.service.AuthManager;

import org.springframework.security.core.AuthenticationException;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class LoginLayout extends VerticalLayout {
	
	private static final long serialVersionUID = -5389697616798959563L;
	
	private TextField loginField = new TextField("Nazwa użytkownika");
	private PasswordField passField = new PasswordField("Hasło");
	private Button logButton = new Button("Zaloguj");

	public LoginLayout() {
		
		loginField.setWidth(306, Unit.PIXELS);
		loginField.setIcon(FontAwesome.USER);
		loginField.addStyleName("inline-icon");
		passField.setWidth(306, Unit.PIXELS);
		passField.setIcon(FontAwesome.LOCK);
		passField.addStyleName("inline-icon");
		
		addComponent(loginField);
		addComponent(passField);
		addComponent(logButton);
		
		setComponentAlignment(loginField, Alignment.MIDDLE_CENTER);
		setComponentAlignment(passField, Alignment.MIDDLE_CENTER);
		setComponentAlignment(logButton, Alignment.BOTTOM_RIGHT);
		
		buildStyles();
		final AuthManager authManager = EMuseoUtil.getAppContext().getBean(AuthManager.class);
		logButton.addClickListener(new Button.ClickListener() {

			private static final long serialVersionUID = 1324235262L;

			@Override
			public void buttonClick(ClickEvent event) {
				try {
					authManager.login(loginField.getValue(), passField.getValue());
				} catch (AuthenticationException e) {
					Notification.show("Niepoprawny login lub hasło");
				}
			}
		});
		logButton.setClickShortcut(KeyCode.ENTER);

	}

	private void buildStyles() {
		setWidth(400, Unit.PIXELS);
		addStyleName("layout-border");
		setSpacing(true);
		setMargin(true);
		logButton.addStyleName("primary");
	}
}
