package ru.geekbrains.engine.math;

import java.util.Random;

/**
 * Генератор случайных чисел
 */
public class Rnd {
    private static final Random random = new Random();
    private static int i;
    private static float positive = 1f;
    private static float negative = -1f;

    /**
     * Сгенерировать случайное число
     * @param min минимальное значение случайного числа
     * @param max максимальное значение случайного числа
     * @return результат
     */
    public static float nextFloat(float min, float max) {
        return random.nextFloat() * (max - min) + min;
    }
    public static float positiveOrNegative() {
        i = random.nextInt(10);
        if(i<5) {
            return negative;
        } else {
            return positive;
        }
    }
}
