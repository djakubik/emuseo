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
package me.uni.emuseo.service.resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import me.uni.emuseo.model.resources.ResourceDTO;
import me.uni.emuseo.service.ResourceService;
import me.uni.emuseo.utils.ServletUtils;
import me.uni.emuseo.utils.StorageUtils;

public class ResourceServlet extends HttpServlet {

	private static final long serialVersionUID = 1111119L;
	private static final Logger LOG = LoggerFactory.getLogger(ResourceServlet.class);

	public static final String ATTACHMENT_PARAM = "attachment";

	@Autowired
	ResourceService resourceService;

	@Override
	public void init(ServletConfig config) {
		try {
			super.init(config);
		} catch (ServletException e) {
		}
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		processRequest(req, resp);
	}

	private void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			String requestedPath = request.getPathInfo();
			String transformedRequestedPath = requestedPath.substring(1).replace('\\', File.separatorChar).replace('/',
					File.separatorChar);

			// whether download as attachment or just data
			boolean asAttachment = false;
			String attachmentString = request.getParameter(ATTACHMENT_PARAM);
			if (attachmentString != null) {
				asAttachment = Boolean.parseBoolean(attachmentString);
			}

			// resolving path and name together
			String requestedCode = transformedRequestedPath;

			ResourceDTO resource = null;
			resource = resourceService.getResourceByCode(requestedCode);
			if (resource == null) {
				LOG.debug("Error getting resource: {}", requestedCode);
				response.sendError(HttpServletResponse.SC_NOT_FOUND,
						String.format("Resource %s could not be found.", requestedCode));
			}
			// String mimeType = resource.getMimeType();

			String stringPathCode = resource.getPath();
			File filePathCode = new File(stringPathCode);

			FileInputStream fileAsStream = StorageUtils.readFileAsStream(filePathCode);
			if (asAttachment) {
				ServletUtils.sendAsAttachment(response, fileAsStream, resource.getFileName(), resource.getMimeType());
			} else {
				ServletUtils.sendData(response, fileAsStream, resource.getFileName(), resource.getMimeType());
			}
		} catch (IOException e) {
			response.sendError(HttpServletResponse.SC_GONE, String.format("Resource happen to be gone."));
		}
	}
}
