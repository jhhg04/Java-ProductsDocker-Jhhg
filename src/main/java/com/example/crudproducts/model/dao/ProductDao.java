package com.example.crudproducts.model.dao;

import com.example.crudproducts.model.entity.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductDao extends CrudRepository<Product, Integer> {
}
