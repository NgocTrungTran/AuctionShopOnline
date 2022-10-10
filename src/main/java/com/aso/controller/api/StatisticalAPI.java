package com.aso.controller.api;

import com.aso.model.dto.OrderDetailDTO;
import com.aso.service.order.OrderService;
import com.aso.service.orderdetail.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/statistical")
public class StatisticalAPI {
    @Autowired
    private OrderDetailService orderDetailService;

// Số liệu thống kê đơn hàng đã giao
    @GetMapping("/statistical-by-month-year/{month}/{year}")
    public ResponseEntity<?> getStatisticalByMonthYear(@PathVariable int month, @PathVariable int year, @Param("statusOrderDetail") String statusOrderDetail) {
        List<OrderDetailDTO> orderDetailDTOS = orderDetailService.findOderByCreateMonthYearAndStatusOrderDetail(month, year,"Đơn hàng đã giao thành công!");
        return new ResponseEntity<>(orderDetailDTOS, HttpStatus.OK);
    }
}
