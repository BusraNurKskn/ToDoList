package com.busra.todolist.list.repository;

import com.busra.todolist.list.model.ToDoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ItemRepository extends JpaRepository<ToDoItem, Long> {
    ToDoItem findByToDoItemId(Long id);

    @Modifying
    @Transactional
    @Query("delete from ToDoItem t where toDoItemId = ?1")
    void deleteUsingSingleQuery(Long id);

    @Modifying
    @Transactional
    @Query("delete from ToDoItem t where TODOLIST_ID = ?1")
    void deleteAllList(Long id);

    @Modifying
    @Transactional
    @Query("update ToDoItem t set t.status='finished' where toDoItemId = ?1")
    void updateUsingSingleQuery(Long id);


}