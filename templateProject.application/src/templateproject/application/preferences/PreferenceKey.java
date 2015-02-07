package templateproject.application.preferences;

/**
 * Each constant refers to a in the preferences persisted value.
 * The constants are used to ease the access to the preference variables and are nothing else then string literals.
 * 
 * @author tzwickl
 *
 */
public interface PreferenceKey {
	/**
	 * The used language.
	 */
	String LANGUAGE = "LANGUAGE";
	
	/**
	 * The font family of the application.
	 */
	String FONT_FAMILY = "FONT_FAMILY";
	
	/**
	 * The used font size.
	 */
	String FONT_SIZE = "FONT_SIZE";
}
