package com.example.slice.controller;

import com.example.slice.entity.TodoItem;
import com.example.slice.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TodoController {
    @Autowired
    TodoService todoService;

    @RequestMapping(value = "/createTodo", method = RequestMethod.GET)
    @ResponseBody
    public int createTodo(String name, int userid){
        TodoItem todoItem = new TodoItem();

        todoItem.setName(name);
        todoItem.setUserid(userid);

        int id = todoService.createTodoItem(todoItem);
        return id;
    }

    @RequestMapping(value = "/deleteTodo", method = RequestMethod.GET)
    @ResponseBody
    public int deleteTodo(int id){
        return todoService.deleteTodoItem(id);
    }
}
