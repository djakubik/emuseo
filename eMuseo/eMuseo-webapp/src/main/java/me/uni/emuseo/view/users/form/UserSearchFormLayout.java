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

import me.uni.emuseo.model.users.UserSearchDTO;
import me.uni.emuseo.view.common.form.FormBuilder;
import me.uni.emuseo.view.common.form.SearchFormLayout;
import me.uni.emuseo.view.common.helpers.FieldConfiguratorCaptionImpl;

public class UserSearchFormLayout extends SearchFormLayout<UserSearchDTO> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5467853686293188777L;

	@Override
	public UserSearchDTO getBean() {
		return new UserSearchDTO();
	}

	@Override
	protected List<String>[] configureFormBuilder(UserSearchDTO initBean, FormBuilder<UserSearchDTO> formBuilder) {
		@SuppressWarnings("unchecked")
		List<String>[] fields = new List[] { Arrays.asList("firstName", "lastName", "login"),
				Arrays.asList("emailAddress", "phoneNumber", "userType") };

		FieldConfiguratorCaptionImpl fieldConfiguratorCaptionImpl = new FieldConfiguratorCaptionImpl()
				.setNullRepresentation("").put("firstName", "Imię").put("lastName", "Nazwisko")
				.put("login", "Login").put("emailAddress", "Adres e-mail").put("userType", "Typ Konta")
				.put("phoneNumber", "Nr telefonu");
		formBuilder.addFieldConfigurator(fieldConfiguratorCaptionImpl);
		return fields;
	}
}
