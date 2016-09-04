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
package me.uni.emuseo.view.common.form;

import java.util.List;

import me.uni.emuseo.view.common.search.SearchBeanChangeListener;
import me.uni.emuseo.view.common.search.SearchBeanEventPropagator;

import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Layout;

public abstract class SearchFormLayout<T> extends CssLayout {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2658201930021894432L;
	private SearchBeanEventPropagator<T> eventPropagator;
	private FormBuilder<T> formBuilder;

	public SearchFormLayout() {
		init();
	}

	public abstract T getBean();

	protected abstract List<String>[] configureFormBuilder(T initBean, FormBuilder<T> formBuilder);

	protected void init() {
		eventPropagator = new SearchBeanEventPropagator<T>();
		formBuilder = new FormBuilder<T>();

		T initBean = getBean();
		List<String>[] fields = configureFormBuilder(initBean, formBuilder);
		formBuilder.setBean(initBean, fields);
		Layout formContent = formBuilder.getContent();

		CssLayout buttonsLayout = createButtonsLayout();

		addComponents(formContent, buttonsLayout);
		addStyleName("search-layout");
	}

	private CssLayout createButtonsLayout() {
		CssLayout buttonsLayout = new CssLayout();
		Button searchButton = new Button();
		searchButton.addStyleName("primary");
		searchButton.setIcon(FontAwesome.SEARCH);
		searchButton.addClickListener(new Button.ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -8034803606737706285L;

			@Override
			public void buttonClick(ClickEvent event) {
				try {
					formBuilder.commit();
					eventPropagator.fireChangeEvent(formBuilder.getBean());
				} catch (CommitException e) {
					e.printStackTrace();
				}
			}
		});
		Button clearButton = new Button();
		clearButton.addStyleName("primary");
		clearButton.setIcon(FontAwesome.ERASER);
		clearButton.addClickListener(new Button.ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -8684340029871714722L;

			@Override
			public void buttonClick(ClickEvent event) {
				T bean = getBean();
				formBuilder.setBean(bean);
				eventPropagator.fireClearEvent(formBuilder.getBean());
			}

		});
		buttonsLayout.addComponents(clearButton, searchButton);
		buttonsLayout.setSizeFull();
		buttonsLayout.addStyleName("buttons-layout");
		return buttonsLayout;
	}

	public void setSearchBean(T bean) {
		formBuilder.setBean(bean);
		eventPropagator.fireChangeEvent(formBuilder.getBean());
	}

	public T getSearchBean() {
		return formBuilder.getBean();
	}

	public void addSearchBeanChangeListener(SearchBeanChangeListener<T> listener) {
		eventPropagator.addSearchBeanChangeListener(listener);
	}

	public void removeSearchBeanChangeListener(SearchBeanChangeListener<T> listener) {
		eventPropagator.removeSearchBeanChangeListener(listener);
	}

}
