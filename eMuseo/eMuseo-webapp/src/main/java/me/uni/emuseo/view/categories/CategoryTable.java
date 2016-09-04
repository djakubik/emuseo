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

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.Action;
import com.vaadin.event.Action.Handler;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.PaintException;
import com.vaadin.server.PaintTarget;
import com.vaadin.ui.Component;
import com.vaadin.ui.Table;

import me.uni.emuseo.model.categories.CategoryDTO;
import me.uni.emuseo.view.common.paging.OrderedTable;

public class CategoryTable extends Table implements OrderedTable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4259620007270745765L;
	private BeanItemContainer<CategoryDTO> beans;

	public CategoryTable() {

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
					onEdit((CategoryDTO) target);
					break;
				case "Usuń":
					onDelete((CategoryDTO) target);
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
		setItemDescriptionGenerator(new CategoryDescriptionGenerator());

		beans = new BeanItemContainer<CategoryDTO>(CategoryDTO.class);
		setContainerDataSource(beans);
		setVisibleColumns(new Object[] { "name", "exhibitsCount" });
		setColumnHeaders("Nazwa", "Ilość eksponatów");

	}

	protected void onEdit(CategoryDTO itemId) {
	}

	protected void onDelete(CategoryDTO itemId) {
	}

	private final class CategoryDescriptionGenerator implements ItemDescriptionGenerator {
		/**
		 * 
		 */
		private static final long serialVersionUID = 5411457990079259107L;

		@Override
		public String generateDescription(Component source, Object itemId, Object propertyId) {
			CategoryDTO categoryDTO = (CategoryDTO) itemId;
			String description = "<h2>" + categoryDTO.getName() + "</h2>";
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
