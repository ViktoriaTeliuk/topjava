package ru.javawebinar.topjava.util;

import java.util.concurrent.atomic.AtomicInteger;

public class Counter {
        private static AtomicInteger count = new AtomicInteger(0);

        public static int incrementCount() {
            return count.incrementAndGet();
        }

}
