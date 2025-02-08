package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupplierDto {
    private Integer id;
    private String name;
    private String address;
    private Integer zip;
    private String city;
    private String country;
}
