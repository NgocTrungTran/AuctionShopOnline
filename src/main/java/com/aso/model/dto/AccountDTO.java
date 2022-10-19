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
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class AccountDTO {
    private Long id;
    private Date createdAt;
    private String createdBy;
    private Date updateAt;
    private String updateBy;
    @NotBlank(message = "username not blank")
    @Size(min = 8, max = 20, message = "Username size between 8 and 20")
    private String username;
    @Size(min = 5, max = 30, message = "Full name size between 5 and 30")
    private String fullName;
    @Size(min = 5, max = 30,  message = "Email size between 5 and 30")
    private String email;
    private String phone;
    @Size(min = 8, max = 20,  message = "Password size between 8 and 20")
    private String password;
    private boolean blocked;
    private String avatar;
    private RoleDTO role;
    private LocationRegionDTO locationregion;

    public AccountDTO(Long id, String username, String fullName, String email, String phone, String password, boolean blocked, String avatar, Role role, LocationRegion locationregion) {
        this.id = id;
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.blocked = blocked;
        this.avatar = avatar;
        this.role = role.toRoleDTO();
        this.locationregion = locationregion.toLocationRegionDTO();
    }

    public AccountDTO(Long id, String username, String fullName, String email, String phone, String avatar, boolean blocked, LocationRegion locationregion) {
        this.id = id;
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.avatar = avatar;
        this.blocked = blocked;
        this.locationregion = locationregion.toLocationRegionDTO ();
    }

    public AccountDTO(Long id, Date createdAt, String createdBy, Date updateAt, String updateBy, String username, String fullName, String email, String phone, String password, boolean blocked, String avatar, Role role, LocationRegion locationregion) {
        this.id = id;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updateAt = updateAt;
        this.updateBy = updateBy;
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.blocked = blocked;
        this.avatar = avatar;
        this.role = role.toRoleDTO();
        this.locationregion = locationregion.toLocationRegionDTO();
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
                .setFullName ( fullName )
                .setEmail ( email )
                .setPhone ( phone )
                .setAvatar ( avatar )
                .setRole ( role.toRole () )
                .setLocationRegion ( locationregion.toLocationRegion () );
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
                .setLocationRegion ( locationregion.toLocationRegion () );
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
                ", locationRegion=" + locationregion.toLocationRegion() +
                '}';
    }
}

