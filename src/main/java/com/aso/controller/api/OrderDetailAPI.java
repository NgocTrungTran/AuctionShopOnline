package com.aso.controller.api;

import com.aso.exception.DataInputException;
import com.aso.exception.GlobalExceptionHandler;
import com.aso.model.Order;
import com.aso.model.Product;
import com.aso.model.dto.OrderDTO;
import com.aso.model.dto.OrderDetailDTO;
import com.aso.service.order.OrderService;
import com.aso.service.orderdetail.OrderDetailService;
import com.aso.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders-detail")
public class OrderDetailAPI {
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;

    @PostMapping("/create/{orderId}")
    public ResponseEntity<?> createOrderDetail(@PathVariable("orderId") Long orderId, @RequestBody List<OrderDetailDTO> orderDetailDTOList) {
        List<String> errors = new ArrayList<> ();
        try {
           OrderDTO orderDTO = orderService.findOrderDTOById ( orderId );
            if (orderDTO == null) {
                throw new RuntimeException ("Đơn hàng không tồn tại");
            }

            for (OrderDetailDTO orderDetailDTO: orderDetailDTOList) {
                Optional<Product> productOptional = productService.findById ( orderDetailDTO.getProduct ().getId () );
                if (productOptional.isEmpty ()) {
                    errors.add ( "Không tồn tại " + orderDetailDTO.getProduct ().getTitle () + " trong dữ liệu" );
                }
            }


            if ( !errors.isEmpty () ) {
                for (String error: errors) {
                    throw new RuntimeException (error);
                }
            }

            List<OrderDetailDTO> orderDetailDTOS = orderDetailService.doCreateOrderDetail ( orderId, orderDetailDTOList );


            return new ResponseEntity<>(orderDetailDTOS, HttpStatus.OK);
        }catch (Exception e){
            throw new RuntimeException (e);
        }
    }
}
