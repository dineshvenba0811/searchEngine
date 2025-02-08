package com.example.demo.input;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityInput {
    private int id;
    private String title;
    private int price;
    private String currency;
    private Float rating;
    private boolean specialOffer;
    private Integer supplierId;
}
