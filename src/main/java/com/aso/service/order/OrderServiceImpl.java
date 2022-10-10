package com.aso.service.order;


import com.aso.exception.DataInputException;
import com.aso.model.Order;
import com.aso.model.OrderDetail;
import com.aso.model.Product;
import com.aso.model.dto.CartDTO;
import com.aso.model.dto.CartItemListDTO;
import com.aso.model.dto.OrderDTO;
import com.aso.model.dto.ProductDTO;
import com.aso.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private CartRepository cartRepoSitory;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private ProductRepository productRepository;


    @Override
    public List<Order> findAll() {
        return null;
    }

    @Override
    public Optional<Order> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Order getById(Long id) {
        return null;
    }

    @Override
    public void softDelete(Order order) {

    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

//    @Override
//    @Transactional
//    public Order save(Order order) throws MessagingException, UnsupportedEncodingException {
//        OrderDetail orderDetail = new OrderDetail();
//        orderDetail.setId(0L);
//        orderDetail.setStatusOrderDetail("abc");
//        orderDetail.setCreatedAt(new Date());
//        OrderDetail orderNew = orderDetailRepository.save(orderDetail);
//        BigDecimal sum = BigDecimal.valueOf(0);
//        List<CartItemListDTO> cartItemsDTOList = cartItemRepository.findCartItemDTOById(order.getStatusOrder());
//        for (CartItemListDTO cartItemsDTO : cartItemsDTOList) {
//            Optional<ProductDTO> productDTO = productRepository.findProductDTOById(cartItemsDTO.getProductId().getId());
//            productDTO.get().setAvailable(productDTO.get().getAvailable().subtract(cartItemsDTO.getQuantity()));
//            if (productDTO.get().getAvailable().compareTo(BigDecimal.ZERO) < 0) {
//                cartItemRepository.deleteById(cartItemsDTO.getId());
//                orderDetailRepository.deleteById(orderNew.getId());
//                productDTO.get().setAvailable(Long.valueOf("Đã Hết hàng"));
//                throw new DataInputException("Số lượng sản phẩm " + cartItemsDTO.getProductId().getTitle() + " không đủ để order!");
//            }
//            productRepository.save(productDTO.get().toProduct());
//            order.setId(0L);
//            order.setOrderDetail(orderNew);
//
//        }
//        return order;
//    }

    @Override
    public void delete(Product id) {

    }

    @Override
    public Boolean existById(Long id) {
        return null;
    }

    @Override
    public List<OrderDTO> findOrderDTOById(String id) {
        return orderRepository.findOrderDTOById(id);
    }

    @Override
    public List<OrderDTO> findOrderDTO() {
        return orderRepository.findOrderDTO();
    }

}
