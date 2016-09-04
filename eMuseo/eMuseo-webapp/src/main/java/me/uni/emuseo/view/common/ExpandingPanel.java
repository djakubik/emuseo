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
package me.uni.emuseo.view.common;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

public class ExpandingPanel extends VerticalLayout {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7932244176994357635L;
	protected Component expandingContent;
	protected Button expandingButton;
	protected boolean expanded;

	public ExpandingPanel() {
		expandingButton = new Button();
		expandingButton.addClickListener(new Button.ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -441812999850345526L;

			@Override
			public void buttonClick(ClickEvent event) {
				onToggle();
			}

		});
		expandingButton.setWidth(100, Unit.PERCENTAGE);
		expandingButton.setStyleName("expanding-panel-button");
		setWidth(100, Unit.PERCENTAGE);
		addComponent(expandingButton);
		setExpandRatio(expandingButton, 0);
		this.setSizeFull();
		setStyleName("expanding-panel");
		
		expanded = false;
		expandingButton.setIcon(FontAwesome.CHEVRON_DOWN);
	}

	public void setContent(Component expandingContent) {
		if (expanded) {
			onToggle();
			this.expandingContent = expandingContent;
			onToggle();
		} else {
			this.expandingContent = expandingContent;
		}
	}

	public Component getContent() {
		return expandingContent;
	}

	protected void onToggle() {
		boolean toExpand = !this.expanded;
		if (toExpand) {
			this.addComponent(expandingContent);
			this.setExpandRatio(expandingContent, 1);
			addStyleName("expanded");
			expandingButton.setIcon(FontAwesome.CHEVRON_RIGHT);
		} else {
			this.removeComponent(expandingContent);
			removeStyleName("expanded");
			expandingButton.setIcon(FontAwesome.CHEVRON_DOWN);
		}
		this.expanded = !this.expanded;
	}

	public void setExpanded(boolean expanded) {
		if (this.expanded ^ expanded) {
			onToggle();
		}
	}

	public String getCaption() {
		return expandingButton.getCaption();
	}

	public void setCaption(String caption) {
		expandingButton.setCaption(caption);
	}

}
