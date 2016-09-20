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
package me.uni.emuseo.resource;

import java.io.File;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.omnifaces.servlet.FileServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import me.uni.emuseo.model.resources.ResourceDTO;
import me.uni.emuseo.service.ResourceService;

public class ResourceServlet extends FileServlet<ResourceDTO> {

	private static final long serialVersionUID = -6028328292134993575L;
	private static final Logger LOG = LoggerFactory.getLogger(ResourceServlet.class);
	public static final String ATTACHMENT_PARAM = "attachment";

	@Autowired
	protected transient ResourceService resourceService;

	@Override
	public void init(ServletConfig config) {
		try {
			super.init(config);
		} catch (ServletException e) {
			LOG.error("Servlet initialization error", e);
		}
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
	}

	@Override
	protected ResourceDTO getMetadata(HttpServletRequest req) {
		String requestedPath = req.getPathInfo();
		String transformedRequestedPath = requestedPath.substring(1).replace('\\', File.separatorChar).replace('/',
				File.separatorChar);

		// resolving path and name together
		String requestedCode = transformedRequestedPath;

		return resourceService.getResourceByCode(requestedCode);
	}

	@Override
	protected File getFile(HttpServletRequest request, ResourceDTO resource) {
		if (resource == null) {
			return null;
		}

		String resourcePath = resource.getPath();
		File resourceFile = new File(resourcePath);

		if (!resourceFile.exists()) {
			return null;
		}

		// resource File is valid
		return resourceFile;
	}

	@Override
	protected boolean isAttachment(HttpServletRequest req, String contentType, ResourceDTO resource) {
		// whether download as attachment or just data
		String isAttachmentString = req.getParameter(ATTACHMENT_PARAM);
		if (isAttachmentString != null) {
			return Boolean.parseBoolean(isAttachmentString);
		}
		return super.isAttachment(req, contentType, resource);
	}

	@Override
	protected String getAttachmentName(HttpServletRequest request, File file, ResourceDTO resource) {
		String fileName = resource.getFileName();
		if (fileName != null) {
			return fileName;
		}
		return file.getName();
	}

	@Override
	protected String getContentType(HttpServletRequest request, File file, ResourceDTO metadata) {
		String mimeType = metadata.getMimeType();
		if (mimeType != null) {
			return mimeType;
		}
		return super.getContentType(request, file, metadata);
	}
}
