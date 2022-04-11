package com.example.restapp;


import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController

public class OrderController {

    private OrderRepo orderDatabase;
    private OrderLinkFactory  orderFactory;

    public OrderController(OrderRepo aDatabase,OrderLinkFactory  orderFactory) {
        this.orderDatabase=aDatabase;
        this.orderFactory=orderFactory;
    }




    //-------שאלה 3 מ Product
    @GetMapping("/Orders/{id}/Products")
    public ResponseEntity<CollectionModel<EntityModel<Product>>>
    productsByOrder(@PathVariable long id){
        // הזמנה נמצאת לפי המזהה ואם אינה קיימת נזרקת שגיאה
        Order order = orderDatabase.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        //   כל המוצרים שקשורים לאותה הזמנה נכנסים לרשימה
        List<Product> products = order.getProductList();

        //  המרה לרשימה עם הייצוג של המוצר
        // נוסף קישור לאותו מוצר ואותה הזמנה
        List<EntityModel<Product>> entityModelList = products.stream()
                .map(product ->
                        orderFactory.toModelOfProductWithOrderLink(order, product)
                )
                .collect(Collectors.toList());
        // יוחזר סטאטוס 200
        return ResponseEntity.ok(CollectionModel.of(entityModelList));
    }





// ----------------1
    @GetMapping("/Orders")
    ResponseEntity<CollectionModel<EntityModel<Order>>> allOrders(){
        // רשימה שמחזירה לי את הייצוג של כל ההזמנות

        List<EntityModel<Order>> Orders = orderDatabase.findAll()
                .stream().map(order -> EntityModel.of(order,
                        // קישור להזמנה
                        linkTo(methodOn(OrderController.class).singleOrder(order.getId()))
                                .withSelfRel(),
                        // קישור לכל הההזמנות
                        linkTo(methodOn(OrderController.class).allOrders())
                                .withRel("All products"))).collect(Collectors.toList());
        // יוחזר סטטוס 200 + body
        return ResponseEntity.ok( CollectionModel.of(Orders,
                linkTo(methodOn(ProductController.class).allProducts()).withSelfRel()));
    }
//-----------------2
    @GetMapping("/Order/{id}")
    ResponseEntity<EntityModel<Order>> singleOrder(@PathVariable Long id) {
        // הזמנה נמצאת לפי המזהה ואם אינה קיימת נזרקת שגיאה

        Order order = orderDatabase.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        return
                // מוחזר סטטוס 200 עם קישור להזמנה וקישור לכל ההזמנות

                ResponseEntity.ok( EntityModel.of(order,
                linkTo(methodOn(OrderController.class).singleOrder(id))
                        .withSelfRel(),
                linkTo(methodOn(OrderController.class).allOrders())
                        .withRel("Back to all orders")));
    }

    //---------------3
    @PostMapping("/Orders")
    ResponseEntity<?> placeOrder(@RequestBody Order order) {
        EntityModel<Order> entityProduct =
                orderFactory.toModel(orderDatabase.save(order));
        return ResponseEntity
                .created(entityProduct.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityProduct);
    }
//------------4
    @PutMapping("Order/{id}")
    ResponseEntity<?> addToOrder(@RequestBody Product aProduct, @PathVariable(value = "id") Long orderID) {

        // ההזמנה נכנסת למשתנה מעודכן על פי המזהה שלו
        Order updatedOrder = orderDatabase.getById(orderID);
        // המוצר נוסף לרשימה
        updatedOrder.getProductList().add(aProduct);

        // מוחזר סטטוס 200
        EntityModel<Order> entityOrder = orderFactory.toModel(orderDatabase.save(updatedOrder));
        return ResponseEntity.ok("Product was added successfully");
    }


    //---שאלה 5
    @GetMapping("/Orders")
    public ResponseEntity<?> allOrders(@RequestParam Optional<Double> price) {
        List<Order> orders;//NULL
        if (price.isPresent()) {
            //אם יש הזמנות שהם עונים על התנאי הם יכנסו לרשימה
            orders = orderDatabase.findOrdersThatTheyCheaper(price.get());
        }
        else {
            //אחרת ניקח את כולם פשוט
            orders = orderDatabase.findAll();
        }
        //
        List<EntityModel<Order>> ordersModel = orders.stream()
                .map(order -> orderFactory.toModel(order))
                .collect(Collectors.toList());
        //מחזירים
        return ResponseEntity.ok( CollectionModel.of(orders,
                linkTo(methodOn(ProductController.class).allProducts()).withSelfRel()));
    }



    //---------------------6
    @GetMapping("/Orders/{id}/sale")
    public ResponseEntity<CollectionModel<EntityModel<Product>>>
    productsByOrderWithSale(@PathVariable long id) {
        // הזמנה נמצאת לפי המזהה ואם אינה קיימת נזרקת שגיאה
        Order order = orderDatabase.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));

        // אובייקטים חדשים מאותו סוג מוכנסים לרשימה חדשה עם מחיר לאחר הנחה
        List<Product> productsAfterSale = new LinkedList<>();
        for (Product product : order.getProductList()) {
            Product temp = new Product(product);
            temp.setPrice(temp.getPrice() * 0.75);//25%
            productsAfterSale.add(temp);
        }
        //      המרה לרשימה עם הייצוג של המוצר
        // נוסף קישור לאותו מוצר ואותה הזמנה
        List<EntityModel<Product>> entityModelList = productsAfterSale.stream()
                .map(product ->
                        orderFactory.toModelOfProductWithOrderLink(order, product)
                )
                .collect(Collectors.toList());
        // יוחזר סטאטוס 200
        return ResponseEntity.ok(CollectionModel.of(entityModelList));
    }





//---------------


}
