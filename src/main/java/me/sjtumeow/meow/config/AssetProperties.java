package me.sjtumeow.meow.config;

import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by Vincent on 2017/3/29.
 */
@Configuration
@ConfigurationProperties(prefix = "meow",ignoreInvalidFields = false,ignoreUnknownFields = false)
@PropertySource("classpath:asset.properties")
public class AssetProperties {
    public static class Asset {
        List<String> styles, scripts;

        public List<String> getStyles() {
            return styles;
        }

        public void setStyles(List<String> styles) {
            this.styles = styles;
        }

        public List<String> getScripts() {
            return scripts;
        }

        public void setScripts(List<String> scripts) {
            this.scripts = scripts;
        }
    }

    private final Asset admin = new Asset()

    public Asset getAdmin() {
        return admin;
    }

}
