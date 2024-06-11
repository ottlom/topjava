package ru.javawebinar.topjava.crud;

import ru.javawebinar.topjava.model.MealTo;

import java.util.List;

public interface StorageMeal {
    void save(MealTo meal);
    void delete(int id);
    void update(MealTo meal);
    MealTo get(MealTo meal);
    List<MealTo> getAllMeals();
}
