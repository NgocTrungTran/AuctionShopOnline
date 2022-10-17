package com.aso.controller.api;

import com.aso.model.dto.ProductMediaDTO;
import com.aso.service.productMedia.ProductMediaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productmedia")
public class ProductMediaAPI {
    @Autowired
    private ProductMediaServiceImpl productMediaService;

    @GetMapping("/{id}")
    public ResponseEntity<?> doMedia(@PathVariable Long id) {
        List<ProductMediaDTO> productMediaDTO = productMediaService.findAllById(id);
        return new ResponseEntity<>(productMediaDTO, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<?> doCreate(@RequestBody ProductMediaDTO productMediaDTO) {
        productMediaService.save(productMediaDTO.toProductMedia());
        return new ResponseEntity<>(productMediaDTO, HttpStatus.CREATED);
    }

}
