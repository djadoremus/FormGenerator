package ph.adoremus.formgenerator.fragments;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;

public class DPFragment extends DialogFragment implements OnDateSetListener{
	
	private TextView view;
	
	public DPFragment() {
		// TODO Auto-generated constructor stub
	}
	
	public void setTextView(TextView view){
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
        return new DatePickerDialog(getActivity(), this, year, month, day);	}
	
	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		this.view.setText(dayOfMonth + "/" + monthOfYear+1 + "/" + year);
	}

}
