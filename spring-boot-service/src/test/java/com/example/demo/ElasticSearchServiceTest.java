package com.example.demo;

import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.search.HitsMetadata;
import com.example.demo.dto.ActivityDto;
import com.example.demo.entity.Activity;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.example.demo.entity.Supplier;
import com.example.demo.exception.SupplierNotFoundException;
import com.example.demo.repository.SupplierRepository;
import com.example.demo.service.ElasticSearchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class ElasticSearchServiceTest {
    @Mock
    private ElasticsearchClient elasticsearchClient;

    @Mock
    private SupplierRepository supplierRepository;

    @InjectMocks
    private ElasticSearchService elasticSearchService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSearchActivities() throws IOException {
        SearchResponse<Activity> mockResponse = mock(SearchResponse.class);
        HitsMetadata<Activity> hitsMetadata = mock(HitsMetadata.class);
        Hit<Activity> hit1 = mock(Hit.class);
        when(hit1.source()).thenReturn(new Activity(25651, "German Tour: Parliament Quarter & Reichstag glass dome", 100, "$", 4.5f, true, 1));

        Hit<Activity> hit2 = mock(Hit.class);
        when(hit2.source()).thenReturn(new Activity(15647, "German Tour: Parliament Quarter & Reichstag glass dome", 200, "USD", 4.8f, false, 250));

        when(hitsMetadata.hits()).thenReturn(List.of(hit1, hit2));
        when(mockResponse.hits()).thenReturn(hitsMetadata);
        when(elasticsearchClient.search(any(SearchRequest.class), eq(Activity.class))).thenReturn(mockResponse);

        Supplier supplier1 = new Supplier(1, "ABC Tours", "123 Street", 10001, "NY", "USA");
        Supplier supplier2 = new Supplier(250, "XYZ Adventures", "456 Ave", 20002, "LA", "USA");

        when(supplierRepository.findById(1)).thenReturn(Optional.of(supplier1));
        when(supplierRepository.findById(250)).thenReturn(Optional.of(supplier2));


        List<ActivityDto> result = elasticSearchService.searchActivities("Tour", true,10,5000);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("German Tour: Parliament Quarter & Reichstag glass dome", result.get(0).getTitle());
        assertEquals(100, result.get(0).getPrice());
        assertEquals("$", result.get(0).getCurrency());
        assertEquals(4.5f, result.get(0).getRating());
        assertEquals(true, result.get(0).isSpecialOffer());


        verify(supplierRepository).findById(1);
        verify(supplierRepository).findById(250);
    }

    @Test
    void testSearchSupplierNotFoundException () throws IOException {
        SearchResponse<Activity> mockResponse = mock(SearchResponse.class);
        HitsMetadata<Activity> hitsMetadata = mock(HitsMetadata.class);
        Hit<Activity> hit1 = mock(Hit.class);
        when(hit1.source()).thenReturn(new Activity(25651, "German Tour: Parliament Quarter & Reichstag glass dome", 100, "$", 4.5f, true, 1));

        Hit<Activity> hit2 = mock(Hit.class);
        when(hit2.source()).thenReturn(new Activity(15647, "German Tour: Parliament Quarter & Reichstag glass dome", 200, "USD", 4.8f, false, 250));

        when(hitsMetadata.hits()).thenReturn(List.of(hit1, hit2));
        when(mockResponse.hits()).thenReturn(hitsMetadata);
        when(elasticsearchClient.search(any(SearchRequest.class), eq(Activity.class))).thenReturn(mockResponse);

        Supplier supplier1 = new Supplier(1, "ABC Tours", "123 Street", 10001, "NY", "USA");
        when(supplierRepository.findById(1)).thenReturn(Optional.of(supplier1));

        when(supplierRepository.findById(250)).thenReturn(Optional.empty());

        SupplierNotFoundException thrown = assertThrows(SupplierNotFoundException.class, () -> {
            elasticSearchService.searchActivities("Tour", true,10,5000);
        });

        assertEquals("Supplier not found for this activity15647", thrown.getMessage());

        verify(supplierRepository).findById(1);
        verify(supplierRepository).findById(250);


    }
}
