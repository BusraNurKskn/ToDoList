package com.busra.todolist.list.repository;

import com.busra.todolist.list.model.ToDoList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ListRepository extends JpaRepository<ToDoList, Long> {
    ToDoList findByToDoListId(Long id);

    @Modifying
    @Transactional
    @Query("delete from ToDoList t where toDoListId= ?1")
    void deleteUsingSingleQuery(Long id);

}