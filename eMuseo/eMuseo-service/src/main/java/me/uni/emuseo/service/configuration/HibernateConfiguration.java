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
package me.uni.emuseo.service.configuration;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScan({ "me.uni.emuseo.service.configuration" })
@PropertySource(value = { "classpath:${hibernate.properties.file:application.properties}" })
public class HibernateConfiguration {

	@Autowired
	private Environment environment;

	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		sessionFactory.setPackagesToScan(new String[] { "me.uni.emuseo.service.model" });
		sessionFactory.setHibernateProperties(hibernateProperties());
		return sessionFactory;
	}

	@Bean
	public DataSource dataSource() {
		String app = System.getenv("OPENSHIFT_APP_NAME");
		if (app != null) {
			// Initialize hibernate in Openshift environment
			String dbType = environment.getRequiredProperty("OPENSHIFT_DB_TYPE");
			String serverAndDriver = environment.getRequiredProperty("OPENSHIFT_DB_SERVER_AND_DRIVER");
			String host = System.getenv("OPENSHIFT_" + dbType + "_DB_HOST");
			String port = System.getenv("OPENSHIFT_" + dbType + "_DB_PORT");
			
			String url = "jdbc:" + serverAndDriver + "://" + host + ":" + port + "/" + app + "?characterEncoding=UTF-8";
			String driverClass = environment.getRequiredProperty("OPENSHIFT_DB_DRIVER_CLASS");
			String username = System.getenv("OPENSHIFT_" + dbType + "_DB_USERNAME");
			String password = System.getenv("OPENSHIFT_" + dbType + "_DB_PASSWORD");

			return createDataSource(username, password, url, driverClass);
		}

		String url = environment.getRequiredProperty("hibernate.connection.url");
		String driverClass = environment.getRequiredProperty("hibernate.connection.driver_class");
		String username = environment.getRequiredProperty("hibernate.connection.username");
		String password = environment.getRequiredProperty("hibernate.connection.password");
		return createDataSource(username, password, url, driverClass);
	}

	private DataSource createDataSource(String username, String password, String url, String driverClass) {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(driverClass);
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		return dataSource;
	}

	private Properties hibernateProperties() {
		Properties properties = new Properties();
		String app = System.getenv("OPENSHIFT_APP_NAME");
		if (app != null) {
			// Initialize hibernate in Openshift environment
			properties.put("hibernate.dialect", environment.getRequiredProperty("OPENSHIFT_DB_DIALECT"));
		} else {
			properties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
		}
		properties.put("hibernate.show_sql", environment.getProperty("hibernate.show_sql", "false"));
		properties.put("hibernate.format_sql", environment.getProperty("hibernate.format_sql", "false"));
		properties.put("hibernate.hbm2ddl.auto", environment.getProperty("hibernate.hbm2ddl.auto", "update"));
		return properties;
	}

	@Bean
	@Autowired
	public HibernateTransactionManager transactionManager(SessionFactory s) {
		HibernateTransactionManager txManager = new HibernateTransactionManager();
		txManager.setSessionFactory(s);
		return txManager;
	}
}
