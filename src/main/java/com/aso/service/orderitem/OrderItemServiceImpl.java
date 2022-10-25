package com.aso.service.orderitem;


import com.aso.model.OrderDetail;
import com.aso.model.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderItemServiceImpl implements OrderItemService {

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
        return null;
    }

    @Override
    public void delete(Product id) {
    }

    @Override
    public Boolean existById(Long id) {
        return null;
    }
}
