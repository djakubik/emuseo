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

import me.uni.emuseo.model.users.PasswordDTO;
import me.uni.emuseo.view.common.form.FormBuilder;
import me.uni.emuseo.view.common.form.FormPopUpWindow;
import me.uni.emuseo.view.common.form.InvalidBeanException;

public class PasswordPopUpWindow extends FormPopUpWindow<PasswordDTO> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4023778432405550696L;

	public PasswordPopUpWindow(String caption) {
		super(new PasswordDTO(), caption);
	}

	@Override
	protected FormBuilder<PasswordDTO> createForm(PasswordDTO bean) {
		return new PasswordFormLayout(bean);
	}

	@Override
	protected boolean onSave(PasswordDTO bean) throws InvalidBeanException {
		return false;
	}

}
