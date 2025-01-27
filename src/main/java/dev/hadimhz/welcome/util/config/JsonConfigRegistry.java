package dev.hadimhz.welcome.util.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Optional;

public class JsonConfigRegistry extends AbstractConfigRegistry {
    private static final String EXT = ".json";
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().excludeFieldsWithModifiers(new int[]{8, 128, 64}).serializeNulls().disableHtmlEscaping().create();

    public JsonConfigRegistry() {
    }

    public void save(Object obj, File file) {
        this.trySave(obj, file, (f) -> {
            return f.getName().endsWith(".json");
        }, (o, f) -> {
            try {
                BufferedWriter writer = Files.newBufferedWriter(file.toPath(), StandardCharsets.UTF_8);

                try {
                    GSON.toJson(obj, obj.getClass(), writer);
                } catch (Throwable var8) {
                    if (writer != null) {
                        try {
                            writer.close();
                        } catch (Throwable var7) {
                            var8.addSuppressed(var7);
                        }
                    }

                    throw var8;
                }

                if (writer != null) {
                    writer.close();
                }
            } catch (IOException var9) {
                IOException e = var9;
                e.printStackTrace();
            }

        });
    }

    public <Type> Optional<Type> load(Class<Type> clazz, File file) {
        return Optional.ofNullable(this.tryLoad(clazz, file, (f) -> f.getName().endsWith(".json"),
                (f, instance) -> {
                    try {
                        BufferedReader reader = Files.newBufferedReader(file.toPath(), StandardCharsets.UTF_8);

                        Object var5;
                        try {
                            var5 = GSON.fromJson(reader, clazz);
                        } catch (Throwable var8) {
                            if (reader != null) {
                                try {
                                    reader.close();
                                } catch (Throwable var7) {
                                    var8.addSuppressed(var7);
                                }
                            }

                            throw var8;
                        }

                        if (reader != null) {
                            reader.close();
                        }

                        return (Type) var5;
                    } catch (IOException var9) {
                        IOException e = var9;
                        e.printStackTrace();
                        return null;
                    }
                }));
    }
}
