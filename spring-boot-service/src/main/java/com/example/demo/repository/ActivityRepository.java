package com.example.demo.repository;

import com.example.demo.entity.Activity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface ActivityRepository extends ElasticsearchRepository<Activity, Integer> {
}
