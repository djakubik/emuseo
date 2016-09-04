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
package me.uni.emuseo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import me.uni.emuseo.model.users.UserDetailsDTO;
import me.uni.emuseo.service.UserDetailsService;
import me.uni.emuseo.service.dao.Dao;
import me.uni.emuseo.service.mapper.MapperImpl;
import me.uni.emuseo.service.model.User;
import me.uni.emuseo.service.model.UserDetails;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	protected Dao dao;

	/* (non-Javadoc)
	 * @see me.uni.emuseo.service.UserDetailsService#getUserDetails(java.lang.Long)
	 */
	@Override
	public UserDetailsDTO getUserDetails(Long userId) {
		User user = dao.get(User.class, userId);
		UserDetails userDetails = getUserDetails(user);
		return MapperImpl.map(userDetails);
	}

	/* (non-Javadoc)
	 * @see me.uni.emuseo.service.UserDetailsService#setUserImage(java.lang.Long, byte[])
	 */
	@Override
	public void setUserImage(Long userId, byte[] image) {
		User user = dao.get(User.class, userId);
		UserDetails userDetails = getUserDetails(user);
		userDetails.setImage(image);
		user.setUserDetails(userDetails);
		dao.saveOrUpdate(user);
	}

	/* (non-Javadoc)
	 * @see me.uni.emuseo.service.UserDetailsService#setUserSettings(java.lang.Long, me.uni.emuseo.model.users.UserDetailsDTO)
	 */
	@Override
	public void setUserSettings(UserDetailsDTO userDetailsDTO) {
		User user = dao.get(User.class, userDetailsDTO.getUserId());
		UserDetails userDetails = getUserDetails(user);
		userDetails.setFullName(userDetailsDTO.getFullName());
		userDetails.setTheme(userDetailsDTO.getTheme());
		userDetails.setLanguage(userDetailsDTO.getLanguage());
		user.setUserDetails(userDetails);
		dao.saveOrUpdate(userDetails);
		dao.saveOrUpdate(user);
	}

	private UserDetails getUserDetails(User user) {
		UserDetails userDetails = null;
		if (user != null) {
			userDetails = user.getUserDetails();
		} 
		if (userDetails == null) {
			userDetails = new UserDetails();
			userDetails.setUser(user);
		}
		return userDetails;
	};
}
