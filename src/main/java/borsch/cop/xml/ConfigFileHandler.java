package borsch.cop.xml;

import borsch.cop.domain.Bean;
import borsch.cop.domain.Config;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Class to parse configuration file with all beans
 *
 * Created by Oleh on 18.09.2017.
 */
public class ConfigFileHandler extends DefaultHandler {

    private Config config;

    public ConfigFileHandler() {
        this.config = new Config();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equalsIgnoreCase("bean")) {
            Bean bean = new Bean();
            for (int i = 0; i < attributes.getLength(); ++i) {
                String name = attributes.getLocalName(i);

                if (name.equals("class")) {
                    bean.setType(attributes.getValue(i));
                } else if (name.equals("id")) {
                    bean.setId(attributes.getValue(i));
                }
            }
            config.addBean(bean);
        } else if (qName.equalsIgnoreCase("scan-package")) {
            for (int i = 0; i < attributes.getLength(); ++i) {
                String name = attributes.getLocalName(i);

                if (name.equals("package")) {
                    config.setScanPackage(attributes.getValue(i));
                }
            }
        }
    }

    public Config getConfig() {
        return config;
    }
}
