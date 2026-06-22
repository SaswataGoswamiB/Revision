package Security.Spring.Revision.Controller;


import Security.Spring.Revision.Entity.Product;
import Security.Spring.Revision.Feign.ProductFeign;
import Security.Spring.Revision.Repository.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/Product")
public class ProductController {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ProductDao productDao;

    @Autowired
    ProductFeign productfeign;

    @GetMapping
    public List<Product> getprpducts() {
        Iterable<Product> all = productDao.findAll();
        return (List<Product>) all;
    }

    @PostMapping
    public List<Product> SaveProduct(@RequestBody Product product) {
        productDao.save(product);
        return productfeign.getProducts();
    }

    @GetMapping("/{name}")
    public List<Product> getprpductsbyName(@PathVariable String name) {
       return  productDao.findbyName(name);
    }

    @GetMapping("/Price/{startRange}/{endRange}")
    public List<Product>getProductbyRange(@PathVariable Double startRange, @PathVariable Double endRange){
        List<String> namebyRange = productDao.findNamebyRange(startRange, endRange);
        List<Product>producctList = List.of();
        for(String product:namebyRange){
            producctList.add(exchangeRestTemplateExchange(product));
        }

        return producctList;
    }

    private  Product exchangeRestTemplateExchange(String productname){
        String Base_url = "http://localhost:8089/Product";
        String final_url = Base_url+"/"+productname;

        HttpHeaders httpHeaders  = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        //httpHeaders.setBearerAuth();

        //HttpEntity is is a Spring class that represents an entire HTTP request or response entity, consisting of Headers and Body
        HttpEntity httpEntity = new HttpEntity(httpHeaders);

        ResponseEntity<Product> exchange = restTemplate.exchange(final_url, HttpMethod.GET, httpEntity, Product.class);

        HttpStatusCode statusCode = exchange.getStatusCode();
        System.out.println(statusCode);


       return  exchange.getBody();
    }
}

