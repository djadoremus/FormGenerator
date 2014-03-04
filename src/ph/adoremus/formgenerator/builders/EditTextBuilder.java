package ph.adoremus.formgenerator.builders;

import ph.adoremus.formgenerator.formatter.TextFormatter;
import ph.adoremus.log.Logger;
import android.R;
import android.content.Context;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnFocusChangeListener;
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
	
	public void buildView(Integer idHashCode, Integer attr, String hint, Integer maxLength, final String format){
		buildView(idHashCode);
		view.setInputType(attr);
		view.setHint(hint);
		view.setText(value != null ? value : "");
		if (maxLength > 0){
			InputFilter[] filter = new InputFilter[1];
			filter[0] = new InputFilter.LengthFilter(maxLength);
			view.setFilters(filter);
		}
		view.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (!hasFocus){
					TextFormatter tf = new TextFormatter(format);
					view.setText(tf.format(((EditText) v).getText().toString()));
				}
			}
		});
	}
	
	public void buildView(Integer idHashCode, Integer attr, String hint, Integer maxLength, String format, TextWatcher textWatcher){
		buildView(idHashCode, attr, hint, maxLength, format);
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
