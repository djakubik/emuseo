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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;

import me.uni.emuseo.EMuseoUtil;
import me.uni.emuseo.model.users.UserDTO;
import me.uni.emuseo.model.users.UserDetailsDTO;

// TODO przebudowa autentykacji na SecurityContext
@Component
public class AuthManager implements AuthenticationManager {
	@Autowired
	private UserService userService;
	@Autowired
	private UserDetailsService userDetailsService;

	private ViewPermission viewPermission = new ViewPermission();

	public Authentication authenticate(Authentication auth) throws AuthenticationException {
		String login = (String) auth.getPrincipal();
		String password = (String) auth.getCredentials();
		UserDTO user = userService.authenticate(login, password);
		if (user == null) {
			throw new BadCredentialsException("Niepoprawny login lub hasło");
		}
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(user.getUserType().name()));

		UserDetailsDTO userDetails = userDetailsService.getUserDetails(user.getUserId());
		setDefaultUserDetails(userDetails, user);
		System.out.println("Details " + userDetails);
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(login, password,
				authorities);
		authentication.setDetails(userDetails);
		return authentication;
	}

	public void login(String username, String password) {

		UsernamePasswordAuthenticationToken request = new UsernamePasswordAuthenticationToken(username, password);
		Authentication result = authenticate(request);
		// SecurityContext context = SecurityContextHolder.getContext();
		// System.out.println(Thread.currentThread().getName() +
		// " Using sec context: "+context.hashCode());
		// context.setAuthentication(result);
		// System.out.println(Thread.currentThread().getName() +
		// " Using sec context: "+context.hashCode());
		VaadinSession.getCurrent().setAttribute("authentication", result);
		Navigator navigator = UI.getCurrent().getNavigator();
		navigator.navigateTo(Permissions.MENU_VIEW);
	}

	public void logout() {
		// SecurityContextHolder.clearContext();
		// UI.getCurrent().close();
		VaadinSession.getCurrent().setAttribute("authentication", null);
		Navigator navigator = UI.getCurrent().getNavigator();
		navigator.navigateTo(Permissions.LOGIN_VIEW);
	}

	public boolean isAuthorizedTo(String viewName, String parameters) {
		boolean authorized = false;
		// SecurityContext securityContext = SecurityContextHolder.getContext();
		// System.out.println(Thread.currentThread().getName()+ " Context " +
		// securityContext.hashCode());
		// Authentication authentication = securityContext.getAuthentication();
		Authentication authentication = (Authentication) VaadinSession.getCurrent().getAttribute("authentication");
		if (authentication != null && authentication.isAuthenticated()) {
			// String name = authentication.getName();
			Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
			for (GrantedAuthority ga : authorities) {
				String authority = ga.getAuthority();
				authorized = authorized || viewPermission.isAuthorizedTo(authority, viewName, parameters);
			}
		}
		return authorized;
	}

	public boolean isAuthorizedTo(String fullViewName) {
		boolean authorized = false;
		// SecurityContext securityContext = SecurityContextHolder.getContext();
		// System.out.println(Thread.currentThread().getName()+ " Context " +
		// securityContext.hashCode());
		// Authentication authentication = securityContext.getAuthentication();
		Authentication authentication = (Authentication) VaadinSession.getCurrent().getAttribute("authentication");
		if (authentication != null && authentication.isAuthenticated()) {
			// String name = authentication.getName();
			Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
			for (GrantedAuthority ga : authorities) {
				String authority = ga.getAuthority();
				authorized = authorized || viewPermission.isAuthorizedTo(authority, fullViewName);
			}
		}
		return authorized;
	}

	private void setDefaultUserDetails(UserDetailsDTO userDetails, UserDTO user) {
		if (userDetails.getUserId() == null && user != null) {
			userDetails.setUserId(user.getUserId());
		}
		if (userDetails.getTheme() == null) {
			userDetails.setTheme(EMuseoUtil.DEFAULT_THEME);
		}
		if (userDetails.getFullName() == null && user != null) {
			userDetails.setFullName(user.getFirstName() + " " + user.getLastName());
		}
		if (userDetails.getLanguage() == null) {
			userDetails.setLanguage("pl");
		}
	}

	public UserDetailsDTO getUserDetails() {
		Authentication authentication = (Authentication) VaadinSession.getCurrent().getAttribute("authentication");
		if (authentication == null || authentication.getDetails() == null) {
			UserDetailsDTO userDetailsDTO = new UserDetailsDTO();
			setDefaultUserDetails(userDetailsDTO, null);
			return userDetailsDTO;
		}
		UserDetailsDTO userDetailsDTO = (UserDetailsDTO) authentication.getDetails();
		return userDetailsDTO;
	}

	public Long getLoggedUserId() {
		return getUserDetails().getUserId();
	}

	public String getLoggedUserFullName() {
		return getUserDetails().getFullName();
	}

	public void setUserDetails(UserDetailsDTO userDetailsDTO) {
		userDetailsService.setUserSettings(userDetailsDTO);
		UserDetailsDTO userDetails = userDetailsService.getUserDetails(userDetailsDTO.getUserId());
		setDefaultUserDetails(userDetails, null);
		AbstractAuthenticationToken authentication = (AbstractAuthenticationToken) VaadinSession.getCurrent()
				.getAttribute("authentication");
		authentication.setDetails(userDetails);
	}

}
