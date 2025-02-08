package com.example.demo.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.example.demo.dto.AutoSuggestDto;
import com.example.demo.entity.Activity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ElasticSearchAutoSuggestService {

    private final ElasticsearchClient elasticsearchClient;

    public ElasticSearchAutoSuggestService(ElasticsearchClient elasticsearchClient) {
        this.elasticsearchClient = elasticsearchClient;
    }

    private static final Logger logger = LoggerFactory.getLogger(ActivityService.class);

    public List<AutoSuggestDto> autoSuggestTitles(String keyword) throws IOException {
        logger.info("autoSuggestTitles with keyword {}", keyword);
        SearchResponse<Activity> data = elasticsearchClient.search(
                s -> s.index("activities")
                        .query(Query.of(q -> q.match(m -> m.field("title").query(keyword)))),
                Activity.class
        );

        return data.hits().hits().stream()
                .map(Hit::source)  // Extract Activity object from Hit
                .filter(Objects::nonNull) // Ensure no null values
                .map(activity -> new AutoSuggestDto(activity.getId(), activity.getTitle())) // Convert to DTO
                .collect(Collectors.toList());
    }
}
