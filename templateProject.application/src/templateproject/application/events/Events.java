package templateproject.application.events;

/**
 * This interface defines events which can be put into the eclipse event bus and
 * caught by the corresponding methods.
 * 
 * @author zwickl
 *
 */
public interface Events {

	// Events for E4LifeCycle

	/**
	 * Closes the application.
	 */
	String TOPIC_APPLICATION_CLOSE = "TOPIC_APPLICATION/CLOSE";
	
	/**
	 * Restores the general preference page to default.
	 */
	String TOPIC_PREFERENCES_GENERAL_RESTORE_DEFAULT = "TOPIC_PREFERENCES/GENERAL/RESTORE_DEFAULT";
	
	/**
	 * Applies the changes in the general preference page.
	 */
	String TOPIC_PREFERENCES_GENERAL_APPLY = "TOPIC_PREFERENCES/GENERAL/APPLY";
}
