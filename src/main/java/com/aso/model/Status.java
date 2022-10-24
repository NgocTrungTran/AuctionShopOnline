package com.aso.model;

import com.aso.model.dto.StatusDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "status")
@Accessors(chain = true)
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    private String name;

    @OneToOne(mappedBy = "status")
    private Cart cart;

    @OneToOne(mappedBy = "status")
    private Order order;

    @OneToOne(mappedBy = "status")
    private OrderDetail orderDetail;

    public StatusDTO toStatusDTO() {
        return new StatusDTO ()
                .setId ( id )
                .setCode ( code )
                .setName ( name );
    }
}
