package com.example.slice.service;

import com.example.slice.dao.TodoItemDAO;
import com.example.slice.entity.TodoItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {
    @Autowired
    private TodoItemDAO todoItemDAO;

    public int createTodoItem(TodoItem todoItem){
        return todoItemDAO.createTodoItem(todoItem);
    }

    public int deleteTodoItem(int id){
        return todoItemDAO.deleteTodoItem(id);
    }

    public int changeTodoItem(int id, String name){return todoItemDAO.changeName(id, name);}

    public List<TodoItem> findTodoByUser(int userid){return todoItemDAO.findTodoByUser(userid);}
    public List<TodoItem> findTodoByUser(String username){return todoItemDAO.findTodoByUser(username);}
}
