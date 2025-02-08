package com.example.demo.service;

import com.example.demo.dto.ActivityDto;
import com.example.demo.dto.SupplierDto;
import com.example.demo.entity.Supplier;
import com.example.demo.input.SupplierInput;
import com.example.demo.mapper.EntityToDtoMapper;
import com.example.demo.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SupplierService {

    private final SupplierRepository supplierRepository;

    public SupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }


    public Integer insertSupplier(SupplierInput supplierInput){
        Supplier supplier = EntityToDtoMapper.SupplierInputToSupplierEntity(supplierInput);
        Supplier savedSupplier=this.supplierRepository.save(supplier);
        return savedSupplier.getId();
    }

    public Map<ActivityDto,SupplierDto> getSuppliers(List<ActivityDto>activityDto){
       return activityDto.stream()
               .filter(activityDtoNonNull ->activityDtoNonNull.getSupplierId() !=null)
               .collect(Collectors.toMap(
                       activity ->  activity,
                       activity -> supplierRepository.findById(activity.getSupplierId().getId()).map(EntityToDtoMapper::SupplierEntityToDto)
                               .orElseThrow(() -> new RuntimeException(" Activity noy found for this id "+activity.getSupplierId()))
               ));
    }
}
