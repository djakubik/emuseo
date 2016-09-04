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

import java.util.HashMap;
import java.util.Map;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.Field;

public class FieldConfiguratorCaptionImpl implements FieldConfigurator {

	protected Map<String, String> captions;
	protected Integer width;
	protected String nullRepresentation;

	public FieldConfiguratorCaptionImpl() {
		captions = new HashMap<String, String>();
	}

	public FieldConfiguratorCaptionImpl put(String propertyId, String caption) {
		captions.put(propertyId, caption);
		return this;
	}

	public FieldConfiguratorCaptionImpl setWidth(Integer width) {
		this.width = width;
		return this;
	}

	public FieldConfiguratorCaptionImpl setNullRepresentation(String nullRepresentation) {
		this.nullRepresentation = nullRepresentation;
		return this;
	}

	@Override
	public boolean configure(Field<?> field, String propertyId) {
		if (width != null) {
			field.setWidth(width, Unit.PIXELS);
		}
		String caption = captions.get(propertyId);
		if (caption != null) {
			field.setCaption(caption);
		}
		if (nullRepresentation != null && field instanceof AbstractTextField) {
			((AbstractTextField) field).setNullRepresentation(nullRepresentation);
		}
		return false;
	}

}
