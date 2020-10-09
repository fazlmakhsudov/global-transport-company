package com.epam.gtc;

import java.util.Locale;

public final class Locales {

    private static final Locale ru = new Locale("ru", "RU");
    private static final Locale en = Locale.ENGLISH;

    /**
     * Get locale.
     *
     * @param locale locale to set
     * @return Locale
     */
    public static Locale getLocale(final String locale) {
        Locale loc = en;
        if (locale.equalsIgnoreCase("ru")) {
            loc = ru;
        }
        return loc;
    }

    private Locales() throws IllegalAccessException {
        throw new IllegalAccessException();
    }
}