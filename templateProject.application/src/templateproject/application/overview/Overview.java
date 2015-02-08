package templateproject.application.overview;

import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.jface.resource.FontDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;

import templateproject.application.editor.EditorServices;
import templateproject.application.editors.ExampleEditor;
import templateproject.application.preferences.Configuration;

import org.eclipse.swt.widgets.Button;

/**
 * 
 * @author tzwickl
 *
 */
public class Overview implements Listener {
	/**
	 * Reference to the event broker.
	 */
	@Inject
	private IEventBroker broker;

	/**
	 * Local jface resource manager which is bind to this composite.
	 */
	private LocalResourceManager resManager;
	
	/**
	 * Access to the resource bundle.
	 */
	@Inject
	private ResourceBundle resourceBundle;
	
	/**
	 * Opens the editor.
	 */
	private Button openEditorButton;
	
	/**
	 * Creates the analysis overview.
	 * 
	 * @param parent
	 *            The composite parent.
	 */
	@PostConstruct
	public void createComposite(final Composite parent) {
		// create a local jface resource manager which is bind to this composite
		resManager = new LocalResourceManager(JFaceResources.getResources(), parent);
		
		Font font = resManager.createFont(FontDescriptor.createFrom(Configuration.fontFamily, Configuration.fontSize, SWT.NORMAL));
		
		parent.setLayout(new GridLayout(1, false));
		
		Label partLabel = new Label(parent, SWT.NONE);
		partLabel.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1));
		partLabel.setText(resourceBundle.getString("overviewPartLabel"));
		partLabel.setFont(font);
		new Label(parent, SWT.NONE);
		
		openEditorButton = new Button(parent, SWT.NONE);
		openEditorButton.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		openEditorButton.setText(resourceBundle.getString("overviewPartOpenEditorButton"));
		openEditorButton.addListener(SWT.Selection, this);
	}

	@Override
	public void handleEvent(Event event) {
		if (this.openEditorButton == event.widget) {
			// Check if there is already an open editor with the same input
			if (EditorServices.checkIfEditorIsOpen(ExampleEditor.EDITOR_ID, resourceBundle.getString("overviewPartEditorLabel"))) {
				// if there is bring it to the front
				return;
			}
			// otherwise create a new one
			EditorServices.openEditor(ExampleEditor.EDITOR_ID, resourceBundle.getString("overviewPartEditorLabel"));
		}
	}

}
