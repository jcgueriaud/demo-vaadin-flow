package lu.lusis.demo.utils;

import java.util.Locale;

public class CountryUtils {

    public static String[] getCountryCodes() {
        return Locale.getISOCountries();
    }

    public static String getCountryName(String countryCode) {
        if (countryCode == null){
            return "";
        }
        Locale obj = new Locale("", countryCode);

        return obj.getDisplayCountry();
    }
}
