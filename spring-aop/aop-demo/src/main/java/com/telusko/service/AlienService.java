package com.telusko.service;

import com.telusko.entity.Alien;
import com.telusko.repo.IAlienRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlienService implements IAlienService {

    @Autowired
    private IAlienRepo repo;

    @Override
    public Alien registerAlien(Alien e) {
        System.out.println("AlienService: registerAlien method called");
        return repo.save(e);
    }

    @Override
    public List<Alien> getAllAliens() {
        return repo.findAll();
    }
}
