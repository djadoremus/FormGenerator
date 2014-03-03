package ph.adoremus.formgenerator.builders;

import ph.adoremus.log.Logger;
import android.R;
import android.content.Context;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class EditTextBuilder implements ViewBuilder{
	
	private Logger logger = Logger.getInstance(this.getClass().getName());
	
	private TextView tvTitle;
	private EditText view;
	private LinearLayout llContainer;
	private Boolean readOnly;
	private String value;
	
	public EditTextBuilder(Context context) {
		this.tvTitle = new TextView(context);
		this.view = new EditText(context);
		this.llContainer = new LinearLayout(context);
		this.readOnly = Boolean.FALSE;
		this.value = null;
	}
	
	public EditTextBuilder(Context context, Boolean readOnly, String value) {
		this.tvTitle = new TextView(context);
		this.view = new EditText(context);
		this.llContainer = new LinearLayout(context);
		this.readOnly = readOnly;
		this.value = value;
	}
	
	@Override
	public void buildTitle(Integer idHashCode, String title){
		tvTitle.setId(idHashCode);
		tvTitle.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		tvTitle.setText(title);
		
		view.setTag(title);
	}
	
	@Override
	public void buildView(Integer idHashCode) {
		view.setId(idHashCode);
		view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		view.setEnabled(!readOnly);
	}
	
	public void buildView(Integer idHashCode, Integer attr, String hint){
		buildView(idHashCode);
		view.setInputType(attr);
		view.setHint(hint);
		view.setText(value != null ? value : "");
	}
	
	public void buildView(Integer idHashCode, Integer attr, String hint, TextWatcher textWatcher){
		buildView(idHashCode, attr, hint);
		view.addTextChangedListener(textWatcher);
	}
	
	@Override
	public void buildContainer(){
		llContainer.setOrientation(LinearLayout.VERTICAL);
		llContainer.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		llContainer.addView(tvTitle);
		llContainer.addView(view);
	}
	
	@Override
	public ViewGroup getContainer(){
		return llContainer;
	}

	@Override
	public View getConcreteView() {
		return view;
	}

}
