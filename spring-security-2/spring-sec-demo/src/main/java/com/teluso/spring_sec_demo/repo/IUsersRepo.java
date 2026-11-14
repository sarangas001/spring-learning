package com.teluso.spring_sec_demo.repo;

import com.teluso.spring_sec_demo.enity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUsersRepo extends JpaRepository<Users, Integer> {
    Users findByUsername(String username);
}
