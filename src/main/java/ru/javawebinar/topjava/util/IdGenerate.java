package ru.javawebinar.topjava.util;

import java.util.concurrent.atomic.AtomicInteger;

public class IdGenerate {
    private static final AtomicInteger id = new AtomicInteger(0);

    public static int idGenerate() {
        return id.getAndIncrement();
    }
}
