/*
 * Copyright 2016 OmniFaces
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package org.omnifaces.util;

import static java.util.concurrent.TimeUnit.SECONDS;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import org.omnifaces.component.ParamHolder;
//import org.omnifaces.facesviews.FacesViews;

/**
 * <p>
 * Collection of utility methods for the Servlet API in general. Most of them
 * are internally used by {@link Faces} and {@link FacesLocal}, however they may
 * also be useful in a "plain vanilla" servlet or servlet filter.
 * <p>
 * There are as of now also five special methods related to JSF without needing
 * a {@link FacesContext}:
 * <ul>
 * <li>The {@link #getFacesLifecycle(ServletContext)} which returns the JSF
 * lifecycle, allowing you a.o. to programmatically register JSF application's
 * phase listeners.
 * <li>The {@link #isFacesAjaxRequest(HttpServletRequest)} which is capable of
 * checking if the current request is a JSF ajax request.
 * <li>The {@link #isFacesResourceRequest(HttpServletRequest)} which is capable
 * of checking if the current request is a JSF resource request.
 * <li>The
 * {@link #facesRedirect(HttpServletRequest, HttpServletResponse, String, String...)}
 * which is capable of distinguishing JSF ajax requests from regular requests
 * and altering the redirect logic on it, exactly like as
 * {@link ExternalContext#redirect(String)} does. In other words, this method
 * behaves exactly the same as {@link Faces#redirect(String, String...)}.
 * <li>The {@link #isFacesDevelopment(ServletContext)} which is capable of
 * checking if the current JSF application configuration is set to development
 * project stage.
 * </ul>
 * <p>
 * Those methods can be used in for example a servlet filter.
 *
 * @author Arjan Tijms
 * @author Bauke Scholtz
 * @since 1.6
 */
public final class Servlets {

	// Constants
	// ------------------------------------------------------------------------------------------------------

	// Constructors
	// ---------------------------------------------------------------------------------------------------

	private Servlets() {
		// Hide constructor.
	}

	// HttpServletResponse
	// --------------------------------------------------------------------------------------------

	/**
	 * <p>
	 * Set the cache headers. If the <code>expires</code> argument is larger
	 * than 0 seconds, then the following headers will be set:
	 * <ul>
	 * <li><code>Cache-Control: public,max-age=[expiration time in seconds],must-revalidate</code></li>
	 * <li><code>Expires: [expiration date of now plus expiration time in seconds]</code></li>
	 * </ul>
	 * <p>
	 * Else the method will delegate to
	 * {@link #setNoCacheHeaders(HttpServletResponse)}.
	 * 
	 * @param response
	 *            The HTTP servlet response to set the headers on.
	 * @param expires
	 *            The expire time in seconds (not milliseconds!).
	 * @since 2.2
	 */
	public static void setCacheHeaders(HttpServletResponse response, long expires) {
		if (expires > 0) {
			response.setHeader("Cache-Control", "public,max-age=" + expires + ",must-revalidate");
			response.setDateHeader("Expires", System.currentTimeMillis() + SECONDS.toMillis(expires));
			response.setHeader("Pragma", ""); // Explicitly set pragma to
												// prevent container from
												// overriding it.
		} else {
			setNoCacheHeaders(response);
		}
	}

	/**
	 * <p>
	 * Set the no-cache headers. The following headers will be set:
	 * <ul>
	 * <li><code>Cache-Control: no-cache,no-store,must-revalidate</code></li>
	 * <li><code>Expires: [expiration date of 0]</code></li>
	 * <li><code>Pragma: no-cache</code></li>
	 * </ul>
	 * Set the no-cache headers.
	 * 
	 * @param response
	 *            The HTTP servlet response to set the headers on.
	 * @since 2.2
	 */
	public static void setNoCacheHeaders(HttpServletResponse response) {
		response.setHeader("Cache-Control", "no-cache,no-store,must-revalidate");
		response.setDateHeader("Expires", 0);
		response.setHeader("Pragma", "no-cache"); // Backwards compatibility for
													// HTTP 1.0.
	}

}