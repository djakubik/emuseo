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
package me.uni.emuseo.view.exhibits.form;

import java.util.Arrays;
import java.util.List;

import me.uni.emuseo.model.exhibits.ExhibitDTO;
import me.uni.emuseo.view.common.form.FormBuilder;
import me.uni.emuseo.view.common.helpers.FieldConfiguratorCaptionImpl;

public class ExhibitFormLayout extends FormBuilder<ExhibitDTO> {

	protected ExhibitDTO exhibitBean;

	public ExhibitFormLayout() {
		exhibitBean = new ExhibitDTO();
		init2();
	}

	public ExhibitFormLayout(ExhibitDTO exhibit) {
		this.exhibitBean = exhibit;
		init2();
	}

	protected void init2() {
		@SuppressWarnings("unchecked")
		List<String>[] fields = new List[] { Arrays.asList("exhibitName", "exhibitNo", "genericYear", "exhibitCategory",
				"exhibitLocation", "exhibitDesc") };

		FieldConfiguratorCaptionImpl fieldConfiguratorCaptionImpl = new FieldConfiguratorCaptionImpl()
				.setNullRepresentation("").setWidth(300).put("exhibitName", "Nazwa").put("exhibitNo", "Numer kat.")
				.put("genericYear", "Wiek w latach").put("exhibitCategory", "Kategoria")
				.put("exhibitLocation", "Lokalizacja").put("exhibitDesc", "Opis");

		addFieldBuilder(new ExhibitLocationFieldBuilder());
		addFieldBuilder(new DescriptionFieldBuilder("exhibitDesc"));
		addFieldBuilder(new ExhibitCategoryFieldBuilder());
		addFieldConfigurator(fieldConfiguratorCaptionImpl);
		setBean(exhibitBean, fields);
	}

}
