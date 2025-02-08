package com.example.demo.configuration;

import com.example.demo.entity.Activity;
import com.example.demo.entity.Supplier;
import com.example.demo.repository.ActivityRepository;
import com.example.demo.repository.SupplierRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Override
    public void run(String... args) throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        ObjectMapper objectMapper = new ObjectMapper();
        InputStream inputStreamActivity = classLoader.getResourceAsStream("activities.json");
        JsonNode jsonNodeActivity = objectMapper.readTree(inputStreamActivity);

        ClassLoader classLoaderSupplier = getClass().getClassLoader();
        ObjectMapper objectMapperSupplier = new ObjectMapper();
        InputStream inputStreamSupplier = classLoaderSupplier.getResourceAsStream("suppliers.json");
        JsonNode jsonNodeSupplier = objectMapperSupplier.readTree(inputStreamSupplier);


        List<Activity> activities = new ArrayList<>();
        for (JsonNode node : jsonNodeActivity) {
            Activity activity = objectMapper.treeToValue(node, Activity.class);
            System.out.println("Data inserted into Elasticsearch!"+activity.getTitle());
            activities.add(activity);
            activityRepository.save(activity);
        }


        List<Supplier> supplierList = new ArrayList<>();
        for (JsonNode nodeSupplier : jsonNodeSupplier) {
            Supplier supplier = objectMapperSupplier.treeToValue(nodeSupplier, Supplier.class);
            System.out.println("Data inserted into Elasticsearch!"+supplier.getId());
            supplierList.add(supplier);
            supplierRepository.save(supplier);
        }
    }
}
