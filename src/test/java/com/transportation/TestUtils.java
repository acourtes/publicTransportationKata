package com.transportation;

import java.io.File;

public final class TestUtils {

    public static File getFile(String fileNameWithPath) {
        var filePath = TestUtils.class.getClassLoader()
                .getResource(fileNameWithPath).getFile();

        return new File(filePath);
    }
}
