package kr.or.connect.todo.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository("todoDao")
public class TodoDao {
	private NamedParameterJdbcTemplate jdbc;
	private SimpleJdbcInsert insertAction;
	
	@Autowired
	public TodoDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
		this.insertAction = new SimpleJdbcInsert(dataSource)
				.withTableName("todo")
				.usingGeneratedKeyColumns("id")
				.usingColumns("todo");
	}
		
	public List<Todo> selectAll(){
		return jdbc.query(TodoSqls.SELECT_ALL, new BeanPropertyRowMapper<Todo>(Todo.class));
	}
	
	public Todo selectOne(Integer id){
		SqlParameterSource params = new MapSqlParameterSource("id", id);
		return jdbc.queryForObject(TodoSqls.SELECT_BY_ID, params, new BeanPropertyRowMapper<Todo>(Todo.class));
	}
	
	public List<Todo> selectActive(){
		return jdbc.query(TodoSqls.SELECT_ACTIVE, new BeanPropertyRowMapper<Todo>(Todo.class));
	}
	
	public List<Todo> selectCompleted(){
		return jdbc.query(TodoSqls.SELECT_COMPLETED, new BeanPropertyRowMapper<Todo>(Todo.class));
	}
	
	public Integer insert(Todo todo){
		SqlParameterSource params = new BeanPropertySqlParameterSource(todo);
		return insertAction.executeAndReturnKey(params).intValue();
	}
	
	public void updateCompleted(Integer id){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", id);
		jdbc.update(TodoSqls.UPDATE_COMPLETED, param);
	}
	
	public void delete(Integer id){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", id);
		jdbc.update(TodoSqls.DELETE_BY_ID, param);
	}

	public Integer countNotCompleted() {
		return jdbc.queryForObject(TodoSqls.COUNT_NOT_COMPLETED,(SqlParameterSource)null, Integer.class);
	}
}
