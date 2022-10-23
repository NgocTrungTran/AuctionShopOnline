package com.aso.service.order;


import com.aso.model.Order;
import com.aso.model.dto.OrderDTO;
import com.aso.service.IGeneralService;

import java.util.List;

public interface OrderService extends IGeneralService<Order> {
    List<OrderDTO> findOrderDTOById(String id);
    List<OrderDTO> findOrderDTO();
    List<OrderDTO> findOrderDTOByDeliver(String order);
    List<OrderDTO> findAllOrderDTOByOrderDetailId (Long id);


}
