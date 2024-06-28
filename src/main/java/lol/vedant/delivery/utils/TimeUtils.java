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

    // Format duration to readable string
    public static String formatDuration(Duration duration) {
        long days = duration.toDays();
        long hours = duration.toHours() % 24;
        long minutes = duration.toMinutes() % 60;
        long seconds = duration.getSeconds() % 60;
        return String.format("%d days, %d hours, %d minutes, %d seconds", days, hours, minutes, seconds);
    }

    /*
        Taken From https://www.spigotmc.org/threads/parsing-time-with-strings.162162/
    */
    public static Duration parseDuration(String string) {
        if (string == null || string.isEmpty())
            return Duration.ZERO;
        // this replaces the regex for 0-9 and the other characters
        string = string.replaceAll("[^0-9smhdw]", "");
        // checks if the new string is empty since we removed some characters
        if (string.isEmpty())
            return Duration.ZERO;
        // Check if string contains "w"
        if (string.contains("w")) {
            // Replace all non numbers with nothing
            string = string.replaceAll("[^0-9]", "");
            // Another empty check
            if (string.isEmpty())
                return Duration.ZERO;
            // If it has a number we change the number value to days by
            // multiplying by 7 then we can change it to seconds
            return Duration.ofSeconds(TimeUnit.DAYS.toSeconds(Long.parseLong(string) * 7));
        }
        // First we check for days using "d"
        TimeUnit unit = string.contains("d") ? TimeUnit.DAYS
                // If the string contains "h" it goes for hours
                : string.contains("h") ? TimeUnit.HOURS
                // If the string contains "m" it goes for minutes
                : string.contains("m") ? TimeUnit.MINUTES
                // Finally, if none match we go with seconds
                : TimeUnit.SECONDS;
        // Next we replace all the non-numbers with nothing so it can match a
        // number
        string = string.replaceAll("[^0-9]", "");
        // Another empty check to make sure something is there
        if (string.isEmpty())
            return Duration.ZERO;
        // Then we return the string as a long in seconds using the unit
        // selected earlier
        return Duration.ofSeconds(unit.toSeconds(Long.parseLong(string)));
    }
}
