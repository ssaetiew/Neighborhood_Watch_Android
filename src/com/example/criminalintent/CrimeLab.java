package com.example.criminalintent;

import java.util.ArrayList;
import java.util.UUID;

import android.content.Context;

public class CrimeLab {
	//CrimeLab is a singleton class because we want it to be unchanged
	//no matter if activity or fragment life cycle is changed as long as
	//this class is still in memory
private static CrimeLab sCrimeLab;
private Context mAppContext;
private ArrayList<Criminal> mCrimes;
private CrimeLab(Context context)
{
	mAppContext = context;
	mCrimes = new ArrayList<Criminal>();
	
	//Generate sample crimes for testing
	for(int i = 1; i<= 100;i++)
	{
		
		Criminal c = new Criminal();
		c.setTitle("Crime #" + i);
		c.setSolved(i%2 == 0); //Every other one
		mCrimes.add(c);
	}
}
public static CrimeLab get(Context c)
{
	if(sCrimeLab == null)
	{
		sCrimeLab = new CrimeLab(c.getApplicationContext());
	}
	return sCrimeLab;
}
public ArrayList<Criminal> getCrimes()
{
	return mCrimes;
}
public Criminal getCrime(UUID id)
{
	for(Criminal cm : mCrimes)
	{
		if(cm.getId().equals(id))
		{
			return cm;
		}
	}
	return null;
}
}
