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

import java.util.Arrays;
import java.util.List;

import me.uni.emuseo.model.users.UserDTO;
import me.uni.emuseo.view.common.form.FormBuilder;
import me.uni.emuseo.view.common.helpers.FieldConfiguratorCaptionImpl;

public class UserFormLayout extends FormBuilder<UserDTO> {

	private static final long serialVersionUID = 5738751948943411287L;

	protected UserDTO userBean;

	public UserFormLayout() {
		userBean = new UserDTO();
		init2();
	}

	public UserFormLayout(UserDTO user) {
		this.userBean = user;
		init2();
	}

	protected void init2() {
		@SuppressWarnings("unchecked")
		List<String>[] fields = new List[] { Arrays.asList("login", "firstName", "lastName", "password"),
				Arrays.asList("emailAddress", "phoneNumber", "userType") };

		FieldConfiguratorCaptionImpl fieldConfiguratorCaptionImpl = new FieldConfiguratorCaptionImpl()
				.setNullRepresentation("").put("firstName", "Imię").put("lastName", "Nazwisko").put("login", "Login")
				.put("password", "Hasło").put("emailAddress", "Adres e-mail").put("userType", "Typ Konta")
				.put("phoneNumber", "Nr telefonu");
		addFieldBuilder(new PasswordFieldBuilder());
		addFieldConfigurator(fieldConfiguratorCaptionImpl);
		setBean(userBean, fields);
	}

}
