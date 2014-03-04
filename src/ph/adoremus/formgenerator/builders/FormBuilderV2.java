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
		while(keys.hasNext()){
			String key = keys.next();
			JSONObject obj = form.getJSONObject(key);
//			logger.debug("obj " + obj);
			if (Type.TEXT == Type.getValue(obj.getString("type"))){
				obj.put("attr", InputType.TYPE_CLASS_TEXT);
				buildEditTexts(obj);
			} else if (Type.PHONE == Type.getValue(obj.getString("type"))){
				obj.put("attr", InputType.TYPE_CLASS_PHONE);
				buildEditTexts(obj);
			} else if (Type.NUMBER == Type.getValue(obj.getString("type"))){
				obj.put("attr", InputType.TYPE_CLASS_NUMBER);
				buildEditTexts(obj);
			} else if (Type.DATEPICKER == Type.getValue(obj.getString("type"))){
				buildDatePickers(obj);
			} else if (Type.RADIOBUTTONS == Type.getValue(obj.getString("type"))){
				logger.debug("radiobuttons to be added");
			} else if (Type.CHECKBOXES == Type.getValue(obj.getString("type"))){
				logger.debug("checkboxes to be added");
			} 
			views.add(obj);
		}
//		JSONObject[] s = bubbleSort(views);
//		for (int i=0; i<s.length; i++){
//			JSONObject obj = s[i];
//			if (Type.TEXT == Type.getValue(obj.getString("type"))){
//				obj.put("attr", InputType.TYPE_CLASS_TEXT);
//				buildEditTexts(obj);
//			} else if (Type.PHONE == Type.getValue(obj.getString("type"))){
//				obj.put("attr", InputType.TYPE_CLASS_PHONE);
//				buildEditTexts(obj);
//			} else if (Type.NUMBER == Type.getValue(obj.getString("type"))){
//				obj.put("attr", InputType.TYPE_CLASS_NUMBER);
//				buildEditTexts(obj);
//			} else if (Type.DATEPICKER == Type.getValue(obj.getString("type"))){
//				buildDatePickers(obj);
//			} else if (Type.RADIOBUTTONS == Type.getValue(obj.getString("type"))){
//				logger.debug("radiobuttons to be added");
//			} else if (Type.CHECKBOXES == Type.getValue(obj.getString("type"))){
//				logger.debug("checkboxes to be added");
//			} 
//		}
		
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
			etb.buildView(etObject.getString("id").hashCode(), etObject.getInt("attr"), etObject.getString("hint"));
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
	
	private JSONObject[] bubbleSort(ArrayList<JSONObject> sort) throws JSONException{
//		JSONObject[] arrSort = new JSONObject[sort.size()];
//		for (int i=0; i<arrSort.length; i++){
//			arrSort[i] = sort.get(i);
//		}
//		for (int i=0; i<sort.size(); i++){
//			JSONObject left = sort.get(i);
//			for (int k=i; k<sort.size(); k++){
//				JSONObject right = sort.get(k);
//				if (left.getInt("sort") > right.getInt("sort")){
//					arrSort[i] = right;
//				} else {
//					arrSort[i] = left;
//				}
//				logger.debug("arrSort[" + i + "]" + arrSort[i].getInt("sort"));
//			}
//		}
//		
		
		JSONObject[] arrSort = new JSONObject[sort.size()];
		int j;
		boolean flag = true;   // set flag to true to begin first pass
		JSONObject temp;   //holding variable
		
		while ( flag ){
			flag= false;    //set flag to false awaiting a possible swap
			for( j=0;  j < sort.size() -1;  j++ ){
				JSONObject left = sort.get(j);
				JSONObject right = sort.get(j+1);
				// change to > for ascending sort 
				logger.debug(left.getInt("sort") + "<" + right.getInt("sort"));
				if ( left.getInt("sort") < right.getInt("sort") ){
					temp = sort.get(j);                //swap elements
					arrSort[j] = sort.get(j+1);
					arrSort[j+1] = temp;
					flag = true;              //shows a swap occurred  
				} 
			} 
		} 
		return arrSort;
	}
}
