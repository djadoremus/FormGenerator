package ph.adoremus.formgenerator.builders;

import ph.adoremus.log.Logger;
import android.content.Context;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ButtonBuilder implements ViewBuilder{
	private Logger logger = Logger.getInstance(this.getClass().getName());
	
	private Button view;
	private LinearLayout llContainer;
	
	public ButtonBuilder(Context context) {
		this.view = new Button(context);
		this.llContainer = new LinearLayout(context);
	}
	
	@Override
	public void buildTitle(Integer idHashCode, String title){
	}
	
	@Override
	public void buildView(Integer idHashCode) {
		view.setId(idHashCode);
		view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
	}
	
	public void buildView(Integer idHashCode, String name, View.OnClickListener listener){
		buildView(idHashCode);
		view.setText(name);
		view.setOnClickListener(listener);
	}
	
	@Override
	public void buildContainer(){
		llContainer.setOrientation(LinearLayout.VERTICAL);
		llContainer.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
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
