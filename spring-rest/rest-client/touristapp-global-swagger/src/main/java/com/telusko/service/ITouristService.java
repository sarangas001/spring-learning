package com.telusko.service;

import com.telusko.entity.Tourist;

import java.util.List;

public interface ITouristService
{
    public String addTourist(Tourist tourist);

    public String updateTourist(Integer id, Double budget);

    public String updateTourist(Tourist tourist);

    public String deleteTourist(Integer id);

    public Tourist getTourist(Integer id);

    public List<Tourist> getAllTourists();


}
