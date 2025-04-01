package bt.edu.gcit.usermicroservice.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bt.edu.gcit.usermicroservice.dao.UserDAO;
import bt.edu.gcit.usermicroservice.entity.User;

@Service
public class UserServiceImpl implements UserService {

    private UserDAO userDAO;

    @Autowired
    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    @Transactional
    public User save(User user) {
        return userDAO.save(user);
    }

    @Override
    public User getUserById(Long id) {
        return userDAO.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public User updateUser(Long id, User user) {
        Optional<User> optionalUser = userDAO.findById(id);
        
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            if (user.getFirstName() != null) existingUser.setFirstName(user.getFirstName());
            if (user.getLastName() != null) existingUser.setLastName(user.getLastName());
            if (user.getEmail() != null) existingUser.setEmail(user.getEmail());
            if (user.getPassword() != null) existingUser.setPassword(user.getPassword());
            if (user.getPhoto() != null) existingUser.setPhoto(user.getPhoto());
            if (user.getRoles() != null && !user.getRoles().isEmpty()) existingUser.setRoles(user.getRoles());

            return userDAO.save(existingUser);
        } else {
            throw new RuntimeException("User not found with id: " + id);
        }
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        Optional<User> optionalUser = userDAO.findById(id);
        if (optionalUser.isPresent()) {
            userDAO.delete(optionalUser.get());  // Delete the user if found
        } else {
            throw new RuntimeException("User not found with id: " + id);  // User not found
        }
    }
}
