package Security.Spring.Revision.Repository;

import Security.Spring.Revision.Entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<User,String> {
}
