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
package me.uni.emuseo.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import me.uni.emuseo.model.resources.ResourceAddEditDTO;
import me.uni.emuseo.model.resources.ResourceDTO;
import me.uni.emuseo.service.ResourceService;
import me.uni.emuseo.service.dao.Alias;
import me.uni.emuseo.service.dao.Dao;
import me.uni.emuseo.service.model.Exhibit;
import me.uni.emuseo.service.model.Resource;
import me.uni.emuseo.service.utils.MapperImpl;
import me.uni.emuseo.service.utils.ResourceUtils;

@Service
@Transactional
public class ResourceServiceImpl implements ResourceService {

	@Autowired
	protected Dao dao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see me.uni.emuseo.service.ResourceService#getResource(java.lang.Long)
	 */
	@Override
	public ResourceDTO getResource(Long id) {
		Resource resource = dao.get(Resource.class, id);
		return MapperImpl.map(resource);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see me.uni.emuseo.service.ResourceService#getResourceByCode(java.lang.
	 * String)
	 */
	@Override
	public ResourceDTO getResourceByCode(String code) {
		List<Criterion> criterions = new ArrayList<Criterion>(1);
		criterions.add(Restrictions.eq("code", code));
		List<Resource> resources = dao.get(Resource.class, 0, 1, criterions, null);
		if (resources.isEmpty()) {
			return null;
		} else {
			return MapperImpl.map(resources.get(0));
		}
	}

	@Override
	public Long getResourcesCount(/* ResourceSearchDTO searchDTO */) {
		List<Criterion> criterions = null;// CriterionProducer.getCriterions(searchDTO);
		return dao.count(Resource.class, criterions, new ArrayList<Alias>(0));
	}

	@Override
	public List<ResourceDTO> getResources(int firstResult, int maxResults/* , ResourceSearchDTO */) {
		List<Criterion> criterions = null;// CriterionProducer.getCriterions(searchDTO);
		List<Resource> list = dao.get(Resource.class, firstResult, maxResults, criterions, new ArrayList<Alias>(0));
		List<ResourceDTO> retAll = new ArrayList<ResourceDTO>(list.size());
		for (Resource exhibit : list) {
			retAll.add(MapperImpl.map(exhibit));
		}
		return retAll;
	}

	@Override
	public void addOrEditExhibitResource(Long exhibitId, ResourceAddEditDTO resourceDTO) {
		Exhibit exhibit = dao.get(Exhibit.class, exhibitId);
		Resource resource = exhibit.getResource();
		byte[] fileData = resourceDTO.getFileData();

		if (fileData == null || fileData.length == 0) {
			if (resource != null) {
				exhibit.setResource(null);
				dao.saveOrUpdate(exhibit);
				FileUtils.deleteQuietly(new File(resource.getPath()));
			}
		} else {
			if (resource == null) {
				resource = new Resource();
				resource.setCode(ResourceUtils.calculateResourceCode(exhibit, resourceDTO));
				resource.setPath(ResourceUtils.calculateResourceFile(resource).getAbsolutePath());
				resource.setExhibit(exhibit);
				exhibit.setResource(resource);
			}
			resource = MapperImpl.map(resourceDTO, resource);
			dao.saveOrUpdate(exhibit);
			dao.saveOrUpdate(resource);
			try {
				FileUtils.writeByteArrayToFile(new File(resource.getPath()), fileData);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public String getResourceLinkById(Long id) {
		Resource resource = dao.get(Resource.class, id);
		return ResourceUtils.getResourceUrl(resource);
	}

	@Override
	public String getResourceLinkByExhibitId(Long exhibitId) {
		List<Alias> aliasses = new ArrayList<>();
		aliasses.add(Alias.getInstance("exhibit", "exhibit"));
		List<Criterion> criterions = new ArrayList<>();
		criterions.add(Restrictions.eq("exhibit.exhibitId", exhibitId));
		List<Resource> list = dao.get(Resource.class, 0, 1, criterions, aliasses);
		if (list.isEmpty()) {
			return null;
		} else {
			Resource resource = list.iterator().next();
			return ResourceUtils.getResourceUrl(resource);
		}
	}

}
