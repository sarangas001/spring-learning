package com.telusko.datajpa2page.service;

import com.telusko.datajpa2page.entity.Product;
import com.telusko.datajpa2page.repo.IProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements IProductService {

    @Autowired
    private IProductRepo repo;

    @Override
    public Iterable<Product> fetchProductInfoSorting(boolean status, String... properties) {
        Sort sort = Sort.by(status? Sort.Direction.ASC: Sort.Direction.DESC,properties);
        return repo.findAll(sort);
    }

    @Override
    public Iterable<Product> fetchProductByPagination(int pgNo, int pgSize, boolean status, String... properties) {
        // static way
        Sort sort = Sort.by(status? Sort.Direction.ASC: Sort.Direction.DESC,properties);
        PageRequest pageable = PageRequest.of(pgNo, pgSize, sort);
        Page<Product> page = repo.findAll(pageable);
        return page.getContent();
    }

    @Override
    public void fetchProductByPagination(int pgSize) {
        long count = 6;
        long pagecount = count/pgSize;
        //14/5 ==> 2.8 ==> 2 ==> 3
        //14%5 == 0 ? increment pagecount
        //14%2 == 0 ? keep pagecount

        pagecount = count%pgSize == 0 ?pagecount : ++pagecount;

        for (int i = 0; i < pagecount; i++)
        {
            PageRequest pageable = PageRequest.of(i, pgSize);
            Page<Product> page = repo.findAll(pageable);
            page.getContent().forEach(System.out::println);
            System.out.println("******************************");
        }

    }
}
