package bt.edu.gcit.usermicroservice.dao;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import bt.edu.gcit.usermicroservice.entity.Role;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class RoleDAOImpl implements RoleDAO {

    @PersistenceContext
    private EntityManager entityManager;

    
    public Role save(Role role) {
        entityManager.persist(role);
        return role;
    }

    @Override
    public Optional<Role> findById(int id) {
        Role role = entityManager.find(Role.class, id);
        return Optional.ofNullable(role);
    }

    @Override
    public Role update(Role role) {
        return entityManager.merge(role);
    }

    @Override
    public void delete(Role role) {
        entityManager.remove(role); // Remove role from the database
    }
}
