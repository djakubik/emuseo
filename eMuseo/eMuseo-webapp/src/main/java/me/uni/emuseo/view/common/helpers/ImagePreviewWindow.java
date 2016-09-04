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

import com.vaadin.server.ExternalResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Image;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class ImagePreviewWindow extends Window {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4023778432405550696L;

	protected Button cancelButton;
	protected VerticalLayout windowLayout;
	protected HorizontalLayout buttonLayout;

	public ImagePreviewWindow(String url) {
		setSizeUndefined();
		setWidth(400, Unit.PIXELS);
		setHeight(360, Unit.PIXELS);
		windowLayout = new VerticalLayout();

		if (url != null) {
			Image image = new Image();
			image.setSource(new ExternalResource(url));
			image.setSizeFull();
			windowLayout.addComponent(image);
		}

		// buildButtons();
		// windowLayout.addComponent(buttonLayout);
		setContent(windowLayout);
		setModal(true);
	}

	public ImagePreviewWindow(String url, String caption) {
		this(url);
		setCaption(caption);
	}

	protected void buildButtons() {

		cancelButton = new Button();
		cancelButton.addStyleName("primary");
		cancelButton.setIcon(FontAwesome.TIMES);
		cancelButton.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 7610746471261080892L;

			@Override
			public void buttonClick(ClickEvent event) {
				onCancel();
			}

		});

		buttonLayout = new HorizontalLayout();
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

}
