package lol.vedant.delivery.utils;

import com.iridium.iridiumcolorapi.IridiumColorAPI;
import lol.vedant.delivery.Delivery;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.List;

public class Utils {
    private static Delivery plugin = Delivery.getInstance();

    public static String cc(String message) {
        return IridiumColorAPI.process(message);
    }

    public static String fromList(List<?> list) {
        if (list == null || list.isEmpty()) return null;
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < list.size(); i++) {
            if(org.bukkit.ChatColor.stripColor(list.get(i).toString()).equals("")) builder.append("\n&r");
            else builder.append(list.get(i).toString()).append(i + 1 != list.size() ? "\n" : "");
        }

        return builder.toString();
    }

    public static Location parseLoc(String location) {
        if(location == null) {
            return null;
        }

        String[] parts = location.split(", ");

        // Check if the array has enough elements
        if (parts.length < 4) {
            // Log an error or handle the situation appropriately
            Bukkit.getLogger().warning("Invalid location format: " + location);
            return null;
        }

        try {

            double x = Double.parseDouble(parts[1]);
            double y = Double.parseDouble(parts[2]);
            double z = Double.parseDouble(parts[3]);

            // Get the Bukkit World object
            World world = Bukkit.getWorld(parts[0]);

            // Create and return the Bukkit Location
            return new Location(world, x, y, z);
        } catch (Exception e) {
            // Log the exception for further debugging
            Bukkit.getLogger().warning("Error while parsing location: " + location);
            e.printStackTrace();
            return null;  // Or return a default location or handle the error as needed
        }
    }


    public static String formatLocation(Location location) {
        if(location == null) {
            return null;
        }
        return location.getWorld().getName() + ", " + location.getBlockX() + ", " + location.getBlockY() + ", " + location.getBlockZ();
    }


}