package com.pedroluizforlan.pontodoc.util;

import java.text.Normalizer;

public class TextUtils {

    public static String normalize(String input) {
    if (input == null) return "";
    String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
    return normalized.replaceAll("\\p{M}", "")
                     .toUpperCase()
                     .trim()
                     .replaceAll("\\s+", " ");
}
    
}
