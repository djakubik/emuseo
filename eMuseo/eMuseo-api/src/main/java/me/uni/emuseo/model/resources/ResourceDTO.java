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

public class ResourceDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7278074964531696292L;
	private Long resourceId;
	private String code;
	private String path;
	private String fileName;
	private String mimeType;
	private long fileSize;

	public Long getResourceId() {
		return resourceId;
	}

	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
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
		ResourceDTO rhs = (ResourceDTO) obj;
		return new EqualsBuilder().appendSuper(super.equals(obj)).append(resourceId, rhs.resourceId)
				.append(code, rhs.code).append(path, rhs.path).append(fileName, rhs.fileName)
				.append(mimeType, rhs.mimeType).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(27, 47).append(resourceId).append(code).append(path).append(fileName)
				.append(mimeType).toHashCode();
	}
}
