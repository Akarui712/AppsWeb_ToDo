package com.alumnositm.todo.services.impl;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import com.alumnositm.todo.dtos.request.CreateTodoRequest;
import com.alumnositm.todo.entities.TodoEntity;
import com.alumnositm.todo.helpers.TodoStatus;
import com.alumnositm.todo.repositorys.TodoRepository;
import com.alumnositm.todo.services.TodoServices;

@Service
public class TodoServicesImpl implements TodoServices 
{
    private final TodoRepository todoRepository;
    private final JdbcTemplate jdbcTemplate;

    public TodoServicesImpl(TodoRepository todoRepository, JdbcTemplate jdbcTemplate)
    {
        this.todoRepository = todoRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<TodoEntity> allTodos() 
    {
        List<TodoEntity> todos = todoRepository.findAll();
        //Query name --> interpreta a que tabla de una BD se van a hacer las consultas
        return todos;
    }

    @Override
    public TodoEntity createTodo(CreateTodoRequest createTodoRequest)
    {
        TodoEntity entity = new TodoEntity();
        entity.setTitle(createTodoRequest.getTitle());
        entity.setDescription(createTodoRequest.getDescription());
        entity.setStatus(TodoStatus.PENDING);

        return todoRepository.save(entity);
    }

    @Override
    public TodoEntity findById(int idTodo)
    {
        TodoEntity todo = todoRepository.findById((long)idTodo).orElse(null);
        return todo;
    }

    @Override
    public TodoEntity updateTodoById(int idTodo, CreateTodoRequest entity)
    {
        TodoEntity todoEntity = todoRepository.findById((long)idTodo).orElse(null);
        if(todoEntity != null)
        {
            todoEntity.setTitle(entity.getTitle());
            todoEntity.setDescription(entity.getDescription());
            todoEntity.setStatus(TodoStatus.COMPLETED);
            todoRepository.save(todoEntity);
            return todoEntity;
        }
        return null;
    }

    //Eliminar por id
    @Override
    public String deleteById(int idTodo)
    {
        TodoEntity entity = todoRepository.findById((long)idTodo).orElse(null);
        if (entity != null)
        {
            todoRepository.delete(entity);
            return "Eliminado";
        }
        return "No existe el id";
    }

    //Eliminación virtual por id
    @Override
    public boolean eliminacionVirtualPorId(int idTodo)
    {
        Optional<TodoEntity> optionalTodo = todoRepository.findById((long) idTodo);
        if (optionalTodo.isPresent()) 
        {
            TodoEntity todo = optionalTodo.get();
            todo.setStatus(TodoStatus.ELIMINADO);
            todoRepository.save(todo);
            return true;
        }
        return false;
    }

    @Override
    public List<TodoEntity> findTodosByTitle(String queyParam)
    {
        String sql = "Select * From todos Where title like '%"+queyParam+"%'";  //Query
        RowMapper<TodoEntity> rowMapper = (rs,rowNum) -> 
        {
            TodoEntity todo = new TodoEntity();
            todo.setId(rs.getLong("id"));
            todo.setTitle(rs.getString("title"));
            todo.setDescription(rs.getString("description"));
            todo.setStatus(TodoStatus.valueOf(rs.getString("status")));
            return todo;
        };
        List<TodoEntity> todos = jdbcTemplate.query(sql, rowMapper);
        return todos;
    }
}