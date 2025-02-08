package com.example.demo.repository;

import com.example.demo.entity.Supplier;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;



public interface SupplierRepository extends ElasticsearchRepository<Supplier, Integer> {

}
