package ru.javawebinar.topjava.crud;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class MealStorageInMemory implements MealStorage {
    //геттер для доступа? и нужен ли тут final? нужна другая канкарент коллекция почитать подумать какая больше подойдёт
    //по сути мы везде ищем коллекцию по дате
    private static final ConcurrentHashMap<LocalDateTime, CopyOnWriteArrayList<Meal>> mealList = new ConcurrentHashMap<>();

    @Override
    public Meal create(Meal meal) {
        if (mealList.get(meal.getDateTime()) == null) {
            CopyOnWriteArrayList<Meal> list = new CopyOnWriteArrayList<>();
            list.add(meal);
            mealList.put(meal.getDateTime(), list);
        } else {
            mealList.get(meal.getDateTime()).add(meal);
        }
        return meal;
    }

    @Override
    public void delete(LocalDateTime dateTime, Meal meal) {
        mealList.get(dateTime).remove(meal);
    }

    @Override
    public Meal update(Meal meal) {
        return null;
    }

    @Override
    public Meal get(Meal meal) {
        return mealSearch(meal);
    }

    @Override
    public List<Meal> getAll() {
        return null;
    }

    private Meal mealSearch(Meal meal) {
        Meal mealReturn = null;
        for (Meal m : mealList.get(meal.getDateTime())) {
            if (m.equals(meal)) {
                mealReturn = meal;
            } else {
                throw new NoSuchElementException("such element in list not found");
            }
        }
        return mealReturn;
    }
}
