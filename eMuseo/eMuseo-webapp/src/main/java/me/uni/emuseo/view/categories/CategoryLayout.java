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
package me.uni.emuseo.view.categories;

import java.util.List;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import me.uni.emuseo.EMuseoUtil;
import me.uni.emuseo.exception.CannotDeleteCategoryException;
import me.uni.emuseo.model.categories.CategoryDTO;
import me.uni.emuseo.model.categories.CategorySearchDTO;
import me.uni.emuseo.service.CategoryService;
import me.uni.emuseo.view.categories.form.CategorySearchFormLayout;
import me.uni.emuseo.view.common.ExpandingPanel;
import me.uni.emuseo.view.common.form.InvalidBeanException;
import me.uni.emuseo.view.common.helpers.ConfirmationWindow;
import me.uni.emuseo.view.common.paging.LazyLoadable;
import me.uni.emuseo.view.common.paging.PagedTableLayout;
import me.uni.emuseo.view.common.search.SearchBeanChangeEvent;
import me.uni.emuseo.view.common.search.SearchBeanChangeListener;
import me.uni.emuseo.view.common.search.SearchBeanClearEvent;

public class CategoryLayout extends VerticalLayout implements View, SearchBeanChangeListener<CategorySearchDTO> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1336543483525792809L;
	private CategorySearchFormLayout categorySearchFormLayout;
	private CategoryTable categoryTable;
	private PagedTableLayout<CategoryDTO> pagedTableLayout;
	private Button addButton;
	private ExpandingPanel searchPanel;
	private ExpandingPanel categoryPanel;
	private CategoryService categoryService;

	public CategoryLayout() {

		categoryService = EMuseoUtil.getAppContext().getBean(CategoryService.class);

		categorySearchFormLayout = new CategorySearchFormLayout();
		categorySearchFormLayout.addSearchBeanChangeListener(this);
		searchPanel = new ExpandingPanel();
		searchPanel.setContent(categorySearchFormLayout);
		searchPanel.setCaption("Wyszukaj kategorię");

		createTable();
		categoryTable.setWidth(100, Unit.PERCENTAGE);
		categoryTable.setPageLength(5);
		categoryTable.addStyleName("small");
		pagedTableLayout = new PagedTableLayout<CategoryDTO>(categoryTable, new CategoryLazyLoadable(categoryService));

		categoryPanel = new ExpandingPanel();
		categoryPanel.setContent(pagedTableLayout);
		categoryPanel.setCaption("Kategorie");
		categoryPanel.setExpanded(true);

		addButton = new Button("Dodaj kategorię");
		addButton.setIcon(FontAwesome.PLUS);
		addButton.addStyleName("emuseo-button-icon");
		addButton.addStyleName("emuseo-button-margin");
		addButton.setWidth(100, Unit.PERCENTAGE);

		addComponent(searchPanel);
		addComponent(categoryPanel);
		addComponent(addButton);

		addButton.addClickListener(new ClickListener() {

			private static final long serialVersionUID = -263205007196895260L;

			@Override
			public void buttonClick(ClickEvent event) {
				CategoryPopUpWindow categoryPopUpWidow = new CategoryPopUpWindow(new CategoryDTO(), "Dodaj kategorię") {
					private static final long serialVersionUID = 3776311059670953583L;

					@Override
					protected boolean onSave(CategoryDTO bean) throws InvalidBeanException {
						categoryService.addCategory(bean);
						pagedTableLayout.refresh();
						return true;
					}
				};
				UI.getCurrent().addWindow(categoryPopUpWidow);
			}
		});
	}

	private void createTable() {
		categoryTable = new CategoryTable() {

			private static final long serialVersionUID = 4201722098147104008L;

			@Override
			protected void onEdit(CategoryDTO itemId) {
				CategoryDTO category = categoryService.getCategory(itemId.getCategoryId());
				CategoryPopUpWindow categoryPopUpWidow = new CategoryPopUpWindow(category, "Edytuj kategorię") {
					private static final long serialVersionUID = 3776311059670953584L;

					@Override
					protected boolean onSave(CategoryDTO bean) throws InvalidBeanException {
						categoryService.editCategory(bean);
						pagedTableLayout.refreshPage();
						return true;
					}
				};
				UI.getCurrent().addWindow(categoryPopUpWidow);
			}

			@Override
			protected void onDelete(final CategoryDTO itemId) {
				ConfirmationWindow confirmationWindow = new ConfirmationWindow("Usuń kategorię",
						"Czy na pewno chcesz usunąć kategorię: " + itemId.getName() + "?") {

					private static final long serialVersionUID = -39445547305022448L;

					@Override
					protected boolean onConfirm() {
						try {
							categoryService.deleteCategory(itemId.getCategoryId());
							pagedTableLayout.refresh();
							return true;
						} catch (CannotDeleteCategoryException e) {
							new Notification("Błąd", "Nie można usunąć kategorii (posiada powiązane eksponaty).",
									Type.ERROR_MESSAGE, true).show(Page.getCurrent());
						}
						return false;
					}
				};
				UI.getCurrent().addWindow(confirmationWindow);
			}
		};
	}

	@Override
	public void enter(ViewChangeEvent event) {
	}

	@Override
	public void onSearchBeanChange(SearchBeanChangeEvent<CategorySearchDTO> event) {
		pagedTableLayout.refresh();
	}

	@Override
	public void onSearchBeanClear(SearchBeanClearEvent<CategorySearchDTO> event) {
		pagedTableLayout.refresh();
	}

	private final class CategoryLazyLoadable implements LazyLoadable<CategoryDTO> {

		private CategoryService categoryService;

		public CategoryLazyLoadable(CategoryService categoryService) {
			this.categoryService = categoryService;
		}

		@Override
		public int getItemsCount() {
			CategorySearchDTO searchBean = categorySearchFormLayout.getSearchBean();
			int intValue = categoryService.getCategoriesCount(searchBean).intValue();
			return intValue;
		}

		@Override
		public List<CategoryDTO> getItems(int startIndex, int count) {
			CategorySearchDTO searchBean = categorySearchFormLayout.getSearchBean();
			List<CategoryDTO> categories = categoryService.getCategories(startIndex, count, searchBean);
			return categories;
		}
	}
}
