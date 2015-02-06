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
}
