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

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import me.uni.emuseo.model.exhibits.ExhibitDTO;
import me.uni.emuseo.model.exhibits.ExhibitLocation;
import me.uni.emuseo.model.exhibits.ExhibitSearchDTO;
import me.uni.emuseo.model.exhibits.ExhibitionExhibitDTO;
import me.uni.emuseo.model.resources.ResourceAddEditDTO;
import me.uni.emuseo.service.ExhibitService;
import me.uni.emuseo.service.ResourceService;
import me.uni.emuseo.service.dao.Alias;
import me.uni.emuseo.service.dao.Dao;
import me.uni.emuseo.service.mapper.CriterionProducer;
import me.uni.emuseo.service.mapper.MapperImpl;
import me.uni.emuseo.service.model.Exhibit;

@Service
@Transactional
public class ExhibitServiceImpl implements ExhibitService {

	@Autowired
	protected Dao dao;

	@Autowired
	protected ResourceService resourceService;

	@Override
	public List<ExhibitDTO> getAllExhibits() {
		List<Exhibit> all = dao.getAll(Exhibit.class);
		List<ExhibitDTO> retAll = new ArrayList<ExhibitDTO>();
		for (Exhibit exhibit : all) {
			retAll.add(MapperImpl.map(exhibit));
		}
		return retAll;
	}

	@Override
	public Long getExhibitsCount(ExhibitSearchDTO searchDTO) {
		List<Criterion> criterions = CriterionProducer.getCriterions(searchDTO);
		return dao.count(Exhibit.class, criterions, new ArrayList<Alias>(0));
		// Arrays.asList(Alias.getInstance("category", "category_")));
	}

	@Override
	public List<ExhibitDTO> getExhibits(int firstResult, int maxResults, ExhibitSearchDTO searchDTO) {
		List<Criterion> criterions = CriterionProducer.getCriterions(searchDTO);
		List<Exhibit> list = dao.get(Exhibit.class, firstResult, maxResults, criterions, new ArrayList<Alias>(0));
		// Arrays.asList(Alias.getInstance("category", "category_")));
		List<ExhibitDTO> retAll = new ArrayList<ExhibitDTO>(list.size());
		for (Exhibit exhibit : list) {
			retAll.add(MapperImpl.map(exhibit));
		}
		return retAll;
	}

	@Override
	public void addExhibit(ExhibitDTO exhibit) {
		Exhibit ex = MapperImpl.map(exhibit);
		dao.save(ex);
	}

	@Override
	public void editExhibit(ExhibitDTO exhibit) {
		Exhibit ex = MapperImpl.map(exhibit);
		dao.saveOrUpdate(ex);
	}

	@Override
	public void editExhibitImage(Long id, ResourceAddEditDTO resourceDTO) {
		resourceService.addOrEditExhibitResource(id, resourceDTO);
	}

	@Override
	public boolean deleteExhibit(Long id) {
		Exhibit exhibit = dao.get(Exhibit.class, id);
		if (exhibit != null) {
			dao.delete(exhibit);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public ExhibitDTO getExhibit(Long id) {
		Exhibit exhibit = dao.get(Exhibit.class, id);
		return MapperImpl.map(exhibit);
	};

	@Override
	public List<ExhibitionExhibitDTO> getExhibitionExhibits() {
		List<Criterion> criterions = new ArrayList<>();
		criterions.add(Restrictions.eq("location", ExhibitLocation.EXHIBITION));
		List<Exhibit> all = dao.get(Exhibit.class, 0, 0, criterions, null);
		List<ExhibitionExhibitDTO> retAll = new ArrayList<ExhibitionExhibitDTO>();
		for (Exhibit exhibit : all) {
			retAll.add(MapperImpl.map(exhibit, exhibit.getResource()));
		}
		return retAll;
	}

	@Override
	public ExhibitionExhibitDTO getExhibitionExhibit(Long id) {
		List<Criterion> criterions = new ArrayList<>();
		criterions.add(Restrictions.eq("id", id));
		criterions.add(Restrictions.eq("location", ExhibitLocation.EXHIBITION));
		List<Exhibit> exhibits = dao.get(Exhibit.class, 0, 1, criterions, null);
		if (!exhibits.isEmpty()) {
			Exhibit exhibit = exhibits.iterator().next();
			return MapperImpl.map(exhibit, exhibit.getResource());
		} else {
			return null;
		}
	};

}
