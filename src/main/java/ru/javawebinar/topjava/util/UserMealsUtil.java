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
        for (UserMeal meal : meals) {
            mapDateAndCalories.merge(meal.getDateTime().toLocalDate(), meal.getCalories(), Integer::sum);
        }

        List<UserMealWithExcess> filteredList = new ArrayList<>();
        for (UserMeal meal : meals) {
            if (TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                filteredList.add(convertToUserMealWithExcess(meal,mapDateAndCalories.get(meal.getDateTime().toLocalDate()),caloriesPerDay));
            }
        }
        return filteredList;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> mapDateAndCalories = meals.stream()
                .collect(Collectors.groupingBy(
                        meal -> meal.getDateTime().toLocalDate(), Collectors.summingInt(UserMeal::getCalories)));

        return meals.stream()
                .filter(meal -> TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime))
                .map(meal -> convertToUserMealWithExcess(meal,mapDateAndCalories.get(meal.getDateTime().toLocalDate()),caloriesPerDay))
                .collect(Collectors.toList());
    }

    private static UserMealWithExcess convertToUserMealWithExcess(UserMeal userMeal, int sumCalories, int caloriesPerDay) {
        if (sumCalories > caloriesPerDay) {
            return new UserMealWithExcess(userMeal.getDateTime(),userMeal.getDescription(), userMeal.getCalories(), true);
        } else {
            return new UserMealWithExcess(userMeal.getDateTime(),userMeal.getDescription(), userMeal.getCalories(), false);
        }
    }
}