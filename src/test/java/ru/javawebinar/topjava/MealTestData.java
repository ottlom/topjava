package ru.javawebinar.topjava;


import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int USER_MEAL_ID = START_SEQ;
    public static final int ADMIN_MEAL_ID = START_SEQ + 1;

    public static final Meal USER_MEAL = new Meal(100000, LocalDateTime.of(2024,7,1,13,10), "breakfast", 540);
    public static final Meal ADMIN_MEAL = new Meal(100001, LocalDateTime.of(2024,7,1,21,0), "dinner", 1000);

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.of(2000,1,1,1,0), "newFood", 333);
    }

    public static Meal getUpdated() {
        Meal update = new Meal();
        update.setId(USER_MEAL.getId());
        update.setDateTime(USER_MEAL.getDateTime());
        update.setDescription(USER_MEAL.getDescription());
        update.setCalories(USER_MEAL.getCalories());
        return update;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().ignoringFields("registered", "roles").isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

        public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparatorIgnoringFields("registered", "roles").isEqualTo(expected);
    }
}
