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

import javax.validation.Valid;
import java.io.IOException;
import java.math.BigDecimal;
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
    public ResponseEntity<?> getAllProducts() { //đã test ok
        List<ProductDTO> productDTOList = productService.findAllProductsDTO();

        if (productDTOList.isEmpty()) {
            throw new DataOutputException("No data");
        }

        return new ResponseEntity<>(productDTOList, HttpStatus.OK);
    }

//    @GetMapping("/trash")
////    @PreAuthorize("hasAnyAuthority('ADMIN')")
//    public ResponseEntity<?> getAllProductsTrash(@PathVariable String productId) {
//        List<ProductDTO> products = productService.findAllProductsDTOTrash();
////        products.remove(productId);
//        if (products.isEmpty()) {
//            throw new DataOutputException("No data");
//        }
//        if (!validation.isIntValid(productId)) {
//            throw new DataInputException("Product ID invalid!");
//        }
//        return new ResponseEntity<>(products, HttpStatus.OK);
//    }

    @GetMapping("/{productId}")
//    @PreAuthorize("hasAnyAuthority('ADMIN')")
//    Đã test ok
    public ResponseEntity<?> getProductById(@PathVariable String productId) {

        if (!validation.isIntValid(productId)) {
            throw new DataInputException("Product ID invalid!");
        }
        Long product_id = Long.parseLong(productId);

        Optional<Product> productOptional = productService.findById(product_id);

        if (!productOptional.isPresent()) {
            throw new ResourceNotFoundException("Product invalid");
        }

        return new ResponseEntity<>(productOptional.get().toProductDTO(), HttpStatus.OK);
    }

//    @PostMapping("/create")
////    @PreAuthorize("hasAnyAuthority('ADMIN')")
//    public ResponseEntity<?> doAddProduct(@Validated @RequestBody ProductDTO productDTO, BindingResult bindingResult  ) {
//        new ProductDTO().validate(productDTO, bindingResult);
//
//        if (bindingResult.hasErrors()) {
//            return appUtil.mapErrorToResponse(bindingResult);
//        }
//
//        String slug = Validation.makeSlug(productDTO.getTitle());
//
//        productDTO.getCategory().setId (0L );
//        Category category = categoryService.save(productDTO.getCategory().toCategory());
//        productDTO.setCategory(category.toCategoryDTO());
//
//        try {
//            Product product = productDTO.toProduct();
//            product.setSlug(slug);
////            product.setCreatedBy (productDTO.getCreatedBy());
//            Product newProduct = productService.save(product);
//
//            return new ResponseEntity<>( newProduct.toProductDTO(), HttpStatus.CREATED);
//
//        } catch (DataIntegrityViolationException e) {
//            throw new DataInputException ("Product information is not valid, please check the information again");
//        }
//    }

    @PostMapping("/create")
    // Test đã ok
    public ResponseEntity<?> doCreate(@Validated @RequestBody ProductDTO productDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return appUtil.mapErrorToResponse(bindingResult);
        }
        String checkPrice = String.valueOf(new BigDecimal(String.valueOf(productDTO.getPrice())));
        if (!checkPrice.toString().matches("\"(^$|[0-9]*$)\"")) {
            productDTO.setId(0L);
            // Note: thêm category vô đây
            Product newProduct = productService.save(productDTO.toProduct());
            return new ResponseEntity<>(newProduct.toProductDTO(), HttpStatus.CREATED);
        }
        throw new DataInputException("Tạo mới thất bại");
    }

    @PutMapping("/edit/{id}")
// đã test ok
    public ResponseEntity<?> doEdit(@PathVariable Long id, @Validated @RequestBody ProductDTO productDTO,
                                    BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return appUtil.mapErrorToResponse(bindingResult);
        }

        Optional<Product> p = productService.findById(id);

        if (!p.isPresent()) {
            return new ResponseEntity<>("Không tồn tại sản phẩm", HttpStatus.NOT_FOUND);
        }

        try {
            productDTO.getCategory().setTitle(p.get().getCategory().getTitle());
            productDTO.setId(p.get().getId());
            productService.save(productDTO.toProduct());

            return new ResponseEntity<>(productDTO, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>("Server ko xử lý được", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete-soft/{id}")
    // đã test ok
    public ResponseEntity<?> doDelete(@PathVariable Long id) {

        Optional<Product> optionalProduct = productService.findById(id);

        if (optionalProduct.isPresent()) {
            productService.softDelete(optionalProduct.get());
            return new ResponseEntity<>("Delete success!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Delete error!", HttpStatus.BAD_REQUEST);

        }

    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) throws IOException {

        Optional<Product> product = productService.findById(id);

        if (product.isPresent()) {
            productService.delete(product.get());

            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } else {
            throw new DataInputException("Invalid product information");
        }
    }

    @GetMapping("/search/{query}")
    // đã test ok (tìm kiếm theo tên title)
    public ResponseEntity<?> searchListProduct(@PathVariable String query) {
        List<ProductDTO> productDTOList = productService.findProductByValue(query);
        return new ResponseEntity<>(productDTOList, HttpStatus.OK);
    }
}
