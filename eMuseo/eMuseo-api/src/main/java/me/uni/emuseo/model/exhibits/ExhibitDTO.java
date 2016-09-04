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

public class ExhibitDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8285196726545290601L;
	private Long exhibitId;
	private String exhibitName;
	private String exhibitNo;
	private Integer genericYear;
	private String exhibitDesc;
	private CategoryDTO exhibitCategory;
	private ExhibitLocation exhibitLocation;

	public Long getExhibitId() {
		return exhibitId;
	}

	public void setExhibitId(Long exhibitId) {
		this.exhibitId = exhibitId;
	}

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

	public Integer getGenericYear() {
		return genericYear;
	}

	public void setGenericYear(Integer i) {
		this.genericYear = i;
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
		return "ExhibitDTO [exhibitName=" + exhibitName + ", exhibitNo="
				+ exhibitNo + ", genericYear=" + genericYear + ", exhibitDesc="
				+ exhibitDesc + ", exhibitCategory=" + exhibitCategory
				+ ", exhibitLocation=" + exhibitLocation + "]";
	}

}
