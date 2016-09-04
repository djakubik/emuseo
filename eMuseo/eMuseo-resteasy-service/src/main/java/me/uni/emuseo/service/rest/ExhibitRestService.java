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
package me.uni.emuseo.service.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import me.uni.emuseo.model.exhibits.ExhibitionExhibitDTO;
import me.uni.emuseo.service.ExhibitService;

@Component
@Path("/exhibits")
public class ExhibitRestService {

	@Autowired
	private ExhibitService exhibitService;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<ExhibitionExhibitDTO> getExhibits() {
		List<ExhibitionExhibitDTO> exhibits = exhibitService.getExhibitionExhibits();
		return exhibits;
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getExhibit(@PathParam("id") Long id) {
		ExhibitionExhibitDTO exhibitById = exhibitService.getExhibitionExhibit(id);
		if (exhibitById != null) {
			return Response.status(200).entity(exhibitById).header("Access-Control-Allow-Headers", "X-extra-header")
					.allow("OPTIONS").build();
		} else {
			return Response.status(404).entity("The exhibit with the id " + id + " does not exist").build();
		}
	}
}
