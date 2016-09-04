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
package me.uni.emuseo.view.users;

import java.util.List;

import me.uni.emuseo.EMuseoUtil;
import me.uni.emuseo.model.users.UserDTO;
import me.uni.emuseo.model.users.UserSearchDTO;
import me.uni.emuseo.service.UserService;
import me.uni.emuseo.view.common.ExpandingPanel;
import me.uni.emuseo.view.common.form.InvalidBeanException;
import me.uni.emuseo.view.common.helpers.ConfirmationWindow;
import me.uni.emuseo.view.common.paging.LazyLoadable;
import me.uni.emuseo.view.common.paging.PagedTableLayout;
import me.uni.emuseo.view.common.search.SearchBeanChangeEvent;
import me.uni.emuseo.view.common.search.SearchBeanChangeListener;
import me.uni.emuseo.view.common.search.SearchBeanClearEvent;
import me.uni.emuseo.view.users.form.UserSearchFormLayout;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class UserLayout extends VerticalLayout implements View, SearchBeanChangeListener<UserSearchDTO> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5422410290307286209L;
	private UserSearchFormLayout userSearchFormLayout;
	private UserTable userTable;
	private PagedTableLayout<UserDTO> pagedTableLayout;
	private Button addButton;
	private ExpandingPanel searchPanel;
	private ExpandingPanel userPanel;
	private UserService userService;

	public UserLayout() {

		userService = EMuseoUtil.getAppContext().getBean(UserService.class);

		userSearchFormLayout = new UserSearchFormLayout();
		userSearchFormLayout.addSearchBeanChangeListener(this);
		searchPanel = new ExpandingPanel();
		searchPanel.setContent(userSearchFormLayout);
		searchPanel.setCaption("Wyszukaj użytkownika");

		createTable();
		userTable.setWidth(100, Unit.PERCENTAGE);
		userTable.setPageLength(5);
		userTable.addStyleName("small");
		pagedTableLayout = new PagedTableLayout<UserDTO>(userTable, new UserLazyLoadable(userService));

		userPanel = new ExpandingPanel();
		userPanel.setContent(pagedTableLayout);
		userPanel.setCaption("Użytkownicy");
		userPanel.setExpanded(true);

		addButton = new Button("Dodaj użytkownika");
		addButton.setIcon(FontAwesome.PLUS);
		addButton.addStyleName("emuseo-button-icon");
		addButton.addStyleName("emuseo-button-margin");
		addButton.setWidth(100, Unit.PERCENTAGE);

		addComponent(searchPanel);
		addComponent(userPanel);
		addComponent(addButton);

		addButton.addClickListener(new ClickListener() {


			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				UserPopUpWindow userPopUpWidow = new UserPopUpWindow(new UserDTO(), "Dodaj użytkownika") {
					private static final long serialVersionUID = 3776311059670953585L;

					@Override
					protected boolean onSave(UserDTO bean) throws InvalidBeanException {
						userService.addUser(bean);
						pagedTableLayout.refresh();
						return true;
					}
				};
				UI.getCurrent().addWindow(userPopUpWidow);
			}
		});
	}

	private void createTable() {
		userTable = new UserTable() {

			private static final long serialVersionUID = 4201722098147104008L;

			@Override
			protected void onEdit(UserDTO itemId) {
				UserDTO exhibit = userService.getUser(itemId.getUserId());
				UserPopUpWindow userPopUpWidow = new UserPopUpWindow(exhibit, "Edytuj użytkownika") {
					private static final long serialVersionUID = 3776311059670953584L;

					@Override
					protected boolean onSave(UserDTO bean) throws InvalidBeanException {
						userService.editUser(bean);
						pagedTableLayout.refreshPage();
						return true;
					}
				};
				UI.getCurrent().addWindow(userPopUpWidow);
			}

			@Override
			protected void onDelete(final UserDTO itemId) {
				ConfirmationWindow confirmationWindow = new ConfirmationWindow("Usuń użytkownika",
						"Czy na pewno chcesz usunąć użytkownika: " + itemId.getFirstName()+" "+itemId.getLastName() + "?") {

					private static final long serialVersionUID = -39445547305022448L;

					@Override
					protected boolean onConfirm() {
						userService.deleteUser(itemId.getUserId());
						pagedTableLayout.refresh();
						return true;
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
	public void onSearchBeanChange(SearchBeanChangeEvent<UserSearchDTO> event) {
		pagedTableLayout.refresh();
	}

	@Override
	public void onSearchBeanClear(SearchBeanClearEvent<UserSearchDTO> event) {
		pagedTableLayout.refresh();
	}

	private final class UserLazyLoadable implements LazyLoadable<UserDTO> {

		private UserService userService;

		public UserLazyLoadable(UserService userService) {
			this.userService = userService;
		}

		@Override
		public int getItemsCount() {
			UserSearchDTO searchBean = userSearchFormLayout.getSearchBean();
			return userService.getUsersCount(searchBean).intValue();
		}

		@Override
		public List<UserDTO> getItems(int startIndex, int count) {
			UserSearchDTO searchBean = userSearchFormLayout.getSearchBean();
			return userService.getUsers(startIndex, count, searchBean);
		}
	}

}
