package com.aso.service.order;


import com.aso.exception.AccountInputException;
import com.aso.exception.DataInputException;
import com.aso.model.*;
import com.aso.model.dto.LocationRegionDTO;
import com.aso.model.dto.OrderDTO;
import com.aso.model.dto.OrderDetailDTO;
import com.aso.model.dto.ProductDTO;
import com.aso.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private StatusRepository statusRepository;
    @Autowired
    private LocationRegionRepository locationRegionRepository;
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
    public List<OrderDTO> findOrderDTOByUsername(String username) {
        return orderRepository.findOrderDTOByUsername ( username );
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

    @Override
    public OrderDTO doCheckoutOrder(Long accountId, OrderDTO orderDTO) {
//        List<OrderDetailDTO> orderDetailDTOS = new ArrayList<> ();
        Optional<Account> accountOptional = accountRepository.findById ( accountId );
        if ( accountOptional.isEmpty () ) {
            throw new AccountInputException ( "Tài khoản không tồn tại" );
        }
        Optional<Status> status = statusRepository.findById ( 7L );

        LocationRegion newLocationRegion = locationRegionRepository.save ( orderDTO.getLocationRegion ().toLocationRegion () );
        orderDTO.setLocationRegion ( newLocationRegion.toLocationRegionDTO () );

        orderDTO.setAccount ( accountOptional.get ().toAccountDTO () );
        orderDTO.setStatus ( status.get ().toStatusDTO () );
        orderDTO.setCreatedBy ( accountOptional.get ().getUsername () );
        Order order = orderRepository.save ( orderDTO.toOrder () );

//        for (OrderDetailDTO orderDetailDTO: orderDetailDTOList) {
//            Optional<Product> optionalProduct = productRepository.findById ( orderDetailDTO.getProduct ().getId () );
//            if ( optionalProduct.isEmpty () ) {
//                throw new DataInputException ( "Sản phẩm không tồn tại" );
//            }
//
//            OrderDetail orderDetail = orderDetailDTO.toOrderDetail ();
//            orderDetail.setProduct ( optionalProduct.get () );
//
//            OrderDetail newOrderDetail = orderDetailRepository.save ( orderDetail );
//            orderDetailDTOS.add ( newOrderDetail.toOrderDetailDTO () );
//        }

        return order.toOrderDTO ();
    }
}
