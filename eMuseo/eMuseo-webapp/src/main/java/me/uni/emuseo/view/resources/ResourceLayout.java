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
package me.uni.emuseo.view.resources;

import java.util.List;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import me.uni.emuseo.EMuseoUtil;
import me.uni.emuseo.model.resources.ResourceDTO;
import me.uni.emuseo.service.ResourceService;
import me.uni.emuseo.view.common.ExpandingPanel;
import me.uni.emuseo.view.common.helpers.ImagePreviewWindow;
import me.uni.emuseo.view.common.paging.LazyLoadable;
import me.uni.emuseo.view.common.paging.PagedTableLayout;

public class ResourceLayout extends VerticalLayout implements View
// , SearchBeanChangeListener<ResourceSearchDTO>
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5422410290307286209L;
	// private ResourceSearchFormLayout resourceSearchFormLayout;
	private ResourceTable resourceTable;
	private PagedTableLayout<ResourceDTO> pagedTableLayout;
	// private Button addButton;
	// private ExpandingPanel searchPanel;
	private ExpandingPanel resourcePanel;
	private ResourceService resourceService;

	public ResourceLayout() {

		resourceService = EMuseoUtil.getAppContext().getBean(ResourceService.class);

		// resourceSearchFormLayout = new ResourceSearchFormLayout();
		// resourceSearchFormLayout.addSearchBeanChangeListener(this);
		// searchPanel = new ExpandingPanel();
		// searchPanel.setContent(resourceSearchFormLayout);
		// searchPanel.setCaption("Wyszukaj zasób");

		createTable();
		resourceTable.setWidth(100, Unit.PERCENTAGE);
		resourceTable.setPageLength(5);
		resourceTable.addStyleName("small");
		pagedTableLayout = new PagedTableLayout<ResourceDTO>(resourceTable, new ResourceLazyLoadable(resourceService));

		resourcePanel = new ExpandingPanel();
		resourcePanel.setContent(pagedTableLayout);
		resourcePanel.setCaption("Zasoby");
		resourcePanel.setExpanded(true);

		// addButton = new Button("Dodaj zasób");
		// addButton.setIcon(FontAwesome.PLUS);
		// addButton.addStyleName("emuseo-button-icon");
		// addButton.addStyleName("emuseo-button-margin");
		// addButton.setWidth(100, Unit.PERCENTAGE);

		// addComponent(searchPanel);
		addComponent(resourcePanel);
		// addComponent(addButton);

		// addButton.addClickListener(new ClickListener() {
		//
		// /**
		// *
		// */
		// private static final long serialVersionUID = 1L;
		//
		// @Override
		// public void buttonClick(ClickEvent event) {
		// ResourcePopUpWindow resourcePopUpWidow = new ResourcePopUpWindow(new
		// ResourceDTO(),
		// "Dodaj użytkownika") {
		// private static final long serialVersionUID = 3776311059670953585L;
		//
		// @Override
		// protected boolean onSave(ResourceDTO bean) throws
		// InvalidBeanException {
		// resourceService.addResource(bean);
		// pagedTableLayout.refresh();
		// return true;
		// }
		// };
		// UI.getCurrent().addWindow(resourcePopUpWidow);
		// }
		// });
	}

	private void createTable() {
		resourceTable = new ResourceTable() {

			private static final long serialVersionUID = 4201722098147104008L;

			// @Override
			// protected void onEdit(ResourceDTO itemId) {
			// ResourceDTO exhibit =
			// resourceService.getResource(itemId.getResourceId());
			// ResourcePopUpWindow resourcePopUpWidow = new
			// ResourcePopUpWindow(exhibit, "Edytuj użytkownika") {
			// private static final long serialVersionUID =
			// 3776311059670953584L;
			//
			// @Override
			// protected boolean onSave(ResourceDTO bean) throws
			// InvalidBeanException {
			// resourceService.editResource(bean);
			// pagedTableLayout.refreshPage();
			// return true;
			// }
			// };
			// UI.getCurrent().addWindow(resourcePopUpWidow);
			// }

			// @Override
			// protected void onDelete(final ResourceDTO itemId) {
			// ConfirmationWindow confirmationWindow = new
			// ConfirmationWindow("Usuń użytkownika",
			// "Czy na pewno chcesz usunąć użytkownika: " +
			// itemId.getFirstName() + " " + itemId.getLastName()
			// + "?") {
			//
			// private static final long serialVersionUID = -39445547305022448L;
			//
			// @Override
			// protected boolean onConfirm() {
			// resourceService.deleteResource(itemId.getResourceId());
			// pagedTableLayout.refresh();
			// return true;
			// }
			// };
			// UI.getCurrent().addWindow(confirmationWindow);
			// }
			@Override
			protected void onShow(ResourceDTO itemId) {
				String pathByExhibitId = resourceService.getResourceLinkById(itemId.getResourceId());
				ImagePreviewWindow imagePreviewWindow = new ImagePreviewWindow(pathByExhibitId);
				UI.getCurrent().addWindow(imagePreviewWindow);
			}
		};
	}

	@Override
	public void enter(ViewChangeEvent event) {
	}

	// @Override
	// public void onSearchBeanChange(SearchBeanChangeEvent<ResourceSearchDTO>
	// event) {
	// pagedTableLayout.refresh();
	// }

	// @Override
	// public void onSearchBeanClear(SearchBeanClearEvent<ResourceSearchDTO>
	// event) {
	// pagedTableLayout.refresh();
	// }

	private final class ResourceLazyLoadable implements LazyLoadable<ResourceDTO> {

		private ResourceService resourceService;

		public ResourceLazyLoadable(ResourceService resourceService) {
			this.resourceService = resourceService;
		}

		@Override
		public int getItemsCount() {
			// ResourceSearchDTO searchBean =
			// resourceSearchFormLayout.getSearchBean();
			return resourceService.getResourcesCount().intValue();
		}

		@Override
		public List<ResourceDTO> getItems(int startIndex, int count) {
			// ResourceSearchDTO searchBean =
			// resourceSearchFormLayout.getSearchBean();
			return resourceService.getResources(startIndex, count);
		}
	}

}
