package Security.Spring.Revision.Feign;

import Security.Spring.Revision.Entity.Product;
import Security.Spring.Revision.Interceptors.FeignInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name ="ProductName",url ="http://localhost:8089",configuration = FeignInterceptor.class)
public interface ProductFeign {
    @GetMapping("/Product")
    List<Product> getProducts();
}
