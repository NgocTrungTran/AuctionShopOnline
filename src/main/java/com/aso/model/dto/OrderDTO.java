package com.aso.model.dto;


import com.aso.model.LocationRegion;
import com.aso.model.Order;
import com.aso.model.OrderDetail;
import com.aso.model.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
//@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class OrderDTO {

    private Long id;

    private String fullName;

    private String phone;

    private String email;
    private LocationRegionDTO locationRegion;
    private String description;

    private StatusDTO status;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm", timezone = "Asia/Ho_Chi_Minh")
    private Date createdAt;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm", timezone = "Asia/Ho_Chi_Minh")
    private Date updatedAt;


    private OrderDetailDTO orderDetail;

    public OrderDTO(Long id, LocationRegion locationRegion, String description, Status status, OrderDetail orderDetail, Date createdAt, Date updatedAt) {
        this.id = id;
        this.locationRegion = locationRegion.toLocationRegionDTO();
        this.description = description;
        this.status = status.toStatusDTO ();
        this.orderDetail = orderDetail.toOrderDetailDTO();
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Order toOrder() {
        return new Order()
                .setId(id)
                .setDescription(description)
                .setLocationRegion(locationRegion.toLocationRegion())
                .setStatus (status.toStatus ())
                .setOrderDetail(orderDetail.toOrderDetail())
                ;

    }
}
