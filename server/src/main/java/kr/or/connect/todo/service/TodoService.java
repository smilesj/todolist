package kr.or.connect.todo.service;

import java.util.List;
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
	
	public List<Todo> findAll(){
		return dao.selectAll();
	}
	
	public Todo findById(Integer id){
		return dao.selectOne(id);
	}
	
	public Integer create(Todo todo){
		return dao.insert(todo);
	}
}
