package com.example.restapp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
public interface OrderRepo extends JpaRepository<Order, Long >{
  //  @Query("select o from Order o where o.price < :price")

    //קיבלנו מחיר ועברנו על כול מוצר ברשימת המוצרים של הזמנה. ובדקנו אם יש מוצר יותר נמוך מהמחיר
    @Query("select o from Order o select p from o.productList where p.price < :price")
    List<Order> findOrdersThatTheyCheaper(double price);

}
