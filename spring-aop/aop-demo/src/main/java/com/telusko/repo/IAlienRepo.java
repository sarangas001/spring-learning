package com.telusko.repo;

import com.telusko.entity.Alien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAlienRepo extends JpaRepository<Alien, Integer> {
}
