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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import me.uni.emuseo.exception.PasswordChangeException;
import me.uni.emuseo.model.users.PasswordDTO;
import me.uni.emuseo.model.users.UserDTO;
import me.uni.emuseo.model.users.UserSearchDTO;
import me.uni.emuseo.service.UserService;
import me.uni.emuseo.service.dao.Alias;
import me.uni.emuseo.service.dao.Dao;
import me.uni.emuseo.service.mapper.CriterionProducer;
import me.uni.emuseo.service.mapper.MapperImpl;
import me.uni.emuseo.service.mapper.PasswordUtil;
import me.uni.emuseo.service.model.User;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	protected Dao dao;

	@Override
	public List<UserDTO> getAllUsers() {
		List<User> all = dao.getAll(User.class);
		List<UserDTO> retAll = new ArrayList<UserDTO>();
		for (User user : all) {
			retAll.add(MapperImpl.map(user));
		}
		return retAll;
	}

	@Override
	public Long getUsersCount(UserSearchDTO searchDTO) {
		List<Criterion> criterions = CriterionProducer.getCriterions(searchDTO);
		return dao.count(User.class, criterions, new ArrayList<Alias>(0));
		// Arrays.asList(Alias.getInstance("category", "category_")));
	}

	@Override
	public List<UserDTO> getUsers(int firstResult, int maxResults, UserSearchDTO searchDTO) {
		List<Criterion> criterions = CriterionProducer.getCriterions(searchDTO);
		List<User> list = dao.get(User.class, firstResult, maxResults, criterions, new ArrayList<Alias>(0));
		// Arrays.asList(Alias.getInstance("category", "category_")));
		List<UserDTO> retAll = new ArrayList<UserDTO>(list.size());
		for (User user : list) {
			retAll.add(MapperImpl.map(user));
		}
		return retAll;
	}

	@Override
	public void addUser(UserDTO user) {
		User ex = MapperImpl.map(user);
		dao.save(ex);
	}

	@Override
	public void editUser(UserDTO user) {
		User ex = dao.get(User.class, user.getUserId());
		MapperImpl.map(user, ex);
		dao.saveOrUpdate(ex);
	}

	@Override
	public void deleteUser(Long id) {
		User user = dao.get(User.class, id);
		dao.delete(user);
	}

	@Override
	public UserDTO getUser(Long id) {
		User user = dao.get(User.class, id);
		return MapperImpl.map(user);
	};

	@Override
	public UserDTO authenticate(String login, String password) {
		try {
			List<User> list = dao.get(User.class, 0, 0, Arrays.asList(CriterionProducer.getLoginCriterion(login)),
					null);
			if (list.size() == 1) {
				User user = list.get(0);
				String hashPassword = PasswordUtil.hashPassword(password);
				if (hashPassword.equals(user.getPassword())) {
					return MapperImpl.map(user);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void changePassword(Long userId, PasswordDTO password) throws PasswordChangeException {
		if (password.getOldPassword() == null || password.getOldPassword().isEmpty()) {
			throw new PasswordChangeException(PasswordChangeException.INVALID_OLD_PASSWORD);
		}
		if (password.getNewPassword() == null || password.getNewPassword().isEmpty()) {
			throw new PasswordChangeException(PasswordChangeException.INVALID_NEW_PASSWORD);
		}
		if (!password.getNewPassword().equals(password.getNewPasswordRetype())) {
			throw new PasswordChangeException(PasswordChangeException.NEW_PASSWORDS_NOT_MATCHED);
		}
		String oldPassHashed = PasswordUtil.hashPassword(password.getOldPassword());
		User user = dao.get(User.class, userId);

		if (!oldPassHashed.equals(user.getPassword())) {
			throw new PasswordChangeException(PasswordChangeException.INVALID_OLD_PASSWORD);
		}
		String newPassHashed = PasswordUtil.hashPassword(password.getNewPassword());
		user.setPassword(newPassHashed);
		dao.saveOrUpdate(user);
	}
}
