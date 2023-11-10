package net.breezeware.cosspringproject.food.service.impl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import net.breezeware.cosspringproject.exception.CustomException;
import net.breezeware.cosspringproject.food.dao.AvailabilityRepository;
import net.breezeware.cosspringproject.food.entity.Availability;
import net.breezeware.cosspringproject.food.service.api.AvailabilityService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AvailabilityServiceImpl implements AvailabilityService {

    private final AvailabilityRepository availabilityRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Availability> findAllAvailabilities() {
        log.info("Entering findAllAvailabilities()");
        List<Availability> availabilities = availabilityRepository.findAll();
        log.info("Leaving findAllAvailabilities()");
        return availabilities;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Availability findAvailabilityById(long availabilityId) {
        log.info("Entering findAvailabilityById()");
        if (availabilityId <= 0) {
            throw new CustomException("The Availability Id should be Greater than Zero.", HttpStatus.BAD_REQUEST);
        }

        log.info("Leaving findAvailabilityById()");
        return availabilityRepository.findById(availabilityId)
                .orElseThrow(() -> new CustomException("The Availability Id not Found", HttpStatus.NOT_FOUND));
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public Availability saveAvailability(Availability availability) {
        log.info("Entering saveAvailability()");
        Availability savedAvailability = availabilityRepository.save(availability);
        log.info("Leaving saveAvailability()");
        return savedAvailability;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void deleteAvailability(Availability availability) {
        log.info("Entering deleteAvailability()");
        availabilityRepository.delete(availability);
        log.info("Leaving deleteAvailability()");
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void deleteAvailabilityById(long availabilityId) {
        log.info("Entering deleteAvailabilityById()");
        availabilityRepository.deleteById(availabilityId);
        log.info("Leaving deleteAvailabilityById()");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Availability findAvailabilityByDay(String day) {
        log.info("Entering findAvailabilityByDay()");
        Availability availabilityByDay = availabilityRepository.findByDay(day);
        log.info("Leaving findAvailabilityByDay()");
        return availabilityByDay;

    }
}
