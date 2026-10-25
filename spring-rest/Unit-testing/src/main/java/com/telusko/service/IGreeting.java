package com.telusko.service;

import com.telusko.model.Alien;

public interface IGreeting
{
    String generateGreeting(String name);
    Boolean addAlienInfo(Alien alien);
}
