package ph.adoremus.formgenerator.builders;

import android.view.ViewGroup;

public interface ViewBuilder {

	/**
	 * Builds the title for the form<br/>
	 * @param idHashCode - used as the identifier of the TextView.
	 * @param title - Title to be used by the TextView.
	 */
	public void buildTitle(Integer idHashCode, String title);

	/**
	 * Builds the View of the Concrete Builder. You can overload this.<br/>
	 * @param idHashCode
	 */
	public void buildView(Integer idHashCode);
	
	/**
	 * Builds the container (LinearLayout) that will hold the TextView and View.<br/>
	 */
	public void buildContainer();

	/**
	 * Returns the ViewGroup (from buildContainer())<br/>
	 * @return
	 */
	public ViewGroup getContainer();
}
