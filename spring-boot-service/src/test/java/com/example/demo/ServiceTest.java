package com.example.demo;

import com.example.demo.dto.ActivityDto;
import com.example.demo.entity.Activity;
import com.example.demo.entity.Supplier;
import com.example.demo.exception.ActivityNotFoundException;
import com.example.demo.exception.SupplierNotFoundException;
import com.example.demo.repository.ActivityRepository;
import com.example.demo.repository.SupplierRepository;
import com.example.demo.service.ActivityService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class ServiceTest {

    @Mock
    private ActivityRepository activityRepository;

    @Mock
    private SupplierRepository supplierRepository;

    @InjectMocks
    private ActivityService activityService;

    @Test
    void testGetActivity_Success() {
        Activity mockActivity = new Activity(1, "Berlin City Tour", 50, "$", 4.5F, true, 2);

        Supplier mockSupplier = new Supplier(2, "TravelCorp", "Berlin", 124,"123 Main St", "Germany");

        when(activityRepository.findById(1)).thenReturn(Optional.of(mockActivity));
        when(supplierRepository.findById(2)).thenReturn(Optional.of(mockSupplier));

        ActivityDto result = activityService.getActivity(1);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("Berlin City Tour", result.getTitle());
        Assertions.assertEquals(50.0, result.getPrice());
        Assertions.assertEquals("$", result.getCurrency());

        verify(activityRepository).findById(1);
        verify(supplierRepository).findById(2);
    }

    @Test
    void testGetActivity_ActivityNotFound() {
        when(activityRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ActivityNotFoundException.class, () -> {
            activityService.getActivity(1);
        });

        Assertions.assertEquals("Activity is not valid1", exception.getMessage());

        verify(activityRepository).findById(1);
        verifyNoInteractions(supplierRepository);
    }

    @Test
    void testGetActivity_SupplierNotFound() {
        Activity mockActivity = new Activity(1, "Berlin City Tour", 50, "$", 4.5F, true, 2);
        when(activityRepository.findById(1)).thenReturn(Optional.of(mockActivity));
        when(supplierRepository.findById(2)).thenReturn(Optional.empty());

        Exception exception = assertThrows(SupplierNotFoundException.class, () -> {
            activityService.getActivity(1);
        });

        Assertions.assertEquals("Supplier is not valid2", exception.getMessage());

        verify(activityRepository).findById(1);
        verify(supplierRepository).findById(2);
    }
}
