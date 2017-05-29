package kr.or.connect.todo.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.or.connect.todo.persistence.Todo;
import kr.or.connect.todo.persistence.TodoDao;

@Service
public class TodoService {
	
	@Resource(name="todoDao")
	private TodoDao dao;
	
	private ConcurrentMap<Integer, Todo> repo = new ConcurrentHashMap<>();
	private AtomicInteger maxId = new AtomicInteger(0);
	
	public Collection<Todo> findAll(){
		return Arrays.asList(
			new Todo(1, "첫번째할일입니다.", 0, "2017/05/28"),
			new Todo(2, "두번째로할일", 1, "2017/05/29")
		);
	}
	
	public Integer create(Todo todo){
		return dao.insert(todo);
	}
}
