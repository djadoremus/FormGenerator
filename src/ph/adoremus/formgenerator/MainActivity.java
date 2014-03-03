package ph.adoremus.formgenerator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ph.adoremus.formgenerator.builders.ButtonBuilder;
import ph.adoremus.formgenerator.builders.EditTextBuilder;
import ph.adoremus.formgenerator.builders.FormBuilder;
import ph.adoremus.formgenerator.builders.ViewBuilder;
import ph.adoremus.formgenerator.callback.FormCallback;
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
import android.widget.Toast;

public class MainActivity extends Activity implements FormCallback{

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
		JSONArray spArray = new JSONArray();
		JSONArray dpArray = new JSONArray();
		
		try {
			/*
			for (int i=0; i<3; i++){
				JSONObject et = new JSONObject();
				et.put("id", "lorem" + i);
				et.put("title", "ipsum" + i);
				et.put("attr", InputType.TYPE_CLASS_TEXT);
				et.put("hint", "dolor sit amet " + i);
				
				etArray.put(et);
			}
			
			for (int k=0; k<2; k++){
				JSONObject sp = new JSONObject();
				sp.put("id", "consectetur" + k);
				sp.put("title", "adipiscing" + k);
				sp.put("selections", new String[]{"elit", "Mauris", "vel", "vulputate", "ipsum"});
				
				spArray.put(sp);
			}
			
			for (int l=0; l<2; l++){
				JSONObject dp = new JSONObject();
				dp.put("id", "non" + l);
				dp.put("title", "eleifend" + l);
				
				dpArray.put(dp);
			}
			
			obj.put("editTexts", etArray);
			obj.put("spinners", spArray);
			obj.put("datepickers", dpArray);
			*/
			
			JSONObject username = new JSONObject();
			JSONObject birthdate = new JSONObject();
			JSONObject phonenumber = new JSONObject();
			JSONObject salary = new JSONObject();
			JSONObject picture = new JSONObject();
			
			username.put("id", "username");
			username.put("title", "Username");
			username.put("attr", InputType.TYPE_CLASS_TEXT);
			username.put("hint", "Type your username");
			
			birthdate.put("id", "birthdate");
			birthdate.put("title", "Birthday");
			
			phonenumber.put("id", "phonenumber");
			phonenumber.put("title", "Phone Number");
			phonenumber.put("attr", InputType.TYPE_CLASS_PHONE);
			phonenumber.put("hint", "Type your phone number");
			
			salary.put("id", "salary");
			salary.put("title", "Salary");
			salary.put("attr", InputType.TYPE_CLASS_NUMBER);
			salary.put("hint", "Type your salary");
			
			picture.put("id", "picture");
			picture.put("title", "Picture");
			picture.put("attr", InputType.TYPE_CLASS_TEXT);
			picture.put("hint", "Type your picture URL");
			
			etArray.put(username);
			etArray.put(phonenumber);
			etArray.put(salary);
			etArray.put(picture);
			dpArray.put(birthdate);
			
			obj.put("editTexts", etArray);
			obj.put("spinners", spArray);
			obj.put("datepickers", dpArray);
			
			FormBuilder fb = new FormBuilder(this, obj);
			
			LinearLayout rl = (LinearLayout) findViewById(R.id.activitymain_llContainer);
//			rl.addView(fb.getFormContainer());
			for (View v : fb.getViews()){
				rl.addView(v);
			}
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

	@Override
	public void call(JSONObject response) {
		Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
	}

}
