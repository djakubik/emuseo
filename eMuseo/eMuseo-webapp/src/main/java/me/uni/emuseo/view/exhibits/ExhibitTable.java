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

import me.uni.emuseo.model.exhibits.ExhibitDTO;
import me.uni.emuseo.view.common.paging.OrderedTable;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.Action;
import com.vaadin.event.Action.Handler;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.PaintException;
import com.vaadin.server.PaintTarget;
import com.vaadin.ui.Component;
import com.vaadin.ui.Table;

public class ExhibitTable extends Table implements OrderedTable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4259620007270745765L;
	public static final String SHOW_CAPTION = "Pokaż";
	public static final String EDIT_IMAGE_CAPTION = "Edytuj zdjęcie";
	private BeanItemContainer<ExhibitDTO> beans;

	public ExhibitTable() {

		setSortEnabled(false);
		addActionHandler(new Handler() {

			private static final long serialVersionUID = -1458601748782504737L;

			@Override
			public void handleAction(Action action, Object sender, Object target) {
				if (target == null) {
					return;
				}
				switch (action.getCaption()) {
				case "Edytuj":
					onEdit((ExhibitDTO) target);
					break;
				case "Usuń":
					onDelete((ExhibitDTO) target);
					break;
				case EDIT_IMAGE_CAPTION:
					onImageEdit((ExhibitDTO) target);
					break;
				case SHOW_CAPTION:
					onShow((ExhibitDTO) target);
					break;
				default:
					break;
				}
			}

			@Override
			public Action[] getActions(Object target, Object sender) {

				Action actionEdit = new Action("Edytuj", FontAwesome.EDIT);
				Action actionDelete = new Action("Usuń", FontAwesome.TRASH_O);
				Action actionEditImage = new Action(EDIT_IMAGE_CAPTION, FontAwesome.FILE_IMAGE_O);
				Action actionShow = new Action(SHOW_CAPTION, FontAwesome.EYE);
				return new Action[] { actionEdit, actionDelete, actionEditImage, actionShow };
			}
		});
		setItemDescriptionGenerator(new ExhibitDescriptionGenerator());

		beans = new BeanItemContainer<ExhibitDTO>(ExhibitDTO.class);
		setContainerDataSource(beans);
		beans.addNestedContainerBean("exhibitCategory");
		setVisibleColumns(
				new Object[] { "exhibitName", "exhibitNo", "genericYear", "exhibitCategory.name", "exhibitLocation" });
		setColumnHeaders("Nazwa", "Numer kat.", "Wiek w latach", "Kategoria", "Lokalizacja");

	}

	protected void onEdit(ExhibitDTO itemId) {
	}

	protected void onDelete(ExhibitDTO itemId) {
	}

	protected void onImageEdit(ExhibitDTO itemId) {
	}

	protected void onShow(ExhibitDTO itemId) {
	}

	private final class ExhibitDescriptionGenerator implements ItemDescriptionGenerator {
		/**
		 * 
		 */
		private static final long serialVersionUID = 5411457990079259107L;

		@Override
		public String generateDescription(Component source, Object itemId, Object propertyId) {
			ExhibitDTO exhibitDTO = (ExhibitDTO) itemId;
			String description = "<h2>" + exhibitDTO.getExhibitName() + "</h2><p>" + exhibitDTO.getExhibitDesc()
					+ "</p>";
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
