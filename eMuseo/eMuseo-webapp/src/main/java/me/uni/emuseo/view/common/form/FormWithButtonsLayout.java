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
package me.uni.emuseo.view.common.form;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

public abstract class FormWithButtonsLayout<T> extends VerticalLayout {

	private static final long serialVersionUID = 205262263499688089L;
	private static final Logger LOG = LoggerFactory.getLogger(FormWithButtonsLayout.class);
	private T bean;
	private FormBuilder<T> formBuilder;

	public FormWithButtonsLayout(T bean) {
		this.bean = bean;
		init();
	}

	protected void init() {
		formBuilder = buildForm(bean);
		addComponent(formBuilder.getContent());
		setComponentAlignment(formBuilder.getContent(), Alignment.MIDDLE_CENTER);

		HorizontalLayout buildButtons = buildButtons();
		addComponent(buildButtons);
	}
	
	protected abstract FormBuilder<T> buildForm(T bean);

	protected HorizontalLayout buildButtons() {

		final Button saveButton = new Button();
		final Button cancelButton = new Button();
		ClickListener clickListener = new ClickListener() {

			private static final long serialVersionUID = 5320702365033343873L;

			@Override
			public void buttonClick(ClickEvent event) {
				Button button = event.getButton();
				if (button.equals(saveButton)) {
					onPreSave();
				} else if (button.equals(cancelButton)) {
					onCancel();
				}
			}
		};
		HorizontalLayout buttonLayout = new HorizontalLayout();

		saveButton.addStyleName("primary");
		saveButton.setIcon(FontAwesome.SAVE);
		saveButton.addClickListener(clickListener);

		cancelButton.addStyleName("primary");
		cancelButton.setIcon(FontAwesome.UNDO);
		cancelButton.addClickListener(clickListener);

		buttonLayout.addComponent(saveButton);
		buttonLayout.addComponent(cancelButton);
		buttonLayout.setSpacing(true);
		buttonLayout.addStyleName("form-buttons");

		return buttonLayout;
	}

	protected void onCancel() {
		formBuilder.discard();
	}

	protected void onPreSave() {
		try {
			formBuilder.commit();
			T bean = formBuilder.getBean();
			onSave(bean);
		} catch (CommitException e) {
			LOG.warn("Commit failed", e);
		}
	}

	protected abstract void onSave(T bean);
}
