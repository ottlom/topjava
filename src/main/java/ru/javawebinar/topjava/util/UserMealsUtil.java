package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2016, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));

        List<UserMealWithExcess> mealsToByCycles = filteredByCycles(meals, LocalTime.of(10, 0), LocalTime.of(13, 1), 2000);
        mealsToByCycles.forEach(System.out::println);
        System.out.println("-------------------");
        List<UserMealWithExcess> mealsToByStream = filteredByStreams(meals, LocalTime.of(10, 0), LocalTime.of(13, 1), 2000);
        mealsToByStream.forEach(System.out::println);
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> mapDateAndCalories = new HashMap<>();
        ArrayList<UserMealWithExcess> mealWithExcessesList = new ArrayList<>();
        UserMealWithExcess userMealWithExcess;
        for (UserMeal meal : meals) {
            userMealWithExcess = new UserMealWithExcess(meal);
            mealWithExcessesList.add(userMealWithExcess);
            mapDateAndCalories.merge(meal.getDateTime().toLocalDate(), meal.getCalories(), Integer::sum);
        }

        ArrayList<UserMealWithExcess> filteredList = new ArrayList<>();
        for (UserMealWithExcess meal : mealWithExcessesList) {
            if (mapDateAndCalories.getOrDefault(meal.getDateTime().toLocalDate(), 0) <= caloriesPerDay) {
                meal.setExcess(true);
            }
            if (TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                filteredList.add(meal);
            }
        }
        return filteredList;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> mapDateAndCalories = meals.stream()
                .collect(Collectors.groupingBy(
                        (meal) -> meal.getDateTime().toLocalDate(), Collectors.summingInt(UserMeal::getCalories)));

        return meals.stream()
                .map(UserMealWithExcess::new)
                .filter((meal) -> {
                    if (mapDateAndCalories.get(meal.getDateTime().toLocalDate()) <= caloriesPerDay) {
                        meal.setExcess(true);
                    }
                    return true;
                })
                .filter((meal) -> TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime))
                .collect(Collectors.toList());
    }
}