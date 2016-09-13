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
package me.uni.emuseo.view.menu;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.FontIcon;
import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.themes.ValoTheme;

import me.uni.emuseo.EMuseoUtil;
import me.uni.emuseo.model.users.UserDetailsDTO;
import me.uni.emuseo.service.AuthManager;
import me.uni.emuseo.service.Permissions;
import me.uni.emuseo.view.categories.CategoryLayout;
import me.uni.emuseo.view.exhibits.ExhibitLayout;
import me.uni.emuseo.view.resources.ResourceLayout;
import me.uni.emuseo.view.settings.MyAccountView;
import me.uni.emuseo.view.settings.SettingsView;
import me.uni.emuseo.view.users.UserLayout;

public class MenuView extends ValoMenuLayout implements View {

	private static final long serialVersionUID = -7108622286179543898L;

	private Resource defaultIcon;
	private AuthManager authManager;

	private Navigator navigator;
	private CssLayout menu = new CssLayout();
	private CssLayout menuItemsLayout = new CssLayout();
	private final LinkedHashMap<String, String> menuItems = new LinkedHashMap<String, String>();
	private MenuItem settingsItem;

	public MenuView() {
		init();
	}

	protected void init() {
		defaultIcon = new ThemeResource("img/profile-pic-300px.jpg");
		authManager = EMuseoUtil.getAppContext().getBean(AuthManager.class);
		this.setWidth("100%");
		this.addMenu(buildMenu());
		reloadUserDetails();
	}

	private CssLayout buildMenu() {
		// Add items
		if (authManager.isAuthorizedTo(Permissions.MENU_USERS_VIEW)) {
			menuItems.put(Permissions.MENU_USERS_VIEW, "Użytkownicy");
		}
		if (authManager.isAuthorizedTo(Permissions.MENU_EXHIBIT_VIEW)) {
			menuItems.put(Permissions.MENU_EXHIBIT_VIEW, "Katalog eksponatów");
		}
		if (authManager.isAuthorizedTo(Permissions.MENU_CATEGORIES_VIEW)) {
			menuItems.put(Permissions.MENU_CATEGORIES_VIEW, "Kategorie");
		}
		if (authManager.isAuthorizedTo(Permissions.MENU_RESOURCES_VIEW)) {
			menuItems.put(Permissions.MENU_RESOURCES_VIEW, "Zasoby");
		}

		final HorizontalLayout top = new HorizontalLayout();
		top.setWidth("100%");
		top.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
		top.addStyleName("valo-menu-title");
		menu.addComponent(top);
		final Button showMenu = new Button("Menu", new ClickListener() {
			private static final long serialVersionUID = -719702284721453362L;

			@Override
			public void buttonClick(final ClickEvent event) {
				if (menu.getStyleName().contains("valo-menu-visible")) {
					menu.removeStyleName("valo-menu-visible");
				} else {
					menu.addStyleName("valo-menu-visible");
				}
			}
		});
		showMenu.addStyleName(ValoTheme.BUTTON_PRIMARY);
		showMenu.addStyleName(ValoTheme.BUTTON_SMALL);
		showMenu.addStyleName("valo-menu-toggle");
		showMenu.setIcon(FontAwesome.LIST);
		menu.addComponent(showMenu);
		final Label title = new Label("<h3>e<strong>Museo</strong></h3>", ContentMode.HTML);
		title.setSizeUndefined();
		top.addComponent(title);
		top.setExpandRatio(title, 1);
		final MenuBar settings = new MenuBar();
		settings.addStyleName("user-menu");

		settingsItem = settings.addItem("Jan Kowalski", defaultIcon, null);
		if (authManager.isAuthorizedTo(Permissions.MENU_MY_ACCOUNT_VIEW)) {
			settingsItem.addItem("Moje konto", new MenuBar.Command() {
				private static final long serialVersionUID = 7015035735144235104L;

				@Override
				public void menuSelected(MenuItem selectedItem) {
					navigator.navigateTo(Permissions.MENU_MY_ACCOUNT_VIEW);
				}

			});
		}
		if (authManager.isAuthorizedTo(Permissions.MENU_SETTINGS_VIEW)) {
			settingsItem.addItem("Ustawienia", new MenuBar.Command() {
				private static final long serialVersionUID = 7015035735144235105L;

				@Override
				public void menuSelected(MenuItem selectedItem) {
					navigator.navigateTo(Permissions.MENU_SETTINGS_VIEW);
				}

			});
		}
		settingsItem.addSeparator();
		settingsItem.addItem("Wyloguj", new MenuBar.Command() {
			private static final long serialVersionUID = 1333473616079310225L;

			@Override
			public void menuSelected(MenuItem selectedItem) {
				final AuthManager authManager = EMuseoUtil.getAppContext().getBean(AuthManager.class);
				authManager.logout();
			}
		});
		menu.addComponent(settings);
		menuItemsLayout.setPrimaryStyleName("valo-menuitems");
		menu.addComponent(menuItemsLayout);

		for (final Entry<String, String> item : menuItems.entrySet()) {
			FontIcon icon = null;
			if (item.getKey().equals(Permissions.MENU_USERS_VIEW)) {
				icon = FontAwesome.USERS;
			} else if (item.getKey().endsWith(Permissions.MENU_EXHIBIT_VIEW)) {
				icon = FontAwesome.UNIVERSITY;
			} else if (item.getKey().endsWith(Permissions.MENU_CATEGORIES_VIEW)) {
				icon = FontAwesome.ARCHIVE;
			} else if (item.getKey().endsWith(Permissions.MENU_RESOURCES_VIEW)) {
				icon = FontAwesome.IMAGE;
			}
			final Button b = new Button(item.getValue(), new ClickListener() {
				private static final long serialVersionUID = -7089398070311521853L;

				@Override
				public void buttonClick(final ClickEvent event) {
					navigator.navigateTo(item.getKey());
				}
			});
			b.setHtmlContentAllowed(true);
			b.setPrimaryStyleName("valo-menu-item");
			if (icon != null) {
				b.setIcon(icon);
			}
			menuItemsLayout.addComponent(b);
		}
		return menu;
	}

	public void setNavigator(final Navigator navigator) {
		this.navigator = navigator;
		final ViewChangeListener viewChangeListener = new ViewChangeListener() {
			private static final long serialVersionUID = -4564885643037277448L;

			@Override
			public boolean beforeViewChange(final ViewChangeEvent event) {
				return true;
			}

			@Override
			public void afterViewChange(final ViewChangeEvent event) {
				for (final Iterator<Component> it = menuItemsLayout.iterator(); it.hasNext();) {
					it.next().removeStyleName("selected");
				}
				String fullViewName = event.getViewName() + '/' + event.getParameters();
				for (final Entry<String, String> item : menuItems.entrySet()) {
					if (fullViewName.equals(item.getKey())) {
						for (final Iterator<Component> it = menuItemsLayout.iterator(); it.hasNext();) {
							final Component c = it.next();
							if (c.getCaption() != null && c.getCaption().startsWith(item.getValue())) {
								c.addStyleName("selected");
								break;
							}
						}
						break;
					}
				}
				menu.removeStyleName("valo-menu-visible");
			}
		};
		navigator.addViewChangeListener(viewChangeListener);
		addDetachListener(new DetachListener() {
			private static final long serialVersionUID = -8082565032894897397L;

			@Override
			public void detach(DetachEvent event) {
				navigator.removeViewChangeListener(viewChangeListener);
			}
		});
	}

	@Override
	public void enter(ViewChangeEvent event) {
		setNavigator(event.getNavigator());
		String parameters = event.getParameters();

		ComponentContainer content = getContentContainer();
		if (parameters == null || parameters.isEmpty()) {
			content.addComponent(new MenuStartLayout());
		} else if (Permissions.USERS_VIEW.equals(parameters)) {
			content.addComponent(new UserLayout());
		} else if (Permissions.EXHIBITS_VIEW.equals(parameters)) {
			content.addComponent(new ExhibitLayout());
		} else if (Permissions.CATEGORIES_VIEW.equals(parameters)) {
			content.addComponent(new CategoryLayout());
		} else if (Permissions.RESOURCES_VIEW.equals(parameters)) {
			content.addComponent(new ResourceLayout());
		} else if (Permissions.MY_ACCOUNT_VIEW.equals(parameters)) {
			content.addComponent(new MyAccountView());
		} else if (Permissions.SETTINGS_VIEW.equals(parameters)) {
			content.addComponent(new SettingsView());
		} else {
			content.addComponent(new Label("Błędna strona eMuseo?"));
		}
	}

	public void reloadUserDetails() {

		UserDetailsDTO userDetails = authManager.getUserDetails();
		settingsItem.setText(userDetails.getFullName());

		EMuseoUtil.refreshTheme(userDetails);

		if (userDetails.getImage() != null && userDetails.getImage().length > 0) {
		} else {
			settingsItem.setIcon(defaultIcon);
		}

	}
}
