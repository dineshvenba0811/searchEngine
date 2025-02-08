package com.example.demo.mapper;

import com.example.demo.dto.ActivityDto;
import com.example.demo.dto.SupplierDto;
import com.example.demo.entity.Activity;
import com.example.demo.entity.Supplier;
import com.example.demo.input.ActivityInput;
import com.example.demo.input.SupplierInput;

public class EntityToDtoMapper {

    public static ActivityDto ActivityEntityToDto(Activity activity,SupplierDto supplierDto){
        return new ActivityDto(activity.getId(),
                activity.getTitle(),
                activity.getPrice(),
                activity.getCurrency(),
                activity.getRating(),
                activity.isSpecialOffer(),
                supplierDto);
    }

    public static Activity ActivityInputToActivityEntity(ActivityInput activityInput){
        return new Activity(activityInput.getId(), activityInput.getTitle(), activityInput.getPrice(), activityInput.getCurrency(), activityInput.getRating(),
                activityInput.isSpecialOffer(), activityInput.getSupplierId());
    }

    public static Supplier SupplierInputToSupplierEntity(SupplierInput supplierInput){
        return new Supplier(supplierInput.getId(), supplierInput.getName(), supplierInput.getAddress(), supplierInput.getZip(), supplierInput.getCity(),
                supplierInput.getCountry());
    }


    public static SupplierDto SupplierEntityToDto(Supplier supplier){
        return new SupplierDto(
                supplier.getId(), supplier.getName(), supplier.getAddress(), supplier.getZip(), supplier.getCity(), supplier.getCountry()
        );
    }
}
