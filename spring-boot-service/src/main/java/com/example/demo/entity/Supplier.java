package com.example.demo.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "supplier")
public class Supplier {
    private Integer id;
    private String name;
    private String address;
    private Integer zip;
    private String city;
    private String country;


}
