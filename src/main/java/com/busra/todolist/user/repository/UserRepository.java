package com.busra.todolist.user.repository;

import com.busra.todolist.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserName(String username);

}
