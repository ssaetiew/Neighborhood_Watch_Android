package com.example.criminalintent;

import java.util.ArrayList;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

public class CrimeListFragment extends ListFragment {

	private static final String TAG = "CrimeListFragment";
	private ArrayList<Criminal> mCrimes;
	private boolean mSubtitleVisible;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		//Retain rotation config
		setRetainInstance(true);
		mSubtitleVisible = false;
		setHasOptionsMenu(true);
		getActivity().setTitle(R.string.crimes_title);//Set title to be displayed on Activity's action bar
		
		//Get a list of criminal
		mCrimes = CrimeLab.get(getActivity()).getCrimes();
		
		//Create an ArrayAdapter to display appropriate list view on screen
		//ArrayAdapter<Criminal> adapter = new ArrayAdapter<Criminal>(getActivity(), android.R.layout.simple_list_item_1, mCrimes);//simple_list_item_1 is predefined layout from the resources provided by Android SDK
		//We comment above codes because we'll hook up our custom adapter instead
		CrimeAdapter adapter = new CrimeAdapter(mCrimes);
		
		setListAdapter(adapter);
		
	}
	
	//onResume will save the result from MainActivity to CrimeListFragment
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		((CrimeAdapter)getListAdapter()).notifyDataSetChanged();
	}
	
	

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		//getListAdapter() return the adapter that is set on the ListFragment's list view
		//Criminal c = (Criminal) (getListAdapter()).getItem(position); We comment this code because
		//we'll use CrimeAdapter
		Criminal c = ((CrimeAdapter)getListAdapter()).getItem(position);
		
		//Start an intent to get to the next activity
		//Intent i = new Intent(getActivity(), MainActivity.class);
		//Instead of the above code, below code will let ViewPager handle fragments
		Intent i = new Intent(getActivity(),CrimePagerActivity.class);
		i.putExtra(CrimeFragment.EXTRA_CRIME_ID, c.getId());
		startActivity(i);
	}
	
	//Set Menu items
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.fragment_crime_list, menu);
		
		//Make sure you're displaying the correct menu item title
		MenuItem showSubtitle = menu.findItem(R.id.menu_item_show_subtitle);
		if(mSubtitleVisible && showSubtitle != null)
		{
			showSubtitle.setTitle(R.string.hide_subtitle);
		}
	}
	
	

	@TargetApi(11)
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId())
		{
		case R.id.menu_item_new_crime:
			Criminal crime = new Criminal();
			CrimeLab.get(getActivity()).addCrime(crime);
			Intent i = new Intent(getActivity(), CrimePagerActivity.class);
			i.putExtra(CrimeFragment.EXTRA_CRIME_ID, crime.getId());
			startActivityForResult(i, 0);
			return true;
		case R.id.menu_item_show_subtitle:
			if(getActivity().getActionBar().getSubtitle() == null)
			{
			getActivity().getActionBar().setSubtitle(R.string.subtitle);
			item.setTitle(R.string.hide_subtitle);
			mSubtitleVisible = true;
			}
			else
			{
				getActivity().getActionBar().setSubtitle(null);
				item.setTitle(R.string.show_subtitle);
				mSubtitleVisible = false;
			}
			return true;
			default:
				return super.onOptionsItemSelected(item);

		}
	}

	//Check if subtitle should be shown when orientation changes
	@TargetApi(11)
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = super.onCreateView(inflater, container, savedInstanceState);
		
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
		{
			if(mSubtitleVisible)
			{
				getActivity().getActionBar().setSubtitle(R.string.subtitle);
			}
		}
		return v;
	}
	
	
	//Need to create inner class so our custom layout called list_item_crime can be displayed
	//Thus, new adapter that need to know about Criminal need to be made
	private class CrimeAdapter extends ArrayAdapter<Criminal>
	{
		public CrimeAdapter(ArrayList<Criminal> crimes)
		{
			super(getActivity(),0,crimes);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			//If we weren't using a view, inflate one
			if(convertView == null)
			{
				convertView = getActivity().getLayoutInflater()
						.inflate(R.layout.list_item_crime, null);
				
			}
			
			//Configure the view for this crime
			Criminal c = getItem(position);
			
			TextView titleTv = (TextView) convertView.findViewById(R.id.crime_list_item_tv);
			titleTv.setText(c.getTitle());
			TextView dateTv = (TextView) convertView.findViewById(R.id.crime_list_item_date_tv);
			dateTv.setText(c.getDate().toString());
			CheckBox solvedCb = (CheckBox) convertView.findViewById(R.id.crime_list_item_cb);
			solvedCb.setChecked(c.isSolved());
			
			return convertView;//Return the view object to ListView
		}
		
	}
	

}
