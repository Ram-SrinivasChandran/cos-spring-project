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

@Slf4j
@Service
@RequiredArgsConstructor
public class AvailabilityServiceImpl implements AvailabilityService {

    private final AvailabilityRepository availabilityRepository;

    @Override
    public List<Availability> findAll() {
        return null;
    }

    @Override
    public Availability findById(long id) {
        log.info("Entering findById()");
        if (id <= 0) {
            log.info("Leaving findById()");
            throw new CustomException("The Availability Id should be Greater than Zero.", HttpStatus.BAD_REQUEST);
        }

        log.info("Leaving findById()");
        return availabilityRepository.findById(id)
                .orElseThrow(() -> new CustomException("The Availability Id not Found", HttpStatus.NOT_FOUND));
    }

    @Override
    public Availability save(Availability object) {
        return null;
    }

    @Override
    public void delete(Availability object) {

    }

    @Override
    public void deleteById(long id) {

    }

    @Override
    public void update(Long id, Availability availability) {

    }

    @Override
    public Availability findByDay(String day) {
        return availabilityRepository.findByDay(day);
    }
}
