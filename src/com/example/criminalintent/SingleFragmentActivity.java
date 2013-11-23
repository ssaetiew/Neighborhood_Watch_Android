package com.example.criminalintent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

public abstract class SingleFragmentActivity extends FragmentActivity {

	protected abstract Fragment createFragment();

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_fragment);

		/*
		 * Make explicit call to FragmentManager so CrimeFragment can be managed
		 * Change getSupportFragmentManager() to getFragmentManager() if
		 * compatibility isn't a concern.
		 */
		FragmentManager fm = getSupportFragmentManager();
		Fragment fragment = fm.findFragmentById(R.id.fragmentContatiner);

		// If fragment is null, add fragment to FragmentManager's list
		if (fragment == null) {
			fragment = createFragment();
			fm.beginTransaction().add(R.id.fragmentContatiner, fragment)
					.commit();

		}
	}

}
