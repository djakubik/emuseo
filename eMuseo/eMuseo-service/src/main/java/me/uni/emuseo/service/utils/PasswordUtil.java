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
package me.uni.emuseo.service.utils;


public class PasswordUtil {

	public static String hashPassword(String password) {
		if (password == null || password.isEmpty()){
			return null;
		}
		String sha256hex = org.apache.commons.codec.digest.DigestUtils.sha256Hex(password);
		return sha256hex;
	}

	public static void main(String[] args) {
		String example = "pass";
		System.out.println(hashPassword(example));
	}
}
