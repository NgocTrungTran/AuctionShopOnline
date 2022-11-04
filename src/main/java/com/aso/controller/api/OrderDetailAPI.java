package com.aso.controller.api;

import com.aso.exception.AccountInputException;
import com.aso.exception.AttributesExistsException;
import com.aso.exception.DataInputException;
import com.aso.exception.DataOutputException;
import com.aso.model.Account;
import com.aso.model.OrderDetail;
import com.aso.model.Product;
import com.aso.model.Status;
import com.aso.model.dto.CartItemDTO;
import com.aso.model.dto.OrderDTO;
import com.aso.model.dto.OrderDetailDTO;
import com.aso.model.dto.StatusDTO;
import com.aso.service.account.AccountService;
import com.aso.service.order.OrderService;
import com.aso.service.orderdetail.OrderDetailService;
import com.aso.service.product.ProductService;
import com.aso.service.status.StatusService;
import com.aso.utils.AppUtil;
import com.aso.utils.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.zip.DataFormatException;

@RestController
@RequestMapping("/api/orders-detail")
@CrossOrigin(origins = "http://localhost:3000")
public class OrderDetailAPI {
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;
    @Autowired
    private StatusService statusService;
    @Autowired
    private AppUtil appUtil;

    @GetMapping("/{email}")
    // đã test ok
    public ResponseEntity<?> getOrderByAccountEmail(@PathVariable String email) {
        try {
            Optional<Account> accountOptional = accountService.getByEmail ( email );
            if ( accountOptional.isEmpty () ) {
                throw new AccountInputException ( "Tài khoản không tồn tại!" );
            }
            List<OrderDetailDTO> orderDetailDTOS = orderDetailService.findAllOrderDetailByAccountEmail (email);
            return new ResponseEntity<>(orderDetailDTOS, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException("Không lấy được danh sách sản phảm trong giỏ hàng");
        }
    }
    @GetMapping("/get-by-product-created-by/{createdBy}")
    // đã test ok
    public ResponseEntity<?> getOrderDetailByProductCreatedBy(@PathVariable String createdBy) {
        try {
            Optional<Account> accountOptional = accountService.getByEmail ( createdBy );
            if ( accountOptional.isEmpty () ) {
                throw new AccountInputException ( "Tài khoản không tồn tại!" );
            }
            List<OrderDetailDTO> orderDetailDTOS = orderDetailService.findAllOrderDetailByProductCreatedBy (createdBy);
            return new ResponseEntity<>(orderDetailDTOS, HttpStatus.OK);
        } catch (Exception e) {
            throw new DataOutputException ( e.getMessage () );
        }
    }

    @PostMapping("/create/{orderId}")
    public ResponseEntity<?> createOrderDetail(@PathVariable("orderId") Long orderId, @Validated @RequestBody List<OrderDetailDTO> orderDetailDTOList) {
        String email = appUtil.getPrincipalEmail ();
        try {

           OrderDTO orderDTO = orderService.findOrderDTOById ( orderId );
            if (orderDTO == null) {
                throw new RuntimeException ("Đơn hàng không tồn tại");
            }

            for (OrderDetailDTO orderDetailDTO: orderDetailDTOList) {
                Optional<Product> productOptional = productService.findById ( orderDetailDTO.getProduct ().getId () );
                if (productOptional.isEmpty ()) {
                    throw new RuntimeException ("Không tồn tại " + orderDetailDTO.getProduct ().getTitle () + " trong dữ liệu");
                }
                Long currentAvailable = productOptional.get ().getAvailable ();
                long newAvailable = currentAvailable - orderDetailDTO.getQuantity ();

                if ( newAvailable < 0 ) {
                    throw new DataInputException ( "Số lượng không hợp lệ" );
                }
            }

            List<OrderDetailDTO> orderDetailDTOS = orderDetailService.doCreateOrderDetail ( orderId, orderDetailDTOList );


            return new ResponseEntity<>(orderDetailDTOS, HttpStatus.OK);
        }catch (Exception e){
            throw new RuntimeException (e);
        }
    }
    @PutMapping("/update-status/{orderDetailId}")
    public ResponseEntity<?> updateStatus(@PathVariable("orderDetailId") Long orderDetailId, @RequestBody StatusDTO statusDTO) {
        String email = appUtil.getPrincipalEmail ();
        try {
            Optional<OrderDetail> optionalOrderDetail = orderDetailService.findById ( orderDetailId );
            if ( optionalOrderDetail.isEmpty () ) {
                throw new DataInputException ( "Đơn hàng không tồn tại" );
            }

            Optional<Status> statusOptional = statusService.findById ( statusDTO.getId () );
            if ( statusOptional.isEmpty () ) {
                throw new DataOutputException ( "Trạng thái không tồn tại" );
            }

            if ( statusDTO.getId ().equals ( optionalOrderDetail.get ().getStatus ().getId () ) ) {
                throw new AttributesExistsException ( "Hãy chọn trạng thái cần thay đổi" );
            }

            OrderDetailDTO orderDetailDTO = orderDetailService.doUpdateStatus ( optionalOrderDetail.get (), statusOptional.get () );
            return new ResponseEntity<>(orderDetailDTO, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage (), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/chart/{sYear}")
    public ResponseEntity<?> doChart(@PathVariable String sYear) {
        return new ResponseEntity<>(orderDetailService.getListChart(sYear), HttpStatus.OK);
    }
}
