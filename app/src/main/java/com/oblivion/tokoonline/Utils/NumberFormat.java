package com.oblivion.tokoonline.Utils;


import java.util.Locale;

public class NumberFormat {

    public String currencyFormatter(String num) {

        Locale local = new Locale("id", "id");

        String cleanString = num.replaceAll("[Rp.,]",
                "");

        double parsed;
        try {
            parsed = Double.parseDouble(cleanString);
        } catch (NumberFormatException e) {
            parsed = 0.00;
        }

        java.text.NumberFormat formatter = java.text.NumberFormat
                .getCurrencyInstance(local);
        formatter.setMaximumFractionDigits(0);
        formatter.setParseIntegerOnly(true);


        return formatter.format((parsed));
    }
}
