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
    @Override
    public List<OrderDTO> findOrderDTOByDeliver(String order) {
        return orderRepository.findOrderDTOByDeliver(order);
    }
    @Override
    public List<OrderDTO> findAllOrderDTOByOrderDetailId(Long id) {
        return orderRepository.findAllOrderDTOByOrderDetailId(id);
    }
}
