package com.techfun.mvc.repository;

import java.sql.ResultSet;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.techfun.mvc.model.Login;
import com.techfun.mvc.model.User;

@Repository("userRepository")
public class UserRepositoryImpl implements UserRepository {
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public void registerUser(User user) {
		jdbcTemplate.update("INSERT INTO users(username, password, firstname, lastname, email, address, phone) "
				+ "VALUES(?, ?, ?, ?, ?, ?, ?)", user.getUsername(), user.getPassword(), user.getFirstname(), 
				user.getLastname(), user.getEmail(), user.getAddress(), user.getPhone());
	}

	@Override
	public User validateUser(Login login) {
		String sql = "select * from users where username='" + login.getUsername() + "' and password='"
				+ login.getPassword() + "'";

		List<User> users = jdbcTemplate.query(sql, new UserMapper());

		return users.size() > 0 ? users.get(0) : null;
	}

}

class UserMapper implements RowMapper<User> {

	public User mapRow(ResultSet rs, int arg1) throws SQLException {
		User user = new User();

		user.setUsername(rs.getString("username"));
		user.setPassword(rs.getString("password"));
		user.setFirstname(rs.getString("firstname"));
		user.setLastname(rs.getString("lastname"));
		user.setEmail(rs.getString("email"));
		user.setAddress(rs.getString("address"));
		user.setPhone(rs.getInt("phone"));

		return user;
	}
}
