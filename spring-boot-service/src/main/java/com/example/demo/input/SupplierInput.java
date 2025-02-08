package com.example.demo.input;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupplierInput {
    private Integer id;
    private String name;
    private String address;
    private Integer zip;
    private String city;
    private String country;
}
