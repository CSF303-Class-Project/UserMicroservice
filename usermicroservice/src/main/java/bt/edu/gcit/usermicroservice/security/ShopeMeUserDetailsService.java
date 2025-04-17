package bt.edu.gcit.usermicroservice.security;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import bt.edu.gcit.usermicroservice.dao.UserDAO;
import bt.edu.gcit.usermicroservice.entity.User;
import java.util.List;
import java.util.stream.Collectors;
import bt.edu.gcit.usermicroservice.security.ShopmeuserDetails;
import bt.edu.gcit.usermicroservice.dao.CustomerDAO; // import the new DAO forCustomer entities
import bt.edu.gcit.usermicroservice.entity.Customer; // import the new Customerentity
import java.util.Collections; // import the Collections class

@Service
public class ShopeMeUserDetailsService implements UserDetailsService {
    private final UserDAO userDAO;
    private final CustomerDAO customerDAO; // new DAO for Customer entities

    @Autowired
    public ShopeMeUserDetailsService(UserDAO userDAO, CustomerDAO customerDAO) {
        this.userDAO = userDAO;
        this.customerDAO = customerDAO; // initialize the new DAO
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userDAO.findByEmail(email);

        if (user != null) {
            List<GrantedAuthority> authorities = user.getRoles().stream()
                    .map(role -> new SimpleGrantedAuthority(role.getName()))
                    .collect(Collectors.toList());
            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                    authorities);
        }
        Customer customer = customerDAO.findByEMail(email); // try to load a Customerif no User was found

        if (customer != null) {
            // since Customer entities don't have authorities, we just pass an emptylist of
            // authorities
            return new org.springframework.security.core.userdetails.User(customer.getEmail(), customer.getPassword(),
                    Collections.emptyList());
        }

        throw new UsernameNotFoundException("User not found with email: " + email);
    }
}
