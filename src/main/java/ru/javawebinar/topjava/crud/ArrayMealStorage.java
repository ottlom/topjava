package ru.javawebinar.topjava.crud;

import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ArrayMealStorage implements StorageMeal {
    public final CopyOnWriteArrayList<MealTo> MEALS_LIST = new CopyOnWriteArrayList<>();

    {
        MEALS_LIST.add(new MealTo(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500, true, MealsUtil.CALORIES_PER_DAY));
    }

    @Override
    public void save(MealTo meal) {
        MEALS_LIST.add(meal);
    }

    @Override
    public void delete(int id) {
        MEALS_LIST.remove(getSearchKey(id));
    }

    @Override
    public void update(MealTo meal) {
        MEALS_LIST.set(getSearchKey(meal.getId()), meal);
    }

    @Override
    public MealTo get(MealTo meal) {
        return MEALS_LIST.get(getSearchKey(meal.getId()));
    }

    @Override
    public List<MealTo> getAllMeals() {
        return new ArrayList<>(MEALS_LIST);
    }

    protected Integer getSearchKey(int id) {
        for (int i = 0; i < MEALS_LIST.size(); i++) {
            if (id == MEALS_LIST.get(i).getId()) {
                return i;
            }
        }
        return -1;
    }
}
