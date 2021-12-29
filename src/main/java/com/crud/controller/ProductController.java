package com.crud.controller;

import com.crud.dto.ProductoDto;
import com.crud.dto.message;
import com.crud.model.Product;
import com.crud.service.ProductService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@CrossOrigin()
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/list")
    public ResponseEntity<List<Product>> getAll(){
        List<Product> list = productService.getAll();
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<Product> getById(@PathVariable("id")  int id){
        if(!productService.existsByid(id))
            return new ResponseEntity(new message("Product not exists"), HttpStatus.NOT_FOUND);

        return new ResponseEntity(productService.findById(id).get(),HttpStatus.OK);
    }

    @GetMapping("/detailsByName/{name}")
    public ResponseEntity<Product> getByName(@PathVariable("name") String name){
        if(!productService.existsByName(name)){
            return new ResponseEntity(new message("Product not exists"), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(productService.findByName(name).get(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody ProductoDto productDto){
        if(StringUtils.isBlank(productDto.getName()))
            return new ResponseEntity(new message("ThenName is requerid"), HttpStatus.BAD_REQUEST);
        if(productDto.getPrice()<0 || productDto.getPrice()==null)
            return new ResponseEntity(new message("The price must be positive"), HttpStatus.BAD_REQUEST);
        if(productService.existsByName(productDto.getName()))
            return new ResponseEntity(new message("The name already exists"), HttpStatus.BAD_REQUEST);

        Product product = new Product(productDto.getName(), productDto.getPrice());
        productService.save(product);
        return new ResponseEntity(new message("Product created"), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") int id, @RequestBody ProductoDto productoDto){
        if(!productService.existsByid(id))
            return new ResponseEntity(new message("The product is not exists"), HttpStatus.NOT_FOUND);
        if(StringUtils.isBlank(productoDto.getName()))
            return new ResponseEntity(new message("ThenName is requerid"), HttpStatus.BAD_REQUEST);
        if(productoDto.getPrice()==null || productoDto.getPrice()<0)
            return new ResponseEntity(new message("The price must be positive"), HttpStatus.BAD_REQUEST);

        Product product = productService.findById(id).get();
        product.setName(productoDto.getName());
        product.setPrice(productoDto.getPrice());
        productService.save(product);

        return new ResponseEntity(new message("Product updated"), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id){
        if(!productService.existsByid(id))
            return new ResponseEntity(new message("Product is not exists"), HttpStatus.NOT_FOUND);

        productService.delete(id);
        return new ResponseEntity(new message("Product deleted"), HttpStatus.OK);
    }


}
