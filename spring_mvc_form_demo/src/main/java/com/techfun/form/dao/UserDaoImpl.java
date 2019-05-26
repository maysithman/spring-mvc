package com.techfun.form.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.techfun.form.model.User;
//import com.techfun.mvc.repository.UserMapper;

@Repository("userDao")
public class UserDaoImpl implements UserDao {

	//NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	/*@Autowired
	public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate)
			throws DataAccessException {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}*/

	@Override
	public User findById(Integer id) {

		String sql = "SELECT * FROM users WHERE id= ?";

		User result = null;
		try {
			result = jdbcTemplate.queryForObject(sql, new UserMapper(), id);
		} catch (EmptyResultDataAccessException e) {
			// do nothing, return null
		}

		return result;

	}

	@Override
	public List<User> findAll() {

		String sql = "SELECT * FROM users";
		List<User> result = jdbcTemplate.query(sql, new UserMapper());

		return result;

	}

	@Override
	public void save(User user) {

		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		String insertUser = "INSERT INTO users(name, email, address, password, newsletter, framework, sex, number, country, skill) "
				+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {

				PreparedStatement ps = con.prepareStatement(insertUser,
						new String[] { "id" });
				ps.setString(1, user.getName());
		        ps.setString(2, user.getEmail());
		        ps.setString(3, user.getAddress());
		        ps.setString(4, user.getPassword());
		        ps.setBoolean(5, user.isNewsletter()); 
		        ps.setString(6, convertListToDelimitedString(user.getFramework()));
		        ps.setString(7, user.getSex());
		        ps.setInt(8, user.getNumber());
		        ps.setString(9, user.getCountry());
		        ps.setString(10, convertListToDelimitedString(user.getSkill()));
				return ps;
			}
		}, keyHolder);
		
		user.setId(keyHolder.getKey().intValue());
		
	}

	@Override
	public void update(User user) {

		String updateUser = "UPDATE users SET name=?, email=?, address=?, password=?, "
				+ "newsletter=?, framework=?, sex=?, number=?, country=?, skill=? WHERE id=?";
		
		jdbcTemplate.update(updateUser, user.getName(), user.getEmail(), user.getAddress(), user.getPassword(),
				user.isNewsletter(), convertListToDelimitedString(user.getFramework()), user.getSex(), 
				user.getNumber(), user.getCountry(), convertListToDelimitedString(user.getSkill()), user.getId());

	}

	@Override
	public void delete(Integer id) {

		String sql = "DELETE FROM USERS WHERE id= ?";
		jdbcTemplate.update(sql, id);

	}

	private SqlParameterSource getSqlParameterByModel(User user) {

		// Unable to handle List<String> or Array
		// BeanPropertySqlParameterSource

		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("id", user.getId());
		paramSource.addValue("name", user.getName());
		paramSource.addValue("email", user.getEmail());
		paramSource.addValue("address", user.getAddress());
		paramSource.addValue("password", user.getPassword());
		paramSource.addValue("newsletter", user.isNewsletter());

		// join String
		paramSource.addValue("framework", convertListToDelimitedString(user.getFramework()));
		paramSource.addValue("sex", user.getSex());
		paramSource.addValue("number", user.getNumber());
		paramSource.addValue("country", user.getCountry());
		paramSource.addValue("skill", convertListToDelimitedString(user.getSkill()));

		return paramSource;
	}

	private static final class UserMapper implements RowMapper<User> {

		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User user = new User();
			user.setId(rs.getInt("id"));
			user.setName(rs.getString("name"));
			user.setEmail(rs.getString("email"));
			user.setFramework(convertDelimitedStringToList(rs.getString("framework")));
			user.setAddress(rs.getString("address"));
			user.setCountry(rs.getString("country"));
			user.setNewsletter(rs.getBoolean("newsletter"));
			user.setNumber(rs.getInt("number"));
			user.setPassword(rs.getString("password"));
			user.setSex(rs.getString("sex"));
			user.setSkill(convertDelimitedStringToList(rs.getString("skill")));
			return user;
		}
	}

	private static List<String> convertDelimitedStringToList(String delimitedString) {

		List<String> result = new ArrayList<String>();

		if (!StringUtils.isEmpty(delimitedString)) {
			result = Arrays.asList(StringUtils.delimitedListToStringArray(delimitedString, ","));
		}
		return result;

	}

	private String convertListToDelimitedString(List<String> list) {

		String result = "";
		if (list != null) {
			result = StringUtils.arrayToCommaDelimitedString(list.toArray());
		}
		return result;

	}

}
