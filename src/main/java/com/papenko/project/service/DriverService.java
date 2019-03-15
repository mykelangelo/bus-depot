package com.papenko.project.service;

import com.papenko.project.entity.Driver;
import com.papenko.project.repository.DriverRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DriverService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DriverService.class);
    private final DriverRepository driverRepository;

    public DriverService(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    public Driver findDriverByEmail(String driverEmail) {
        LOGGER.debug("about to find driver by email");
        Driver driverByEmail = driverRepository.findDriverByEmail(driverEmail);
        LOGGER.debug("finished finding driver by email");
        return driverByEmail;
    }

    public void setDriverAwarenessToTrue(String driverEmail) {
        LOGGER.debug("about to set driver's awareness to true");
        driverRepository.updateDriverSetAwareness(driverRepository.findDriverByEmail(driverEmail), true);
        LOGGER.debug("finished setting driver's awareness to true");
    }
}
