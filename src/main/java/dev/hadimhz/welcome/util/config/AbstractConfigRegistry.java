package dev.hadimhz.welcome.util.config;


import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class AbstractConfigRegistry implements ConfigRegistry {

    protected final Map<Class<?>, Conf> loadedConfigs = new ConcurrentHashMap<>();

    protected <Type> void registerData(Class<Type> clazz, Type type, File file) {
        this.loadedConfigs.put(clazz, new Conf(file, type));
    }

    @Override
    public <Type> Type register(Class<Type> clazz, Type instance, File file) {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        registerData(clazz, instance, file);

        reload(clazz, instance, file);
        save(instance, file);

        return instance;
    }

    protected void trySave(Object obj, File file, Predicate<File> predicate, BiConsumer<Object, File> consumer) {
        try {
            if (!file.createNewFile()) {
                file.delete();
                file.createNewFile();
            }

            if (predicate.test(file)) {
                consumer.accept(obj, file);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(Object obj) {
        if (this.loadedConfigs.containsKey(obj.getClass())) {
            save(obj, this.loadedConfigs.get(obj.getClass()).getFile());
        } else throw new ConfigNotRegisteredException();
    }

    protected <Type> Type tryLoad(Class<Type> clazz, File file, Predicate<File> predicate, BiFunction<File, Class<Type>, Type> function) {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (predicate.test(file)) {
            return function.apply(file, clazz);
        } else return null;
    }


    @Override
    public <Type> Optional<Type> load(Class<Type> clazz) {
        if (this.loadedConfigs.containsKey(clazz)) {
            return load(clazz, this.loadedConfigs.get(clazz).getFile());
        } else throw new ConfigNotRegisteredException();
    }

    @Override
    public Collection<Object> getConfigs() {
        return this.loadedConfigs.values().stream().map(Conf::getObj).collect(Collectors.toList());
    }


}
