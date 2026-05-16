package Security.Spring.Revision.Repository;

import Security.Spring.Revision.Entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends CrudRepository<User,String> {
}
