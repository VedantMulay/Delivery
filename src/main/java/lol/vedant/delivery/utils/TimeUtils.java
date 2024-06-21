package lol.vedant.delivery.utils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

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

    public static void main(String[] args) {
        // Example usage
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime futureDate = addTime(now, 10, ChronoUnit.MINUTES);

        System.out.println("Current time: " + now);
        System.out.println("Future time after adding 10 minutes: " + futureDate);

        LocalDateTime pastDate = subtractTime(now, 5, ChronoUnit.HOURS);
        System.out.println("Past time after subtracting 5 hours: " + pastDate);

        Duration cooldown = Duration.ofMinutes(15);
        boolean isCooldownOver = isCooldownOver(now, cooldown);
        System.out.println("Is cooldown over: " + isCooldownOver);

        Duration remainingCooldown = getRemainingCooldown(now, cooldown);
        System.out.println("Remaining cooldown: " + formatDuration(remainingCooldown));
    }
}
