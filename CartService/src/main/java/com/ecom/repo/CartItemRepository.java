//package com.ecom.repo;
//
//import com.ecom.model.Cart;
//import com.ecom.model.CartItem;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import java.util.List;
//
//public interface CartItemRepository extends JpaRepository<CartItem,Integer>{
//    List<CartItem> findByCart(Cart cart);
//    void deleteByCart(Cart cart);
//}
package com.ecom.repo;

import com.ecom.model.Cart;
import com.ecom.model.CartItem;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CartItemRepository extends MongoRepository<CartItem, String> {

     List<CartItem> findByCart(Cart cart);
     void deleteByCart(Cart cart);


}
