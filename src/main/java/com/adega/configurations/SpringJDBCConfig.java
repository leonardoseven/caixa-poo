package com.adega.configurations;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class SpringJDBCConfig {
	

 	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
 		return new JdbcTemplate(dataSource);
 	}
	
	
// 	@Bean
//    public DataSource mysqlDataSource() {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
//        dataSource.setUrl("jdbc:mysql://localhost:3306/adega?useTimezone=true&serverTimezone=America/Sao_Paulo");
//        dataSource.setUsername("root");
//        dataSource.setPassword("");
//        return dataSource;
//    }
}
