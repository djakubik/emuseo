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
package me.uni.emuseo.model.resources;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class ResourceAddEditDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2085215448753859044L;

	protected String fileName;
	protected String mimeType;
	protected byte[] fileData;
	protected long fileSize;

	public ResourceAddEditDTO() {
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public byte[] getFileData() {
		return fileData;
	}

	public void setFileData(byte[] fileData) {
		this.fileData = fileData;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (obj.getClass() != getClass()) {
			return false;
		}
		ResourceAddEditDTO rhs = (ResourceAddEditDTO) obj;
		return new EqualsBuilder().appendSuper(super.equals(obj)).append(fileName, rhs.fileName)
				.append(mimeType, rhs.mimeType).append(fileSize, rhs.fileSize).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(29, 47).append(fileName).append(mimeType).append(fileSize).toHashCode();
	}
}
