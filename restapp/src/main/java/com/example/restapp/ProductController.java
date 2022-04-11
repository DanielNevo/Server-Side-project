package com.example.restapp;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

// Indicates that the data from our methods will be injected to the response payload (body)

@RestController
public class ProductController {
    private ProductRepo productDatabase;
    private ProductEntityFactory productFactory;


    public ProductController(ProductRepo aDatabase,ProductEntityFactory productFactory) {

        this.productDatabase = aDatabase;
        this.productFactory=productFactory;
    }


    // home route
//    @GetMapping("/products")
//    List<Product> getAllProducts(){
//        return productDatabase.findAll();
//    }

    /*
    אנחנו יודעים ש-findAll() מחזירה לנו רשימה של products
    אנחנו רוצים שכל product יהיה עטוף בקונטיינר
    ניקח כל איבר ברשימה של products ונהפוך אותו לקונטיינר + לינקים (לפרודקט עצמו ולכל הפרודקטים)
    אם זה המצב - נקבל רשימה של קונטיינרים
    אבל רשימה זה טיפוס קונקרטי - אנו רוצים להחזיר ייצוג של משאב
    ייצוג של collection נקרא CollectionModel
    וגם לייצוג נוסיף קישור - לאותו קולשקשן עצמו
     */
    @GetMapping("/products")
    public ResponseEntity<CollectionModel<EntityModel< Product>>> allProducts(){
    // רשימה שמחזירה לי את הייצוג של כל המוצרים
        List<EntityModel<Product>> products = productDatabase.findAll()
                .stream().map(product -> EntityModel.of(product,
                        // קישור למוצר
                        linkTo(methodOn(ProductController.class).singleProduct(product.getId()))
                                .withSelfRel(),
                        // קישור לכל המוצרים
                        linkTo(methodOn(ProductController.class).allProducts())
                                .withRel("All products"))).collect(Collectors.toList());
        // יוחזר סטטוס 200 + body
        return ResponseEntity.ok( CollectionModel.of(products,
                linkTo(methodOn(ProductController.class).allProducts()).withSelfRel()));
    }

    //----------------------------------------------------------------------------------------------



    @PostMapping("/products")
    ResponseEntity<?> createProduct(@RequestBody Product newProduct) {
        EntityModel<Product> productRepresentation = productFactory.toModel(productDatabase.save(newProduct));
        return ResponseEntity.ok(productRepresentation);
    }


    //----------------------------------------------------------------------------------------------


    @GetMapping("/products/{id}")
    public ResponseEntity<EntityModel< Product>> singleProduct(@PathVariable long id){
        // מוצר נמצא לפי המזהה ואם אינו קיים נזרקת שגיאה
        Product product = productDatabase.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        return
                // מוחזר סטטוס 200 עם קישור למוצר וקישור לכל המוצרים

                ResponseEntity.ok(  EntityModel.of(product,
                linkTo(methodOn(ProductController.class).singleProduct(id))
                        .withSelfRel(),
                linkTo(methodOn(ProductController.class).allProducts())
                        .withRel("Back to all products")));
    }


    //----------------------------------------------------------------------------------------------

    @GetMapping("/products")
    public EntityModel<Product> singleProduct(@RequestParam String title){
          return  EntityModel.of(productDatabase.findByTitle(title));
    }

//    public Product singleProduct(@RequestParam String title) {
//       return productDatabase.findByTitle(title);
//    }

}





