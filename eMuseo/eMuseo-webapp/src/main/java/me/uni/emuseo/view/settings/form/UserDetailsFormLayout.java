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

import me.uni.emuseo.model.users.UserDetailsDTO;
import me.uni.emuseo.view.common.form.FormBuilder;
import me.uni.emuseo.view.common.helpers.FieldConfiguratorCaptionImpl;

public class UserDetailsFormLayout extends FormBuilder<UserDetailsDTO> {

	protected UserDetailsDTO userDetailsBean;

	public UserDetailsFormLayout() {
		userDetailsBean = new UserDetailsDTO();
		init2();
	}

	public UserDetailsFormLayout(UserDetailsDTO userDetails) {
		this.userDetailsBean = userDetails;
		init2();
	}

	protected void init2() {
		@SuppressWarnings("unchecked")
		List<String>[] fields = new List[] { Arrays.asList("fullName", "theme", "language") };

		FieldConfiguratorCaptionImpl fieldConfiguratorCaptionImpl = new FieldConfiguratorCaptionImpl()
				.setNullRepresentation("").put("fullName", "Nazwa użytkownika").put("theme", "Motyw").put("language", "Język");
		addFieldBuilder(new UserDetailsFieldBuilder());
		addFieldConfigurator(fieldConfiguratorCaptionImpl);
		setBean(userDetailsBean, fields);
	}

}
