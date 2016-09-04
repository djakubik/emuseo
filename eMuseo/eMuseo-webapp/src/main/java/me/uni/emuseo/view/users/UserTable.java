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

import me.uni.emuseo.model.users.UserDTO;
import me.uni.emuseo.view.common.paging.OrderedTable;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.Action;
import com.vaadin.event.Action.Handler;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.PaintException;
import com.vaadin.server.PaintTarget;
import com.vaadin.ui.Component;
import com.vaadin.ui.Table;

public class UserTable extends Table implements OrderedTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4012001831755114771L;
	private BeanItemContainer<UserDTO> beans;

	public UserTable() {

		setSortEnabled(false);
		addActionHandler(new Handler() {

			private static final long serialVersionUID = -1458601748782504737L;

			@Override
			public void handleAction(Action action, Object sender, Object target) {
				if (target == null){
					return;
				}
				switch (action.getCaption()) {
				case "Edytuj":
					onEdit((UserDTO) target);
					break;
				case "Usuń":
					onDelete((UserDTO) target);
					break;
				default:
					break;
				}
			}

			@Override
			public Action[] getActions(Object target, Object sender) {

				Action actionEdit = new Action("Edytuj", FontAwesome.EDIT);
				Action actionDelete = new Action("Usuń", FontAwesome.TRASH_O);
				return new Action[] { actionEdit, actionDelete };
			}
		});
		setItemDescriptionGenerator(new UserDescriptionGenerator());

		beans = new BeanItemContainer<UserDTO>(UserDTO.class);
		setContainerDataSource(beans);
		setVisibleColumns("firstName", "lastName", "login", "emailAddress", "phoneNumber", "userType");
		setColumnHeaders("Imię", "Nazwisko", "Login", "Adres e-mail", "Nr telefonu", "Typ konta");

	}

	protected void onEdit(UserDTO itemId) {
	}

	protected void onDelete(UserDTO itemId) {
	}

	private final class UserDescriptionGenerator implements ItemDescriptionGenerator {
		/**
		 * 
		 */
		private static final long serialVersionUID = 5411457990079259107L;

		@Override
		public String generateDescription(Component source, Object itemId, Object propertyId) {
			UserDTO userDTO = (UserDTO) itemId;
			String description = "<h2>" + userDTO.getFirstName() + "</h2><p>" + userDTO.getLastName() + "</p>";
			return description;
		}
	}

	private int offset = 0;

	@Override
	public void setOffset(int offset) {
		this.offset = offset;
	}

	@Override
	protected void paintRowHeader(PaintTarget target, Object[][] cells, int indexInRowbuffer) throws PaintException {
		target.addAttribute("caption", Integer.toString(offset + indexInRowbuffer + 1));
	}

}
