package com.example.demo.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.Operator;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.RangeQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.TextQueryType;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.json.JsonData;
import com.example.demo.dto.ActivityDto;
import com.example.demo.dto.SupplierDto;
import com.example.demo.entity.Activity;
import com.example.demo.exception.SupplierNotFoundException;
import com.example.demo.mapper.EntityToDtoMapper;
import com.example.demo.repository.SupplierRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ElasticSearchService {

    private final ElasticsearchClient elasticsearchClient;

    private  final SupplierRepository supplierRepository;

    public static final String TITLE = "title";
    public static final String SPECIAL_OFFER = "specialOffer";
    public static final String PRICE = "price";
    public static final String INDEX = "activities";

    public ElasticSearchService(ElasticsearchClient elasticsearchClient, SupplierRepository supplierRepository) {
        this.elasticsearchClient = elasticsearchClient;
        this.supplierRepository = supplierRepository;
    }

    public List<ActivityDto> searchActivities(String title,Boolean specialOffer,Integer minPrice,Integer maxPrice) throws IOException {

        List<Query> queryFilters = new ArrayList<>();
        if(title!=null && !title.isEmpty()){
            queryFilters.add(Query.of(q -> q.multiMatch(m -> m.fields(List.of(TITLE)).query(title).type(TextQueryType.BestFields).operator(Operator.And)
                            .fuzziness("AUTO")
            )));
        }

        if(specialOffer!=null){
            queryFilters.add(Query.of(q -> q.term(m -> m.field(SPECIAL_OFFER).value(specialOffer))));
        }

        if (minPrice !=null && maxPrice !=null){
            queryFilters.add(Query.of(q -> q.range(r -> {
                RangeQuery.Builder rangeQuery = r.field(PRICE); // Assuming "price" is the field name in Elasticsearch
                rangeQuery.gte(JsonData.of(minPrice)); // Greater than or equal to minPrice
                rangeQuery.lte(JsonData.of(maxPrice)); // Less than or equal to maxPrice
                return rangeQuery;
            })));
        }

        SearchRequest searchRequest = new SearchRequest.Builder()
                .index(INDEX)
                .query(q -> q.bool(b -> b.filter(queryFilters)))
                .build();

        SearchResponse<Activity> response = elasticsearchClient.search(searchRequest, Activity.class);


        return response.hits().hits().stream()
                .map(Hit::source).filter(Objects::nonNull)
                .map(this::attachSupplier)
                .collect(Collectors.toList());
    }

    private ActivityDto attachSupplier(Activity activity) {
        SupplierDto supplierDto = supplierRepository.findById(activity.getSupplierId())
                .map(EntityToDtoMapper::SupplierEntityToDto).orElseThrow(() -> new SupplierNotFoundException("Supplier not found for this activity"+activity.getId()));
        return  EntityToDtoMapper.ActivityEntityToDto(activity,supplierDto);
    }

    public List<ActivityDto> getAllActivities() throws IOException {
        SearchResponse<Activity> response = elasticsearchClient.search(s -> s
                .index(INDEX)
                .query(q -> q.matchAll(m -> m)), Activity.class);

        return response.hits().hits().stream()
                .map(Hit::source).filter(Objects::nonNull)
                .map(this::attachSupplier)
                .collect(Collectors.toList());
    }




}
