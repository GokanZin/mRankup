package br.com.gokan.mtemplate.utils;

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

}
