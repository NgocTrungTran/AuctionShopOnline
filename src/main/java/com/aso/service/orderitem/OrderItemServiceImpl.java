package com.aso.service.orderitem;


import com.aso.model.OrderDetail;
import com.aso.model.Product;
import com.aso.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public List<OrderDetail> findAll() {
        return null;
    }

    @Override
    public Optional<OrderDetail> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public OrderDetail getById(Long id) {
        return null;
    }

    @Override
    public void softDelete(OrderDetail orderDetail) {

    }

    @Override
    public OrderDetail save(OrderDetail orderDetail) {
        return orderItemRepository.save( orderDetail );
    }

    @Override
    public void delete(Product id) {

    }

    @Override
    public Boolean existById(Long id) {
        return null;
    }
}
