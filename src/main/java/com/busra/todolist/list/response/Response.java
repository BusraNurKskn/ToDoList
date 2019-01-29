package com.busra.todolist.list.response;


import java.util.Set;

public class Response {

    private Set<ResponseList> toDoLists;

    public Set<ResponseList> getToDoLists() {
        return toDoLists;
    }

    public void setToDoLists(Set<ResponseList> toDoLists) {
        this.toDoLists = toDoLists;
    }
}
