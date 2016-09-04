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

import java.util.ArrayList;
import java.util.List;

public class SearchBeanEventPropagator<SEARCH_BEAN> {

	protected List<SearchBeanChangeListener<SEARCH_BEAN>> listeners;

	public SearchBeanEventPropagator() {
		listeners = new ArrayList<SearchBeanChangeListener<SEARCH_BEAN>>(1);
	}

	public void addSearchBeanChangeListener(SearchBeanChangeListener<SEARCH_BEAN> listener) {
		if (listener == null) {
			return;
		}
		listeners.add(listener);
	}

	public void removeSearchBeanChangeListener(SearchBeanChangeListener<SEARCH_BEAN> listener) {
		if (listener == null) {
			return;
		}
		listeners.remove(listener);
	}

	public synchronized void fireChangeEvent(SEARCH_BEAN searchBean) {
		SearchBeanChangeEvent<SEARCH_BEAN> event = new SearchBeanChangeEvent<SEARCH_BEAN>(searchBean);
		for (SearchBeanChangeListener<SEARCH_BEAN> listener : listeners) {
			listener.onSearchBeanChange(event);
		}
	}

	public synchronized void fireClearEvent(SEARCH_BEAN searchBean) {
		SearchBeanClearEvent<SEARCH_BEAN> event = new SearchBeanClearEvent<SEARCH_BEAN>(searchBean);
		for (SearchBeanChangeListener<SEARCH_BEAN> listener : listeners) {
			listener.onSearchBeanClear(event);
		}
	}
}
