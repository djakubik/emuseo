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
package me.uni.emuseo.view.settings.password;

import java.util.Arrays;
import java.util.List;

import com.vaadin.ui.Field;
import com.vaadin.ui.PasswordField;

import me.uni.emuseo.model.users.PasswordDTO;
import me.uni.emuseo.view.common.form.FormBuilder;
import me.uni.emuseo.view.common.helpers.FieldBuilder;
import me.uni.emuseo.view.common.helpers.FieldConfiguratorCaptionImpl;

public class PasswordFormLayout extends FormBuilder<PasswordDTO> {

	private static final long serialVersionUID = -7714211565610395947L;

	protected PasswordDTO passwordBean;

	public PasswordFormLayout() {
		passwordBean = new PasswordDTO();
		init2();
	}

	public PasswordFormLayout(PasswordDTO password) {
		this.passwordBean = password;
		init2();
	}

	protected void init2() {
		@SuppressWarnings("unchecked")
		List<String>[] fields = new List[] { Arrays.asList("oldPassword", "newPassword", "newPasswordRetype") };

		FieldConfiguratorCaptionImpl fieldConfiguratorCaptionImpl = new FieldConfiguratorCaptionImpl()
				.setNullRepresentation("").setWidth(300).put("oldPassword", "Stare hasło")
				.put("newPassword", "Nowe hasło").put("newPasswordRetype", "Powtórz nowe hasło");
		addFieldConfigurator(fieldConfiguratorCaptionImpl);
		addFieldBuilder(new FieldBuilder() {

			@Override
			public Field<?> build(String propertyId) {
				return new PasswordField();
			}
		});
		setBean(passwordBean, fields);
	}

}
