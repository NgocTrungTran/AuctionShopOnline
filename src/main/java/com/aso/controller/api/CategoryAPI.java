package com.aso.controller.api;

import com.aso.exception.EmailExistsException;
import com.aso.exception.ResourceNotFoundException;
import com.aso.model.Category;
import com.aso.model.Product;
import com.aso.model.dto.CategoryDTO;
import com.aso.service.category.CategoryService;
import com.aso.utils.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
public class CategoryAPI {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private AppUtil appUtils;

    @GetMapping
    private ResponseEntity<?> findAll() {
        // đã test ok
        try {
            List<CategoryDTO> categoryList = categoryService.findAllCategoryDTO();

            return new ResponseEntity<>(categoryList, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/{id}")
    // đã test ok
    public ResponseEntity<?> getCategoryById(@PathVariable Long id) {
        Optional<CategoryDTO> categoryDTO = categoryService.findCategoryDTOById(id);
        if (!categoryDTO.isPresent()) {
            throw new ResourceNotFoundException("Invalid category ID");
        }
        return new ResponseEntity<>(categoryDTO.get().toCategory(), HttpStatus.OK);
    }
    @PostMapping("/create")
    // đã test ok
    public ResponseEntity<?> doCreate(@Valid  @RequestBody CategoryDTO categoryDTO, BindingResult bindingResult) throws MessagingException, UnsupportedEncodingException {
        if (bindingResult.hasFieldErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }
        Boolean exitByCategory = categoryService.existsCategoryByTitle(categoryDTO.getTitle());
        if (exitByCategory) {
            throw new EmailExistsException("Loại sản phẩm đã tồn tại! Vui lòng nhập loại khác");
        }
        Category category = categoryService.save(categoryDTO.toCategory());
        return new ResponseEntity<>(category.toCategoryDTO(), HttpStatus.OK);
    }
    @DeleteMapping("/delete-categories/{id}")
    // đã test ok
    public ResponseEntity<?> deleteCategory( @PathVariable Long id) {
//        categoryService.deleteCategory(id);
//        return new ResponseEntity<>(HttpStatus.OK);

        if (categoryService.findById(id).isPresent()) {
            categoryService.deleteCategory(id);
            return new ResponseEntity<>("Delete success!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Delete error!", HttpStatus.BAD_REQUEST);

        }
    }

}
