package borsch.cop.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Configuration file that contains all necessary information
 * about beans in current container
 *
 * Created by Oleh on 18.09.2017.
 */
public class Config {

    private List<Bean> beans;
    private String scanPackage;

    public String getScanPackage() {
        return scanPackage;
    }

    public void setScanPackage(String scanPackage) {
        this.scanPackage = scanPackage;
    }

    public List<Bean> getBeans() {
        return beans;
    }

    public void setBeans(List<Bean> beans) {
        this.beans = beans;
    }

    public void addBean(Bean bean) {
        if (this.beans == null) {
            this.beans = new ArrayList<>();
        }

        this.beans.add(bean);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Config{");
        sb.append("beans=").append(beans);
        sb.append('}');
        return sb.toString();
    }
}
