package com.aso.service.order;


import com.aso.model.Order;
import com.aso.model.dto.OrderDTO;
import com.aso.model.dto.OrderDetailDTO;
import com.aso.service.IGeneralService;

import java.util.List;

public interface OrderService extends IGeneralService<Order> {
    List<OrderDTO> findOrderDTOById(String id);
    List<OrderDTO> findOrderDTOByUsername(String username);
    List<OrderDTO> findOrderDTO();
    List<OrderDTO> findOrderDTOByDeliver(String order);
    List<OrderDTO> findAllOrderDTOByOrderDetailId (Long id);

    List<OrderDetailDTO> doCreateOrder (String username, OrderDTO orderDTO, List<OrderDetailDTO> orderDetailDTOList);
}
