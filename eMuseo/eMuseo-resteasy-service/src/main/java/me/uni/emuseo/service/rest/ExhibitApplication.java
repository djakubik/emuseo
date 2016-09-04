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

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.core.Application;

public class ExhibitApplication extends Application {
	private Set<Object> singletons = new HashSet<Object>();

	public ExhibitApplication() {
		singletons.add(new ExhibitRestService());
	}

	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}
}
