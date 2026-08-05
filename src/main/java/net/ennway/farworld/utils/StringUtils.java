package net.ennway.farworld.utils;

import java.util.List;

public class StringUtils {
    public static List<String> linesFrom(String original)
    {
        return original.lines().toList();
    }
}
