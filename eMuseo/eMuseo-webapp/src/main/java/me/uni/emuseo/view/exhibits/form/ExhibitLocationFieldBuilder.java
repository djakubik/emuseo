package me.uni.emuseo.view.exhibits.form;

import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Field;

import me.uni.emuseo.model.exhibits.ExhibitLocation;
import me.uni.emuseo.view.common.helpers.FieldBuilder;
import me.uni.emuseo.view.exhibits.util.ExhibitLocationTranslator;

public class ExhibitLocationFieldBuilder implements FieldBuilder {

	@Override
	public Field<?> build(String propertyId) {
		if ("exhibitLocation".equals(propertyId)) {
			ComboBox cb = new ComboBox();
			cb.setNullSelectionAllowed(false);
			for (ExhibitLocation exhLoc : ExhibitLocation.values()) {
				cb.addItem(exhLoc);
				cb.setItemCaption(exhLoc, ExhibitLocationTranslator.getTranslation(exhLoc));
			}
			return cb;
		}
		return null;
	}

}
