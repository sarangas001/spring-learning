package com.teluso.spring_sec_demo.service;

import com.teluso.spring_sec_demo.enity.Users;
import com.teluso.spring_sec_demo.repo.IUsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private IUsersRepo repo;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = repo.findByUsername(username);
        if (user == null)
        {
            throw new UsernameNotFoundException("User not found : " + username);
        }
        return new UserPrinciple(user);
    }
}
