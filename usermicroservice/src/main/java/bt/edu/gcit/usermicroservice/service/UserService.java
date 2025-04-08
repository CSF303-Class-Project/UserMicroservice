package bt.edu.gcit.usermicroservice.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import bt.edu.gcit.usermicroservice.entity.User;

public interface UserService {

    User save(User user);
    boolean isEmailDuplicate(String email); // New method for checking duplicate email
    User updateUser(int id, User updateUser); // New method for updating a user
    void deleteById(int theId); // Add delete method
    void updateUserEnabledStatus(int id, boolean enabled); // New method for updating user enabled status
    void uploadUserPhoto(int id, MultipartFile photo) throws IOException;
    User findByID(int theId);
}
