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

import java.util.Date;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Field;

public class BeanFieldGroupFixed<T> extends BeanFieldGroup<T> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 400309855668313392L;

	public BeanFieldGroupFixed(Class<T> clazz) {
		super(clazz);
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <F extends Field> F buildAndBind(String caption, Object propertyId, Class<F> fieldType) throws BindException {
		final Class<?> type = getPropertyType(propertyId);

		F field;
		if (Enum.class.isAssignableFrom(type)) {
			field = (F) super.buildAndBind(caption, propertyId, ComboBox.class);
		} else if (Date.class.isAssignableFrom(type)) {
			field = getFieldFactory().createField(type, (Class<F>) DateField.class);
			if (field == null)
				field = (F) new DateField(caption);
			else
				field.setCaption(caption);

			bind(field, propertyId);
		} else {
			field = super.buildAndBind(caption, propertyId, fieldType);
		}
		return field;
	}

}
