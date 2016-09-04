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

import java.util.ArrayList;
import java.util.List;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.RowHeaderMode;
import com.vaadin.ui.VerticalLayout;

public class PagedTableLayout<BEANTYPE> extends VerticalLayout {

	private static final long serialVersionUID = 4623388076269170527L;
	private LazyLoadable<BEANTYPE> lazyLoadable;
	private Table pagedTable;
	private PageButtonListener pageButtonListener;
	private HorizontalLayout navigationLayout;
	private HorizontalLayout middleLayout;
	private Button prevPageButton;
	private Button nextPageButton;
	private Button firstPageButton;
	private Button lastPageButton;
	private ComboBox itemsPerPageComboBox;
	private int itemsPerPage;

	private int pagesCount;
	private int currentPage;

	public PagedTableLayout(Table pagedTable, LazyLoadable<BEANTYPE> lazyLoadable) {

		this.pagedTable = pagedTable;
		this.lazyLoadable = lazyLoadable;
		pageButtonListener = new PageButtonListener(this);

		itemsPerPage = 5;

		buildNavPanel();
		refresh();
		addComponent(pagedTable);
		addComponent(navigationLayout);
		if (pagedTable instanceof OrderedTable) {
			((OrderedTable) pagedTable).setRowHeaderMode(RowHeaderMode.INDEX);
		}
		pagedTable.setWidth(100, Unit.PERCENTAGE);
		pagedTable.setHeight(100, Unit.PERCENTAGE);
	}

	protected void buildNavPanel() {
		navigationLayout = new HorizontalLayout();

		HorizontalLayout extraLayout = new HorizontalLayout();

		createItemsPeerPageComboBox();
		itemsPerPageComboBox.setWidth(100, Unit.PIXELS);

		middleLayout = new HorizontalLayout();
		middleLayout.setSpacing(true);
		buildNavButtons();
		extraLayout.addComponent(firstPageButton);
		extraLayout.addComponent(prevPageButton);
		extraLayout.addComponents(middleLayout);
		extraLayout.addComponent(nextPageButton);
		extraLayout.addComponent(lastPageButton);
		extraLayout.setSpacing(true);

		navigationLayout.addComponent(itemsPerPageComboBox);
		navigationLayout.addComponent(extraLayout);
		navigationLayout.setExpandRatio(itemsPerPageComboBox, 0);
		navigationLayout.setExpandRatio(extraLayout, 1);
		navigationLayout.setComponentAlignment(itemsPerPageComboBox, Alignment.MIDDLE_LEFT);
		navigationLayout.setComponentAlignment(extraLayout, Alignment.BOTTOM_CENTER);
		navigationLayout.setWidth(100, Unit.PERCENTAGE);
		navigationLayout.setMargin(new MarginInfo(false, true, false, true));
	}

	protected void buildNavButtons() {
		prevPageButton = new Button(FontAwesome.ANGLE_LEFT);
		prevPageButton.addClickListener(new ClickListener() {

			private static final long serialVersionUID = -2235461491455250659L;

			@Override
			public void buttonClick(ClickEvent event) {
				goToPage(getCurrentPage() - 1);
			}
		});

		nextPageButton = new Button(FontAwesome.ANGLE_RIGHT);
		nextPageButton.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 277167907625013749L;

			@Override
			public void buttonClick(ClickEvent event) {
				goToPage(getCurrentPage() + 1);
			}
		});

		firstPageButton = new Button(FontAwesome.ANGLE_DOUBLE_LEFT);
		firstPageButton.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 277167907625013750L;

			@Override
			public void buttonClick(ClickEvent event) {
				goToPage(0);
			}
		});
		lastPageButton = new Button(FontAwesome.ANGLE_DOUBLE_RIGHT);
		lastPageButton.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 277167907625013751L;

			@Override
			public void buttonClick(ClickEvent event) {
				goToPage(getPagesCount() - 1);
			}
		});
	}

	protected void recalculatePages(int itemsCount, int itemsPerPage) {
		int pagesCount = itemsCount / itemsPerPage;
		if (itemsCount % itemsPerPage > 0) {
			pagesCount++;
		}
		this.pagesCount = pagesCount;
		/**
		 * Oba warunki w tej kolejności są konieczne. Sprawdza czy strona
		 * istniała, jeśli nie to ją inicjuje jako pierwszą. Drugi warunek
		 * ustawia aktualną stronę.
		 */
		if (currentPage < 0) {
			currentPage = 0;
		}
		if (currentPage >= pagesCount) {
			currentPage = pagesCount - 1;
		}
		pagedTable.setPageLength(itemsPerPage);
		refreshNavButtons();
	}

	protected void refreshNavButtons() {
		int currentPage = getCurrentPage();
		int pagesCount = getPagesCount();
		boolean isCurrentFirstPage = currentPage <= 0;
		boolean isCurrentLastPage = currentPage == pagesCount - 1;
		lastPageButton.setEnabled(!isCurrentLastPage);
		nextPageButton.setEnabled(!isCurrentLastPage);

		firstPageButton.setEnabled(!isCurrentFirstPage);
		prevPageButton.setEnabled(!isCurrentFirstPage);

		int sidePageButtonsToShow = 2;
		List<Button> pageButtons = new ArrayList<Button>((sidePageButtonsToShow * 2 + 1));

		if (pagesCount > 0) {
			for (int i = currentPage - (sidePageButtonsToShow - 1), n = 0; n < sidePageButtonsToShow
					&& i <= pagesCount; n++, i++) {
				if (i <= 0) {
					continue;
				}
				Button pageButton = createPageButton(i);
				pageButtons.add(pageButton);
			}
			Button currentPageButton = createPageButton(currentPage + 1);
			currentPageButton.addStyleName("primary");
			pageButtons.add(currentPageButton);
			for (int i = currentPage + 2, n = 0; n < sidePageButtonsToShow && i <= pagesCount; n++, i++) {
				Button pageButton = createPageButton(i);
				pageButtons.add(pageButton);
			}
		}
		middleLayout.removeAllComponents();
		middleLayout.addComponents(pageButtons.toArray(new Button[pageButtons.size()]));
	}

	private Button createPageButton(int i) {
		Button pageButton = new Button();
		pageButton.setCaption(((Integer) i).toString());
		pageButton.setData(i - 1);
		pageButton.addClickListener(pageButtonListener);
		return pageButton;
	}

	public void goToPage(int pageNo) {
		if (pageNo < 0 || pageNo >= getPagesCount()) {
			return;
		}
		this.currentPage = pageNo;
		refreshPage();
		refreshNavButtons();
	}

	public void refreshPage() {
		int startIndex = getCurrentPage() * getItemsPerPage();
		int itemsCount = getItemsPerPage();
		List<BEANTYPE> items = lazyLoadable.getItems(startIndex, itemsCount);
		if (pagedTable instanceof OrderedTable) {
			((OrderedTable) pagedTable).setOffset(startIndex);
		}
		@SuppressWarnings("unchecked")
		BeanItemContainer<BEANTYPE> beans = (BeanItemContainer<BEANTYPE>) pagedTable.getContainerDataSource();
		beans.removeAllItems();
		beans.addAll(items);
	}

	public void refresh() {
		int itemsCount = lazyLoadable.getItemsCount();
		recalculatePages(itemsCount, getItemsPerPage());
		refreshPage();
	}

	protected void createItemsPeerPageComboBox() {
		itemsPerPageComboBox = new ComboBox("Na stronie:");
		itemsPerPageComboBox.addItem(5);
		itemsPerPageComboBox.addItem(10);
		itemsPerPageComboBox.addItem(15);
		itemsPerPageComboBox.addItem(25);
		itemsPerPageComboBox.setSizeUndefined();
		itemsPerPageComboBox.setNewItemsAllowed(false);
		itemsPerPageComboBox.setNullSelectionAllowed(false);

		// select default
		itemsPerPageComboBox.select(getItemsPerPage());

		ValueChangeListener itemListener = new ValueChangeListener() {

			private static final long serialVersionUID = 223620644065451804L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				setItemsPerPage((Integer) event.getProperty().getValue());
			}
		};
		itemsPerPageComboBox.addValueChangeListener(itemListener);
	}

	/**
	 * Zwraca numer aktualnej strony (iterowany od 0) lub -1 jeśli nie istnieje
	 * żadna strona.
	 * 
	 * @return numer aktualnej strony iterowany od 0, jeśli nie ma żadnej strony
	 *         -1
	 */
	public int getCurrentPage() {
		return currentPage;
	}

	public int getPagesCount() {
		return pagesCount;
	}

	public int getItemsPerPage() {
		return itemsPerPage;
	}

	public void setItemsPerPage(int itemsPerPage) {
		this.itemsPerPage = itemsPerPage;
		refresh();
	}

	public Table getPagedTable() {
		return pagedTable;
	}
}
