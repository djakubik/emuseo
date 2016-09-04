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
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.Set;

import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.Query;

import me.uni.emuseo.service.model.Resource;
import me.uni.emuseo.utils.StorageUtils;

public class ResourceUtils {

	private static File resourceDir;
	private static String resourceUrl;

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
			StorageUtils.createDirIfNotExists(resourceDir);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return resourceDir;
	}

	public static String getResourceServletUrl() {
		if (resourceUrl != null) {
			return resourceUrl;
		}
		String OPENSHIFT_GEAR_DNS = System.getenv("OPENSHIFT_GEAR_DNS");
		if (OPENSHIFT_GEAR_DNS != null) {
			resourceUrl = "http://" + OPENSHIFT_GEAR_DNS + "/resource/";
		} else {
			resourceUrl = "http://localhost:" + retrieveServerPort() + "/eMuseo-webapp/resource/";
		}
		return resourceUrl;
	}

	public static String getResourceUrl(String resourceCode) {
		return getResourceServletUrl() + resourceCode;
	}
	
	public static String getResourceUrl(Resource resource) {
		if (resource != null && resource.getCode() != null) {
			String resourceUrl = null;
			resourceUrl = getResourceUrl(resource.getCode());
			return resourceUrl;
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
			e.printStackTrace();
		}
		return "8080";
	}

}
