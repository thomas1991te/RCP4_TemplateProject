package templateproject.application.preferences;

import java.net.URI;

/**
 * Contains the current active configuration of the preference store.
 * 
 * @author tzwickl
 *
 */
public class Configuration {

	/**
	 * The current language.
	 */
	public static String language;

	/**
	 * The current font family.
	 */
	public static String fontFamily;

	/**
	 * The current font size.
	 */
	public static int fontSize;

	/**
	 * The path of the workspace.
	 */
	public static URI workspaceLocation;
}
