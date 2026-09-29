package com.telusko.datajpa2page.service;

import com.telusko.datajpa2page.entity.Product;

import java.util.List;

public interface IProductService
{
    Iterable<Product> fetchProductInfoSorting(boolean status, String...properties);
    Iterable<Product> fetchProductByPagination(int pgNo, int pgSize,boolean status, String...properties);
    void fetchProductByPagination(int pgSize);
}
