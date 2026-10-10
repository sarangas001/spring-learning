package com.telusko.datafinder3.service;

import com.telusko.datafinder3.view.View;

import java.util.List;

public interface IProductService
{
    <T extends View> List<T> searchByPriceLessThan(Double price, Class<T> cls);
}
