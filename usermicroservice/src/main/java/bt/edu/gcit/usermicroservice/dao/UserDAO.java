package bt.edu.gcit.usermicroservice.dao;

import java.util.Optional;

import bt.edu.gcit.usermicroservice.entity.User;

public interface UserDAO {

    User save(User user);

    Optional<User> findById(Long id);  // Change return type to Optional<User>
    
    User update(User user); // New method for updating a user

    void delete(User user); // Add delete method

}
