package bt.edu.gcit.usermicroservice.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bt.edu.gcit.usermicroservice.dao.RoleDAO;
import bt.edu.gcit.usermicroservice.entity.Role;

@Service
public class RoleServiceImpl implements RoleService {

    private RoleDAO roleDAO;

    @Autowired
    public RoleServiceImpl(RoleDAO roleDAO) {
        this.roleDAO = roleDAO;
    }

    @Override
    @Transactional
    public Role save(Role role) {
        return roleDAO.save(role);
    }

    @Override
    public Role getRoleById(int id) {
        return roleDAO.findById(id).orElse(null);  // Use Optional to handle null safely
    }

    @Override
    @Transactional
    public Role updateRole(int id, Role role) {
        Optional<Role> optionalRole = roleDAO.findById(id);

        if (optionalRole.isPresent()) {
            Role existingRole = optionalRole.get();
            existingRole.setName(role.getName());  // Update role name
            existingRole.setDescription(role.getDescription());  // Update role description

            return roleDAO.save(existingRole);  // Save the updated role
        } else {
            throw new RuntimeException("Role not found with id: " + id);  // Handle the case where the role is not found
        }
    }

    @Override
    @Transactional
    public void deleteRole(int id) {
        Optional<Role> optionalRole = roleDAO.findById(id);
        if (optionalRole.isPresent()) {
            roleDAO.delete(optionalRole.get());  // Delete the role if found
        } else {
            throw new RuntimeException("Role not found with id: " + id);  // Role not found
        }
    }

    // Implement the addRole method with void return type
    @Override
    @Transactional
    public void addRole(Role role) {
        roleDAO.save(role);  // Save the role, no return value
    }
}
