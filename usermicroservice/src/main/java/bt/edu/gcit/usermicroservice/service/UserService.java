package bt.edu.gcit.usermicroservice.service;

import bt.edu.gcit.usermicroservice.entity.User;

public interface UserService {

    User save(User user);
    User getUserById(Long id); // New method for fetching a user
    User updateUser(Long id, User user); // New method for updating a user
    void deleteUser(Long id); // Add delete method
}
