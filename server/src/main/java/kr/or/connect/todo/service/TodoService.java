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
	
	public List<Todo> findAll(){
		return dao.selectAll();
	}
	
	public Todo findById(Integer id){
		return dao.selectOne(id);
	}
	
	public List<Todo> findActive(){
		return dao.selectActive();
	}
	
	public List<Todo> findCompleted(){
		return dao.selectCompleted();
	}
	
	public Integer create(Todo todo){
		return dao.insert(todo);
	}
	
	public void modifyCompleted(Integer id){
		dao.updateCompleted(id);
	}
	
	public void remove(Integer id){
		dao.delete(id);
	}
	
	public Integer countNotCompleted() {
		return dao.countNotCompleted();
	}
}
