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
package me.uni.emuseo.view.exhibits;

import me.uni.emuseo.model.exhibits.ExhibitDTO;
import me.uni.emuseo.view.common.form.FormBuilder;
import me.uni.emuseo.view.common.form.FormPopUpWindow;
import me.uni.emuseo.view.common.form.InvalidBeanException;
import me.uni.emuseo.view.exhibits.form.ExhibitFormLayout;

public class ExhibitPopUpWindow extends FormPopUpWindow<ExhibitDTO> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4023778432405550696L;

	public ExhibitPopUpWindow(ExhibitDTO exhibit, String caption) {
		super(exhibit, caption);
	}

	@Override
	protected FormBuilder<ExhibitDTO> createForm(ExhibitDTO bean) {
		return new ExhibitFormLayout(bean);
	}

	@Override
	protected boolean onSave(ExhibitDTO bean) throws InvalidBeanException {
		return false;
	}

}
