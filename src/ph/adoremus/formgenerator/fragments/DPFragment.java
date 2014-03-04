package ph.adoremus.formgenerator.fragments;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;

public class DPFragment extends DialogFragment implements OnDateSetListener {

	private TextView view;
	private String format;
	private SimpleDateFormat sdfIntended;
	private SimpleDateFormat sdfDefault;

	public DPFragment(){
		sdfDefault = new SimpleDateFormat("MM/dd/yyyy");
	}
	
	public void setFormat(String format) {
		this.format = format;
		sdfIntended = new SimpleDateFormat(format);
	}

	public void setTextView(TextView view) {
		this.view = view;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the current date as the default date in the picker
		final Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);

		// Create a new instance of DatePickerDialog and return it
		return new DatePickerDialog(getActivity(), this, year, month, day);
	}

	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		try {
			String sDate = (monthOfYear + 1) + "/" + dayOfMonth + "/" + year;
			Date date = sdfDefault.parse(sDate);
			this.view.setText(sdfIntended.format(date));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
