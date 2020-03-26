package com.eventview.demo.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.eventview.demo.model.EvenType;
import com.eventview.demo.model.Evens;
import com.eventview.demo.model.EventsPayload;
import com.eventview.demo.model.Users;

@Repository
public class UserRepo {

	@Autowired
	JdbcTemplate jdbcTemplate;

	/*
	 * public List<String> getAllUserNames() { List<String> usernameList = new
	 * ArrayList<>(); usernameList.addAll(jdbcTemplate.
	 * query("select user_id,first_name,last_name,phone,email from users;", (rs,
	 * rowNum) -> String.format("%s %s %s %s %s", rs.getString(1), rs.getString(2),
	 * rs.getString(3), rs.getString(4), rs.getString(5)))); return usernameList; }
	 */

	public List<Users> getAllUsers() {

		String usql = "SELECT user_id,first_name,last_name,phone,email FROM users";

		List<Users> users = jdbcTemplate.query(usql, new UserRowMapper());

		return users;

	}

	public List<EventsPayload> getAllEvens() {
		String esql = "select e.event_id,concat(u.first_name,' ',u.last_name) as full_name, et.event_type,e.event_date from events e left join users u on e.user_id = u.user_id left join eventtypes et on et.event_type_id=e.event_type_id";
		List<EventsPayload> payload = jdbcTemplate.query(esql, new EventsPayloadRowMapper());
		return payload;
	}

	public List<EvenType> getAllEvenTypes() {
		String etsql = "select event_type_id,event_type from eventtypes";
		List<EvenType> evens = jdbcTemplate.query(etsql, new EvenTypeRowMapper());
		return evens;
	}

	public void insertUser(Users user) {
		String iusql = "insert into users(user_id,first_name,last_name,phone,email) values (?,?,?,?,?)";
		jdbcTemplate.update(iusql, new Object[] { user.getUser_id(), user.getFirst_name(), user.getLast_name() });
	}
}
