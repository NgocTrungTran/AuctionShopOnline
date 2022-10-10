package com.aso.controller.api;

import com.aso.exception.DataInputException;
import com.aso.model.CartItem;
import com.aso.model.dto.CartItemListDTO;
import com.aso.service.cartItem.CartItemService;
import com.aso.utils.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cart-item")
public class CartItemsAPI {
    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private AppUtil appUtils;

    @GetMapping("/{title}")
    // đã test ok
    public ResponseEntity<?> getCartByTitle(@PathVariable String title) {
        try {
            List<CartItemListDTO> cartItemsDTO = cartItemService.findCartItemDTOById(title);
            return new ResponseEntity<>(cartItemsDTO, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException("Không lấy được danh sách đơn hàng");
        }
    }

    @GetMapping("/id/{id}")
    // đã test ok
    public ResponseEntity<?> GetCartById(@PathVariable Long id) {
        try {
            Optional<CartItemListDTO> cartItemsDTO = cartItemService.getCartItemDTOById(id);
            return new ResponseEntity<>(cartItemsDTO.get(), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>("Không lấy được cart item", HttpStatus.NO_CONTENT);
        }
    }

//    @PutMapping("/increasing")
//    public ResponseEntity<?> increasingCart(@RequestBody CartItem cartItem, BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            return appUtils.mapErrorToResponse(bindingResult);
//        }
//        try {
//            return new ResponseEntity<>(cartItemService.SaveIncreasing(cartItem).toCartItemListDTO(), HttpStatus.ACCEPTED);
//        } catch (Exception e) {
//            throw new DataInputException("Sản phẩm không đủ để thêm nữa!");
//        }
//    }
//    @PutMapping("/reduce")
//    public ResponseEntity<?> ReduceCart(@RequestBody CartItem cartItem, BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            return appUtils.mapErrorToResponse(bindingResult);
//        }
//        try {
//            return new ResponseEntity<>(cartItemService.SaveReduce(cartItem).toCartItemListDTO(), HttpStatus.ACCEPTED);
//
//        } catch (Exception e) {
//            return new ResponseEntity<>("Không thể Giảm số lượng sản phẩm", HttpStatus.NO_CONTENT);
//        }
//    }

}
