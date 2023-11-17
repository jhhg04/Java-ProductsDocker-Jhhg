package com.example.crudproducts.controller;

import com.example.crudproducts.model.dto.ProductDto;
import com.example.crudproducts.model.entity.Product;
import com.example.crudproducts.model.payload.MensajeResponse;
import com.example.crudproducts.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ProductController {
    @Autowired
    private IProductService productService;

    @GetMapping("products")
    public ResponseEntity<?> showAll(){
        List<Product> getList = productService.listAll();

        if (getList == null){
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .mensaje("No hay registros")
                            .object(null)
                            .build()
                    , HttpStatus.OK);
        }
        return new ResponseEntity<>(
                MensajeResponse.builder()
                        .mensaje("Success")
                        .object(getList)
                        .build()
                , HttpStatus.OK);
    }

    @PostMapping("product")
    public ResponseEntity<?> create(@RequestBody ProductDto productDto){
        Product productSave = null;
        try{
            productSave = productService.save(productDto);
            return new ResponseEntity<>(MensajeResponse.builder()
                    .mensaje("Guardado correctamente")
                    .object(ProductDto.builder()
                            .idProduct(productSave.getIdProduct())
                            .nombre(productSave.getNombre() )
                            .precio(productSave.getPrecio())
                            .build())
                    .build()
                    , HttpStatus.CREATED);
        }catch (DataAccessException exDt){
            return new ResponseEntity<>(MensajeResponse.builder()
                    .mensaje(exDt.getMessage())
                    .object(null)
                    .build(), HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @PutMapping("product/{id}")
    public ResponseEntity<?> update(@RequestBody ProductDto productDto, @PathVariable Integer id){
        Product productUpdate = null;
        try{
            if (productService.existsById(id)){
                productDto.setIdProduct(id);
                productUpdate = productService.save(productDto);
                return new ResponseEntity<>(MensajeResponse.builder()
                        .mensaje("Actualizado Correctamente")
                        .object(ProductDto.builder()
                                .idProduct(productUpdate.getIdProduct())
                                .nombre(productUpdate.getNombre() )
                                .precio(productUpdate.getPrecio())
                                .build())
                        .build()
                        , HttpStatus.CREATED);
            }else{
                return new ResponseEntity<>(MensajeResponse.builder()
                        .mensaje("El registro que intenta actualizar no se encuentra en DB")
                        .object(null)
                        .build(), HttpStatus.NOT_FOUND);
            }
        }catch (DataAccessException exDt){
            return new ResponseEntity<>(MensajeResponse.builder()
                    .mensaje(exDt.getMessage())
                    .object(null)
                    .build(), HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @DeleteMapping("product/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        try{
            Product productDelete = productService.findById(id);
            productService.delete(productDelete);
            return new ResponseEntity<>(productDelete, HttpStatus.NO_CONTENT);
        }catch (DataAccessException exDt){
            return new ResponseEntity<>(MensajeResponse.builder()
                    .mensaje(exDt.getMessage())
                    .object(null)
                    .build(), HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @GetMapping("product/{id}")
    public ResponseEntity<?> showById(@PathVariable Integer id){
        Product product = productService.findById(id);

        if (product == null){
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .mensaje("El Registro no existe")
                            .object(null)
                            .build()
                    , HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(
                MensajeResponse.builder()
                        .mensaje("Success")
                        .object(ProductDto.builder()
                                .idProduct(product.getIdProduct())
                                .nombre(product.getNombre() )
                                .precio(product.getPrecio())
                                .build())
                        .build()
                , HttpStatus.OK);
    }

}
