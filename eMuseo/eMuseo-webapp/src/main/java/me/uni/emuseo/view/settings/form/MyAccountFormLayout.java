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
package me.uni.emuseo.view.settings.form;

import java.util.Arrays;
import java.util.List;

import com.vaadin.ui.Field;

import me.uni.emuseo.model.users.UserDTO;
import me.uni.emuseo.view.common.form.FormBuilder;
import me.uni.emuseo.view.common.helpers.FieldConfigurator;
import me.uni.emuseo.view.common.helpers.FieldConfiguratorCaptionImpl;

public class MyAccountFormLayout extends FormBuilder<UserDTO> {

	protected UserDTO userBean;

	public MyAccountFormLayout(UserDTO user) {
		this.userBean = user;
		init2();
	}

	protected void init2() {
		@SuppressWarnings("unchecked")
		List<String>[] fields = new List[] { Arrays.asList("firstName", "lastName", "login"),
				Arrays.asList("emailAddress", "phoneNumber", "userType") };

		FieldConfiguratorCaptionImpl fieldConfiguratorCaptionImpl = new FieldConfiguratorCaptionImpl()
				.setNullRepresentation("").put("firstName", "Imię").put("lastName", "Nazwisko").put("login", "Login")
				.put("emailAddress", "Adres e-mail").put("userType", "Typ Konta").put("phoneNumber", "Nr telefonu");
		addFieldConfigurator(fieldConfiguratorCaptionImpl);
		addFieldConfigurator(new FieldConfigurator() {

			@Override
			public boolean configure(Field<?> field, String propertyId) {
				if ("login".equals(propertyId)) {
					field.setEnabled(false);
				} else if ("userType".equals(propertyId)) {
					field.setEnabled(false);
				}
				return false;
			}
		});
		setBean(userBean, fields);
	}

}
