package templateproject.application.handlers;

import java.util.ResourceBundle;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

/**
 * Opens and shows the about dialog.
 * 
 * @author zwickl
 */
public final class AboutHandler {

	/**
	 * Access to the resource bundle.
	 */
	@Inject
	private ResourceBundle resourceBundle;

	/**
	 * Opens the about dialog.
	 * 
	 * @param shell
	 *            The active shell.
	 */
	@Execute
	public void execute(final Shell shell) {

		MessageDialog.openInformation(shell,
				resourceBundle.getString("aboutHandlerTitle"),
				resourceBundle.getString("aboutHandlerMessage"));
	}
}
