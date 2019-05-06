package com.example.slice.controller;

import com.example.slice.entity.TodoItem;
import com.example.slice.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

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

    @RequestMapping(value = "/changeTodo", method = RequestMethod.GET)
    @ResponseBody
    public int changeTodo(int id, String name){
        return todoService.changeTodoItem(id, name);
    }

    @RequestMapping(value = "/findTodo", method = RequestMethod.GET)
    @ResponseBody
    public List<String> findTodo(int userid){
        List<String> result = todoService.findTodoByUser(userid);
        return result;
    }

    @RequestMapping(value = "/findTodoByName", method = RequestMethod.GET)
    @ResponseBody
    public List<String> findTodoByName(String username){
        List<String> result = todoService.findTodoByUser(username);
        return result;
    }
}
