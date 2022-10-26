package com.aso.model.dto;


import com.aso.model.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
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

    @NotNull
    private String fullName;
    @NotNull
    private String phone;
    @NotNull
    private String email;
    private LocationRegionDTO locationRegion;
    private String description;

    private AccountDTO account;
    private StatusDTO status;

    private OrderDetailDTO orderDetail;

    private String createdBy;

    public OrderDTO(Long id, String fullName, String phone, String email, LocationRegion locationRegion, String description, Account account, Status status, String createdBy) {
        this.id = id;
        this.fullName = fullName;
        this.phone = phone;
        this.email = email;
        this.locationRegion = locationRegion.toLocationRegionDTO ();
        this.description = description;
        this.account = account.toAccountDTO ();
        this.status = status.toStatusDTO ();
        this.createdBy = createdBy;
    }

    public Order toOrder() {
        return (Order) new Order ()
                .setId ( id )
                .setFullName ( fullName )
                .setPhone ( phone )
                .setEmail ( email )
                .setLocationRegion ( locationRegion.toLocationRegion () )
                .setDescription ( description )
                .setAccount ( account.toAccount () )
                .setStatus ( status.toStatus () )
                .setCreatedBy ( createdBy )
                ;

    }
}
