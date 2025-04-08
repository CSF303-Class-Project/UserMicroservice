package bt.edu.gcit.usermicroservice.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import bt.edu.gcit.usermicroservice.entity.User;
import bt.edu.gcit.usermicroservice.exception.FileSizeException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Repository
public class UserDAOImpl implements UserDAO {
  
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User save(User user) {
        entityManager.persist(user);
        return user;
    }
  @Override
    public User findByEmail(String email) {
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class);
        query.setParameter("email", email);
        List<User> users = query.getResultList();
        System.out.println(users.size());
        if (users.isEmpty()) {
            return null;  // No user found with the given email
        } else {
            System.out.println(users.get(0)+" " + users.get(0).getEmail());
            return users.get(0);  // Return the first user found
        }
    }

    @Override
    public User findByID(int theId) {
        //implement the logic to find user by their ID in the database
        //and return the user object
        User user = entityManager.find(User.class, theId);
        return user;
    }

    @Override
    public void deleteById(int theId) {
       // Implement the logic to delete a user by their ID from the database
    //find user by id
    User user = findByID(theId);
    //remove user
    entityManager.remove(user);
    }

    @Override
    public void updateUserEnabledStatus(int id, boolean enabled) {
        // Implement the logic to update the user's enabled status
        User user = entityManager.find(User.class, id);
        System.out.println(user);
        if (user == null) {
            throw new FileSizeException("User not found with id: " + id);
        }
        user.setEnabled(enabled);
        entityManager.persist(user);
    }

}
  