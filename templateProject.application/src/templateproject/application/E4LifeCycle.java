package templateproject.application;

import java.util.Locale;
import java.util.ResourceBundle;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.workbench.lifecycle.PostContextCreate;
import org.eclipse.e4.ui.workbench.lifecycle.PreSave;
import org.eclipse.e4.ui.workbench.lifecycle.ProcessAdditions;
import org.eclipse.e4.ui.workbench.lifecycle.ProcessRemovals;

/**
 * This is a stub implementation containing e4 LifeCycle annotated methods.<br />
 * There is a corresponding entry in <em>plugin.xml</em> (under the
 * <em>org.eclipse.core.runtime.products' extension point</em>) that references
 * this class.
 **/
@SuppressWarnings("restriction")
public final class E4LifeCycle {

	/**
	 * Is called after the Applications IEclipseContext is created, can be used
	 * to add objects, services, etc. to the context. This context is created
	 * for the MApplication class.
	 * 
	 * @param workbenchContext
	 *            The workbench context.
	 * @param jobManager
	 *            A reference to the job manager.
	 */
	@PostContextCreate
	void postContextCreate(final IEclipseContext workbenchContext) {
		Locale locale = new Locale("en", "US");
		workbenchContext.set(ResourceBundle.class, ResourceBundle.getBundle("templateproject.messages.Messages", locale));
	}

	/**
	 * Is called directly before the model is passed to the renderer, can be
	 * used to add additional elements to the model.
	 * 
	 * @param workbenchContext
	 *            The workbench context.
	 */
	@PreSave
	void preSave(final IEclipseContext workbenchContext) {

	}

	/**
	 * Is called before the application model is saved. You can modify the model
	 * before it is persisted.
	 * 
	 * @param workbenchContext
	 *            The workbench context.
	 */
	@ProcessAdditions
	void processAdditions(final IEclipseContext workbenchContext) {

	}

	/**
	 * Same as @ProcessAdditions but for removals.
	 * 
	 * @param workbenchContext
	 *            The workbench context.
	 */
	@ProcessRemovals
	void processRemovals(final IEclipseContext workbenchContext) {

	}
}
