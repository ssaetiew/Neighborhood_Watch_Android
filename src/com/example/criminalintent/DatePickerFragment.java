/*This class will handle dialog box for date. We put dialog in DialogFragment
 * because FragmentManager can manage it as well as avoiding dialog box 
 * disappear when user change device orientation
 * 
 */
package com.example.criminalintent;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;

public class DatePickerFragment extends DialogFragment {

	public static final String EXTRA_DATE = "com.example.criminalintent.date";
	private Date mDate;
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		mDate = (Date)getArguments().getSerializable(EXTRA_DATE);
		
		//Create a Calendar object to get the year, month, and day
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(mDate);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		
		//Inflate dialog_date
		View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_date, null);
		
		DatePicker datePicker = (DatePicker) v.findViewById(R.id.dialog_dp);
		datePicker.init(year, month, day, new OnDateChangedListener() {
			
			@Override
			public void onDateChanged(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				// TODO Auto-generated method stub
				//Translate year, month, day into a Date object using GreorianCalendar
				mDate = new GregorianCalendar(year, monthOfYear, dayOfMonth).getTime();
				
				//Update argument to preserve selected value when screen is rotating
				getArguments().putSerializable(EXTRA_DATE, mDate);
			}
		});
		
		//Call, create, and set instance of AlertDialog.Builder class
		//Set Ok button at the right-most for HoneyComb and up but left-most below HoneyComb
		return new AlertDialog.Builder(getActivity()).setView(v).setTitle(R.string.date_picker_title)
				.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						sendResult(Activity.RESULT_OK);
					}
				}).create();
		
		
		
	}
	
	//Passing date between CrimeFragment and DatePickerFragment by using newInstance()
	public static DatePickerFragment newInstance(Date date)
	{
		Bundle args = new Bundle();
		args.putSerializable(EXTRA_DATE, date);
		
		DatePickerFragment fragment = new DatePickerFragment();
		fragment.setArguments(args);
		
		return fragment;
	}
	
	//Having two fragments using the same Activity, we can use Fragment.onActivityResult() to pass back data to target
	private void sendResult(int resultCode)
	{
		if(getTargetFragment() == null)
		{
			return;
		}
		Intent i = new Intent();
		i.putExtra(EXTRA_DATE, mDate);
		
		getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
		
		
	}

}
