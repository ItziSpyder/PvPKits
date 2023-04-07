package io.github.itzispyder.pvpkits.util;

public abstract class ArrayUtils {

    public static boolean matchAll(String[] args, String... candidates) {
        for (String arg : args)
            for (String candidate : candidates)
                if (arg.equals(candidate))
                    return true;
        return false;
    }

    public static boolean matchAllIgnoreCase(String[] args, String... candidates) {
        for (String arg : args)
            for (String candidate : candidates)
                if (arg.equalsIgnoreCase(candidate))
                    return true;
        return false;
    }
}
