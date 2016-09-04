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
package me.uni.emuseo.view.exhibits;

import org.vaadin.easyuploads.ImagePreviewField;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import me.uni.emuseo.model.exhibits.ExhibitDTO;
import me.uni.emuseo.model.resources.ResourceAddEditDTO;
import me.uni.emuseo.view.common.form.InvalidBeanException;

public abstract class ExhibitImageEditWindow extends Window {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4023778432405550695L;

	protected ExhibitDTO bean;
	protected Button saveButton;
	protected Button cancelButton;
	protected VerticalLayout windowLayout;
	protected HorizontalLayout buttonLayout;
	protected ExhibitionImagePreviewField imagePreviewField;

	public ExhibitImageEditWindow(ExhibitDTO bean) {
		this.bean = bean;
		setSizeUndefined();
		setWidth(400, Unit.PIXELS);
		setHeight(360, Unit.PIXELS);
		windowLayout = new VerticalLayout();

		buildButtons();

		windowLayout.setMargin(true);
		imagePreviewField = new ExhibitionImagePreviewField();
		imagePreviewField.setCaption("Wybierz zdjęcie eksponatu");
		windowLayout.addComponent(imagePreviewField);
		windowLayout.addComponent(buttonLayout);

		setContent(windowLayout);
		setModal(true);
	}

	public ExhibitImageEditWindow(ExhibitDTO bean, String caption) {
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
		super.close();
	}

	protected void onPreSave() {
		byte[] imageData = (byte[]) imagePreviewField.getValue();
		ResourceAddEditDTO resourceDTO = new ResourceAddEditDTO();
		resourceDTO.setFileName(imagePreviewField.getLastFileName());
		resourceDTO.setMimeType(imagePreviewField.getLastMimeType());
		resourceDTO.setFileData(imageData);
		resourceDTO.setFileSize(imagePreviewField.getLastFileSize());
		try {
			if (onSave(bean, resourceDTO)) {
				super.close();
			}
		} catch (InvalidBeanException e) {
			e.printStackTrace();
		}
	}

	protected abstract boolean onSave(ExhibitDTO bean, ResourceAddEditDTO resourceDTO) throws InvalidBeanException;
	
	public static class ExhibitionImagePreviewField extends ImagePreviewField {

		private static final long serialVersionUID = 8122769686585628384L;

		@Override
		public String getLastMimeType() {
			// TODO Auto-generated method stub
			return super.getLastMimeType();
		}
		
		@Override
		public long getLastFileSize() {
			if (isEmpty()){
				return 0;
			}
			return super.getLastFileSize();
		}
	}

}
