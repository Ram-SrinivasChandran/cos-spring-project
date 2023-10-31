package net.breezeware.cosspringproject.food.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.breezeware.cosspringproject.exception.CustomException;
import net.breezeware.cosspringproject.food.dao.FoodMenuRepository;
import net.breezeware.cosspringproject.food.entity.FoodItem;
import net.breezeware.cosspringproject.food.entity.FoodMenu;
import net.breezeware.cosspringproject.food.service.api.FoodMenuService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.validation.Validator;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FoodMenuServiceImpl implements FoodMenuService {

    private final FoodMenuRepository foodMenuRepository;
    private final Validator fieldValidator;
    @Override
    public List<FoodMenu> findAll() {
        log.info("Entering findAll()");
        List<FoodMenu> foodMenus = foodMenuRepository.findAll();
        log.info("Leaving findAll()");
        return foodMenus;
    }

    @Override
    public FoodMenu findById(long id) {
        log.info("Entering findById()");
        if(id<=0){
            log.info("Leaving findById()");
            throw new CustomException("The Food Menu Id should be Greater than Zero.", HttpStatus.BAD_REQUEST);
        }
        log.info("Leaving findById()");
        return foodMenuRepository.findById(id).orElseThrow(()->new CustomException("The Food Menu not Found", HttpStatus.NOT_FOUND));
    }

    @Override
    public FoodMenu save(FoodMenu foodMenu) {
        return null;
    }

    @Override
    public void update(Long id, FoodMenu foodMenu) {

    }

    @Override
    public void delete(FoodMenu foodMenu) {

    }

    @Override
    public void deleteById(long id) {

    }
}
