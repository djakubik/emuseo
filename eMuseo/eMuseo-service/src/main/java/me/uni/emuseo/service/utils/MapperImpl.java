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

import me.uni.emuseo.model.categories.CategoryDTO;
import me.uni.emuseo.model.exhibits.ExhibitDTO;
import me.uni.emuseo.model.exhibits.ExhibitionExhibitDTO;
import me.uni.emuseo.model.resources.ResourceAddEditDTO;
import me.uni.emuseo.model.resources.ResourceDTO;
import me.uni.emuseo.model.users.UserDTO;
import me.uni.emuseo.model.users.UserDetailsDTO;
import me.uni.emuseo.service.model.Category;
import me.uni.emuseo.service.model.Exhibit;
import me.uni.emuseo.service.model.Resource;
import me.uni.emuseo.service.model.User;
import me.uni.emuseo.service.model.UserDetails;

import org.joda.time.LocalDate;

public class MapperImpl {

	public static ExhibitDTO map(Exhibit exhibit) {
		ExhibitDTO rex = new ExhibitDTO();
		rex.setExhibitId(exhibit.getExhibitId());
		rex.setExhibitName(exhibit.getName());
		rex.setExhibitNo(exhibit.getNumber());
		rex.setExhibitDesc(exhibit.getDescription());
		rex.setExhibitLocation(exhibit.getLocation());
		rex.setExhibitCategory(map(exhibit.getCategory()));
		rex.setGenericYear(creationYearToGeneticYear(exhibit.getCreationDate()));
		return rex;
	}

	public static Exhibit map(ExhibitDTO exhibit) {
		Exhibit rex = new Exhibit();
		rex.setExhibitId(exhibit.getExhibitId());
		rex.setName(exhibit.getExhibitName());
		rex.setNumber(exhibit.getExhibitNo());
		rex.setDescription(exhibit.getExhibitDesc());
		rex.setLocation(exhibit.getExhibitLocation());
		rex.setCategory(map(exhibit.getExhibitCategory()));
		rex.setCreationDate(genericYearToCreationYear(exhibit.getGenericYear()));
		return rex;
	}

	public static UserDTO map(User user) {
		UserDTO rex = new UserDTO();
		rex.setUserId(user.getUserId());
		rex.setFirstName(user.getFirstName());
		rex.setLastName(user.getLastName());
		rex.setEmailAddress(user.getEmailAddress());
		rex.setPhoneNumber(user.getPhoneNumber());
		rex.setLogin(user.getLogin());
		rex.setUserType(user.getUserType());
		return rex;
	}

	public static User map(UserDTO user) {
		User rex = new User();
		rex.setUserId(user.getUserId());
		return map(user, rex);
	}

	public static User map(UserDTO user, User rex) {
		rex.setFirstName(user.getFirstName());
		rex.setLastName(user.getLastName());
		rex.setEmailAddress(user.getEmailAddress());
		rex.setPhoneNumber(user.getPhoneNumber());
		rex.setLogin(user.getLogin());
		rex.setUserType(user.getUserType());
		if (user.getPassword() != null && !user.getPassword().isEmpty()) {
			rex.setPassword(PasswordUtil.hashPassword(user.getPassword()));
		}
		return rex;
	}

	public static CategoryDTO map(Category category) {
		CategoryDTO rex = new CategoryDTO();
		rex.setCategoryId(category.getCategoryId());
		rex.setName(category.getName());
		rex.setExhibitsCount(category.getExhibits().size());
		return rex;
	}

	public static Category map(CategoryDTO category) {
		Category rex = new Category();
		rex.setCategoryId(category.getCategoryId());
		rex.setName(category.getName());
		return rex;
	}

	public static UserDetailsDTO map(UserDetails userDetails) {
		UserDetailsDTO rex = new UserDetailsDTO();
		if (userDetails != null) {
			rex.setUserDetailsId(userDetails.getUserDetailsId());
			rex.setUserId(userDetails.getUser().getUserId());
			rex.setFullName(userDetails.getFullName());
			rex.setLanguage(userDetails.getLanguage());
			rex.setTheme(userDetails.getTheme());
			rex.setImage(userDetails.getImage());
		}
		return rex;
	}

	public static UserDetails map(UserDetailsDTO userDetails) {
		UserDetails rex = new UserDetails();
		rex.setUserDetailsId(userDetails.getUserDetailsId());
		rex.setFullName(userDetails.getFullName());
		return rex;
	}
	
	public static ResourceDTO map(Resource resource) {
		ResourceDTO rex = new ResourceDTO();
		if (resource != null) {
			rex.setResourceId(resource.getResourceId());
			rex.setCode(resource.getCode());
			rex.setFileName(resource.getFileName());
			rex.setMimeType(resource.getMimeType());
			rex.setFileSize(resource.getFileSize());
			rex.setPath(resource.getPath());
		}
		return rex;
	}

	public static Resource map(ResourceDTO resource) {
		Resource rex = new Resource();
		rex.setResourceId(resource.getResourceId());
		rex.setCode(resource.getCode());
		rex.setFileName(resource.getFileName());
		rex.setMimeType(resource.getMimeType());
		rex.setFileSize(resource.getFileSize());
		rex.setPath(resource.getPath());
		return rex;
	}
	
	public static Resource map(ResourceAddEditDTO resource, Resource rex) {
		rex.setFileName(resource.getFileName());
		rex.setMimeType(resource.getMimeType());
		rex.setFileSize(resource.getFileSize());
		return rex;
	}
	
//	public static ExhibitShowDTO map(ExhibitDTO exhibit) {
//		ExhibitShowDTO rex = new ExhibitShowDTO();
//		rex.setExhibitId(exhibit.getExhibitId());
//		rex.setExhibitName(exhibit.getExhibitName());
//		rex.setExhibitNo(exhibit.getExhibitNo());
//		rex.setExhibitDesc(exhibit.getExhibitDesc());
//		rex.setLocation(exhibit.getExhibitLocation());
//		rex.setExhibitCategory(exhibit.getExhibitCategory().getName());
//		rex.setGenericYear(exhibit.getGenericYear());
//		return rex;
//	}
	public static ExhibitionExhibitDTO map(Exhibit exhibit, Resource resource) {
		ExhibitionExhibitDTO rex = new ExhibitionExhibitDTO();
		rex.setExhibitId(exhibit.getExhibitId());
		rex.setExhibitName(exhibit.getName());
		rex.setExhibitNo(exhibit.getNumber());
		rex.setExhibitDesc(exhibit.getDescription());
		rex.setExhibitCategory(exhibit.getCategory().getName());
		rex.setGenericYear(creationYearToGeneticYear(exhibit.getCreationDate()));
		rex.setResourceUrl(ResourceUtils.getResourceUrl(resource));
		return rex;
	}
	
	public static int creationYearToGeneticYear(Integer year) {
		return new LocalDate().minusYears(year).getYear();
	}

	public static int genericYearToCreationYear(Integer year) {
		if (year != null) {
			return new LocalDate().minusYears(year).getYear();
		} else {
			return new LocalDate().getYear();
		}
	}

}
