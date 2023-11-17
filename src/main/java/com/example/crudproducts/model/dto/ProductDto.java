package com.example.crudproducts.model.dto;

import lombok.*;

import java.io.Serializable;
import java.util.Date;
@Data
@ToString
@Builder
public class ProductDto implements Serializable {
    private Integer idProduct;
    private String nombre;
    private float precio;
}
