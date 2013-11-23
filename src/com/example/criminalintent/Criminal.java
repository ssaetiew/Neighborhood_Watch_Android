package com.example.criminalintent;

import java.text.DateFormat;
import java.util.Date;
import java.util.UUID;


public class Criminal {
	private UUID mId;
	private String mTitle;
	private Date mDate;
	private boolean mSolved;
	String formattedString;
	public Criminal()
	{
		mId = UUID.randomUUID();//Generate unique ID
		//formattedString = DateFormat.getDateInstance().format(mDate);
		mDate = new Date();
	}

	public String getTitle() {
		return mTitle;
	}

	public void setTitle(String title) {
		mTitle = title;
	}

	public UUID getId() {
		return mId;
	}

	public Date getDate() {
		return mDate;
	}

	public void setDate(Date date) {
		mDate = date;
	}

	public boolean isSolved() {
		return mSolved;
	}

	public void setSolved(boolean solved) {
		mSolved = solved;
	}
	
	//We need to override toString so ArrayAdapter can display string of text not memory object
	@Override
	public String toString() {
		return mTitle;
	}
	
	
	
}
