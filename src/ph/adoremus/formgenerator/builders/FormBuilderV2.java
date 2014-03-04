package ph.adoremus.formgenerator.builders;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ph.adoremus.formgenerator.callback.FormCallback;
import ph.adoremus.formgenerator.enums.Type;
import ph.adoremus.formgenerator.serializable.JSONSerializable;
import ph.adoremus.log.Logger;

import android.app.Activity;
import android.content.Context;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class FormBuilderV2 {

	private Logger logger = Logger.getInstance(this.getClass().getName());
	protected JSONObject form;
	protected JSONObject response;
	protected Boolean isReadOnly;
//	private LinearLayout container;
	protected final LinkedList<View> views;
	protected LinkedList<JSONObject> sorted;
	protected Context context;
	protected Activity activity;
	
	public FormBuilderV2(Activity activity, JSONObject form, Boolean isReadOnly, JSONObject response) throws JSONException{
		this.activity = activity;
		this.context = activity.getApplicationContext();
		this.form = form;
		this.response = response;
		this.isReadOnly = isReadOnly;
//		this.container = new LinearLayout(this.context);
//		this.container.setOrientation(LinearLayout.HORIZONTAL);
//		this.container.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		this.views = new LinkedList<View>();
		this.sorted = new LinkedList<JSONObject>();
		
		ArrayList<JSONObject> views = new ArrayList<JSONObject>();
		
		Iterator<String> keys = form.keys();
		logger.debug("form " + form);
		while(keys.hasNext()){
			String key = keys.next();
			if (!"form_name".equals(key)){
				JSONObject obj = form.getJSONObject(key);
				views.add(obj);
			}
		}
		JSONObject[] s = new JSONObject[views.size()];
		s = views.toArray(s);
		s = bubbleSort(s);
		for (int i=0; i<s.length; i++){
			logger.debug("obj " + s[i].getInt("sort"));
		}
		for (int i=0; i<s.length; i++){
			JSONObject obj = s[i];
			if (Type.TEXT == Type.getValue(obj.getString("type"))){
				obj.put("attr", InputType.TYPE_CLASS_TEXT);
				buildEditTexts(obj);
			} else if (Type.PHONE == Type.getValue(obj.getString("type"))){
				obj.put("attr", InputType.TYPE_CLASS_PHONE);
				buildEditTexts(obj);
			} else if (Type.NUMBER == Type.getValue(obj.getString("type"))){
				obj.put("attr", InputType.TYPE_CLASS_NUMBER);
						buildEditTexts(obj);
			} else if (Type.PASSWORD == Type.getValue(obj.getString("type"))){
				obj.put("attr", InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
				buildEditTexts(obj);
			} else if (Type.DATEPICKER == Type.getValue(obj.getString("type"))){
				buildDatePickers(obj);
			} else if (Type.RADIOBUTTONS == Type.getValue(obj.getString("type"))){
				logger.debug("radiobuttons to be added");
			} else if (Type.CHECKBOXES == Type.getValue(obj.getString("type"))){
				logger.debug("checkboxes to be added");
			} 
		}
		
		if (!isReadOnly){
			buildFormButtons();
		}
		
//		for (View v : views){
//			container.addView(v);
//		}
	}
	
	
	protected void buildEditTexts(JSONObject etObject){
		try {
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
					if (etObject.getString("title").equals(v)){
						etVal = response.getString(v);
						break;
					}
				}
			}
			
			EditTextBuilder etb = new EditTextBuilder(context, isReadOnly, etVal);
			etb.buildTitle(etObject.getString("title").hashCode(), etObject.getString("title"));
			etb.buildView(etObject.getString("id").hashCode(), etObject.getInt("attr"), etObject.getString("hint"), etObject.getInt("size"));
			etb.buildContainer();
			
			this.views.add(etb.getContainer());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void buildSpinners(JSONObject spObject){
		try{
			String[] selections = (String[]) spObject.get("selections");
			
			ArrayList<String> lSelections = new ArrayList<String>();
			for (int k=0; k<selections.length; k++){
				lSelections.add((String)selections[k]);
			}
			
			SpinnerBuilder sb = new SpinnerBuilder(context, lSelections);
			sb.buildTitle(spObject.getString("title").hashCode(), spObject.getString("title"));
			sb.buildView(spObject.getString("id").hashCode());
			sb.buildContainer();
			
			this.views.add(sb.getContainer());
		} catch (JSONException e){
			e.printStackTrace();
		}
	}
	
	protected void buildDatePickers(JSONObject dpObject){
		try{
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
					if (dpObject.getString("title").equals(v)){
						dpVal = response.getString(v);
						break;
					}
				}
			}
			
			DatePickerBuilder dpb = new DatePickerBuilder(activity, isReadOnly, dpVal);
			dpb.buildTitle(dpObject.getString("title").hashCode(), dpObject.getString("title"));
			dpb.buildView(dpObject.getString("id").hashCode());
			dpb.buildContainer();
			
			this.views.add(dpb.getContainer());
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
	
	public LinkedList<View> getViews() {
		return views;
	}
	
	private JSONObject[] bubbleSort(JSONObject[] arrSort) throws JSONException{
		Boolean flag = Boolean.TRUE;
		JSONObject temp;
		while(flag){
			flag = Boolean.FALSE;
			for (int i=0; i<arrSort.length-1; i++){
				if (arrSort[i].getInt("sort") > arrSort[i+1].getInt("sort")){
					temp = arrSort[i];
					arrSort[i] = arrSort[i+1];
					arrSort[i+1] = temp;
					flag = Boolean.TRUE;
				}
			}
		}
		
		return arrSort;
	}
}
