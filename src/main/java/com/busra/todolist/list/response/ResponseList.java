package com.busra.todolist.list.response;

import com.busra.todolist.list.model.ToDoItem;

import java.util.Set;

public class ResponseList {

    private Long id;

    private String name;

    private Set<ResponseItem> items;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<ResponseItem> getToDoItemSet() {
        return items;
    }

    public void setToDoItemSet(Set<ResponseItem> items) {
        this.items = items;
    }
}
