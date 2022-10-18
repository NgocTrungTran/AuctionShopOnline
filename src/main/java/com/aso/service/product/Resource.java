package com.aso.service.product;

import com.aso.model.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface Resource<T> {
	@GetMapping
	ResponseEntity<Page<ProductDTO>> findAll(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
}
