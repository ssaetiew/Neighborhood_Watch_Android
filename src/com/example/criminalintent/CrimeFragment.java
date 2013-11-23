/*This class is a fragment which handle, according to MVC architecture,
 * View and Model. Users can type title of the crime and view its details.
 */
package com.example.criminalintent;

import java.util.UUID;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;

public class CrimeFragment extends Fragment implements OnCheckedChangeListener{
		
		static final String EXTRA_CRIME_ID = "com.example.criminalintent.crime_id";
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
			
			
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			
			View v = inflater.inflate(R.layout.fragment_crime, container, false);//false because I will add the view in the activity's code not its parent(container).
			mTitle = (EditText) v.findViewById(R.id.crimeTitleEt);
			mDateBt = (Button) v.findViewById(R.id.crime_date_bt);
			mDateBt.setText(mCrime.getDate().toString());
			mDateBt.setEnabled(false);
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
		
		
}
