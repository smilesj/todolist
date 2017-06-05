package kr.or.connect.todo.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import kr.or.connect.todo.persistence.Todo;
import kr.or.connect.todo.service.TodoService;

@RestController
@RequestMapping("/api/todos")
public class TodoController {
	
	private final TodoService service;
	
	public TodoController(TodoService service) {
		this.service = service;
	}
	
	@GetMapping()
	public List<Todo> readList(Model model){
		model.addAttribute("list", service.findAll());
		return service.findAll();
	}
	
	@GetMapping(value="/active")
	public List<Todo> readActiveList(){
		return service.findActive();
	}
	
	@GetMapping(value="/completed")
	public List<Todo> readCompletedList(){
		return service.findCompleted();
	}
	
	@GetMapping("/count")
	public Integer readNotCompleted(Model model){
		return service.countNotCompleted();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Todo create(@RequestParam(value="todo") String str){
		Todo todo = new Todo();
		todo.setTodo(str);
		int id = service.create(todo);
		return service.findById(id);
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void modifyCompleted(@PathVariable Integer id){
		service.modifyCompleted(id);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remove(@PathVariable Integer id){
		service.remove(id);
	}
}
