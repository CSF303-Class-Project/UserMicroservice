package bt.edu.gcit.usermicroservice.service;

import bt.edu.gcit.usermicroservice.entity.Role;

public interface RoleService {

    void addRole(Role role);
    Role getRoleById(int id);
    Role updateRole(int id, Role role);
    void deleteRole(int id);
    Role save(Role role);


}
