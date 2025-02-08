package com.example.demo.exception;

import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.schema.DataFetchingEnvironment;
import lombok.NonNull;
import org.springframework.graphql.data.method.annotation.GraphQlExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    Map<String,Object> errorMap = new HashMap<>();
    @GraphQlExceptionHandler
    public GraphQLError handle(@NonNull ActivityNotFoundException activityNotFoundException, @NonNull DataFetchingEnvironment environment){
        errorMap.put("errorCode", "Activity_NOT_FOUND");
        errorMap.put("userMessage", "There are no activities found");
        errorMap.put("timestamp", Instant.now().toString());
        errorMap.put("actionableSteps", "Please try another activity ");


        return GraphQLError.newError().errorType(ErrorType.DataFetchingException)
                .message("No Activities are available based on the search")
                .path(environment.getExecutionStepInfo().getPath())
                .location(environment.getField().getSourceLocation())
                .extensions(errorMap)
                .build();
    }

    Map<String,Object> errorMapSupplier = new HashMap<>();
    @GraphQlExceptionHandler
    public GraphQLError handle(@NonNull SupplierNotFoundException supplierNotFoundException, @NonNull DataFetchingEnvironment environment){
        errorMapSupplier.put("errorCode", "SUPPLIER_NOT_FOUND");
        errorMapSupplier.put("userMessage", "There are no supplier found for this activity");
        errorMapSupplier.put("timestamp", Instant.now().toString());
        errorMapSupplier.put("actionableSteps", "Please try another activity ");


        return GraphQLError.newError().errorType(ErrorType.DataFetchingException)
                .message("No supplier are available based on the search")
                .path(environment.getExecutionStepInfo().getPath())
                .location(environment.getField().getSourceLocation())
                .extensions(errorMapSupplier)
                .build();
    }
}
