package bt.edu.gcit.usermicroservice.service;

import bt.edu.gcit.usermicroservice.entity.User;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

public interface UserService {
    User save(User user);

    // User findById(Long id);
    List<User> findAll();

    void deleteById(Long id);

    // User update(User user);
    Boolean isEmailDuplicate(String email);

    User updateUser(int id, User updatedUser);

    void updateUserEnabledStatus(int id, boolean enabled);

    void uploadUserPhoto(int id, MultipartFile photo) throws IOException;

    User findByID(int theId);

}