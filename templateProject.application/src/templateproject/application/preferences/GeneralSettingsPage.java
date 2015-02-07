package templateproject.application.preferences;

import java.awt.GraphicsEnvironment;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.di.extensions.Preference;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.jface.resource.FontDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.osgi.service.prefs.BackingStoreException;

import templateproject.application.events.Events;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Combo;

/**
 * The preference page for general settings.
 * 
 * @author tzwickl
 *
 */
public class GeneralSettingsPage {
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
	 * The selected language.
	 */
	private Combo language;
	
	/**
	 * The selected font family.
	 */
	private Combo font;
	
	/**
	 * The selected font size.
	 */
	private Combo fontSize;

	/**
	 * Creates the general settings page.
	 * 
	 * @param parent
	 *            The parent composite.
	 * @param prefs
	 *            Reference to the preference store.
	 */
	@PostConstruct
	public void postConstruct(final Composite parent, 
			@Preference(nodePath = PreferencesHandler.preferenceStore) final IEclipsePreferences prefs) {
		// create a local jface resource manager which is bind to this composite
		resManager = new LocalResourceManager(JFaceResources.getResources(), parent);
		parent.setLayout(new GridLayout(1, false));
		
		Font normalFont = resManager.createFont(FontDescriptor.createFrom(Configuration.fontFamily, Configuration.fontSize, SWT.NORMAL));
		
		Composite content = new Composite(parent, SWT.NONE);
		content.setLayout(new GridLayout(4, false));
		content.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		content.setBounds(0, 0, 64, 64);
		
		Label header = new Label(content, SWT.NONE);
		header.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 4, 1));
		header.setText(resourceBundle.getString("generalSettingsHeader"));
		header.setFont(resManager.createFont(FontDescriptor.createFrom(Configuration.fontFamily, Configuration.fontSize, SWT.BOLD)));
		
		Label label = new Label(content, SWT.SEPARATOR | SWT.HORIZONTAL);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 4, 1));
		
		Label languageLabel = new Label(content, SWT.NONE);
		languageLabel.setText(resourceBundle.getString("generalSettingsLanguageLabel"));
		languageLabel.setFont(normalFont);
		
		language = new Combo(content, SWT.READ_ONLY);
		language.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		
		language.setItems(DefaultSettings.SUPPORTED_LANGUAGES.split(","));
		
		int itemCount = 0;
		for (String item : language.getItems()) {
			if (item.equals(Configuration.language)) {
				language.select(itemCount);
				break;
			}
			itemCount++;
		}
		
		Label fontLabel = new Label(content, SWT.NONE);
		fontLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		fontLabel.setText(resourceBundle.getString("generalSettingsFontLabel"));
		fontLabel.setFont(normalFont);
		
		font = new Combo(content, SWT.READ_ONLY);
		font.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    String[] fonts = e.getAvailableFontFamilyNames(); // Get the fonts
	    itemCount = 0;
	    for (String f : fonts) {
	      font.add(f);
	      if (f.equals(Configuration.fontFamily)) {
	    	  font.select(itemCount);
	      }
	      itemCount++;
	    }
		
		Label fontSizeLabel = new Label(content, SWT.NONE);
		fontSizeLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		fontSizeLabel.setText(resourceBundle.getString("generalSettingsFontSizeLabel"));
		fontSizeLabel.setFont(normalFont);
		
		fontSize = new Combo(content, SWT.READ_ONLY);
		fontSize.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		for (int i = 0; i < DefaultSettings.MAX_FONT_SIZE; i++) {
			String currentFontSize = new Integer(i + 1).toString();
			fontSize.add(currentFontSize);
			if ((i + 1) == Configuration.fontSize) {
				fontSize.select(i);
			}
		}
		
		Composite footer = new Composite(parent, SWT.NONE);
		footer.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		footer.setBounds(0, 0, 64, 64);
		footer.setLayout(new GridLayout(2, false));
		
		Button restoreDefault = new Button(footer, SWT.NONE);
		restoreDefault.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));
		restoreDefault.setText(resourceBundle.getString("generalSettingsRestoreDefaultButton"));
		restoreDefault.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(final Event event) {
				broker.post(Events.TOPIC_PREFERENCES_GENERAL_RESTORE_DEFAULT, null);
			}
		});

		Button apply = new Button(footer, SWT.NONE);
		apply.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		apply.setText(resourceBundle.getString("generalSettingsApplyButton"));
		apply.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(final Event event) {
				broker.post(Events.TOPIC_PREFERENCES_GENERAL_APPLY, null);
			}
		});
	}

	/**
	 * Persists all in this preference page managed settings.
	 * 
	 * @param o
	 *            Empty object.
	 * @param prefs
	 *            Reference to the preference store.
	 */
	@Inject
	@Optional
	private void apply(@UIEventTopic(Events.TOPIC_PREFERENCES_GENERAL_APPLY) final Object o,
			@Preference(nodePath = PreferencesHandler.preferenceStore) final IEclipsePreferences prefs) {
		
		prefs.put(PreferenceKey.LANGUAGE, this.language.getText());
		prefs.put(PreferenceKey.FONT_FAMILY, this.font.getText());
		prefs.putInt(PreferenceKey.FONT_SIZE, new Integer(this.fontSize.getText()));

		// Persists
		try {
			prefs.flush();
		} catch (BackingStoreException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Restores all in this preference page managed settings to default.
	 * 
	 * @param o
	 *            Empty Object.
	 * @param prefs
	 *            Reference to the preference store.
	 */
	@Inject
	@Optional
	private void restoreDefault(@UIEventTopic(Events.TOPIC_PREFERENCES_GENERAL_RESTORE_DEFAULT) final Object o,
			@Preference(nodePath = PreferencesHandler.preferenceStore) final IEclipsePreferences prefs) {
		
		int index = 0;
		for(String lang : this.language.getItems()) {
			if (lang.equals(DefaultSettings.DEFAULT_LANGUAGE)) {
				language.select(index);
				break;
			}
			index++;
		}
		
		index = 0;
		for (String font : this.font.getItems()) {
			if (font.equals(DefaultSettings.DEFAULT_FONT_FAMILY)) {
				this.font.select(index);
				break;
			}
			index++;
		}
		
		index = 0;
		for (String size : this.fontSize.getItems()) {
			int s = new Integer(size);
			if (s == DefaultSettings.DEFAULT_FONT_SIZE) {
				this.fontSize.select(index);
				break;
			}
			index++;
		}

		// persists default settings
		this.apply(null, prefs);
	}
}
