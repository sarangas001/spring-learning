package com.telusko.service;

import com.telusko.entity.Alien;

import java.util.List;

public interface IAlienService {
    Alien registerAlien(Alien e);
    List<Alien>  getAllAliens();
}
