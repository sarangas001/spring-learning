package com.telusko.security_2.service;

import com.telusko.security_2.model.Users;
import com.telusko.security_2.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUsersService{

    @Autowired
    private UserRepo repo;

    @Override
    public Users registerUser(Users user) {
        return repo.save(user);
    }
}
