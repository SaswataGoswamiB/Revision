package Security.Spring.Revision.Repository;

import Security.Spring.Revision.Entity.User;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends CrudRepository<User,String> {
    Optional<User> findByName(String name);


    @Query(value="select * from user where name=:name and password =:password", nativeQuery = true)
    Optional<User> findByNameAndPassword(
            @Param(value = "name")String name,
            @Param(value = "password")String password
    );
}
