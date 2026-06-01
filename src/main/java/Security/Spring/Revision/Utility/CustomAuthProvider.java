package Security.Spring.Revision.Utility;

import Security.Spring.Revision.Entity.User;
import Security.Spring.Revision.Repository.UserRepo;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomAuthProvider implements AuthenticationProvider {

    @Autowired
    UserRepo userRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public @Nullable Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String encodedPassword = authentication.getCredentials().toString();
                //passwordEncoder.encode(authentication.getCredentials().toString());
        User user = userRepo.findByNameAndPassword(username, encodedPassword).get();

        if (user!=null && user.getPassword().equals(encodedPassword)){
             return new UsernamePasswordAuthenticationToken(username,user.getPassword(), AuthorityUtils.createAuthorityList(
                     "ROLE_" + user.getRole()
             ));
        }
    return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class
                .isAssignableFrom(authentication);
    }
}
