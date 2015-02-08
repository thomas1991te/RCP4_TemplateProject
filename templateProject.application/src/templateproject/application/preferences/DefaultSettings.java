package templateproject.application.preferences;

import java.io.IOException;
import java.util.Properties;

import javax.swing.JLabel;

/**
 * This class contains all default values for all settings in the preference
 * store. The default values are loaded at the first start of the RCP
 * application or if the user presses the "restore default" button.
 * 
 * @author zwickl
 */
public abstract class DefaultSettings {
    /**
     * The default settings loaded from the property file.
     */
    private static final Properties DEFAULT_PROP = new Properties();

    /**
     * Loads the default settings from the property file "DefaultSettings.xml".
     */
    static {
        try {
            DEFAULT_PROP.loadFromXML(DefaultSettings.class.getResourceAsStream("DefaultSettings.xml"));
        } catch (IOException e) {
            System.err.println("Unable to load the default settings from the property file (DefaultSettings.xml)!");
        }
    }

    /**
     * Default Language.
     */
    public static final String DEFAULT_LANGUAGE = DEFAULT_PROP.getProperty("DEFAULT_LANGUAGE");
    
    /**
     * Supported Languages.
     */
    public static final String SUPPORTED_LANGUAGES = DEFAULT_PROP.getProperty("SUPPORTED_LANGUAGES");
    
    /**
     * The default font family.
     */
    public static final String DEFAULT_FONT_FAMILY = new JLabel().getFont().getFamily();
    
    /**
     * The default font size.
     */
    public static final int DEFAULT_FONT_SIZE = new JLabel().getFont().getSize();
    
    /**
     * The max font size.
     */
    public static final int MAX_FONT_SIZE = new Integer(DEFAULT_PROP.getProperty("MAX_FONT_SIZE"));

}
