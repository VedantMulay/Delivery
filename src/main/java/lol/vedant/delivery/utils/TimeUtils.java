/*
 * Copyright (c) 2024 Vedant Mulay. All rights reserved.
 */

package lol.vedant.delivery.utils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeUtils {

    // Adds specified amount of time to the given date and returns the new date
    public static LocalDateTime addTime(LocalDateTime dateTime, long amountToAdd, ChronoUnit unit) {
        return dateTime.plus(amountToAdd, unit);
    }

    // Subtracts specified amount of time from the given date and returns the new date
    public static LocalDateTime subtractTime(LocalDateTime dateTime, long amountToSubtract, ChronoUnit unit) {
        return dateTime.minus(amountToSubtract, unit);
    }

    // Calculates the duration between two dates
    public static Duration getDurationBetween(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return Duration.between(startDateTime, endDateTime);
    }

    // Checks if a cooldown period has passed since the given start time
    public static boolean isCooldownOver(LocalDateTime startTime, Duration cooldownDuration) {
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(startTime.plus(cooldownDuration));
    }

    // Gets the remaining time of a cooldown period
    public static Duration getRemainingCooldown(LocalDateTime startTime, Duration cooldownDuration) {
        LocalDateTime endTime = startTime.plus(cooldownDuration);
        LocalDateTime now = LocalDateTime.now();
        return Duration.between(now, endTime).isNegative() ? Duration.ZERO : Duration.between(now, endTime);
    }

    public static String formatDuration(Duration duration) {
        long days = duration.toDays();
        duration = duration.minusDays(days);

        long hours = duration.toHours();
        duration = duration.minusHours(hours);

        long minutes = duration.toMinutes();
        duration = duration.minusMinutes(minutes);

        long seconds = duration.getSeconds();

        StringBuilder formattedDuration = new StringBuilder();
        if (days > 0) {
            formattedDuration.append(days).append("d");
        }
        if (hours > 0) {
            formattedDuration.append(hours).append("h");
        }
        if (minutes > 0) {
            formattedDuration.append(minutes).append("m");
        }
        if (seconds > 0) {
            formattedDuration.append(seconds).append("s");
        }

        return formattedDuration.toString();
    }

    public static String getDays(Duration duration) {
        return String.format("%d", duration.toDays());
    }

    public static String getHours(Duration duration) {
        return String.format("%d", duration.toHours() % 24);
    }

    public static String getMinutes(Duration duration) {
        return String.format("%d", duration.toMinutes() % 60);
    }

    public static String getSeconds(Duration duration) {
        return String.format("%d", duration.toSeconds() % 60);
    }
}
