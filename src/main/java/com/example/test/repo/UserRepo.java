package com.example.test.repo;

import com.example.test.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> { //繼承了JPA就可以使用JPA裡面的原生function，裡面存<實體,主鍵>
    Optional<User> findByMemAccount(String memAccount);
}
