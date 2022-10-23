package com.aso.controller.api;

import com.aso.exception.DataInputException;
import com.aso.exception.DataOutputException;
import com.aso.exception.ResourceNotFoundException;
import com.aso.model.Category;
import com.aso.model.dto.CategoryDTO;
import com.aso.model.dto.ProductDTO;
import com.aso.repository.CategoryRepository;
import com.aso.service.category.CategoryService;
import com.aso.utils.AppUtil;
import com.aso.utils.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
public class CategoryAPI {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private AppUtil appUtils;

    @GetMapping("")
    private ResponseEntity<?> findAll() {
        try {
            List<CategoryDTO> categoryList = categoryService.findAllCategoryDTO();
            return new ResponseEntity<>(categoryList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable Long id) {
        Optional<CategoryDTO> categoryDTO = categoryService.findCategoryDTOById(id);
        if (categoryDTO.isEmpty()) {
            throw new ResourceNotFoundException("Invalid category ID");
        }
        return new ResponseEntity<>(categoryDTO.get().toCategory(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> doCreate(@Valid @RequestBody CategoryDTO categoryDTO, BindingResult bindingResult) throws MessagingException, UnsupportedEncodingException {
        if (bindingResult.hasFieldErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }
        Boolean exitByCategory = categoryService.existsCategoryByTitle(categoryDTO.getTitle());
        if (exitByCategory) {
            throw new DataInputException("Loại sản phẩm đã tồn tại! Vui lòng nhập loại khác");
        }
        categoryDTO.setSlug(Validation.makeSlug(categoryDTO.getTitle()));
        Category category = categoryService.save(categoryDTO.toCategory());
        return new ResponseEntity<>(category.toCategoryDTO(), HttpStatus.OK);
    }

    @PutMapping("/delete-soft/{id}")
    public ResponseEntity<?> doDelete(@PathVariable Long id) {
        Optional<Category> optionalCategory = categoryService.findById(id);
        if (optionalCategory.isPresent()) {
            categoryService.softDelete(optionalCategory.get());
            return new ResponseEntity<>("Delete success!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Delete error!", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> doEdit(@PathVariable Long id, @Validated @RequestBody CategoryDTO categoryDTO,
                                    BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }
        Optional<Category> category = categoryService.findById(id);
        if (category.isEmpty()) {
            return new ResponseEntity<>("Không tồn tại danh mục!", HttpStatus.NOT_FOUND);
        }
        try {
            String slug = Validation.makeSlug(categoryDTO.getTitle());
            category.get().setUpdatedAt(new Date());
            category.get().setTitle(categoryDTO.getTitle());
            category.get().setSlug(slug);

            Category newCategory = categoryService.save(category.get());
            return new ResponseEntity<>(newCategory.toCategoryDTO(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/g")
    public ResponseEntity<Page<CategoryDTO>> getAllCagetory(Pageable pageable) {
        Page<CategoryDTO> categoryDTOPage = categoryService.findAllCategoryDTOPage(pageable);
        if (categoryDTOPage.isEmpty()) {
            throw new DataOutputException("No data");
        }
        return new ResponseEntity<>(categoryDTOPage, HttpStatus.OK);
    }

    @GetMapping("/g/{keyword}")
    public ResponseEntity<Page<CategoryDTO>> getAllCategroys(Pageable pageable, @PathVariable("keyword") String keyword) {
        try {
            keyword = "%" + keyword + "%";
            Page<CategoryDTO> categoryDTOPage = categoryService.getAllCategroys(pageable, keyword);
            if (categoryDTOPage.isEmpty()) {
                throw new DataOutputException("Danh sách sản phẩm trống");
            }
            return new ResponseEntity<>(categoryDTOPage, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
