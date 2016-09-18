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

import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.Set;

import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.Query;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.uni.emuseo.model.resources.ResourceAddEditDTO;
import me.uni.emuseo.service.model.Exhibit;
import me.uni.emuseo.service.model.Resource;

public class ResourceUtils {

	private static final Logger LOG = LoggerFactory.getLogger(ResourceUtils.class);

	private static File resourceDir;
	private static String resourceUrl;

	public static String calculateResourceCode(Exhibit exhibit, ResourceAddEditDTO resource) {
		String id = Long.toString(exhibit.getExhibitId());
		return new StringBuilder("EXH-").append(id).toString();
	}

	public static String calculateResourceCodeWithExtension(Exhibit exhibit, ResourceAddEditDTO resource) {
		String id = Long.toString(exhibit.getExhibitId());
		String extension = FilenameUtils.getExtension(resource.getFileName());
		return new StringBuilder("EXH-").append(id).append('.').append(extension).toString();
	}

	public static File calculateResourceFile(Resource resource) {
		return new File(getResourceDir(), resource.getCode());
	}

	public static File getResourceDir() {
		if (resourceDir != null) {
			return resourceDir;
		}
		String OPENSHIFT_DATA_DIR = System.getenv("OPENSHIFT_DATA_DIR");
		if (OPENSHIFT_DATA_DIR != null) {
			resourceDir = new File(OPENSHIFT_DATA_DIR, "emuseo-res");
		} else {
			String currentUsersHomeDir = System.getProperty("user.home");
			resourceDir = new File(currentUsersHomeDir, "emuseo-res");
		}
		try {
			FileUtils.forceMkdir(resourceDir);
		} catch (IOException e) {
			LOG.error("Could not create resource directory", e);
		}
		return resourceDir;
	}

	public static String getResourceServletUrl() {
		if (resourceUrl != null) {
			return resourceUrl;
		}
		String OPENSHIFT_GEAR_DNS = System.getenv("OPENSHIFT_GEAR_DNS");
		if (OPENSHIFT_GEAR_DNS != null) {
			resourceUrl = "http://" + OPENSHIFT_GEAR_DNS + "/app/resource/";
		} else {
			resourceUrl = "http://localhost:" + retrieveServerPort() + "/app/resource/";
		}
		return resourceUrl;
	}

	public static String getResourceUrl(String resourceCode) {
		return getResourceServletUrl() + resourceCode;
	}

	public static String getResourceUrl(Resource resource) {
		if (resource != null && resource.getCode() != null) {
			return getResourceUrl(resource.getCode());
		}
		return null;
	}

	private static String retrieveServerPort() {
		try {
			MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
			Set<ObjectName> objs = mbs.queryNames(new ObjectName("*:type=Connector,*"),
					Query.match(Query.attr("protocol"), Query.value("HTTP/1.1")));
			String endPointsPort = new String();
			for (ObjectName obj : objs) {
				endPointsPort = obj.getKeyProperty("port");
			}
			return endPointsPort;
		} catch (MalformedObjectNameException e) {
			LOG.error("Could not retrieve server port", e);
		}
		return "8080";
	}

}
