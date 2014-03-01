package ph.adoremus.formgenerator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ph.adoremus.formgenerator.builders.ButtonBuilder;
import ph.adoremus.formgenerator.builders.EditTextBuilder;
import ph.adoremus.formgenerator.builders.FormBuilder;
import ph.adoremus.formgenerator.builders.ViewBuilder;
import ph.adoremus.log.Logger;
import android.os.Bundle;
import android.app.Activity;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MainActivity extends Activity {

	private Logger logger = Logger.getInstance(MainActivity.class.getName());
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		/*
		final EditTextBuilder vb01 = new EditTextBuilder(getApplicationContext());
		vb01.buildTitle("Sample Title 01".hashCode(), "Sample Title 01");
		vb01.buildView("sampletitle01".hashCode(), InputType.TYPE_CLASS_TEXT, "Set Text here");
		vb01.buildContainer();
		
		final EditTextBuilder vb02 = new EditTextBuilder(getApplicationContext());
		vb02.buildTitle("Sample Title 02".hashCode(), "Sample Title 02");
		vb02.buildView("sampletitle02".hashCode(), InputType.TYPE_CLASS_NUMBER, "Set Number here");
		vb02.buildContainer();
		
		final EditTextBuilder vb03 = new EditTextBuilder(getApplicationContext());
		vb03.buildTitle("Sample Title 03".hashCode(), "Sample Title 03");
		vb03.buildView("sampletitle03".hashCode(), InputType.TYPE_CLASS_DATETIME, "Set Date here");
		vb03.buildContainer();
		
		ButtonBuilder bb = new ButtonBuilder(getApplicationContext());
		bb.buildView("submit".hashCode(), "Submit", new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				EditText text = (EditText) vb01.getConcreteView();
				EditText num  = (EditText) vb02.getConcreteView();
				EditText dat = (EditText) vb03.getConcreteView();
				
				logger.debug("text " + text.getText().toString());
				logger.debug("num " + num.getText().toString());
				logger.debug("dat " + dat.getText().toString());
			}
		});
		bb.buildContainer();
		
		LinearLayout rl = (LinearLayout) findViewById(R.id.activitymain_llContainer);
		rl.addView(vb01.getContainer());
		rl.addView(vb02.getContainer());
		rl.addView(vb03.getContainer());
		rl.addView(bb.getContainer());
		
		logger.debug("rl count " + rl.getChildCount());
		*/
		
		JSONObject obj = new JSONObject();
		JSONArray etArray = new JSONArray();
		
		try {
			for (int i=0; i<3; i++){
				JSONObject et = new JSONObject();
				et.put("id", "lorem" + i);
				et.put("title", "ipsum" + i);
				et.put("attr", InputType.TYPE_CLASS_TEXT);
				et.put("hint", "dolor sit amet " + i);
				
				etArray.put(et);
			}
			
			obj.put("editTexts", etArray);
			
			FormBuilder fb = new FormBuilder(getApplicationContext(), obj);
			
			LinearLayout rl = (LinearLayout) findViewById(R.id.activitymain_llContainer);
//			rl.addView(fb.getFormContainer());
			for (View v : fb.getViews()){
				rl.addView(v);
			}
			
			logger.debug("rl count " + rl.getChildCount());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
