package com.muzaffer.orun.application.configuration;

import java.beans.PropertyVetoException;
import java.io.File;
import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@EnableTransactionManagement
public class DbConfiguration {

	@Bean
	public SessionFactory sessionFactory(DataSource dataSource) {
		LocalSessionFactoryBuilder builder = new LocalSessionFactoryBuilder(dataSource);
		builder.scanPackages("com.muzaffer.orun.*").addProperties(getHibernateProperties());
		return builder.buildSessionFactory();
	}

	private Properties getHibernateProperties() {
		Properties prop = new Properties();
		prop.put("hibernate.format_sql", "true");
		prop.put("hibernate.show_sql", "true");
		prop.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
		prop.put("hibernate.max_fetch_depth", "5");
		prop.put("hibernate.jdbc.batch_size", "5");
		prop.put("hibernate.c3p0.min_size", "1");
		prop.put("hibernate.c3p0.max_size", "5");
		prop.put("hibernate.c3p0.max_statements", "100");
		prop.put("hibernate.c3p0.idle_test_period", "100");
//		prop.put("hibernate.hbm2ddl.auto", "create-drop");
		prop.put("hibernate.hbm2ddl.auto", "update");
		return prop;
	}

	@Bean(name = "dataSource")
	public ComboPooledDataSource dataSource() throws PropertyVetoException {
		File file = new File(System.getProperty("user.home")+"/.egitimLabirenti/db");
		if(!file.exists()) {
			file.mkdirs();
		}

		ComboPooledDataSource ds = new ComboPooledDataSource();
		ds.setDriverClass("org.h2.Driver");
		ds.setJdbcUrl("jdbc:h2:file:"+file.getPath()+"/dbVeri");
		ds.setUser("sa");
		ds.setPassword("");
		ds.setMinPoolSize(3);
		ds.setMaxIdleTime(300000);
		ds.setMaxIdleTimeExcessConnections(300000);
		ds.setAcquireIncrement(1);
		ds.setMaxPoolSize(5);
		ds.setMaxStatements(100);
		return ds;
	}

	@Bean
	public HibernateTransactionManager txManager(SessionFactory sessionFactory) {
		return new HibernateTransactionManager(sessionFactory);
	}

}
