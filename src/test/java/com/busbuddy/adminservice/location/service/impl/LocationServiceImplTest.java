package com.busbuddy.adminservice.location.service.impl;

import com.busbuddy.adminservice.common.constants.ErrorCodes;
import com.busbuddy.adminservice.common.exception.TechnicalException;
import com.busbuddy.adminservice.common.exception.BusinessException;
import com.busbuddy.adminservice.location.model.Location;
import com.busbuddy.adminservice.location.repository.LocationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LocationServiceImplTest {

    @Mock
    private LocationRepository locationRepository;

    @InjectMocks
    private LocationServiceImpl locationService;

    private Location location;

    @BeforeEach
    void setUp() {
        location = new Location();
        location.setLocationName("Siruseri");
    }

    // ------------------- CREATE -------------------

    @Test
    void testCreateLocation_Success() {
        when(locationRepository.existsByLocationNameIgnoreCase("Siruseri")).thenReturn(false);
        when(locationRepository.save(any(Location.class))).thenReturn(location);

        Location result = locationService.createLocation(location);

        assertNotNull(result);
        assertEquals("Siruseri", result.getLocationName());
        verify(locationRepository, times(1)).save(any(Location.class));
    }

    @Test
    void testCreateLocation_AlreadyExists() {
        when(locationRepository.existsByLocationNameIgnoreCase("Siruseri")).thenReturn(true);

        TechnicalException ex = assertThrows(TechnicalException.class,
                () -> locationService.createLocation(location));

        assertEquals(ErrorCodes.TECHNICAL_ERROR, ex.getCode());
        verify(locationRepository, never()).save(any(Location.class));
    }

    // ------------------- GET ALL -------------------

    @Test
    void testGetAllLocations_Success() {
        when(locationRepository.findAll()).thenReturn(Arrays.asList(location));

        List<Location> result = locationService.getAllLocations();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Siruseri", result.get(0).getLocationName());
        verify(locationRepository, times(1)).findAll();
    }

    @Test
    void testGetAllLocations_TechnicalException() {
        when(locationRepository.findAll()).thenThrow(new RuntimeException("DB down"));

        TechnicalException ex = assertThrows(TechnicalException.class,
                () -> locationService.getAllLocations());

        assertEquals(ErrorCodes.TECHNICAL_ERROR, ex.getCode());
    }

    // ------------------- GET BY ID -------------------

    @Test
    void testGetLocationById_Success() {
        when(locationRepository.findById(1L)).thenReturn(Optional.of(location));

        Location result = locationService.getLocationById(1L);

        assertNotNull(result);
        assertEquals("Siruseri", result.getLocationName());
    }

    @Test
    void testGetLocationById_NotFound() {
        when(locationRepository.findById(1L)).thenReturn(Optional.empty());

        BusinessException ex = assertThrows(BusinessException.class,
                () -> locationService.getLocationById(1L));

        assertEquals(ErrorCodes.LOCATION_NOT_FOUND, ex.getCode());
    }

    // ------------------- UPDATE -------------------

    @Test
    void testUpdateLocation_Success() {
        Location updated = new Location();
        updated.setLocationName("Thoraipakkam");

        when(locationRepository.findById(1L)).thenReturn(Optional.of(location));
        when(locationRepository.existsByLocationNameIgnoreCase("Thoraipakkam")).thenReturn(false);
        when(locationRepository.save(any(Location.class))).thenReturn(updated);

        Location result = locationService.updateLocation(1L, updated);

        assertNotNull(result);
        assertEquals("Thoraipakkam", result.getLocationName());
    }

    @Test
    void testUpdateLocation_NotFound() {
        when(locationRepository.findById(1L)).thenReturn(Optional.empty());

        TechnicalException ex = assertThrows(TechnicalException.class,
                () -> locationService.updateLocation(1L, location));

        assertEquals(ErrorCodes.TECHNICAL_ERROR, ex.getCode());
    }

    @Test
    void testUpdateLocation_AlreadyExists() {
        Location updated = new Location();
        updated.setLocationName("Perungudi");

        when(locationRepository.findById(1L)).thenReturn(Optional.of(location));
        when(locationRepository.existsByLocationNameIgnoreCase("Perungudi")).thenReturn(true);

        TechnicalException ex = assertThrows(TechnicalException.class,
                () -> locationService.updateLocation(1L, updated));

        assertEquals(ErrorCodes.TECHNICAL_ERROR, ex.getCode());
    }

    // ------------------- DELETE -------------------

    @Test
    void testDeleteLocation_Success() {
        when(locationRepository.findById(1L)).thenReturn(Optional.of(location));

        assertDoesNotThrow(() -> locationService.deleteLocation(1L));

        verify(locationRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteLocation_NotFound() {
        when(locationRepository.findById(1L)).thenReturn(Optional.empty());

        TechnicalException ex = assertThrows(TechnicalException.class,
                () -> locationService.deleteLocation(1L));

        assertEquals(ErrorCodes.TECHNICAL_ERROR, ex.getCode());
        verify(locationRepository, never()).deleteById(anyLong());
    }
}
