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
package me.uni.emuseo.service;

import java.util.List;

import me.uni.emuseo.model.resources.ResourceAddEditDTO;
import me.uni.emuseo.model.resources.ResourceDTO;

public interface ResourceService {

	ResourceDTO getResource(Long id);

	ResourceDTO getResourceByCode(String code);

	Long getResourcesCount();

	List<ResourceDTO> getResources(int firstResult, int maxResults);

	void addOrEditExhibitResource(Long exhibitId, ResourceAddEditDTO resourceDTO);

	String getResourceLinkById(Long id);

	String getResourceLinkByExhibitId(Long exhibitId);

}
