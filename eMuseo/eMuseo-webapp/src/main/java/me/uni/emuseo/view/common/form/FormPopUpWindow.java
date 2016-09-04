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

import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public abstract class FormPopUpWindow<T> extends Window {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4023778432405550695L;
	protected FormBuilder<T> formBuilder;
	protected Button saveButton;
	protected Button cancelButton;
	protected VerticalLayout windowLayout;
	protected HorizontalLayout buttonLayout;

	public FormPopUpWindow(T bean) {
		setSizeUndefined();
		setResizable(false);
		windowLayout = new VerticalLayout();

		formBuilder = createForm(bean);
		buildButtons();

		windowLayout.setMargin(true);
		if (formBuilder != null) {
			windowLayout.addComponent(formBuilder.getContent());
			windowLayout.addComponent(buttonLayout);
		}
		setContent(windowLayout);
		setModal(true);
	}
	
	public FormPopUpWindow(T bean, String caption) {
		this(bean);
		setCaption(caption);
	}

	private void buildButtons() {
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
		buttonLayout = new HorizontalLayout();
		saveButton = new Button();
		saveButton.addStyleName("primary");
		saveButton.setIcon(FontAwesome.SAVE);
		saveButton.addClickListener(clickListener);
		cancelButton = new Button();
		cancelButton.addStyleName("primary");
		cancelButton.setIcon(FontAwesome.UNDO);
		cancelButton.addClickListener(clickListener);
		buttonLayout.addComponent(saveButton);
		buttonLayout.addComponent(cancelButton);
		buttonLayout.setSpacing(true);
		buttonLayout.addStyleName("form-buttons");
	}

	@Override
	public void close() {
		onCancel();
	}

	protected void onCancel() {
		formBuilder.discard();
		super.close();
	}

	protected void onPreSave() {
		try {
			formBuilder.commit();
			T bean = formBuilder.getBean();
			if (onSave(bean)) {
				super.close();
			}
		} catch (CommitException e) {
			e.printStackTrace();
		} catch (InvalidBeanException e) {
			e.printStackTrace();
		}
	}
	
	protected abstract FormBuilder<T> createForm(T bean);

	protected abstract boolean onSave(T bean) throws InvalidBeanException ;

}
