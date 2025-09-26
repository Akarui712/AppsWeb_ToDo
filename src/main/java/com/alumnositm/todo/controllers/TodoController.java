package com.alumnositm.todo.controllers;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.alumnositm.todo.dtos.request.CreateTodoRequest;
import com.alumnositm.todo.entities.TodoEntity;
import com.alumnositm.todo.services.TodoServices;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/todos")
public class TodoController 
{
    
    private final TodoServices todoServices;

    public TodoController(TodoServices todoServices) 
    {
        this.todoServices = todoServices;
    }

    @GetMapping()   //Obtener todos
    public ResponseEntity<List<TodoEntity>> allTodos() 
    {
        return ResponseEntity.ok(todoServices.allTodos());
    }

    @GetMapping("{idTodo}") //Obtener por id
    public ResponseEntity<TodoEntity> findTodoById(@PathVariable int idTodo) 
    {
        TodoEntity todo = todoServices.findById(idTodo);
        if(todo == null)
        {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(todo);
    }

    @PostMapping()  //
    public ResponseEntity<TodoEntity> createTodo(@RequestBody CreateTodoRequest createTodoRequest) 
    {
        TodoEntity saved = todoServices.createTodo(createTodoRequest);
        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(saved.getId())
            .toUri();

        return ResponseEntity.created(location).body(saved);
    }

    @PutMapping("update/{idTodo}")  //
    public ResponseEntity<TodoEntity> putMethodName(@PathVariable int idTodo, @RequestBody CreateTodoRequest entity)
    {
        TodoEntity todo = todoServices.updateTodoById(idTodo,entity);
        if(todo == null)
        {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(todo);
    }

    //Investigar eliminar por id
    @DeleteMapping("delete/{idTodo}")
    public ResponseEntity<String> deleteTodoById(@PathVariable int idTodo) 
    {
        String mensaje = todoServices.deleteById(idTodo);
        if(mensaje.contains("Eliminado"))
        {
            return ResponseEntity.ok(mensaje);
        }
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping("search")
    public ResponseEntity<List<TodoEntity>>
    findTodosByTitle(@RequestParam("q")String queryParam)
    {
        List<TodoEntity> todos = todoServices.findTodosByTitle(queryParam);
        return ResponseEntity.ok(todos);
    }
}