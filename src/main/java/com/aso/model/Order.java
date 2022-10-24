package com.aso.model;


import com.aso.model.dto.OrderDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "orders")
@Accessors(chain = true)
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    private String phone;

    private String email;
    @OneToOne
    @JoinColumn(name = "location_region_id", referencedColumnName = "id")
    private LocationRegion locationRegion;

    private String description;

    @OneToOne
    @JoinColumn(name = "status_id", referencedColumnName = "id", columnDefinition = "BIGINT default 1")
    private Status status;

    @ManyToOne
    @JoinColumn(name = "orderDetail_id")
    private OrderDetail orderDetail;

    public OrderDTO toOrderDTO() {
        return new OrderDTO()
                .setId(id)
                .setDescription(description)
                .setStatus (status.toStatusDTO ())
                .setLocationRegion(locationRegion.toLocationRegionDTO())
                .setOrderDetail(orderDetail.toOrderDetailDTO())
                ;
    }
}
