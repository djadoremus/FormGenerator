package ph.adoremus.formgenerator.builders;

import java.util.ArrayList;

import ph.adoremus.log.Logger;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class SpinnerBuilder implements ViewBuilder{
	
	private Logger logger = Logger.getInstance(this.getClass().getName());
	
	private TextView tvTitle;
	private Spinner view;
	private LinearLayout llContainer;
	private Context context;
	
	private ArrayList<String> selections;
	
	public SpinnerBuilder(Context context, ArrayList<String> selections) {
		this.context = context;
		this.tvTitle = new TextView(context);
		this.view = new Spinner(context);
		this.llContainer = new LinearLayout(context);
		this.selections = selections;
	}
	
	@Override
	public void buildTitle(Integer idHashCode, String title) {
		tvTitle.setId(idHashCode);
		tvTitle.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		tvTitle.setText(title);
		
		view.setTag(title);
	}

	@Override
	public void buildView(Integer idHashCode) {
		view.setId(idHashCode);
		view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, selections);
		view.setAdapter(adapter);
	}

	@Override
	public void buildContainer() {
		llContainer.setOrientation(LinearLayout.VERTICAL);
		llContainer.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		llContainer.addView(tvTitle);
		llContainer.addView(view);
	}

	@Override
	public ViewGroup getContainer() {
		return llContainer;
	}

	@Override
	public View getConcreteView() {
		return view;
	}

}
