package br.com.gokan.mrankup.utils;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Util {


    public static int[] convertSplit(String input) {
        if (input.isEmpty()) {
            return new int[0];
        }
        String[] parts = input.split(",");
        int[] result = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            result[i] = Integer.parseInt(parts[i].trim());
        }
        return result;
    }


    public  String formatterNumber(double numero) {
        return String.format("%,.0f", numero);
    }

    public  String formatterNumberType2(double numero) {
        if (numero < 1000) {
            return String.format("%.0f", numero);
        } else if (numero < 1000000) {
            return String.format("%.0fk", numero / 1000);
        } else if (numero < 1000000000) {
            return String.format("%.0fkk", numero / 1000000);
        } else if (numero < 1000000000000L) {
            return String.format("%.0fB", numero / 1000000000);
        } else {
            return String.format("%.0fT", numero / 1000000000000L);
        }
    }

    public static boolean isSpaceInventory(ItemStack item, Player player) {
        int maxStackSize = item.getMaxStackSize();
        int remainingItems = item.getAmount();

        for (ItemStack inventoryItem : player.getInventory().getContents()) {
            if (inventoryItem == null || inventoryItem.getType() == Material.AIR) {
                remainingItems -= maxStackSize;
            } else if (inventoryItem.isSimilar(item)) {
                int spaceInStack = maxStackSize - inventoryItem.getAmount();
                remainingItems -= spaceInStack;
            }
            if (remainingItems <= 0) {
                return true;
            }
        }
        return remainingItems <= 0;
    }

    public static boolean isSpaceInventory(Player player) {
        for (ItemStack inventoryItem : player.getInventory().getContents()) {
            if (inventoryItem == null || inventoryItem.getType() == Material.AIR) {
                return true;
            }
        }
        return false;
    }

    public static int extractNumberFrom(String input) {
        if (input == null) {
            return -1;
        }
        String numbersOnly = input.replaceAll("[^0-9]", "");
        if (numbersOnly.isEmpty()) {
            return -1;
        }
        return Integer.parseInt(numbersOnly);
    }

}
