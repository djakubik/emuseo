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
package me.uni.emuseo.view.common.search;

public class SearchBeanChangeEvent<SEARCH_BEAN> {

	private SEARCH_BEAN searchBean;

	public SearchBeanChangeEvent(SEARCH_BEAN searchBean) {
		this.searchBean = searchBean;
	}

	public SEARCH_BEAN getSearchBean() {
		return searchBean;
	}
}
