package ph.adoremus.formgenerator.builders;

import ph.adoremus.log.Logger;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CheckBoxBuilder implements ViewBuilder{

	private Logger logger = Logger.getInstance(CheckBoxBuilder.class.getName());
	
	private Context context;
	
	private TextView tvTitle;
	//place view here
	private LinearLayout llContainer;
	
	
	public CheckBoxBuilder(Context context){
		this.context = context;
		this.tvTitle = new TextView(context);
		
		this.llContainer = new LinearLayout(context);
	}
	
	@Override
	public void buildTitle(Integer idHashCode, String title) {
		tvTitle.setId(idHashCode);
		tvTitle.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		tvTitle.setText(title);
		
	}

	@Override
	public void buildView(Integer idHashCode) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void buildContainer(){
		llContainer.setOrientation(LinearLayout.VERTICAL);
		llContainer.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		llContainer.addView(tvTitle);
//		llContainer.addView(view);
	}
	
	@Override
	public ViewGroup getContainer(){
		return llContainer;
	}

	@Override
	public View getConcreteView() {
//		return view;
		return null;
	}

}
