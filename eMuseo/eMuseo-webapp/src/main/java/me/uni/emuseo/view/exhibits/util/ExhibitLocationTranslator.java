package me.uni.emuseo.view.exhibits.util;

import me.uni.emuseo.model.exhibits.ExhibitLocation;

public class ExhibitLocationTranslator {

	public static String getTranslation(ExhibitLocation exhibitLocation) {
		switch (exhibitLocation) {
		case EXHIBITION:
			return "Wystawa";
		case ARCHIVES:
			return "Archiwum";
		case WAREHOUSE:
			return "Magazyn";
		default:
			return "Nieznana";
		}
	}
}
