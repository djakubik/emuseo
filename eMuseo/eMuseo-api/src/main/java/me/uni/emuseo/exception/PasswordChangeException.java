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
package me.uni.emuseo.exception;

public class PasswordChangeException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3952398611686767503L;
	public static String INVALID_OLD_PASSWORD = "Niepoprawne stare hasło";
	public static String INVALID_NEW_PASSWORD = "Niepoprawne nowe hasło";
	public static String NEW_PASSWORDS_NOT_MATCHED = "Nowe i powtórzone hasła są różne";
	private String reason;

	public PasswordChangeException(String reason) {
		this.reason = reason;
	}

	public String getReason() {
		return reason;
	}
}
