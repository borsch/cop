package borsch.cop.domain;

/**
 * Created by Oleh on 18.09.2017.
 */
public class Bean {

    // class name from configuration config
    private String type;
    private String id;
    private Object object;

    // actual class of object
    private Class<?> clazz;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }
}
