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
package me.uni.emuseo.utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

public class ServletUtils {

	public static final String APPLICATION_OCTET_STREAM = "application/octet-stream";

	public static void sendAsAttachment(HttpServletResponse response, InputStream dataStream, String dataName)
			throws IOException {
		sendAsAttachment(response, dataStream, dataName, APPLICATION_OCTET_STREAM);
	}

	public static void sendAsAttachment(HttpServletResponse response, InputStream dataStream, String dataName,
			String dataMimeType) throws IOException {
		ServletOutputStream sos = response.getOutputStream();
		BufferedInputStream bis = null;
		try {
			bis = new BufferedInputStream(dataStream);
			if (dataStream instanceof FileInputStream) {
				long size = ((FileInputStream) dataStream).getChannel().size();
				response.setContentLength((int) size);
			}
			setResponseAsAttachment(response, dataName, dataMimeType);
			StorageUtils.copyStream(bis, sos);
		} finally {
			StorageUtils.closeStream(sos);
			StorageUtils.closeStream(bis);
			StorageUtils.closeStream(dataStream);
		}
	}

	public static void setResponseAsAttachment(HttpServletResponse response, String dataName, String dataMimeType) {
		response.setHeader("Content-Disposition", "attachment; filename=" + dataName);
		setContentType(response, dataMimeType);
	}

	public static void sendData(HttpServletResponse response, FileInputStream dataStream, String dataName)
			throws IOException {
		sendData(response, dataStream, dataName, APPLICATION_OCTET_STREAM);
	}

	public static void sendData(HttpServletResponse response, FileInputStream dataStream, String dataName,
			String dataMimeType) throws IOException {
		ServletOutputStream sos = response.getOutputStream();
		BufferedInputStream bis = null;
		try {
			long size = dataStream.getChannel().size();
			bis = new BufferedInputStream(dataStream);

			setResponseAsData(response, dataMimeType, size);
			StorageUtils.copyStream(bis, sos);
		} finally {
			StorageUtils.closeStream(sos);
			StorageUtils.closeStream(bis);
			StorageUtils.closeStream(dataStream);
		}
	}

	public static void setResponseAsData(HttpServletResponse response, String dataMimeType, long size) {
		setContentType(response, dataMimeType);
		response.setContentLength((int) size);
	}

	private static void setContentType(HttpServletResponse response, String dataMimeType) {
		if (dataMimeType != null) {
			response.setContentType(dataMimeType);
		} else {
			response.setContentType(APPLICATION_OCTET_STREAM);
		}
	}

}
