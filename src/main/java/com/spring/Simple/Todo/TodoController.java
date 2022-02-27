package com.spring.Simple.Todo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class TodoController {
    @Autowired
    TodoRepository todoRepository;

    @GetMapping("/todos")
    public ResponseEntity<List<Todo>> getAllTodos() {
        try {
            List<Todo> todos = new ArrayList<Todo>(todoRepository.findAll());
            if(todos.isEmpty()) return new ResponseEntity<>(todos, HttpStatus.NO_CONTENT);
            return new ResponseEntity<>(todos, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/todos/{id}")
    public ResponseEntity<Todo> getTutorialById(@PathVariable("id") long id) {
        Optional<Todo> todoData = todoRepository.findById(id);
        if (todoData.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(todoData.get(), HttpStatus.OK);
        }
    }

    @PostMapping("/todos")
    public ResponseEntity<Todo> createTodo(@RequestBody Todo todo) {
        try {
            Todo _todo = todoRepository.save(new Todo(todo.getTitle(), todo.getDescription(), todo.getDate()));
            return new ResponseEntity<>(_todo, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/todos/{id}")
    public ResponseEntity<Todo> updateTutorial(@PathVariable("id") long id,  @RequestBody Todo todo) {
        Optional<Todo> todoData = todoRepository.findById(id);
        if(todoData.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            Todo _todo = todoData.get();
            _todo.setTitle(todo.getTitle());
            _todo.setDescription(todo.getDescription());
            _todo.setDate(todo.getDate());
            return new ResponseEntity<>(todoRepository.save(_todo), HttpStatus.OK);
        }
    }

    @DeleteMapping("/todos/{id}")
    public ResponseEntity<Object> deleteTutorial(@PathVariable("id") long id) {
        try {
            Optional<Todo> _todo = todoRepository.findById(id);
            if(_todo.isPresent()) {
                todoRepository.deleteById(id);
                return new ResponseEntity<>("Todo with id "+id+" deleted", HttpStatus.OK);
            }

            return new ResponseEntity<Object>("Cannot find todo" ,HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
