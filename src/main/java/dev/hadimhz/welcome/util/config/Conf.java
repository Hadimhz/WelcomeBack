package dev.hadimhz.welcome.util.config;

import java.io.File;

public class Conf {

    private final File file;
    private final Object obj;

    public Conf(File file, Object obj) {
        this.file = file;
        this.obj = obj;
    }

    public File getFile() {
        return file;
    }

    public Object getObj() {
        return obj;
    }
}