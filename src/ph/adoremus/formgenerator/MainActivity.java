package ph.adoremus.formgenerator;

import ph.adoremus.formgenerator.builders.EditTextBuilder;
import ph.adoremus.formgenerator.builders.ViewBuilder;
import ph.adoremus.log.Logger;
import android.os.Bundle;
import android.app.Activity;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MainActivity extends Activity {

	private Logger logger = Logger.getInstance(MainActivity.class.getName());
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		EditTextBuilder vb01 = new EditTextBuilder(getApplicationContext());
		vb01.buildTitle("Sample Title 01".hashCode(), "Sample Title 01");
		vb01.buildView("sampletitle01".hashCode(), InputType.TYPE_CLASS_TEXT, "Set Text here");
		vb01.buildContainer();
		
		EditTextBuilder vb02 = new EditTextBuilder(getApplicationContext());
		vb02.buildTitle("Sample Title 02".hashCode(), "Sample Title 02");
		vb02.buildView("sampletitle02".hashCode(), InputType.TYPE_CLASS_NUMBER, "Set Number here");
		vb02.buildContainer();
		
		EditTextBuilder vb03 = new EditTextBuilder(getApplicationContext());
		vb03.buildTitle("Sample Title 03".hashCode(), "Sample Title 03");
		vb03.buildView("sampletitle03".hashCode(), InputType.TYPE_CLASS_DATETIME, "Set Date here");
		vb03.buildContainer();
		
		LinearLayout rl = (LinearLayout) findViewById(R.id.activitymain_llContainer);
		rl.addView(vb01.getContainer());
		rl.addView(vb02.getContainer());
		rl.addView(vb03.getContainer());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
