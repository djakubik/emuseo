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
package me.uni.emuseo.view.users;

import me.uni.emuseo.model.users.UserDTO;
import me.uni.emuseo.view.common.form.FormBuilder;
import me.uni.emuseo.view.common.form.FormPopUpWindow;
import me.uni.emuseo.view.common.form.InvalidBeanException;
import me.uni.emuseo.view.users.form.UserFormLayout;

public class UserPopUpWindow extends FormPopUpWindow<UserDTO> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4268065035719002888L;

	public UserPopUpWindow(UserDTO bean, String caption) {
		super(bean, caption);
	}

	@Override
	protected FormBuilder<UserDTO> createForm(UserDTO bean) {
		return new UserFormLayout(bean);
	}

	@Override
	protected boolean onSave(UserDTO bean) throws InvalidBeanException {
		return false;
	}
}
