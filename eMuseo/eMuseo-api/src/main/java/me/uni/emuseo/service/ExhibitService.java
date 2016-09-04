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

import me.uni.emuseo.model.exhibits.ExhibitDTO;
import me.uni.emuseo.model.exhibits.ExhibitSearchDTO;
import me.uni.emuseo.model.exhibits.ExhibitionExhibitDTO;
import me.uni.emuseo.model.resources.ResourceAddEditDTO;

public interface ExhibitService {

	void addExhibit(ExhibitDTO exhibit);

	void editExhibit(ExhibitDTO exhibit);

	void editExhibitImage(Long exhibitId, ResourceAddEditDTO resourceDTO);

	boolean deleteExhibit(Long exhibitId);

	ExhibitDTO getExhibit(Long id);

	List<ExhibitDTO> getAllExhibits();

	Long getExhibitsCount(ExhibitSearchDTO searchDTO);

	List<ExhibitDTO> getExhibits(int firstResult, int maxResults, ExhibitSearchDTO searchDTO);

	List<ExhibitionExhibitDTO> getExhibitionExhibits();

	ExhibitionExhibitDTO getExhibitionExhibit(Long id);

}
