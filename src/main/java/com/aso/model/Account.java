package com.aso.model;


import com.aso.model.dto.AccountDTO;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.List;
@Builder
@Entity
@Table(name = "accounts")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class Account extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;
    @Column(name = "full_name")
    private String fullName;
    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String phone;

    @Column(nullable = false)
    private String password;

    @Column(columnDefinition = "boolean default false")
    private boolean blocked = false;

    private String avatar;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToOne
    @JoinColumn(name = "location_region_id", referencedColumnName = "id")
    private LocationRegion locationRegion;

    @OneToMany(mappedBy = "account")
    private List<Cart> cart;

    @OneToMany(mappedBy = "account")
    private List<Auction> auctions;

    @OneToMany(mappedBy = "account")
    private List<Bid> bids;

    public AccountDTO toAccountDTO() {
        return new AccountDTO ()
                .setId(id)
                .setUsername ( username )
                .setFullName ( fullName )
                .setLocationregion( locationRegion.toLocationRegionDTO ())
                .setEmail ( email )
                .setPhone ( phone )
                .setAvatar ( avatar )
                .setBlocked ( blocked )
                .setRole(role.toRoleDTO());
    }
}
