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

import me.uni.emuseo.model.exhibits.ExhibitSearchDTO;
import me.uni.emuseo.view.common.form.FormBuilder;
import me.uni.emuseo.view.common.form.SearchFormLayout;
import me.uni.emuseo.view.common.helpers.FieldConfiguratorCaptionImpl;

public class ExhibitSearchFormLayout extends SearchFormLayout<ExhibitSearchDTO> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2216704103494439977L;

	@Override
	public ExhibitSearchDTO getBean() {
		return new ExhibitSearchDTO();
	}

	@Override
	protected List<String>[] configureFormBuilder(ExhibitSearchDTO initBean, FormBuilder<ExhibitSearchDTO> formBuilder) {
		@SuppressWarnings("unchecked")
		List<String>[] fields = new List[] {
				Arrays.asList("exhibitName", "exhibitNo", "genericYearFrom", "genericYearTo"),
				Arrays.asList("exhibitDesc", "exhibitCategory", "exhibitLocation") };

		FieldConfiguratorCaptionImpl fieldConfiguratorCaptionImpl = new FieldConfiguratorCaptionImpl()
				.setNullRepresentation("").put("exhibitName", "Nazwa").put("exhibitNo", "Numer kat.")
				.put("genericYearFrom", "Wiek od").put("genericYearTo", "Wiek do").put("exhibitDesc", "Opis")
				.put("exhibitCategory", "Kategoria").put("exhibitLocation", "Lokalizacja");
		formBuilder.addFieldBuilder(new ExhibitCategoryFieldBuilder());
		formBuilder.addFieldConfigurator(fieldConfiguratorCaptionImpl);
		return fields;
	}

}
