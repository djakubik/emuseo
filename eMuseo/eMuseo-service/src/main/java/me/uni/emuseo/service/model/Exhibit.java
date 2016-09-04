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
package me.uni.emuseo.service.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import me.uni.emuseo.model.exhibits.ExhibitLocation;

@Entity
@Table(name = "`EXHIBIT`")
public class Exhibit {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "exhibit_id")
	private Long exhibitId;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "number", unique = true, nullable = false)
	private String number;

	@Column(name = "creation_date")
	private Integer creationDate;

	@Column(name = "description")
	private String description;

	@ManyToOne
	@JoinColumn(name = "parent_exhibit")
	public Exhibit parentExhibit;

	@OneToMany(mappedBy = "parentExhibit", fetch = FetchType.EAGER)
	public Set<Exhibit> subExhibits;

	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;

	@OneToOne(orphanRemoval = true, mappedBy = "exhibit", fetch = FetchType.LAZY)
	private Resource resource;

	@Column(name = "location")
	@Enumerated(EnumType.STRING)
	private ExhibitLocation location;

	public Long getExhibitId() {
		return exhibitId;
	}

	public void setExhibitId(Long exhibitId) {
		this.exhibitId = exhibitId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Integer getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Integer creationDate) {
		this.creationDate = creationDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Exhibit getParentExhibit() {
		return parentExhibit;
	}

	public void setParentExhibit(Exhibit parentExhibit) {
		this.parentExhibit = parentExhibit;
	}

	public Set<Exhibit> getSubExhibits() {
		return subExhibits;
	}

	public void setSubExhibits(Set<Exhibit> subExhibits) {
		this.subExhibits = subExhibits;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public ExhibitLocation getLocation() {
		return location;
	}

	public void setLocation(ExhibitLocation location) {
		this.location = location;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		long result = 1;
		result = prime * result + exhibitId;
		result = prime * result + ((number == null) ? 0 : number.hashCode());
		return (int) result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Exhibit))
			return false;
		Exhibit other = (Exhibit) obj;
		if (exhibitId != other.exhibitId)
			return false;
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Exhibit [exhibitId=" + exhibitId + ", name=" + name + ", number=" + number + ", creationDate="
				+ creationDate + ", description=" + description + ", parentExhibitId="
				+ (parentExhibit != null ? parentExhibit.getExhibitId() : null) + ", subExhibits=" + subExhibits
				+ ", categoryId=" + category.getCategoryId() + ", location=" + location + "]";
	}

}
