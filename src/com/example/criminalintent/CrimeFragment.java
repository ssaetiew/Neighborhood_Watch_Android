/*This class is a fragment which handle, according to MVC architecture,
 * View and Model. Users can type title of the crime and view its details.
 */
package com.example.criminalintent;

import java.util.Date;
import java.util.UUID;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;

public class CrimeFragment extends Fragment implements OnCheckedChangeListener{
		
		 static final String EXTRA_CRIME_ID = "com.example.criminalintent.crime_id";
		private static final String DIALOG_DATE = "date"; 
		private static final int REQUEST_DATE = 0;//Code for getting date from DatePickerFragment
		private Criminal mCrime;
		private EditText mTitle;
		private Button mDateBt;
		private CheckBox mSolvedCb;
		@Override
		public void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			//UUID crimeId = (UUID) getActivity().getIntent().getSerializableExtra(EXTRA_CRIME_ID); we'll replace this code below because we want fragment to be independent of CrimeActivity
			UUID crimeId = (UUID) getArguments().getSerializable(EXTRA_CRIME_ID);
			mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);
			
			//CrimeFragment will be implementing options menu callbacks on behalf of Activity
			
			setHasOptionsMenu(true); 
			
		}
		@TargetApi(11)
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			
			View v = inflater.inflate(R.layout.fragment_crime, container, false);//false because I will add the view in the activity's code not its parent(container).
			mTitle = (EditText) v.findViewById(R.id.crimeTitleEt);
			mDateBt = (Button) v.findViewById(R.id.crime_date_bt);
			//mDateBt.setText(mCrime.getDate().toString());
			updateDate();
			
			//Enable Up button to work on logo available on Android API >= 11
			if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
			{
				if(NavUtils.getParentActivityName(getActivity()) != null)
				{
				getActivity().getActionBar().setDisplayHomeAsUpEnabled(true); //don't set caret arrow if there's no parent
				}
			}
			
			//Change to this code if we don't want date to be picked mDateBt.setEnabled(false);
			mDateBt.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					FragmentManager fm = getActivity().getSupportFragmentManager();
					
					//DatePickerFragment dialog = new DatePickerFragment();
					//Instead of the above code, below code is used to get argument 
					DatePickerFragment dialog = DatePickerFragment.newInstance(mCrime.getDate());
					
					//Setup setTargetFragment so we can get date back (Similar to startActivityForResult() but 
					//setTargetFragment is between fragments)
					dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
					dialog.show(fm, DIALOG_DATE);
				}
			});
			
			mSolvedCb = (CheckBox) v.findViewById(R.id.crime_solved);
			mTitle.setText(mCrime.getTitle());
			mTitle.addTextChangedListener(new TextWatcher() {
				
				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {
					// TODO Auto-generated method stub
					mCrime.setTitle(s.toString());
				}
				
				@Override
				public void beforeTextChanged(CharSequence s, int start, int count,
						int after) {
					// TODO Auto-generated method stub
					//Intentionally left blank
				}
				
				@Override
				public void afterTextChanged(Editable s) {
					// TODO Auto-generated method stub
					//Intentionally left blank
				}
			});
			
			mSolvedCb.setOnCheckedChangeListener(this);
			mSolvedCb.setChecked(mCrime.isSolved());
			return v;
		}

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			// TODO Auto-generated method stub
			mCrime.setSolved(isChecked);

		}
		public static CrimeFragment newInstance(UUID crimeId)
		{
			Bundle args = new Bundle();
			args.putSerializable(EXTRA_CRIME_ID, crimeId);
			
			CrimeFragment fragment = new CrimeFragment();
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public void onActivityResult(int requestCode, int resultCode,
				Intent data) {
			// TODO Auto-generated method stub
			if(resultCode != Activity.RESULT_OK) return;
			if(requestCode == REQUEST_DATE)
			{
				Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
				mCrime.setDate(date);
				updateDate();
			}
		}
		
		public void updateDate()
		{
			mDateBt.setText(mCrime.getDate().toString());
		}
		
		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			// TODO Auto-generated method stub
			switch(item.getItemId())
			{
			case android.R.id.home:
				if(NavUtils.getParentActivityName(getActivity()) != null)
				{
					//Navigate to parent
					NavUtils.navigateUpFromSameTask(getActivity());
			
				}
				return true;
				default:
					return super.onOptionsItemSelected(item);

			}
		}
		
		//Save data every onPause()
		@Override
		public void onPause() {
			// TODO Auto-generated method stub
			super.onPause();
			CrimeLab.get(getActivity()).saveCrimes();
		}
		
		
		
		
		
}
