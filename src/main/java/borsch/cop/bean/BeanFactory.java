package borsch.cop.bean;

import borsch.cop.annotations.Autowired;
import borsch.cop.domain.Bean;
import borsch.cop.domain.Config;
import borsch.cop.exceptions.AutowiredException;
import borsch.cop.exceptions.BeanCreationException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Objects;

/**
 * Created by Oleh on 19.09.2017.
 */
public class BeanFactory {

    private final Config config;

    public BeanFactory(final Config config) throws BeanCreationException, AutowiredException {
        this.config = config;

        createBeans();
        injectBeans();
    }

    private void createBeans() throws BeanCreationException {
        List<Bean> beans = config.getBeans();

        if (beans != null) {
            for (Bean bean : beans) {
                try {
                    Class<?> clazz = Class.forName(bean.getType());
                    Constructor<?> constructor = clazz.getConstructor();

                    bean.setObject(constructor.newInstance());
                    bean.setClazz(clazz);

                    if (bean.getId() == null) {
                        String name = bean.getClazz().getCanonicalName();
                        name = name.substring(name.lastIndexOf('.') + 1);

                        bean.setId(name);
                    }
                } catch (Exception e) {
                    if (e instanceof NoSuchMethodException) {
                        throw new BeanCreationException(String.format("No default constructor for class [%s]", bean.getType()));
                    }

                    if (e instanceof ClassNotFoundException) {
                        throw new BeanCreationException(String.format("Class [%s] does not found", bean.getType()));
                    }

                    throw new BeanCreationException(e.getMessage());
                }
            }
        }
    }

    private void injectBeans() throws AutowiredException {
        List<Bean> beans = config.getBeans();

        if (beans != null) {
            for (Bean bean : beans) {
                for (Field field : bean.getClazz().getDeclaredFields()) {
                    Autowired autowired = field.getAnnotation(Autowired.class);

                    if (autowired != null) {
                        Object object;
                        if (autowired.qualifier().isEmpty()) {
                            object = getBean(field.getType());
                        } else {
                            object = getBean(field.getType(), autowired.qualifier());
                        }

                        if (object == null) {
                            throw new AutowiredException(String.format(
                                    "Can not find bean of type [%s] for component [%s]",
                                    field.getType().getName(),
                                    bean.getClazz().getName()
                            ));
                        }

                        boolean accessible = field.isAccessible();
                        field.setAccessible(true);
                        try {
                            field.set(bean.getObject(), object);
                        } catch (IllegalAccessException e) {
                            throw new AutowiredException(String.format(
                                    "Can not autowire field of type [%s] for component [%s]",
                                    field.getType().getName(),
                                    bean.getClazz().getName()
                            ));
                        }
                        field.setAccessible(accessible);
                    }
                }
            }
        }
    }

    public Object getBean(Class<?> clazz) {
        List<Bean> beans = config.getBeans();

        if (beans != null) {
            for (Bean bean : beans) {
                if (bean.getClazz().isAssignableFrom(clazz)) {
                    return bean.getObject();
                }
            }
        }

        return null;
    }

    public Object getBean(Class<?> clazz, String identifier) {
        List<Bean> beans = config.getBeans();

        if (beans != null) {
            for (Bean bean : beans) {
                if (bean.getClazz().isAssignableFrom(clazz) && identifier.equals(bean.getId())) {
                    return bean.getObject();
                }
            }
        }

        return null;
    }
}
