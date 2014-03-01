package ph.adoremus.formgenerator.builders;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ph.adoremus.log.Logger;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FormBuilder {

	private Logger logger = Logger.getInstance(this.getClass().getName());
	private JSONObject form;
//	private LinearLayout container;
	private final ArrayList<View> views;
	private Context context;
	
	
	public FormBuilder(Context context, JSONObject form) throws JSONException{
		this.context = context;
		this.form = form;
//		this.container = new LinearLayout(this.context);
//		this.container.setOrientation(LinearLayout.HORIZONTAL);
//		this.container.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		this.views = new ArrayList<View>();
		
		buildEditTexts(form.getJSONArray("editTexts"));
		buildFormButtons();
		
//		for (View v : views){
//			container.addView(v);
//		}
	}
	
	
	private void buildEditTexts(JSONArray etArray){
		try {
			for(int i=0; i<etArray.length(); i++){
				JSONObject et = etArray.getJSONObject(i);
				EditTextBuilder etb = new EditTextBuilder(context);
				etb.buildTitle(et.getString("title").hashCode(), et.getString("title"));
				etb.buildView(et.getString("id").hashCode(), et.getInt("attr"), et.getString("hint"));
				etb.buildContainer();
				
				this.views.add(etb.getContainer());
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void buildFormButtons(){
		ButtonBuilder bb = new ButtonBuilder(context);
		bb.buildView("submit".hashCode(), "Submit", new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				for (View view : views){
					if (view instanceof LinearLayout){
						LinearLayout ll = (LinearLayout) view;
						/*
						 * Checking if LinearLayout has more than 2 views.
						 * Based on the design, Input Views will have 2 views 
						 * 	(ie. : TextView and EditText)
						 * Buttons on the other hand doesn't have a TextView so
						 * 	their container's will only have 1 view
						 */
						if (ll.getChildCount() >=2){
							if (ll.getChildAt(1) instanceof EditText){
								EditText et = (EditText) ll.getChildAt(1);
								logger.debug("value " + et.getText());
							}
						}
					}
				}
			}
		});
		bb.buildContainer();
		this.views.add(bb.getContainer());
	}
	
//	public ViewGroup getFormContainer(){
//		
//		return container;
//	}
	
	public ArrayList<View> getViews() {
		return views;
	}
}
