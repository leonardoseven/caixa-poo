package com.adega.DAO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.adega.components.UserLogged;
import com.adega.domains.User;

@Repository
public class UserDAO implements UserDetailsService{

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	UserLogged userlogged;
	
	@Override
	public User loadUserByUsername(String username) throws UsernameNotFoundException {
			
		User user = jdbcTemplate.queryForObject("SELECT * FROM tbuser where name = ? and active = 1",
		new Object[] {username},(rs, rowNum) -> 
		new User(rs.getInt("id"), rs.getString("name"),
		rs.getString("email"), rs.getString("password"), rs.getString("role")));
		
		userlogged.setId(user.getId());
		userlogged.setName(user.getName());
		userlogged.setRole(user.getAccess());
		
		return user;
	}

	public User findUser(String userId) {
		User user = jdbcTemplate.queryForObject("SELECT * FROM tbuser where id = ? and active = 1",
				new Object[] {userId},(rs, rowNum) -> 
				new User(rs.getInt("id"), rs.getString("name"),
				rs.getString("email"), rs.getString("password"), rs.getString("role")));
				return user;
	}

	public List<User> getListUser() {
		String sql = "SELECT * FROM tbuser where active = 1 order by name ";
		List<User> listUser = jdbcTemplate.query(
				  sql, (rs, rowNum) ->
				  new User(rs.getInt("id"), rs.getString("name"),
				  rs.getString("email"), rs.getString("password"), rs.getString("role")));

		return listUser;
	}

	public void save(User user) {
		jdbcTemplate.update("INSERT INTO tbuser (name, email, password, role, active) VALUES (?, ?, ?, ?, ?)",
			      user.getName(), user.getEmail(), user.getPassword(), user.getAccess(), 1);
	}

	public void deleteUser(String userId) {
		jdbcTemplate.update("UPDATE tbuser set active = 0 where id = ?", userId);
	}

	public void update(User user) {
		jdbcTemplate.update("UPDATE tbuser set name = ?, email = ?, password = ?, role = ?  where id = ?",
				user.getName(), user.getEmail(), user.getPassword(), user.getAccess(), user.getId());
	}


}
