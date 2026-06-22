package Security.Spring.Revision.Repository;

import Security.Spring.Revision.Entity.Product;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductDao extends CrudRepository<Product,Integer> {

     @Query(value ="select * from Product p where p.product_name = :productname ",nativeQuery = true)
     List<Product> findbyName(@Param(value = "productname")String productname);

     @Query(value = "select p.product_name" +
             "       from Product p" +
             "       where p.price >= :startValue" +
             "       and p.price <= :endValue",nativeQuery = true)
     List<String> findNamebyRange(@Param(value="startValue") Double startValue,@Param(value="endValue") Double endValue);
}
