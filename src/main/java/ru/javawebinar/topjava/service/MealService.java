package ru.javawebinar.topjava.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {
    private final MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal save(Meal meal, int authUserId) {
        checkNotFoundWithId(meal, meal.getId());
        isBelongToUser(meal, authUserId);

        return repository.save(meal);
    }

    public boolean delete(int id, int userId) {
        Meal meal = repository.get(id);
        checkNotFoundWithId(meal, meal.getId());
        isBelongToUser(meal, userId);

        return repository.delete(id);
    }

    public Meal get(int id, int userId) {
        Meal meal = repository.get(id);
        checkNotFoundWithId(meal, meal.getId());
        isBelongToUser(meal, userId);
        return meal;
    }

    public List<Meal> getAll() {
        return repository.getAll();
    }

    private void isBelongToUser(Meal meal, int userId) {
        if (meal.getUserId() != userId) {
            throw new NotFoundException("meal doesn't belong to user");
        }
    }
}