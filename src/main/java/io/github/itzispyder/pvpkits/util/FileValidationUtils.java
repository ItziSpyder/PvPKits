package io.github.itzispyder.pvpkits.util;

import java.io.File;

public abstract class FileValidationUtils {

    public static boolean validate(File file) {
        try {
            if (file == null)
                return false;
            if (!file.getParentFile().exists())
                if (!file.getParentFile().mkdirs())
                    return false;
            if (!file.exists())
                if (!file.createNewFile())
                    return false;
            return true;
        }
        catch (Exception ex) {
            return false;
        }
    }
}
