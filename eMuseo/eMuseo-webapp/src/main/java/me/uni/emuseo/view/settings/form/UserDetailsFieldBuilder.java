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

import java.util.List;

import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Field;

import me.uni.emuseo.EMuseoUtil;
import me.uni.emuseo.view.common.helpers.FieldBuilder;

public class UserDetailsFieldBuilder implements FieldBuilder {

	@Override
	public Field<?> build(String propertyId) {
		if ("theme".equals(propertyId)) {
			ComboBox comboBox = new ComboBox();
			comboBox.setNullSelectionAllowed(true);

			List<String> availableThemes = EMuseoUtil.getAvailableThemes();
			for (String theme : availableThemes) {
				comboBox.addItem(theme);
				// comboBox.setItemCaption("emuseo-theme", "E-museo");
			}
			return comboBox;
		} else if ("language".equals(propertyId)) {
			ComboBox comboBox = new ComboBox();
			comboBox.setNullSelectionAllowed(false);
			comboBox.addItem("pl");
			return comboBox;
		}
		return null;
	}

}
