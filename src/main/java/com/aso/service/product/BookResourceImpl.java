package com.aso.service.product;

import com.aso.model.Product;
import com.aso.model.dto.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

@RestController
@RequestMapping("/books")
@CrossOrigin(origins="http://localhost:3006")
public class BookResourceImpl implements Resource<Product> {

	ProductService productService;

	@Override
	public ResponseEntity<Page<ProductDTO>> findAll(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
		return new ResponseEntity<>(productService.findAllProducts(
				PageRequest.of(
						pageNumber, pageSize,
						sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending()
				)
		), HttpStatus.OK);
	}
}
