package Security.Spring.Revision.Controller;

import Security.Spring.Revision.Entity.User;
import Security.Spring.Revision.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/User")
public class UserController {

    @Autowired
    UserRepo userrepo;

    @PostMapping("/Register")
    User RegisterUser(@RequestBody User user){
        User saveduser =  userrepo.save(user);
        return saveduser;
    }

    @PostMapping("/Login")
    User LoginUser(@RequestBody User user){
        return user;
    }


}
