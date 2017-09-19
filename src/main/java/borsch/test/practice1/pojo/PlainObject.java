package borsch.test.practice1.pojo;

import borsch.cop.annotations.Autowired;

/**
 * Created by Oleh on 19.09.2017.
 */
public class PlainObject {

    @Autowired
    private PlainObjectTwo plainObjectTwo;

    public PlainObject() {
        System.out.println("constructor: plain object");
    }

    public void sayHi() {
        System.out.println("Hi from PlainObject");
    }

    public PlainObjectTwo getPlainObjectTwo() {
        return plainObjectTwo;
    }
}
