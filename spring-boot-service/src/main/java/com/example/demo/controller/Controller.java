package com.example.demo.controller;

import com.example.demo.dto.ActivityDto;
import com.example.demo.dto.AutoSuggestDto;
import com.example.demo.input.ActivityInput;
import com.example.demo.input.SupplierInput;
import com.example.demo.service.ActivityService;
import com.example.demo.service.ElasticSearchAutoSuggestService;
import com.example.demo.service.ElasticSearchService;
import com.example.demo.service.SupplierService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;

import java.io.IOException;
import java.util.List;

@org.springframework.stereotype.Controller
@Slf4j
public class Controller {
    private final ActivityService activityService;
    private final ElasticSearchService elasticSearchService;
    private  final SupplierService supplierService;
    private final ElasticSearchAutoSuggestService elasticSearchAutoSuggestService;

    public Controller(ActivityService activityService,
                      ElasticSearchService elasticSearchService,
                      SupplierService supplierService,
                      ElasticSearchAutoSuggestService elasticSearchAutoSuggestService) {
        this.activityService = activityService;
        this.elasticSearchService = elasticSearchService;
        this.supplierService = supplierService;
        this.elasticSearchAutoSuggestService = elasticSearchAutoSuggestService;
    }

    @MutationMapping
    public Integer insertActivity (@Argument("activity")ActivityInput activityInput){
        return this.activityService.insertActivity(activityInput);
    }

    @MutationMapping
    public Integer insertSupplier (@Argument("supplier") SupplierInput supplierInput){
        return this.supplierService.insertSupplier(supplierInput);
    }

    @QueryMapping(name = "getActivities")
    public List<ActivityDto> getActivities(@Argument("title")String title,
                                           @Argument("specialOffer")Boolean specialOffer,
                                           @Argument("minPrice")Integer minPrice,
                                           @Argument("maxPrice")Integer maxPrice) throws IOException {
        return this.elasticSearchService.searchActivities(title,specialOffer,minPrice,maxPrice);
    }

    @QueryMapping(name = "getAllActivities")
    public List<ActivityDto> getAllActivities() throws IOException {
        return this.elasticSearchService.getAllActivities();
    }

    @QueryMapping(name = "getActivityById")
    public ActivityDto getActivityById(@Argument("id")Integer id) {
        return this.activityService.getActivity(id);
    }

    @QueryMapping(name="searchAutoSuggest")
    public List<AutoSuggestDto> autoSuggest(@Argument("keyword")String titleKeyword) throws IOException {
        return this.elasticSearchAutoSuggestService.autoSuggestTitles(titleKeyword);
    }



}
