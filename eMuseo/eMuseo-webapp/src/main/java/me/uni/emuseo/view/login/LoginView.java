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

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class LoginView extends VerticalLayout implements View {

	private LoginLayout loginLayout;

	public LoginView() {
		this.setSizeFull();
		Label titleLabel = new Label("<h1>e<strong>Museo</strong></h1>", ContentMode.HTML);
		loginLayout = new LoginLayout();
		addComponent(titleLabel);
		addComponent(loginLayout);
		titleLabel.setWidth(150, Unit.PIXELS);
		setExpandRatio(titleLabel, 0);
		setExpandRatio(loginLayout, 1);
		setComponentAlignment(titleLabel, Alignment.BOTTOM_CENTER);
		setComponentAlignment(loginLayout, Alignment.TOP_CENTER);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -3870704020610041592L;

	@Override
	public void enter(ViewChangeEvent event) {
	}

}
