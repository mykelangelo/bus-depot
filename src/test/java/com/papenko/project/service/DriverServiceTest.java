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
                new Driver("ezio.auditore@da.firenze", new Bus("AS4545AS", new Route("F1"))));
        // WHEN
        Driver driver = driverService.findDriverByEmail("ezio.auditore@da.firenze");
        // THEN
        assertEquals(new Driver("ezio.auditore@da.firenze", new Bus("AS4545AS", new Route("F1"))),
                driver);
    }
}