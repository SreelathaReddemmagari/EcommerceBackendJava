//package com.ecom.repo;
//
//import com.ecom.model.Cart;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//public interface CartRepository extends JpaRepository<Cart,Integer> {
//    Cart findByUserId(Integer userId);
//}
package com.ecom.repo;

import com.ecom.model.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CartRepository extends MongoRepository<Cart, String> {
    Cart findByUserId(Integer userId);
}
