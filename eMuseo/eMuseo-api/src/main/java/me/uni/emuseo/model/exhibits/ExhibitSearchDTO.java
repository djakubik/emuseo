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
package me.uni.emuseo.model.exhibits;

import java.io.Serializable;

import me.uni.emuseo.model.categories.CategoryDTO;

public class ExhibitSearchDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8285196726545290684L;
	private String exhibitName;
	private String exhibitNo;
	private Integer genericYearFrom;
	private Integer genericYearTo;
	private String exhibitDesc;
	private CategoryDTO exhibitCategory;
	private ExhibitLocation exhibitLocation;

	public String getExhibitName() {
		return exhibitName;
	}

	public void setExhibitName(String exhibitName) {
		this.exhibitName = exhibitName;
	}

	public String getExhibitNo() {
		return exhibitNo;
	}

	public void setExhibitNo(String exhibitNo) {
		this.exhibitNo = exhibitNo;
	}

	public Integer getGenericYearFrom() {
		return genericYearFrom;
	}

	public void setGenericYearFrom(Integer genericYearFrom) {
		this.genericYearFrom = genericYearFrom;
	}

	public Integer getGenericYearTo() {
		return genericYearTo;
	}

	public void setGenericYearTo(Integer genericYearTo) {
		this.genericYearTo = genericYearTo;
	}

	public String getExhibitDesc() {
		return exhibitDesc;
	}

	public void setExhibitDesc(String exhibitDesc) {
		this.exhibitDesc = exhibitDesc;
	}

	public ExhibitLocation getExhibitLocation() {
		return exhibitLocation;
	}

	public void setExhibitLocation(ExhibitLocation exhibitLocation) {
		this.exhibitLocation = exhibitLocation;
	}

	public CategoryDTO getExhibitCategory() {
		return exhibitCategory;
	}

	public void setExhibitCategory(CategoryDTO exhibitCategory) {
		this.exhibitCategory = exhibitCategory;
	}

	@Override
	public String toString() {
		return "ExhibitSearchDTO [exhibitName=" + exhibitName + ", exhibitNo=" + exhibitNo + ", genericYearFrom="
				+ genericYearFrom + ", genericYearTo=" + genericYearTo + ", exhibitDesc=" + exhibitDesc
				+ ", exhibitCategory=" + exhibitCategory + ", exhibitLocation=" + exhibitLocation + "]";
	}

}
