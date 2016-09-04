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
package me.uni.emuseo.temp;

import java.util.HashSet;
import java.util.List;

import me.uni.emuseo.model.exhibits.ExhibitLocation;
import me.uni.emuseo.model.users.UserType;
import me.uni.emuseo.service.generic.GenericService;
import me.uni.emuseo.service.model.Category;
import me.uni.emuseo.service.model.Exhibit;
import me.uni.emuseo.service.model.User;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

public class AppMain {

	public static void main(String args[]) {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig2.class);

		GenericService genser = (GenericService) context.getBean("genericService");

		Category cat1 = new Category();
		cat1.setName("category 1");
		genser.save(cat1);

		Category cat2 = new Category();
		cat2.setName("category 2");
		genser.save(cat2);

		Category cat3 = new Category();
		cat3.setName("category 3");
		genser.save(cat3);

		/*
		 * Create Exhibit1
		 */
		Exhibit exhibit1 = new Exhibit();
		exhibit1.setName("Eksponat Wystawa");
		exhibit1.setCreationDate(2010);
		exhibit1.setNumber("EG 1231");
		exhibit1.setDescription("my description");
		exhibit1.setLocation(ExhibitLocation.EXHIBITION);
		exhibit1.setCategory(cat1);

		/*
		 * Create Exhibit2
		 */
		Exhibit exhibit2 = new Exhibit();
		exhibit2.setName("Eksponat Magazyn");
		exhibit2.setCreationDate(1990);
		exhibit2.setNumber("EG 1222");
		exhibit2.setDescription("my description");
		exhibit2.setLocation(ExhibitLocation.WAREHOUSE);
		exhibit2.setCategory(cat2);

		/*
		 * Create Exhibit3
		 */
		Exhibit exhibit3 = new Exhibit();
		exhibit3.setName("Eksponat Archiwum");
		exhibit3.setCreationDate(1910);
		exhibit3.setNumber("EG 1100");
		exhibit3.setDescription("my description");
		exhibit3.setLocation(ExhibitLocation.ARCHIVES);
		exhibit3.setCategory(cat2);

		/*
		 * Create Exhibit4
		 */
		Exhibit exhibit4 = new Exhibit();
		exhibit4.setName("Eksponat Wystawa");
		exhibit4.setCreationDate(1904);
		exhibit4.setNumber("EG 1104");
		exhibit4.setDescription("my description");
		exhibit4.setLocation(ExhibitLocation.EXHIBITION);
		exhibit4.setCategory(cat3);

		/*
		 * Create Exhibit5
		 */
		Exhibit exhibit5 = new Exhibit();
		exhibit5.setName("Eksponat Archiwum");
		exhibit5.setCreationDate(1905);
		exhibit5.setNumber("EG 1105");
		exhibit5.setDescription("my description");
		exhibit5.setLocation(ExhibitLocation.ARCHIVES);
		exhibit5.setCategory(cat3);

		/*
		 * Create Exhibit6
		 */
		Exhibit exhibit6 = new Exhibit();
		exhibit6.setName("Eksponat Archiwum");
		exhibit6.setCreationDate(1905);
		exhibit6.setNumber("EG 1106");
		exhibit6.setDescription("my description");
		exhibit6.setLocation(ExhibitLocation.ARCHIVES);
		exhibit6.setCategory(cat3);

		exhibit4.setParentExhibit(exhibit3);
		exhibit5.setParentExhibit(exhibit3);
		exhibit6.setParentExhibit(exhibit5);

		HashSet<Exhibit> exhibit3Set = new HashSet<Exhibit>();
		exhibit3Set.add(exhibit4);
		exhibit3Set.add(exhibit5);
		exhibit3.setSubExhibits(exhibit3Set);
		HashSet<Exhibit> exhibit5Set = new HashSet<Exhibit>();
		exhibit5Set.add(exhibit6);
		exhibit5.setSubExhibits(exhibit5Set);

		/*
		 * Persist both Exhibits
		 */
		genser.save(exhibit1);
		genser.save(exhibit2);
		genser.save(exhibit3);
		genser.save(exhibit4);
		genser.save(exhibit5);
		genser.save(exhibit6);

		User user1 = new User();
		user1.setFirstName("Name user1");
		user1.setLastName("Surname user1");
		user1.setEmailAddress("email@user1.pl");
		user1.setPhoneNumber("123 456 789");
		user1.setLogin("ADMINISTRATOR");
		user1.setPassword("HASŁO_ZAKODOWANE_SHA256");
		user1.setUserType(UserType.ADMINISTRATOR);

		User user2 = new User();
		user2.setFirstName("Name user2");
		user2.setLastName("Surname user2");
		user2.setEmailAddress("email@user2.pl");
		user2.setPhoneNumber("123 456 789");
		user2.setLogin("MODERATOR");
		user2.setPassword("ENCODED_PASS_WITH_SHA256");
		user2.setUserType(UserType.MODERATOR);

		User user3 = new User();
		user3.setFirstName("Name user3");
		user3.setLastName("Surname user3");
		user3.setEmailAddress("email@user3.pl");
		user3.setPhoneNumber("123 456 789");
		user3.setLogin("USER");
		user3.setPassword("ENCODED_PASS_WITH_SHA256");
		user3.setUserType(UserType.USER);

		genser.save(user1);
		genser.save(user2);
		genser.save(user3);

		System.out.println("Users: " + genser.count(User.class));
		List<User> users = genser.getAll(User.class);
		for (User user : users) {
			System.out.println(user);
		}
		System.out.println("Exhibits: " + genser.count(Exhibit.class));
		List<Exhibit> exhibits = genser.getAll(Exhibit.class);
		for (Exhibit exhibit : exhibits) {
			System.out.println(exhibit);
		}
		System.out.println("Categories: " + genser.count(Category.class));
		sysList(genser.getAll(Category.class));

		sysList(genser.get(Exhibit.class, 1, 2));
		sysList(genser.get(Exhibit.class, 2, 4));

		context.close();
	}

	private static <T> void sysList(List<T> elems) {
		System.out.println("Size: " + elems.size());
		for (T cat : elems) {
			System.out.println("\t" + cat);
		}
	}
}
