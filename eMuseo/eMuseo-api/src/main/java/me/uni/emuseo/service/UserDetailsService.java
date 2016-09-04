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
package me.uni.emuseo.service;

import me.uni.emuseo.model.users.UserDetailsDTO;

public interface UserDetailsService {

	UserDetailsDTO getUserDetails(Long userId);

	void setUserImage(Long userId, byte[] image);

	void setUserSettings(UserDetailsDTO userDetailsDTO);

}
