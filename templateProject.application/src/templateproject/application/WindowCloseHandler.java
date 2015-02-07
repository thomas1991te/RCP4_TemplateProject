package templateproject.application;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.IWindowCloseHandler;

import templateproject.application.events.Events;

/**
 * This class handles the window closing.
 * 
 * @author zwickl
 */
public final class WindowCloseHandler implements IWindowCloseHandler {

	/**
	 * Reference to the eclipse global event broker bus.
	 */
	@Inject
	private IEventBroker eventBroker;

	/**
	 * Default constructor.
	 */
	@Inject
	public WindowCloseHandler() {
	}

	/**
	 * This method is called after the application startup has been completed.
	 * 
	 * @param application
	 *            Reference to the application.
	 * @param modelService
	 *            Reference to the model service.
	 */
	@Inject
	@Optional
	public void startupComplete(
			final @UIEventTopic(UIEvents.UILifeCycle.APP_STARTUP_COMPLETE) MApplication application,
			final EModelService modelService) {
		MWindow window = (MWindow) modelService.find("templateproject.application", application);
		window.getContext().set(IWindowCloseHandler.class, this);
	}

	/**
	 * This method is called when the user presses the close button of the
	 * application.
	 * 
	 * @param window
	 *            The window to close.
	 * @return whether the window should be closed or not.
	 */
	@Override
	public boolean close(final MWindow window) {
		eventBroker.post(Events.TOPIC_APPLICATION_CLOSE, null);
		return false;
	}
}
