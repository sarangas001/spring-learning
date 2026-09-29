package com.telusko.datafinder3.repo;

import com.telusko.datafinder3.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductRepo extends JpaRepository<Product, Integer>
{
    //Crud PagingSorting JPA
    List<Product> findByPrice(Double price);
    List<Product> findByPriceIs(Double price);
    List<Product> findByPriceEquals(Double price);
    List<Product> findByPriceGreaterThan(Double price);
    List<Product> findByPriceLessThan(Double price);
    List<Product> findByPriceBetween(Double minPrice, Double maxPrice);
    List<Product> findByCategoryEquals(String category);
    List<Product> findByCategoryInAndPriceBetween(List<String> categories, Double minPirce, Double maxPrice);
}
