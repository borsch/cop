package borsch.test.practice1;

import borsch.cop.bean.BeanFactory;
import borsch.cop.domain.Config;
import borsch.cop.exceptions.AutowiredException;
import borsch.cop.exceptions.BeanCreationException;
import borsch.cop.xml.XmlParser;
import borsch.test.practice1.pojo.PlainObject;

/**
 * Created by Oleh on 19.09.2017.
 */
public class Main {

    public static void main(String[] args) throws BeanCreationException, AutowiredException {
        XmlParser parser = new XmlParser();
        Config config = parser.parseConfig("practice_1.xml");

        BeanFactory beanFactory = new BeanFactory(config);
        PlainObject plainObject = (PlainObject)beanFactory.getBean(PlainObject.class);


        plainObject.sayHi();
        plainObject.getPlainObjectTwo().sayHi();
    }


}
