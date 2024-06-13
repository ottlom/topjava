package ru.javawebinar.topjava.crud;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

public interface MealStorage {
    Meal create(Meal meal);
    void delete(LocalDateTime dateTime, Meal meal);
    Meal update(Meal meal);
    Meal get(Meal meal);
    List<Meal> getAll();
}
