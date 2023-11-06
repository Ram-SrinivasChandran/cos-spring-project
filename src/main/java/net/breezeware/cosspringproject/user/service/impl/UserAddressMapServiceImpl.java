package net.breezeware.cosspringproject.user.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.breezeware.cosspringproject.exception.CustomException;
import net.breezeware.cosspringproject.user.dao.UserAddressMapRepository;
import net.breezeware.cosspringproject.user.entity.UserAddressMap;
import net.breezeware.cosspringproject.user.service.api.UserAddressMapService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserAddressMapServiceImpl implements UserAddressMapService {
    private final UserAddressMapRepository userAddressMapRepository;
    @Override
    public List<UserAddressMap> findAll() {
        return userAddressMapRepository.findAll();
    }

    @Override
    public UserAddressMap findById(Long id) {
        log.info("Entering findById(), id: {}", id);
        if (id <= 0) {
            throw new CustomException("Address Id Must be Greater Than Zero.", HttpStatus.BAD_REQUEST);
        }
        log.info("Leaving findById()");
        return userAddressMapRepository.findById(id).orElseThrow(() ->new CustomException("The Address not Found", HttpStatus.NOT_FOUND));
    }

    @Override
    public UserAddressMap save(UserAddressMap userAddressMap) {
        return userAddressMapRepository.save(userAddressMap);
    }

    @Override
    public void update(Long id, UserAddressMap userAddressMap) {

    }

    @Override
    public void delete(UserAddressMap userAddressMap) {
        userAddressMapRepository.delete(userAddressMap);
    }

    @Override
    public void deleteById(Long id) {
        userAddressMapRepository.deleteById(id);
    }
}
