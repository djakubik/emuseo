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
package me.uni.emuseo.view.common.helpers;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public abstract class ConfirmationWindow extends Window {
	/**
	 * 
	 */
	private static final long serialVersionUID = 40237784324055506L;
	protected Button confirmButton;
	protected Button cancelButton;
	protected VerticalLayout windowLayout;
	protected HorizontalLayout buttonLayout;

	public ConfirmationWindow(String caption, String info) {
		super(caption);
		setSizeUndefined();
		setResizable(false);
		windowLayout = new VerticalLayout();

		Label infoLabel = new Label(info);
		buildButtons();

		windowLayout.addComponent(infoLabel);
		windowLayout.addComponent(buttonLayout);
		windowLayout.setMargin(true);
		setContent(windowLayout);
		setModal(true);
	}

	private void buildButtons() {
		ClickListener clickListener = new ClickListener() {

			private static final long serialVersionUID = 5320702365033343873L;

			@Override
			public void buttonClick(ClickEvent event) {
				Button button = event.getButton();
				if (button.equals(confirmButton)) {
					confirm();
				} else if (button.equals(cancelButton)) {
					onCancel();
				}
			}
		};
		buttonLayout = new HorizontalLayout();
		confirmButton = new Button("Tak");
		confirmButton.addStyleName("primary");
		confirmButton.addClickListener(clickListener);
		cancelButton = new Button("Nie");
		cancelButton.addStyleName("primary");
		cancelButton.addClickListener(clickListener);
		buttonLayout.addComponent(confirmButton);
		buttonLayout.addComponent(cancelButton);
		buttonLayout.setSpacing(true);
		buttonLayout.addStyleName("form-buttons");
	}

	@Override
	public void close() {
		onCancel();
	}

	protected void onCancel() {
		super.close();
	}

	protected void confirm() {
		if (onConfirm()) {
			super.close();
		}
	}

	protected boolean onConfirm() {
		return true;
	};

}
