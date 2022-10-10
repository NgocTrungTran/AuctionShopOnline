package com.aso.service.product;


import com.aso.model.Product;
import com.aso.model.dto.IProductDTO;
import com.aso.model.dto.ProductDTO;
import com.aso.model.dto.ProductListDTO;
import com.aso.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;


    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public List<ProductListDTO> findAllProductListDTO() {
        return productRepository.findAllProductListDTO();
    }

    @Override
    public List<ProductDTO> findAllProductsDTO() {
        return productRepository.findAllProductsDTO();
    }

    @Override
    public List<ProductDTO> findAllProductsDTOTrash() {
        return productRepository.findAllProductsDTOTrash();
    }

    @Override
    public Boolean existsByTitle(String title) {
        return productRepository.existsByTitle(title);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Product getById(Long id) {
        return null;
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void delete(Product id) {
        productRepository.deleteById(id);
    }

    @Override
    public Boolean existById(Long id) {
        return null;
    }

    @Override
    public void softDelete(Product product) {
        product.setDeleted(true);
        productRepository.save(product);
    }

    @Override
    public List<ProductDTO> findAllBySearchTitle(String title) {
        return productRepository.findAllBySearchTitle(title);
    }
//    @Override
//    public Optional<ProductDTO> findProductDTOBySlug(String slug) {
//        return productRepository.findProductDTOBySlug(slug);
//    }

    //@Override
    public List<ProductDTO> findAllBySearchSlug(String slug) {
        return productRepository.findAllBySearchSlug(slug);
    }
    @Override
    public Optional<ProductDTO> findProductDTOBySlug(String slug) {
        return productRepository.findProductDTOBySlug(slug);
    }
    @Override
    public Optional<Product> findProductBySlug(String slug) {
        return productRepository.findProductBySlug(slug);
    }

}
