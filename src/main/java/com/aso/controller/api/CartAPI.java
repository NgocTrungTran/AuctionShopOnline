package com.aso.controller.api;


import com.aso.exception.NoCartException;
import com.aso.model.Cart;
import com.aso.model.CartItem;
import com.aso.model.Order;
import com.aso.model.OrderDetail;
import com.aso.model.dto.CartItemListDTO;
import com.aso.service.cart.CartService;
import com.aso.service.cartItem.CartItemService;
import com.aso.service.order.OrderService;
import com.aso.service.orderitem.OrderItemService;
import com.aso.utils.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/carts")
public class CartAPI {

    @Autowired
    private CartService cartService;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderItemService orderItemService;

    @GetMapping
    public ResponseEntity<?> getAllCartItems() {

        String createdBy = AppUtil.getPrincipalUsername();

        Optional<Cart> cartOptional = cartService.findByCreatedBy(createdBy);

        if (!cartOptional.isPresent()) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }

        Long cartId = cartOptional.get().getId();

        List<CartItemListDTO> cartItemListDTOS = cartItemService.findAllCartItemsDTO(cartId);

        if (cartItemListDTOS.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }

        return new ResponseEntity<>(cartItemListDTOS, HttpStatus.OK);
    }

    @PostMapping("/create-order")
    public ResponseEntity<?> createOrder() {

        String createdBy = AppUtil.getPrincipalUsername();

        Optional<Cart> cartOptional = cartService.findByCreatedBy(createdBy);

        if (!cartOptional.isPresent()) {
            throw new NoCartException("không có sản phẩm trong giỏ hàng");
        }

        Order order = new Order();
        order.setId(0L);
//        order.setTotalAmount(cartOptional.get().getTotalAmount());
        order.setCreatedBy(createdBy);
        Order newOrder = orderService.save(order);

        List<CartItem> cartItems = cartItemService.findAllByCart(cartOptional.get());

        if (cartItems.isEmpty()) {
            throw new NoCartException("không có sản phẩm trong giỏ hàng");
        }

        for (CartItem cartItem : cartItems) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setId(0L);
            orderDetail.setProduct(cartItem.getProduct());
            orderDetail.setPrice(cartItem.getPrice());
            orderDetail.setQuantity(cartItem.getQuantity());
            orderDetail.setAmountTransaction(cartItem.getAmountTransaction());
            orderDetail.setOrder(newOrder);
            orderDetail.setCreatedBy(createdBy);
            orderItemService.save(orderDetail);

            cartItemService.delete(cartItem);
        }

        cartService.delete(cartOptional.get());


        Map<String, Boolean> result = new HashMap<>();
        result.put("success", true);

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
}
