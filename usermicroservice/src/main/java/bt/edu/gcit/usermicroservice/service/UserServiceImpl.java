package bt.edu.gcit.usermicroservice.service;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;
import java.nio.file.Path;
import bt.edu.gcit.usermicroservice.exception.UserNotFoundException;
import bt.edu.gcit.usermicroservice.exception.FileSizeException;
import java.nio.file.Paths;
import bt.edu.gcit.usermicroservice.dao.UserDAO;
import bt.edu.gcit.usermicroservice.entity.User;


@Service
public class UserServiceImpl implements UserService {

    private UserDAO userDAO;
    private final BCryptPasswordEncoder passwordEncoder;
    private final String uploadDir = "src/main/resources/static/images";

    @Autowired
    @Lazy
    public UserServiceImpl(UserDAO userDAO, BCryptPasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userDAO.save(user);
    }

    @Transactional
    @Override
    public boolean isEmailDuplicate(String email) {
        User user = userDAO.findByEmail(email);
        return user != null;
    }

    @Override
    public User findByID(int theId) {
        return userDAO.findByID(theId);
    }

    @Transactional
    @Override
    public User updateUser(int id, User updateUser) {
        //First, find the user by ID
        User existingUser = userDAO.findByID(id);

        //If  the user does't exist, throw UserNotFoundException
        if (existingUser == null) {
            throw new UserNotFoundException("User not found with id: " + id);
        }

        //update the existing user with the data from updateUser
        existingUser.setFirstName(updateUser.getFirstName());
        existingUser.setLastName(updateUser.getLastName());
        existingUser.setEmail(updateUser.getEmail());

        //check if the passdword has changed. If it has, encodee the new password before saving
        if (!existingUser.getPassword().equals(updateUser.getPassword())) {
            existingUser.setPassword(passwordEncoder.encode(updateUser.getPassword()));
        }

        existingUser.setRoles(updateUser.getRoles());

        //save the updated user and return it
        return userDAO.save(existingUser);
    }

    @Transactional
    @Override
    public void deleteById(int theId) {
        userDAO.deleteById(theId);
    }

    @Transactional 
    @Override 
    public void updateUserEnabledStatus(int id, boolean enabled) { 
        userDAO.updateUserEnabledStatus(id, enabled); 
    }

    @Transactional
    @Override
    public void uploadUserPhoto(int id, MultipartFile photo) throws IOException {
        // Fetch user by ID
        User user = findByID(id);
        if (user == null) {
            throw new UserNotFoundException("User not found with id " + id);
        }

        // Validate file size (limit to 1MB)
        if (photo.getSize() > 1024 * 1024) {
            throw new FileSizeException("File size must be less than 1MB");
        }

        // Clean the original file name
        String originalFilename = StringUtils.cleanPath(photo.getOriginalFilename());

        // Extract the file extension
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);

        // Extract the file name without extension
        String filenameWithoutExtension = originalFilename.substring(0, originalFilename.lastIndexOf("."));

        // Create a timestamp for uniqueness
        String timestamp = String.valueOf(System.currentTimeMillis());

        // Generate the new file name with timestamp
        String filename = filenameWithoutExtension + "_" + timestamp + "." + fileExtension;

        // Create the path to save the file
        Path uploadPath = Paths.get(uploadDir, filename);

        // Save the file to the specified path
        photo.transferTo(uploadPath);

        // Set the file name in the user object and save
        user.setPhoto(filename);
        save(user);
    }
}