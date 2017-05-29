package kr.or.connect.todo.persistence;

public class TodoSqls {
	static final String DELETE_BY_ID =
			"DELETE FROM todo WHERE id= :id";
	static final String INSERT =
			"INSERT INTO todo(todo) VALUES(:todo)";
	static final String SELECT_ALL = "SELECT id, todo, completed, date FROM todo ORDER BY date DESC";
}
