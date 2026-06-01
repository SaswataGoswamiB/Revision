package Security.Spring.Revision.Controller;

import Security.Spring.Revision.Entity.User;
import Security.Spring.Revision.Repository.UserRepo;
import Security.Spring.Revision.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/User")
public class UserController {

    @Autowired
    UserRepo userrepo;

    @Autowired
    UserService userservice;

    @PostMapping("/Register")
    User RegisterUser(@RequestBody User user){
        String password = user.getPassword();
        String newPassword = new BCryptPasswordEncoder().encode(password);
        user.setPassword(newPassword);
        User saveduser =  userrepo.save(user);
        return saveduser;
    }

    @PostMapping("/Login")
    String LoginUser(@RequestBody User user){
        return userservice.vertifyUser(user);
    }


}
