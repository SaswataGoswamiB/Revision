package Security.Spring.Revision.Service;

import Security.Spring.Revision.Entity.User;
import Security.Spring.Revision.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserNameDetails implements UserDetailsService {

    @Autowired
    UserRepo userrepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userrepo.findByName(username).get();
        if(user == null){
            throw new UsernameNotFoundException("User Not Found Against the Username");
        }
        return org.springframework.security.core.userdetails.User.
                withUsername(user.getName()).
                password(user.getPassword()).
                roles(user.getRole()).
                build();
    }
}
