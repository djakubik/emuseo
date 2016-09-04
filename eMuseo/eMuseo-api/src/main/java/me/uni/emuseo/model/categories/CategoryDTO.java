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
package me.uni.emuseo.model.categories;

import java.io.Serializable;

public class CategoryDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7278074964531696292L;
	private Long categoryId;
	private String name;
	private Integer exhibitsCount;

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getExhibitsCount() {
		return exhibitsCount;
	}

	public void setExhibitsCount(Integer exhibitsCount) {
		this.exhibitsCount = exhibitsCount;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (obj == this)
			return true;
		if (!(obj instanceof CategoryDTO))
			return false;
		CategoryDTO cat = (CategoryDTO) obj;
		return cat.getCategoryId().equals(categoryId);
	}

	@Override
	public int hashCode() {
		if (categoryId != null) {
			return 17 + categoryId.intValue();
		} else {
			return 0;
		}
	}
}
