package com.asm.ecommerce.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.asm.ecommerce.model.Product;

public interface ProductService {
    public Product saveProduct(Product product);

	public List<Product> getAllProducts();

	public Boolean deleteProduct(Integer id);

	public Product getProductById(Integer id);

	public Product updateProduct(Product product, MultipartFile file);

    public List<Product> getAllActiveProducts(String category);

}
