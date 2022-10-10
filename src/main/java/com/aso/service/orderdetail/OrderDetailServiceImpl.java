package com.aso.service.orderdetail;

import com.aso.model.OrderDetail;
import com.aso.model.Product;
import com.aso.model.dto.OrderDTO;
import com.aso.model.dto.OrderDetailDTO;
import com.aso.model.dto.ProductDTO;
import com.aso.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderDetailServiceImpl  implements OrderDetailService{

    @Autowired
    private OrderRepository orderRepository;


    @Autowired
    private CartRepository cartRepoSitory;

    @Autowired
    private CartItemRepository cartItemRepository;


    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private ProductRepository productRepository;



    @Override
    public List<OrderDetail> findAll() {
//        return orderDetailRepository.findAll();
        return null;
    }

    @Override
    public Boolean existById(Long id) {
        return null;
    }

    @Override
    public Optional<OrderDetail> findById(Long id) {
        return orderDetailRepository.findById(id);
    }

    @Override
    public OrderDetail getById(Long id) {
        return null;
    }

    @Override
    public OrderDetail save(OrderDetail orderDetail) {
        return null;
    }

    @Override
    public void softDelete(OrderDetail orderDetail) {

    }

    @Override
    public void delete(Product id) {

    }


    @Override
    public List<OrderDetailDTO> findAllOrderDetailById(String id) {
        return orderDetailRepository.findAllOrderDetailId(id);
    }

    @Override
    public List<OrderDetailDTO> findOderByCreateMonthYearAndStatusOrderDetail(int createMonth, int createYear, String statusOrderDetail) {
        return orderDetailRepository.findOderByCreateMonthYearAndStatusOrderDetail(createMonth,createYear,statusOrderDetail);
    }

}
