package com.busra.todolist.list.model;

import com.busra.todolist.user.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "todoitem")
public class ToDoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long toDoItemId;

    @NotNull
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "todolist_id", nullable = false)
    @JsonIgnore
    private ToDoList toDoList;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "deadline")
    private String deadline;

    @Column(name = "status")
    private String status;

    @Column(name = "dependency")
    private Long dependency;

    public Long getToDoItemId() {
        return toDoItemId;
    }

    public void setToDoItemId(Long toDoItemId) {
        this.toDoItemId = toDoItemId;
    }

    public ToDoList getToDoList() {
        return toDoList;
    }

    public void setToDoList(ToDoList toDoList) {
        this.toDoList = toDoList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getDependency() {
        return dependency;
    }

    public void setDependency(Long dependency) {
        this.dependency = dependency;
    }
}
