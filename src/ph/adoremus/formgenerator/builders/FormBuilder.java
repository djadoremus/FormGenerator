package ph.adoremus.formgenerator.builders;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ph.adoremus.formgenerator.callback.FormCallback;
import ph.adoremus.formgenerator.serializable.JSONSerializable;
import ph.adoremus.log.Logger;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class FormBuilder {

	private Logger logger = Logger.getInstance(this.getClass().getName());
	protected JSONObject form;
	protected JSONObject response;
	protected Boolean isReadOnly;
//	private LinearLayout container;
	protected final ArrayList<View> views;
	protected Context context;
	protected Activity activity;
	
	public FormBuilder(Activity activity, JSONObject form, Boolean isReadOnly, JSONObject response) throws JSONException{
		this.activity = activity;
		this.context = activity.getApplicationContext();
		this.form = form;
		this.response = response;
		this.isReadOnly = isReadOnly;
//		this.container = new LinearLayout(this.context);
//		this.container.setOrientation(LinearLayout.HORIZONTAL);
//		this.container.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		this.views = new ArrayList<View>();
		
		buildEditTexts(form.getJSONArray("editTexts"));
		buildSpinners(form.getJSONArray("spinners"));
		buildDatePickers(form.getJSONArray("datepickers"));
		if (!isReadOnly){
			buildFormButtons();
		}
		
//		for (View v : views){
//			container.addView(v);
//		}
	}
	
	
	protected void buildEditTexts(JSONArray etArray){
		try {
			for(int i=0; i<etArray.length(); i++){
				JSONObject et = etArray.getJSONObject(i);
				String etVal = "";
				
				/*
				 * 1. Check if response is not null
				 * 2. check if response.id is not null
				 * 3. Set value of etb.buildView with value of response.id
				 */
				if (response != null){
					Iterator<String> itr = response.keys();
					while(itr.hasNext()){
						String v = itr.next();
						if (et.getString("title").equals(v)){
							etVal = response.getString(v);
							break;
						}
					}
				}
				
				EditTextBuilder etb = new EditTextBuilder(context, isReadOnly, etVal);
				etb.buildTitle(et.getString("title").hashCode(), et.getString("title"));
				etb.buildView(et.getString("id").hashCode(), et.getInt("attr"), et.getString("hint"), et.getInt("size"), et.has("format") ? et.getString("format") : null);
				etb.buildContainer();
				
				this.views.add(etb.getContainer());
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void buildSpinners(JSONArray spArray){
		try{
			for (int i=0; i<spArray.length(); i++){
				JSONObject sp = spArray.getJSONObject(i);
				String[] selections = (String[]) sp.get("selections");
				
				ArrayList<String> lSelections = new ArrayList<String>();
				for (int k=0; k<selections.length; k++){
					lSelections.add((String)selections[k]);
				}
				
				SpinnerBuilder sb = new SpinnerBuilder(context, lSelections);
				sb.buildTitle(sp.getString("title").hashCode(), sp.getString("title"));
				sb.buildView(sp.getString("id").hashCode());
				sb.buildContainer();
				
				this.views.add(sb.getContainer());
			}
		} catch (JSONException e){
			e.printStackTrace();
		}
	}
	
	protected void buildDatePickers(JSONArray dpArray){
		try{
			for (int i=0; i<dpArray.length(); i++){
				JSONObject dp = dpArray.getJSONObject(i);
				String dpVal = "";
				
				/*
				 * 1. Check if response is not null
				 * 2. check if response.id is not null
				 * 3. Set value of etb.buildView with value of response.id
				 */
				if (response != null){
					Iterator<String> itr = response.keys();
					while(itr.hasNext()){
						String v = itr.next();
						if (dp.getString("title").equals(v)){
							dpVal = response.getString(v);
							break;
						}
					}
				}
				
				DatePickerBuilder dpb = new DatePickerBuilder(activity, isReadOnly, dpVal);
				dpb.buildTitle(dp.getString("title").hashCode(), dp.getString("title"));
				dpb.buildView(dp.getString("id").hashCode(), dp.getString("format"));
				dpb.buildContainer();
				
				this.views.add(dpb.getContainer());
			}
		} catch (JSONException e){
			e.printStackTrace();
		}
	}
	
	protected void buildFormButtons(){
		ButtonBuilder bb = new ButtonBuilder(context);
		bb.buildView("submit".hashCode(), "Submit", new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try{
					JSONSerializable obj = new JSONSerializable();
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
									
									obj.put((String)et.getTag(), et.getText().toString());
								} else if (ll.getChildAt(1) instanceof Spinner){
									Spinner sp = (Spinner) ll.getChildAt(1);
									obj.put((String)sp.getTag(), sp.getSelectedItem());
								} else if (ll.getChildAt(1) instanceof TextView){
									TextView dp_tv = (TextView) ll.getChildAt(1);
									obj.put((String)dp_tv.getTag(), dp_tv.getText().toString());
								}
							}
						}
					}
					logger.debug("based form " + form.toString());
					logger.debug("result form" + obj.toString());
					((FormCallback)activity).call(obj);
				} catch (JSONException e){
					e.printStackTrace();
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
