package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.transferobject.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.List;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {
    private MealService service;

    protected final Logger log = LoggerFactory.getLogger(getClass());

    public List<MealTo> getAll() {
        log.info("getAll");
        return service.getAll().stream()
                .map(meal -> MealsUtil.createTo(meal, meal.getCalories() == SecurityUtil.authUserCaloriesPerDay()))
                .collect(Collectors.toList());

    }

    public List<MealTo> getAllForAuth() {
        log.info("getAll");
        return service.getAll().stream()
                .filter(meal -> meal.getUserId() == SecurityUtil.authUserId())
                .map(meal -> MealsUtil.createTo(meal, meal.getCalories() == SecurityUtil.authUserCaloriesPerDay()))
                .collect(Collectors.toList());
    }

    public MealTo get(int id) {
        log.info("get {}", SecurityUtil.authUserId());
        return MealsUtil.createTo(service.get(id, SecurityUtil.authUserId()), service.get(id, SecurityUtil.authUserId()).getCalories() == SecurityUtil.authUserCaloriesPerDay());
    }

    public MealTo save(Meal meal) {
        log.info("create {}", meal);
        checkNew(meal);
        return MealsUtil.createTo(service.save(meal, SecurityUtil.authUserId()), meal.getCalories() == SecurityUtil.authUserCaloriesPerDay());
    }

    public void delete(int id) {
        log.info("delete {}", SecurityUtil.authUserId());
        service.delete(id, SecurityUtil.authUserId());
    }
}