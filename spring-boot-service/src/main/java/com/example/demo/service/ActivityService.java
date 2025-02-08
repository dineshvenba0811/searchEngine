package com.example.demo.service;

import com.example.demo.dto.ActivityDto;
import com.example.demo.entity.Activity;
import com.example.demo.entity.Supplier;
import com.example.demo.exception.ActivityNotFoundException;
import com.example.demo.exception.SupplierNotFoundException;
import com.example.demo.input.ActivityInput;
import com.example.demo.mapper.EntityToDtoMapper;
import com.example.demo.repository.ActivityRepository;
import com.example.demo.repository.SupplierRepository;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ActivityService {
    private final ActivityRepository activityRepository;
    private  final SupplierRepository supplierRepository;

    public ActivityService(ActivityRepository activityRepository, SupplierRepository supplierRepository) {
        this.activityRepository = activityRepository;
        this.supplierRepository = supplierRepository;
    }

    private static final Logger logger = LoggerFactory.getLogger(ActivityService.class);

    public Integer insertActivity(ActivityInput activityInput){
         Activity activity = EntityToDtoMapper.ActivityInputToActivityEntity(activityInput);
         logger.info("insert activity");
         Activity savedEntity=this.activityRepository.save(activity);
         if(savedEntity.getId() >0){
             logger.info("Successfully inserted activity with ID: {}", savedEntity.getId());
             return activity.getId();
         }else{
             throw new SupplierNotFoundException("Supplier is not valid"+activityInput.getSupplierId());
         }
    }

    public ActivityDto getActivity(Integer id){
        Activity activity=this.activityRepository.findById(id).orElseThrow(()->new ActivityNotFoundException("Activity is not valid"+id));
        Supplier supplier=supplierRepository.findById(activity.getSupplierId())
                .orElseThrow(()->new SupplierNotFoundException("Supplier is not valid"+activity.getSupplierId()));
        return EntityToDtoMapper.ActivityEntityToDto(activity,EntityToDtoMapper.SupplierEntityToDto(supplier));
    }

}
