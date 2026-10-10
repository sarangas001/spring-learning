package com.telusko.datafinder3.service;

import com.telusko.datafinder3.entity.Product;
import com.telusko.datafinder3.view.ResultView;

import java.util.List;

public interface IProductService
{
    List<ResultView> searchByPriceLessThan(Double price);

}
