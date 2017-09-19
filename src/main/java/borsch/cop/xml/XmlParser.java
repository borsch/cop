package borsch.cop.xml;

import borsch.cop.domain.Config;
import com.sun.istack.internal.NotNull;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.net.URL;
import java.nio.file.NoSuchFileException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Oleh on 18.09.2017.
 */
public final class XmlParser {

    private static final Logger LOGGER = Logger.getLogger(XmlParser.class.getCanonicalName());

    /**
     *
     * @param fileName - xml configuration file name stored in resources dir
     *                 or it's relative path in resources folder
     * @return configurations gotten from xml file
     */
    public Config parseConfig(@NotNull String fileName) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            ConfigFileHandler configHandler = new ConfigFileHandler();

            File file = null;
            try {
                ClassLoader classLoader = getClass().getClassLoader();
                URL url = classLoader.getResource(fileName);
                String absolutePath = url.getFile();
                file = new File(absolutePath);
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, e.getMessage());
            }

            if (file == null) {
                throw new NoSuchFileException(String.format("Can not find file [%s] at resources folder", fileName));
            }

            parser.parse(file, configHandler);

            return configHandler.getConfig();
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

}
