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
package me.uni.emuseo.service.utils;

import java.util.ArrayList;
import java.util.List;

import me.uni.emuseo.model.categories.CategorySearchDTO;
import me.uni.emuseo.model.exhibits.ExhibitSearchDTO;
import me.uni.emuseo.model.users.UserSearchDTO;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

public class CriterionProducer {

	public static List<Criterion> getCriterions(ExhibitSearchDTO searchDTO) {
		List<Criterion> criterions = new ArrayList<Criterion>(7);
		if (searchDTO.getExhibitCategory() != null) {
			criterions.add(Restrictions.eq("category", MapperImpl.map(searchDTO.getExhibitCategory())));
		}
		if (searchDTO.getExhibitName() != null) {
			criterions.add(Restrictions.like("name", searchDTO.getExhibitName(), MatchMode.ANYWHERE));
		}
		if (searchDTO.getExhibitNo() != null) {
			criterions.add(Restrictions.like("number", searchDTO.getExhibitNo(), MatchMode.ANYWHERE));
		}
		if (searchDTO.getGenericYearFrom() != null) {
			criterions.add(Restrictions.le("creationDate",
					MapperImpl.genericYearToCreationYear(searchDTO.getGenericYearFrom())));
		}
		if (searchDTO.getGenericYearTo() != null) {
			criterions.add(Restrictions.ge("creationDate",
					MapperImpl.genericYearToCreationYear(searchDTO.getGenericYearTo())));
		}
		if (searchDTO.getExhibitDesc() != null) {
			criterions.add(Restrictions.like("description", searchDTO.getExhibitDesc(), MatchMode.ANYWHERE));
		}
		if (searchDTO.getExhibitLocation() != null) {
			criterions.add(Restrictions.eq("location", searchDTO.getExhibitLocation()));
		}
		return criterions;
	}

	public static List<Criterion> getCriterions(CategorySearchDTO searchDTO) {
		List<Criterion> criterions = new ArrayList<Criterion>(1);
		if (searchDTO.getName() != null) {
			criterions.add(Restrictions.like("name", searchDTO.getName(), MatchMode.ANYWHERE));
		}
		return criterions;
	}

	public static List<Criterion> getCriterions(UserSearchDTO searchDTO) {
		List<Criterion> criterions = new ArrayList<Criterion>(6);
		if (searchDTO.getFirstName() != null) {
			criterions.add(Restrictions.like("firstName", searchDTO.getFirstName(), MatchMode.ANYWHERE));
		}
		if (searchDTO.getLastName() != null) {
			criterions.add(Restrictions.like("lastName", searchDTO.getLastName(), MatchMode.ANYWHERE));
		}
		if (searchDTO.getLogin() != null) {
			criterions.add(Restrictions.like("login", searchDTO.getLogin(), MatchMode.ANYWHERE));
		}
		if (searchDTO.getPhoneNumber() != null) {
			criterions.add(Restrictions.like("phoneNumber", searchDTO.getPhoneNumber(), MatchMode.ANYWHERE));
		}
		if (searchDTO.getEmailAddress() != null) {
			criterions.add(Restrictions.like("emailAddress", searchDTO.getEmailAddress(), MatchMode.ANYWHERE));
		}
		if (searchDTO.getUserType() != null) {
			criterions.add(Restrictions.eq("userType", searchDTO.getUserType()));
		}
		return criterions;
	}
	
	public static Criterion getLoginCriterion(String login) {
		return Restrictions.eq("login", login);
	}
}
