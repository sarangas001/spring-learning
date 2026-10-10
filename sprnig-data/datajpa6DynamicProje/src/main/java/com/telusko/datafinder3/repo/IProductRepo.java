package com.telusko.datafinder3.repo;

import com.telusko.datafinder3.entity.Product;
import com.telusko.datafinder3.view.View;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductRepo extends JpaRepository<Product, Integer>
{

    <T extends View> List<T> findByPriceLessThan(Double price, Class<T> cls);

}
