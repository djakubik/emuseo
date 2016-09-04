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
package me.uni.emuseo.view.menu;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class MenuStartLayout extends VerticalLayout implements View {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6954429165037546137L;

	public MenuStartLayout() {
		Label welcome = new Label("Witaj w systemie");
		Label systemName = new Label("<h1>e<strong>Museo</strong></h1>", ContentMode.HTML);
		addComponent(welcome);
		addComponent(systemName);

		setComponentAlignment(welcome, Alignment.BOTTOM_CENTER);
		setComponentAlignment(systemName, Alignment.TOP_CENTER);

		welcome.setSizeUndefined();
		systemName.setSizeUndefined();
		setWidth(100, Unit.PERCENTAGE);
		setHeight(60, Unit.PERCENTAGE);

	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}
}
