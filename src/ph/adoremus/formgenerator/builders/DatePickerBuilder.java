package ph.adoremus.formgenerator.builders;

import java.util.ArrayList;

import ph.adoremus.formgenerator.fragments.DPFragment;
import ph.adoremus.log.Logger;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class DatePickerBuilder implements ViewBuilder, OnDateSetListener{
	
	private Logger logger = Logger.getInstance(this.getClass().getName());
	
	private TextView tvTitle;
	private TextView view;
	private LinearLayout llContainer;
	private Activity activity;
	private Context context;
	
	public DatePickerBuilder(Activity activity, Context context) {
		this.activity = activity;
		this.context = context;
		this.tvTitle = new TextView(context);
		this.view = new TextView(context);
		this.llContainer = new LinearLayout(context);
	}
	
	@Override
	public void buildTitle(Integer idHashCode, String title) {
		tvTitle.setId(idHashCode);
		tvTitle.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		tvTitle.setText(title);
		
		view.setHint("MM/dd/YYYY");
		view.setTag(title);
	}

	@Override
	public void buildView(final Integer idHashCode) {
		view.setId(idHashCode);
		view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		view.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(context, "Will call DatePickerDialog (deprecated)/DialogFragment here", Toast.LENGTH_LONG).show();
				DPFragment dpFragment = new DPFragment();
				dpFragment.setTextView(view);
				dpFragment.show(activity.getFragmentManager(), idHashCode.toString());
			}
		});
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

	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		this.view.setText(dayOfMonth + "/" + monthOfYear+1 + "/" + year);
	}

}
