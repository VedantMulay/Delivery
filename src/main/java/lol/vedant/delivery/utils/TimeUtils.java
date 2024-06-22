/*
 * Copyright (c) 2024 Vedant Mulay. All rights reserved.
 */

package lol.vedant.delivery.utils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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

    // Format duration to readable string
    public static String formatDuration(Duration duration) {
        long days = duration.toDays();
        long hours = duration.toHours() % 24;
        long minutes = duration.toMinutes() % 60;
        long seconds = duration.getSeconds() % 60;
        return String.format("%d days, %d hours, %d minutes, %d seconds", days, hours, minutes, seconds);
    }

    public static Duration parseDuration(String input) {
        // Define regex pattern to extract days, hours, minutes, and seconds
        Pattern pattern = Pattern.compile("(?:(\\d+)d)?\\s*(?:(\\d+)h)?\\s*(?:(\\d+)m)?\\s*(?:(\\d+)s)?");
        Matcher matcher = pattern.matcher(input);

        int days = 0;
        int hours = 0;
        int minutes = 0;
        int seconds = 0;

        if (matcher.matches()) {
            if (matcher.group(1) != null) days = Integer.parseInt(matcher.group(1));
            if (matcher.group(2) != null) hours = Integer.parseInt(matcher.group(2));
            if (matcher.group(3) != null) minutes = Integer.parseInt(matcher.group(3));
            if (matcher.group(4) != null) seconds = Integer.parseInt(matcher.group(4));
        } else {
            throw new IllegalArgumentException("Invalid duration format");
        }

        long totalSeconds = seconds
                + minutes * 60
                + hours * 3600
                + days * 86400;

        return Duration.ofSeconds(totalSeconds);
    }
}
