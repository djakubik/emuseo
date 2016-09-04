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
package me.uni.emuseo.view.users.form;

import com.vaadin.ui.Field;
import com.vaadin.ui.PasswordField;

import me.uni.emuseo.view.common.helpers.FieldBuilder;

public class PasswordFieldBuilder implements FieldBuilder {

	@Override
	public Field<?> build(String propertyId) {
		if ("password".equals(propertyId)){
			return new PasswordField();
		}
		return null;
	}

}
