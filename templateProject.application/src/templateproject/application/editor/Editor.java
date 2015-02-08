package templateproject.application.editor;

/**
 * Every editor needs to implement this interface.
 * 
 * @author zwickl
 */
public interface Editor {

	/**
	 * Returns the input of this {@link Editor}.
	 * 
	 * @return the input of this {@link Editor}.
	 */
	Object getEditorInput();

	/**
	 * Returns the label of this {@link Editor}.
	 * 
	 * @return the label of this {@link Editor}.
	 */
	String getLabel();
}
