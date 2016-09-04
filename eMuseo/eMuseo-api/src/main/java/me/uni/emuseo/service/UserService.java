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

import java.util.List;

import me.uni.emuseo.exception.PasswordChangeException;
import me.uni.emuseo.model.users.PasswordDTO;
import me.uni.emuseo.model.users.UserDTO;
import me.uni.emuseo.model.users.UserSearchDTO;

public interface UserService {

	UserDTO getUser(Long id);

	void deleteUser(Long id);

	void editUser(UserDTO user);

	void addUser(UserDTO user);

	List<UserDTO> getUsers(int firstResult, int maxResults, UserSearchDTO searchDTO);

	Long getUsersCount(UserSearchDTO searchDTO);

	List<UserDTO> getAllUsers();

	UserDTO authenticate(String login, String password);

	void changePassword(Long userId, PasswordDTO password) throws PasswordChangeException;

}
