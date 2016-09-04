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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.uni.emuseo.view.common.helpers.BeanFieldGroupFixed;
import me.uni.emuseo.view.common.helpers.FieldBuilder;
import me.uni.emuseo.view.common.helpers.FieldConfigurator;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Layout;

public class FormBuilder<BEANTYPE> {

	protected Layout content;
	protected BeanFieldGroup<BEANTYPE> fieldGroup;
	protected FieldLayoutManager fieldLayoutManager;
	protected BEANTYPE bean;
	protected List<String>[] visibleFields;
	protected List<FieldBuilder> fieldBuilders;
	protected List<FieldConfigurator> fieldConfigurators;
	protected boolean built;

	public FormBuilder() {
		init();
	}

	protected void init() {
		this.content = createContent();
		this.fieldLayoutManager = createFieldLayoutManager();
		this.fieldBuilders = new ArrayList<FieldBuilder>(1);
		this.fieldConfigurators = new ArrayList<FieldConfigurator>(1);
		this.built = false;
		// this.fieldGroup = createFieldGroup();
	}

	protected Layout createContent() {
		CssLayout content = new CssLayout();
		return content;
	}

	protected FieldLayoutManager createFieldLayoutManager() {
		return new FieldLayoutManager();
	}

	@SuppressWarnings("unchecked")
	public BeanFieldGroup<BEANTYPE> getFieldGroup() {
		if (fieldGroup == null && bean != null) {
			fieldGroup = createFieldGroup((Class<BEANTYPE>) bean.getClass());
		}
		return fieldGroup;
	}

	protected BeanFieldGroup<BEANTYPE> createFieldGroup(Class<BEANTYPE> clazz) {
		return new BeanFieldGroupFixed<BEANTYPE>(clazz);
	}

	public void setVisibleFields(List<String>[] visibleFields) {
		this.visibleFields = visibleFields;
		fieldLayoutManager.setFields(visibleFields);
		if (built) {
			rebuildContent();
		}
	}

	public void commit() throws CommitException {
		fieldGroup.commit();
		afterCommit();
	}

	protected void afterCommit() {
	}

	public void discard() {
		fieldGroup.discard();
		afterDiscard();
	}

	protected void afterDiscard() {
	}

	/**
	 * wstawia bean do zawartości, wyświetla już
	 * 
	 * @param bean
	 */
	public void setBean(BEANTYPE bean) {
		this.bean = bean;
		rebuildContent();
	}

	/**
	 * wstawia bean do zawartości, wyświetla już
	 * 
	 * @param bean
	 * @param visibleFields
	 */
	@SuppressWarnings("unchecked")
	public void setBean(BEANTYPE bean, List<String> visibleFields) {
		setBean(bean, new List[] { visibleFields });
	}

	public void setBean(BEANTYPE bean, List<String>[] visibleFields) {
		this.bean = bean;
		this.visibleFields = visibleFields;
		fieldLayoutManager.setFields(visibleFields);
		rebuildContent();
	}

	protected void rebuildContent() {
		content.removeAllComponents();
		BeanFieldGroup<BEANTYPE> fieldGroup = getFieldGroup();
		fieldGroup.setItemDataSource(this.bean);
		Collection<Object> boundPropertyIds = new ArrayList<Object>(fieldGroup.getBoundPropertyIds());
		for (Object propertyId : boundPropertyIds) {
			fieldGroup.unbind(fieldGroup.getField(propertyId));
		}
		fieldLayoutManager.clearLayouts();
		for (List<String> list : this.visibleFields) {
			for (String visibleField : list) {
				Field<?> builtField = null;
				for (FieldBuilder fieldBuilder : fieldBuilders) {
					builtField = fieldBuilder.build(visibleField);
					if (builtField != null) {
						break;
					}
				}
				if (builtField == null) {
					builtField = fieldGroup.buildAndBind(visibleField);
				} else {
					fieldGroup.bind(builtField, visibleField);
				}
				for (FieldConfigurator fieldConfigurator : fieldConfigurators) {
					boolean fullyConfigured = fieldConfigurator.configure(builtField, visibleField);
					if (fullyConfigured) {
						break;
					}
				}
				Layout layoutForField = fieldLayoutManager.getLayoutForField(visibleField);
				layoutForField.addComponent(builtField);
			}
		}
		for (Layout layout : fieldLayoutManager.getLayouts()) {
			content.addComponent(layout);
		}
		built = true;
	}

	public BEANTYPE getBean() {
		return bean;
	}

	public Layout getContent() {
		return content;
	}

	public void addFieldBuilder(FieldBuilder fieldBuilder) {
		fieldBuilders.add(fieldBuilder);
	}

	public void addFieldConfigurator(FieldConfigurator fieldConfigurator) {
		fieldConfigurators.add(fieldConfigurator);
	}

	public void removeFieldBuilder(FieldBuilder fieldBuilder) {
		fieldBuilders.remove(fieldBuilder);
	}

	public void removeFieldConfigurator(FieldConfigurator fieldConfigurator) {
		fieldConfigurators.remove(fieldConfigurator);
	}

	public class FieldLayoutManager {

		protected Map<String, Integer> fieldToLayoutMap;
		protected Layout[] layouts;

		public FieldLayoutManager() {
			fieldToLayoutMap = new HashMap<String, Integer>();
			layouts = new Layout[0];
		}

		public void setFields(List<String>[] fields) {
			fieldToLayoutMap.clear();
			layouts = new Layout[fields.length];
			for (int i = 0; i < fields.length; i++) {
				List<String> list = fields[i];
				for (String field : list) {
					fieldToLayoutMap.put(field, i);
				}

			}
		}

		public Layout getLayoutForField(String field) {
			Integer layoutId = fieldToLayoutMap.get(field);
			if (layoutId == null) {
				return null;
			}
			Layout layout = layouts[layoutId];
			if (layout == null) {
				layout = createLayout();
				layouts[layoutId] = layout;
			}
			return layout;
		}

		public void clearLayouts() {
			for (Layout layout : layouts) {
				if (layout != null) {
					layout.removeAllComponents();
				}
			}
		}

		public List<Layout> getLayouts() {
			return Arrays.asList(layouts);
		}

		protected Layout createLayout() {
			FormLayout layout = new FormLayout();
			layout.setSizeUndefined();
			layout.setMargin(new MarginInfo(false, false, true, false));
			return layout;
		}

	}

}
