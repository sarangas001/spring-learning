package com.telusko.security_2.repo;

import com.telusko.security_2.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<Users, Integer>
{
        Users findByName(String name);
}
