/**
 * 
 */
package com.sdc.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author arjun
 *
 */
@Configuration
public class WebConfig {

	 @Bean(name = "db1")
	 @ConfigurationProperties(prefix = "spring.datasource")
	 public DataSource dataSource1() {
	  return DataSourceBuilder.create().build();
	 }
	 
	 @Bean(name = "jdbcTemplate1")
	 public JdbcTemplate jdbcTemplate1(@Qualifier("db1") DataSource ds) {
	  return new JdbcTemplate(ds);
	 }
	 
	 @Bean(name = "db2")
	 @ConfigurationProperties(prefix = "spring.second-db")
	 public DataSource dataSource2() {
	  return  DataSourceBuilder.create().build();
	 }

	 @Bean(name = "jdbcTemplate2")
	 public JdbcTemplate jdbcTemplate2(@Qualifier("db2") DataSource ds) {
	  return new JdbcTemplate(ds);
	 }
	 
	 @Bean(name = "db3")
	 @ConfigurationProperties(prefix = "spring.third-db")
	 public DataSource dataSource3() {
	  return  DataSourceBuilder.create().build();
	 }

	 @Bean(name = "jdbcTemplate3")
	 public JdbcTemplate jdbcTemplate3(@Qualifier("db3") DataSource ds) {
	  return new JdbcTemplate(ds);
	 }
}
