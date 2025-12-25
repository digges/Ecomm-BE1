package com.genie.Ecomm.service;


import com.genie.Ecomm.model.Product;
import com.genie.Ecomm.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;



    public List<Product> getAllProducts() {

        return productRepository.findAll();
    }



    public Product getProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(null);
    return product;
    }



    public Product addProduct( Product product) {
       return  productRepository.save(product);
    }


    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
