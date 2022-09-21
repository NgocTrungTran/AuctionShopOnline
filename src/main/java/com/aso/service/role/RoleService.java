package com.aso.service.role;

import com.aso.model.Role;
import com.aso.service.IGeneralService;

public interface RoleService extends IGeneralService<Role> {
    Role findByName(String name);
}
