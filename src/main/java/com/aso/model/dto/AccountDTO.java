package com.aso.model.dto;

import com.aso.model.Account;
import com.aso.model.LocationRegion;
import com.aso.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class AccountDTO {
    private Long id;

    @NotBlank(message = "username not blank")
    @Size(min = 8, max = 20, message = "Username size between 8 and 20")
    private String username;

    @Size(min = 5, max = 30, message = "Full name size between 5 and 30")
    private String fullName;

    @Size(min = 5, max = 30,  message = "Email size between 5 and 30")
    private String email;

    @Size(min = 9, max = 10,  message = "Phone size between 9 and 10")
    private String phone;

    @Size(min = 8, max = 20,  message = "Password size between 8 and 20")
    private String password;

    private boolean blocked;

    private String avatar;

    private RoleDTO role;
    private LocationRegionDTO locationRegion;


    public AccountDTO(Long id, String username, String fullName, String email, String phone, String avatar, boolean blocked, LocationRegion locationRegion) {
        this.id = id;
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.avatar = avatar;
        this.blocked = blocked;
        this.locationRegion = locationRegion.toLocationRegionDTO ();
    }

    public AccountDTO(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    public Account toAccount() {
        return new Account()
                .setId(id)
                .setUsername(username)
                .setPassword(password)
                ;
    }

    public Account toAccountAllAttribute() {
        return new Account()
                .setId(id)
                .setUsername(username)
                .setPassword(password)
                .setFullName ( fullName )
                .setEmail ( email )
                .setPhone ( phone )
                .setAvatar ( avatar )
                .setRole ( role.toRole () )
                .setLocationRegion ( locationRegion.toLocationRegion () );
    }

    @Override
    public String toString() {
        return "AccountDTO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", blocked=" + blocked +
                ", avatar='" + avatar + '\'' +
                ", role=" + role +
                ", locationRegion=" + locationRegion +
                '}';
    }
}

