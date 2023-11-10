package net.breezeware.cosspringproject.user.service.impl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import net.breezeware.cosspringproject.exception.CustomException;
import net.breezeware.cosspringproject.user.dao.UserAddressMapRepository;
import net.breezeware.cosspringproject.user.entity.UserAddressMap;
import net.breezeware.cosspringproject.user.service.api.UserAddressMapService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserAddressMapServiceImpl implements UserAddressMapService {
    private final UserAddressMapRepository userAddressMapRepository;
    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserAddressMap> findAll() {
        log.info("Entering findAll()");
        List<UserAddressMap> userAddressMaps = userAddressMapRepository.findAll();
        log.info("Leaving findAll()");
        return userAddressMaps;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public UserAddressMap findById(Long userAddressMapId) {
        log.info("Entering findById(), id: {}", userAddressMapId);
        if (userAddressMapId <= 0) {
            throw new CustomException("Address Id Must be Greater Than Zero.", HttpStatus.BAD_REQUEST);
        }

        UserAddressMap userAddress = userAddressMapRepository.findById(userAddressMapId)
                .orElseThrow(() -> new CustomException("The Address not Found", HttpStatus.NOT_FOUND));
        log.info("Leaving findById()");
        return userAddress;
    }
    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public UserAddressMap save(UserAddressMap userAddressMap) {
        log.info("Entering save()");
        UserAddressMap savedUserAddressMap = userAddressMapRepository.save(userAddressMap);
        log.info("Leaving save()");
        return savedUserAddressMap;
    }
    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void update(Long userAddressMapId, UserAddressMap userAddressMap) {
    }
    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void delete(UserAddressMap userAddressMap) {
        log.info("Entering delete()");
        userAddressMapRepository.delete(userAddressMap);
        log.info("Leaving delete()");
    }
    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void deleteById(Long userAddressMapId) {
        log.info("Entering deleteById()");
        userAddressMapRepository.deleteById(userAddressMapId);
        log.info("Leaving deleteById()");
    }
}
