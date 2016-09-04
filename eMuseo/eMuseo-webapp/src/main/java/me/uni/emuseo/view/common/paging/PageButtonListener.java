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
package me.uni.emuseo.view.common.paging;

import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

final class PageButtonListener implements ClickListener {
	/**
	 * 
	 */
	private final PagedTableLayout<?> pagedTableLayout;

	/**
	 * @param pagedTableLayout
	 */
	PageButtonListener(PagedTableLayout<?> pagedTableLayout) {
		this.pagedTableLayout = pagedTableLayout;
	}

	private static final long serialVersionUID = 277167907625013748L;

	@Override
	public void buttonClick(ClickEvent event) {
		Integer pageNo = (Integer) event.getButton().getData();
		this.pagedTableLayout.goToPage(pageNo);
	}
}
