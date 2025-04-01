package bt.edu.gcit.usermicroservice.dao;

import java.util.Optional;

import bt.edu.gcit.usermicroservice.entity.Role;

public interface RoleDAO {

    Role save(Role role);

    Optional<Role> findById(int id);  

    Role update(Role role);

    void delete(Role role);
}
