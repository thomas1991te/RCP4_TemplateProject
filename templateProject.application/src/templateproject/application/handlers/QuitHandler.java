package templateproject.application.handlers;

import java.util.ResourceBundle;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.workbench.IWorkbench;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

import templateproject.application.events.Events;

/**
 * Closes the application.
 * 
 * @author zwickl
 */
public final class QuitHandler {

	/**
	 * Access to the resource bundle.
	 */
	@Inject
	private ResourceBundle resourceBundle;
	
	/**
	 * The eclipse global event broker.
	 */
	@Inject
	private IEventBroker eventBroker;

	/**
	 * Default constructor.
	 */
	@Inject
	public QuitHandler() {
	}

	/**
	 * Asks the user if he really wants to close the application.
	 * 
	 * @param workbench
	 *            The workbench.
	 * @param shell
	 *            The parent shell.
	 */
	@Execute
	public void execute(final IWorkbench workbench, final Shell shell) {
		eventBroker.post(Events.TOPIC_APPLICATION_CLOSE, null);
	}

	/**
	 * Closes the application.
	 * 
	 * @param dummy
	 *            Indicating object.
	 * @param workbench
	 *            The workbench.
	 */
	@Inject
	@Optional
	public void closeApplication(@UIEventTopic(Events.TOPIC_APPLICATION_CLOSE) final Object dummy, final IWorkbench workbench) {
		if (MessageDialog.openConfirm(null, resourceBundle.getString("quitHandlerTitle"), resourceBundle.getString("quitHandlerMessage"))) {
			workbench.close();
		}
	}
}
