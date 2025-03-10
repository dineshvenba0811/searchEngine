package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ActivityDto {
    private int id;
    private String title;
    private int price;
    private String currency;
    private Float rating;
    private boolean specialOffer;
    private SupplierDto supplierId;

}
