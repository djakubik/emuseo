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
package me.uni.emuseo.view.exhibits;

import java.util.List;

import me.uni.emuseo.EMuseoUtil;
import me.uni.emuseo.model.exhibits.ExhibitDTO;
import me.uni.emuseo.model.exhibits.ExhibitSearchDTO;
import me.uni.emuseo.model.resources.ResourceAddEditDTO;
import me.uni.emuseo.service.ExhibitService;
import me.uni.emuseo.service.ResourceService;
import me.uni.emuseo.view.common.ExpandingPanel;
import me.uni.emuseo.view.common.form.InvalidBeanException;
import me.uni.emuseo.view.common.helpers.ConfirmationWindow;
import me.uni.emuseo.view.common.helpers.ImagePreviewWindow;
import me.uni.emuseo.view.common.paging.LazyLoadable;
import me.uni.emuseo.view.common.paging.PagedTableLayout;
import me.uni.emuseo.view.common.search.SearchBeanChangeEvent;
import me.uni.emuseo.view.common.search.SearchBeanChangeListener;
import me.uni.emuseo.view.common.search.SearchBeanClearEvent;
import me.uni.emuseo.view.exhibits.form.ExhibitSearchFormLayout;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class ExhibitLayout extends VerticalLayout implements View, SearchBeanChangeListener<ExhibitSearchDTO> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1336543483525792809L;
	private ExhibitSearchFormLayout exhibitSearchFormLayout;
	private ExhibitTable exhibitTable;
	private PagedTableLayout<ExhibitDTO> pagedTableLayout;
	private Button addButton;
	private ExpandingPanel searchPanel;
	private ExpandingPanel exhibitPanel;
	private ExhibitService exhibitService;
	private ResourceService resourceService;

	public ExhibitLayout() {

		exhibitService = EMuseoUtil.getAppContext().getBean(ExhibitService.class);
		resourceService = EMuseoUtil.getAppContext().getBean(ResourceService.class);

		exhibitSearchFormLayout = new ExhibitSearchFormLayout();
		exhibitSearchFormLayout.addSearchBeanChangeListener(this);
		searchPanel = new ExpandingPanel();
		searchPanel.setContent(exhibitSearchFormLayout);
		searchPanel.setCaption("Wyszukaj eksponat");

		createTable();
		exhibitTable.setWidth(100, Unit.PERCENTAGE);
		exhibitTable.setPageLength(5);
		exhibitTable.addStyleName("small");
		pagedTableLayout = new PagedTableLayout<ExhibitDTO>(exhibitTable, new ExhibitLazyLoadable(exhibitService));

		exhibitPanel = new ExpandingPanel();
		exhibitPanel.setContent(pagedTableLayout);
		exhibitPanel.setCaption("Eksponaty");
		exhibitPanel.setExpanded(true);

		addButton = new Button("Dodaj eksponat");
		addButton.setIcon(FontAwesome.PLUS);
		addButton.addStyleName("emuseo-button-icon");
		addButton.addStyleName("emuseo-button-margin");
		addButton.setWidth(100, Unit.PERCENTAGE);

		addComponent(searchPanel);
		addComponent(exhibitPanel);
		addComponent(addButton);

		addButton.addClickListener(new ClickListener() {

			private static final long serialVersionUID = -263205007196895260L;

			@Override
			public void buttonClick(ClickEvent event) {
				ExhibitPopUpWindow exhibitPopUpWidow = new ExhibitPopUpWindow(new ExhibitDTO(), "Dodaj eksponat") {
					private static final long serialVersionUID = 3776311059670953583L;

					@Override
					protected boolean onSave(ExhibitDTO bean) throws InvalidBeanException {
						exhibitService.addExhibit(bean);
						pagedTableLayout.refresh();
						return true;
					}
				};
				UI.getCurrent().addWindow(exhibitPopUpWidow);
			}
		});
	}

	private void createTable() {
		exhibitTable = new ExhibitTable() {

			private static final long serialVersionUID = 4201722098147104008L;

			@Override
			protected void onEdit(ExhibitDTO itemId) {
				ExhibitDTO exhibit = exhibitService.getExhibit(itemId.getExhibitId());
				ExhibitPopUpWindow exhibitPopUpWidow = new ExhibitPopUpWindow(exhibit, "Edytuj eksponat") {
					private static final long serialVersionUID = 3776311059670953584L;

					@Override
					protected boolean onSave(ExhibitDTO bean) throws InvalidBeanException {
						exhibitService.editExhibit(bean);
						pagedTableLayout.refreshPage();
						return true;
					}
				};
				UI.getCurrent().addWindow(exhibitPopUpWidow);
			}

			@Override
			protected void onDelete(final ExhibitDTO itemId) {
				ConfirmationWindow confirmationWindow = new ConfirmationWindow("Usuń eksponat",
						"Czy na pewno chcesz usunąć eksponat: " + itemId.getExhibitName() + "?") {

					private static final long serialVersionUID = -39445547305022448L;

					@Override
					protected boolean onConfirm() {
						exhibitService.deleteExhibit(itemId.getExhibitId());
						pagedTableLayout.refresh();
						return true;
					}
				};
				UI.getCurrent().addWindow(confirmationWindow);
			}

			@Override
			protected void onImageEdit(ExhibitDTO itemId) {
				ExhibitImageEditWindow exhibitPopUpWidow = new ExhibitImageEditWindow(itemId,
						"Edytuj obraz eksponatu") {
					private static final long serialVersionUID = 3776311059670953584L;

					@Override
					protected boolean onSave(ExhibitDTO bean, ResourceAddEditDTO resourceDTO) {
						exhibitService.editExhibitImage(bean.getExhibitId(), resourceDTO);
						// pagedTableLayout.refreshPage();
						return true;
					}
				};
				UI.getCurrent().addWindow(exhibitPopUpWidow);
			}

			@Override
			protected void onShow(ExhibitDTO itemId) {
				String pathByExhibitId = resourceService.getResourceLinkByExhibitId(itemId.getExhibitId());
				ImagePreviewWindow imagePreviewWindow = new ImagePreviewWindow(pathByExhibitId);
				UI.getCurrent().addWindow(imagePreviewWindow);
			}
		};
	}

	@Override
	public void enter(ViewChangeEvent event) {
	}

	@Override
	public void onSearchBeanChange(SearchBeanChangeEvent<ExhibitSearchDTO> event) {
		pagedTableLayout.refresh();
	}

	@Override
	public void onSearchBeanClear(SearchBeanClearEvent<ExhibitSearchDTO> event) {
		pagedTableLayout.refresh();
	}

	private final class ExhibitLazyLoadable implements LazyLoadable<ExhibitDTO> {

		private ExhibitService exhibitService;

		public ExhibitLazyLoadable(ExhibitService exhibitService) {
			this.exhibitService = exhibitService;
		}

		@Override
		public int getItemsCount() {
			ExhibitSearchDTO searchBean = exhibitSearchFormLayout.getSearchBean();
			return exhibitService.getExhibitsCount(searchBean).intValue();
		}

		@Override
		public List<ExhibitDTO> getItems(int startIndex, int count) {
			ExhibitSearchDTO searchBean = exhibitSearchFormLayout.getSearchBean();
			return exhibitService.getExhibits(startIndex, count, searchBean);
		}
	}
}
