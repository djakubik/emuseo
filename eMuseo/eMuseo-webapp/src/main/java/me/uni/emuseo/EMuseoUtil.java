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
package me.uni.emuseo;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.security.core.context.SecurityContext;

import com.vaadin.ui.UI;

public class EMuseoUtil {
	
	public static final String EMUSEO_THEME = "emuseo-theme";
	public static final String DARK_EMUSEO_THEME = "dark-emuseo-theme";
	public static final String DEFAULT_THEME = DARK_EMUSEO_THEME;

	public static ApplicationContext getAppContext() {
		return ((EMuseoUI) UI.getCurrent()).getApplicationContext();
	}

	public static SecurityContext getSecurityContext() {
		return ((EMuseoUI) UI.getCurrent()).getSecurityContext();
	}
	
	public static List<String> getAvailableThemes() {
		return Arrays.asList(EMUSEO_THEME, DARK_EMUSEO_THEME);
	}
}
