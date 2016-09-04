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
package me.uni.emuseo.view.common;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import com.vaadin.server.StreamResource.StreamSource;

public class PNGImageSource implements StreamSource {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1568763507606541412L;
	private byte[] image;

	public PNGImageSource(byte[] image) {
		this.image = image;
	}

	public InputStream getStream() {
		return new ByteArrayInputStream(image);
	}
}
