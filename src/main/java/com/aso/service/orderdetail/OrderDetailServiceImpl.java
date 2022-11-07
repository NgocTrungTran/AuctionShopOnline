package com.aso.service.orderdetail;

import com.aso.model.*;
import com.aso.model.dto.OrderDTO;
import com.aso.model.dto.OrderDetailDTO;
import com.aso.model.dto.StatusDTO;
import com.aso.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    @Autowired
    private StatusRepository statusRepository;


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
    public void removeById(OrderDetail orderDetail) {
        orderDetailRepository.save ( orderDetail );
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
    public List<OrderDetailDTO> findAllOrderDetailByAccountEmail(String email) {
        return orderDetailRepository.findAllOrderDetailByAccountEmail ( email );
    }

    @Override
    public List<OrderDetailDTO> findAllOrderDetailByProductCreatedBy(String createdBy) {
        return orderDetailRepository.findAllOrderDetailByProductCreatedBy (createdBy);
    }

    @Override
    public List<OrderDetailDTO> findAllOrderDetailById(String id) {
        return orderDetailRepository.findAllOrderDetailId(id);
    }

    @Override
    public List<OrderDetailDTO> findOderByCreateMonthYearAndStatusOrderDetail(int createMonth, int createYear, String statusOrderDetail) {
        return orderDetailRepository.findOderByCreateMonthYearAndStatusOrderDetail(createMonth,createYear,statusOrderDetail);
    }

    @Override
    public List<OrderDetailDTO> findOderByCreateYearAndStatusOrderDetail(int createYear, String statusOrderDetail) {
        return orderDetailRepository.findOderByCreateYearAndStatusOrderDetail(createYear,statusOrderDetail);
    }

    @Override
    public List<OrderDetailDTO> doCreateOrderDetail(Long orderId, List<OrderDetailDTO> orderDetailDTOList) {
        List<OrderDetailDTO> newOrderDetailDTOList = new ArrayList<> ();
        for (OrderDetailDTO orderDetailDTO: orderDetailDTOList) {
            Optional<Order> orderOptional = orderRepository.findById ( orderId );
            Optional<Product> productOptional = productRepository.findById ( orderDetailDTO.getProduct ().getId () );
            Long currentAvailable = productOptional.get ().getAvailable ();
            long newAvailable = currentAvailable - orderDetailDTO.getQuantity ();

            productOptional.get ().setAvailable ( newAvailable );
            StatusDTO status = statusRepository.findStatusDTOById ( 7L );

            orderDetailDTO.setOrder (orderOptional.get ().toOrderDTO ());
            orderDetailDTO.setProduct (productOptional.get ().toProductDTO () );
            orderDetailDTO.setStatus ( status );
            orderDetailDTO.setCreatedBy ( orderOptional.get ().getAccount ().getEmail () );

            OrderDetail orderDetail = orderDetailDTO.toOrderDetail ();

            orderOptional.get ().setStatus ( status.toStatus () );

           OrderDetail newOrderDetail = orderDetailRepository.save ( orderDetail );
           orderRepository.save ( orderOptional.get () );
           productRepository.save ( productOptional.get () );
           newOrderDetailDTOList.add ( newOrderDetail.toOrderDetailDTO () );
        }

        return newOrderDetailDTOList;
    }

    @Override
    public OrderDetailDTO doUpdateStatus(OrderDetail orderDetail, Status status) {

        orderDetail.setStatus ( status );
        OrderDetail newOrderDetail = orderDetailRepository.save ( orderDetail );
        return newOrderDetail.toOrderDetailDTO ();
    }

    @Override
    public List<Chart> getListChart(String year) {
        return orderDetailRepository.getListChart(year);
    }

    @Override
    public OrderDetail checkOutOrder(OrderDetail orderDetail, String title) {
//        List<OrderDTO> orderList = orderRepository.findAllOrderDTOByOrderDetailId(orderDetail.getId());
//        for (OrderDTO orderDTO : orderList){
//            orderDTO.setStatusOrder("Đơn hàng đã duyệt");
//            orderRepository.save(orderDTO.toOrder());
//        }
//        orderDetail.setStatusOrderDetail("Đơn hàng đã duyệt");
//        return orderDetailRepository.save(orderDetail);
        return null;
    }
    public OrderDetail deliveryOrder(OrderDetail orderDetail, String Title) {
//        List<OrderDTO> orderList = orderRepository.findAllOrderDTOByOrderDetailId(orderDetail.getId());
//        for (OrderDTO orderDTO : orderList){
//            orderDTO.setStatusOrder("Đang giao hàng");
//            orderRepository.save(orderDTO.toOrder());
//        }
//        orderDetail.setStatusOrderDetail("Đang giao hàng");
//        return orderDetailRepository.save(orderDetail);
        return null;
    }

}
