package templateproject.application.editors;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.ui.di.Persist;
import org.eclipse.e4.ui.di.PersistState;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import templateproject.application.editor.Editor;
import templateproject.application.preferences.Configuration;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;

/**
 * An example implementation of an editor.
 * 
 * @author tzwickl
 */
public class ExampleEditor implements Editor, Listener {
	/**
	 * The editor's id.
	 */
	public static final String EDITOR_ID = "templateproject.application.partdescriptor.editor";
	
	/**
	 * The file to write.
	 */
	public static final String EDITOR_FILE = "EditorOutput.txt";
	
	/**
	 * Access to the resource bundle.
	 */
	@Inject
	private ResourceBundle resourceBundle;
	
	/**
	 * The editor's input.
	 */
	private String editorInput;
	
	/**
	 * The text field for the editor.
	 */
	private Text editorInputText;
	
	/**
	 * Button for saving the editor.
	 */
	private Button saveButton;
	
	/**
	 * The editor part.
	 */
	@Inject
	private MPart part;
	
	/**
	 * Creates a new editor for showing service settings.
	 * 
	 * @param parent
	 *            The parent composite.
	 */
	@PostConstruct
	public void postConstruct(final Composite parent) {
		
		// retrieve the editor's input
		this.editorInput = (String) part.getTransientData().get(ExampleEditor.EDITOR_ID);
		
		part.setLabel(this.getLabel());
		
		parent.setLayout(new GridLayout(1, false));
		
		editorInputText = new Text(parent, SWT.BORDER | SWT.WRAP | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL);
		editorInputText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		loadPersistedState();
		editorInputText.addListener(SWT.CHANGED, this);
		
		saveButton = new Button(parent, SWT.NONE);
		saveButton.setText(resourceBundle.getString("exampleEditorSaveButton"));
		saveButton.addListener(SWT.Selection, this);
	}

	@Override
	public Object getEditorInput() {
		return editorInput;
	}
	
	/**
	 * Is called before the editor is closed.
	 */
	@PreDestroy
	public void preDestroy() {
		
	}

	@Override
	public String getLabel() {
		return this.editorInput;
	}

	@Override
	public void handleEvent(Event event) {
		if (this.saveButton == event.widget) {
			if (this.part.isDirty()) {
				this.persist();
			}
		} else if (this.editorInputText == event.widget) {
			this.part.setDirty(true);
		}
	}
	
	/**
	 * Persists the editor input.
	 */
	@Persist
	private void persist() {		
		try {
			File file = new File(URLDecoder.decode(Configuration.workspaceLocation.getPath(), "UTF-8") + EDITOR_FILE);
			
			if (!file.exists()) {
				file.createNewFile();
			}
			
			FileWriter fileWriter = new FileWriter(file);
			fileWriter.write(this.editorInputText.getText());
			fileWriter.close();
			this.part.setDirty(false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Loads the persisted state of the editor.
	 */
	private void loadPersistedState() {
		try {
			File file = new File(URLDecoder.decode(Configuration.workspaceLocation.getPath(), "UTF-8") + EDITOR_FILE);
			
			if (!file.exists()) {
				return;
			}
			
			FileReader fileReader = new FileReader(file);
			BufferedReader bR = new BufferedReader(fileReader);
			
			String line = "";
			
			while ((line = bR.readLine()) != null) {
				this.editorInputText.setText(this.editorInputText.getText() + line + "\n");
			}
			
			bR.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
