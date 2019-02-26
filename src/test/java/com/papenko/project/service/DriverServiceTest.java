package com.papenko.project.service;

import com.papenko.project.entity.Bus;
import com.papenko.project.entity.Driver;
import com.papenko.project.entity.Route;
import com.papenko.project.repository.DriverRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DriverServiceTest {
    @Mock
    DriverRepository driverRepository;
    @InjectMocks
    DriverService driverService;

    @Test
    void findDriverByEmail_shouldReturnDriverWithSuchEmail() {
        // GIVEN
        given(driverRepository.findDriverByEmail("ezio.auditore@da.firenze")).willReturn(
                new Driver("ezio.auditore@da.firenze", new Bus("AS4545AS", new Route("F1")), true));
        // WHEN
        Driver driver = driverService.findDriverByEmail("ezio.auditore@da.firenze");
        // THEN
        assertEquals(new Driver("ezio.auditore@da.firenze", new Bus("AS4545AS", new Route("F1")), true),
                driver);
    }

    @Test
    void setDriverAwarenessToTrue_shouldInitiateSettingDriverAwarenessToTrue() {
        // GIVEN
        given(driverRepository.findDriverByEmail("un.aware@driver.here")).willReturn(
                new Driver("un.aware@driver.here", new Bus("NT1107NT", new Route("N7")), false));
        // WHEN
        driverService.setDriverAwarenessToTrue("un.aware@driver.here");
        // THEN
        verify(driverRepository).updateDriverSetAwareness(
                new Driver("un.aware@driver.here", new Bus("NT1107NT", new Route("N7")), false),
                true);
    }
}