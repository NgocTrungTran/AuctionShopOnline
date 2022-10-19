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

//    @GetMapping("/{accountId}")
//    // đã test ok
//    public ResponseEntity<?> getCartByTitle(@PathVariable Long accountId) {
//        try {
//            List<CartItemListDTO> cartItemsDTO = cartItemService.findCartItemDTOById(userId);
//            return new ResponseEntity<>(cartItemsDTO, HttpStatus.OK);
//        } catch (Exception e) {
//            throw new RuntimeException("Không lấy được danh sách đơn hàng");
//        }
//    }

    @GetMapping("/id/{id}")
    // đã test ok
    public ResponseEntity<?> getCartById(@PathVariable Long id) {
        try {
            Optional<CartItemListDTO> cartItemsDTO = cartItemService.getCartItemDTOById(id);
            return new ResponseEntity<>(cartItemsDTO.get(), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>("Không lấy được cart item", HttpStatus.NO_CONTENT);
        }
    }

    @PutMapping("/increasing")
    public ResponseEntity<?> increasingCart(@RequestBody CartItem cartItem, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }
        try {
            return new ResponseEntity<>(cartItemService.SaveIncreasing(cartItem).toCartItemListDTO(), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            throw new DataInputException("Sản phẩm không đủ để thêm nữa!");
        }
    }
    @PutMapping("/reduce")
    public ResponseEntity<?> reduceCart(@RequestBody CartItem cartItem, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }
        try {
            return new ResponseEntity<>(cartItemService.SaveReduce(cartItem).toCartItemListDTO(), HttpStatus.ACCEPTED);

        } catch (Exception e) {
            return new ResponseEntity<>("Không thể giảm số lượng sản phẩm", HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> doCreateCart(@RequestBody CartItemListDTO cartItemsDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }
        try {
            try {
                Optional<CartItemListDTO> cartItemDTO = cartItemService.getCartItemDTOByCode(cartItemsDTO.getTitle(), cartItemsDTO.getProduct().getTitle());
                if (cartItemDTO.isPresent()) {
//                    if (cartItemDTO.get().getProductId().getFiles().contains(cartItemsDTO.getProductId())) {
//                        // cho nó nhảy vào catch để xử lý thêm product vào cartItem;
//                    }
                }
                return new ResponseEntity<>(cartItemService.save(cartItemsDTO.toCartItem()).toCartItemListDTO(), HttpStatus.CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>(cartItemService.saveOp(cartItemsDTO.toCartItem()).toCartItemListDTO(), HttpStatus.ACCEPTED);
            }
        } catch (Exception e) {
            throw new DataInputException("không đủ số lượng sản phẩm để thêm vào giỏ hàng!!");
        }
    }

    @PostMapping("/creat-cart-in-detail")
    public ResponseEntity<?> doCreateCartItem(@RequestBody CartItemListDTO cartItemListDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }
        try {
            try {
                Optional<CartItemListDTO> cartItemDTO = cartItemService.getCartItemDTOByCode(cartItemListDTO.getTitle(), cartItemListDTO.getProduct().getTitle());
                if (cartItemDTO.isPresent()) {
//                    if (cartItemDTO.get().getProductId().getFiles().contains(cartItemListDTO.getProductId())) {
//                        // cho nó nhảy vào catch để xử lý thêm product vào cartItem;
//                    }
                }

                CartItem cartItemsDTO1 = cartItemService.save(cartItemListDTO.toCartItem());
                return new ResponseEntity<>(cartItemsDTO1.toCartItemListDTO(), HttpStatus.CREATED);
            } catch (Exception e) {
                CartItem cartItemsDTO1 = cartItemService.saveInDetail(cartItemListDTO.toCartItem());
                return new ResponseEntity<>(cartItemsDTO1.toCartItemListDTO(), HttpStatus.ACCEPTED);
            }
        } catch (Exception e) {
            throw new DataInputException("Không thêm vào giỏ hàng thành công");
        }
    }

}
