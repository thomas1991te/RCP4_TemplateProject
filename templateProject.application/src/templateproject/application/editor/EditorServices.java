package templateproject.application.editor;

import java.util.Arrays;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.model.application.ui.basic.MStackElement;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.EPartService.PartState;

/**
 * This utility class provides services for editor parts.
 * 
 * @author zwickl
 */
@Creatable
@Singleton
public final class EditorServices {

	/**
	 * Reference to the part service.
	 */
	@Inject
	private static EPartService partService;

	/**
	 * Reference to the model service.
	 */
	@Inject
	private static EModelService modelService;

	/**
	 * Reference to the application.
	 */
	@Inject
	private static MApplication application;

	/**
	 * Reference to the editor's part stack.
	 */
	private static MPartStack editorPartStack;

	/**
	 * Default constructor.
	 */
	@Inject
	public EditorServices() {
		// intentionally left blanked
	}

	/**
	 * Retrieves the editor's area.
	 */
	@PostConstruct
	public void postConstruct() {
		editorPartStack = modelService.findElements(application, null, MPartStack.class, Arrays.asList("EditorArea"))
				.get(0);
	}

	/**
	 * Checks if there is already an open editor with the given id and editor
	 * input. If there is such an editor bring it to the front.
	 * 
	 * @param id
	 *            The id of the editor.
	 * @param editorInput
	 *            The input data of the editor.
	 * @return true if there is an open editor with the given id and label,
	 *         otherwise false.
	 */
	public static boolean checkIfEditorIsOpen(final String id, final Object editorInput) {
		Object editorInputObject = editorInput;
		for (MPart part : modelService.findElements(application, id, MPart.class, null)) {
			if (part.getObject() == null) {
				continue;
			}
			if (!(part.getObject() instanceof Editor)) {
				throw new ClassCastException("Every editor needs to implement the Editor interface");
			}
			Editor editor = (Editor) part.getObject();
			if (editor != null) {
				if (editor.getEditorInput().equals(editorInputObject)) {
					part.getTransientData().put(id, editorInput);
					part.getPersistedState();
					part.setVisible(true);
					editorPartStack.setVisible(true);
					partService.activate(part, true);
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Opens a new editor with the given id and editor input.
	 * 
	 * @param id
	 *            The id of the editor to open.
	 * @param editorInput
	 *            The input data of the editor.
	 */
	public static void openEditor(final String id, final Object editorInput) {
		MPart newPart = partService.createPart(id);
		newPart.getTransientData().put(id, editorInput);
		editorPartStack.getChildren().add(newPart);
		editorPartStack.setVisible(true);
		partService.showPart(newPart, PartState.ACTIVATE);
	}

	/**
	 * Closes all editors in the part stack.
	 */
	public static void closeAllEditors() {
		for (MStackElement part : editorPartStack.getChildren()) {
			part.setVisible(false);
		}
		editorPartStack.setVisible(true);
	}
}
