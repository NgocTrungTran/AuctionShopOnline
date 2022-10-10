package com.aso.controller.api;

import com.aso.exception.DataInputException;
import com.aso.exception.DataOutputException;
import com.aso.exception.ResourceNotFoundException;
import com.aso.model.Category;
import com.aso.model.Product;
import com.aso.model.dto.ProductDTO;
import com.aso.service.category.CategoryService;
import com.aso.service.product.ProductService;
import com.aso.utils.AppUtil;
import com.aso.utils.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductAPI {
    @Autowired
    private ProductService productService;

    @Autowired
    private Validation validation;

    @Autowired
    private AppUtil appUtil;

    @Autowired
    private CategoryService categoryService;
    @GetMapping
//    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> getAllProducts() {
        List<ProductDTO> productDTOList = productService.findAllProductsDTO();

        if (productDTOList.isEmpty ()) {
            throw new DataOutputException ( "No data" );
        }

        return new ResponseEntity<>(productDTOList, HttpStatus.OK);
    }

    @GetMapping("/trash")
//    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> getAllProductsTrash() {
        List<ProductDTO> products = productService.findAllProductsDTOTrash ();

        if (products.isEmpty ()) {
            throw new DataOutputException ( "No data" );
        }

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/{productId}")
//    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> getProductById(@PathVariable String productId) {

        if ( !validation.isIntValid ( productId ) ) {
            throw new DataInputException ( "Product ID invalid!" );
        }
        Long product_id = Long.parseLong ( productId );

        Optional<Product> productOptional = productService.findById(product_id);

        if (!productOptional.isPresent()) {
            throw new ResourceNotFoundException ("Product invalid");
        }

        return new ResponseEntity<>(productOptional.get().toProductDTO (), HttpStatus.OK);
    }

    @PostMapping("/create")
//    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> doAddProduct(@Validated @RequestBody ProductDTO productDTO, BindingResult bindingResult  ) {
        new ProductDTO ().validate(productDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            return appUtil.mapErrorToResponse(bindingResult);
        }

        String slug = Validation.makeSlug ( productDTO.getTitle () );

        productDTO.getCategory().setId( 0L );
        Category category = categoryService.save(productDTO.getCategory().toCategory());
        productDTO.setCategory( category.toCategoryDTO () );

        try {
            Product product = productDTO.toProduct ();
            product.setSlug ( slug );
            product.setCreatedBy ( productDTO.getCreatedBy () );
            Product newProduct = productService.save(product);

            return new ResponseEntity<>( newProduct.toProductDTO (), HttpStatus.CREATED);

        } catch (DataIntegrityViolationException e) {
            throw new DataInputException ("Product information is not valid, please check the information again");
        }
    }
}
