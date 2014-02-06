package com.example.criminalintent;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;


public class Criminal {
	private UUID mId;
	private String mTitle;
	private Date mDate;
	private boolean mSolved;
	String formattedString;
	
	//Properties that will be saved to JSON
	private static final String JSON_ID = "id";
	private static final String JSON_TITLE = "title";
	private static final String JSON_SOLVED = "soved";
	private static final String JSON_DATE = "date";
	
	public Criminal()
	{
		mId = UUID.randomUUID();//Generate unique ID
		//formattedString = DateFormat.getDateInstance().format(mDate);
		mDate = new Date();
		
	}

	//Load saved data
	public Criminal(JSONObject json) throws JSONException
	{
		mId = UUID.fromString(json.getString(JSON_ID));
		mTitle = json.getString(JSON_TITLE);
		mSolved = json.getBoolean(JSON_SOLVED);
		mDate = new Date(json.getLong(JSON_DATE));
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
	
	//Return JSON object
	public JSONObject toJSON() throws JSONException
	{
		JSONObject json = new JSONObject();
		json.put(JSON_ID, mId.toString());
		json.put(JSON_TITLE, mTitle);
		json.put(JSON_SOLVED, mSolved);
		json.put(JSON_DATE, mDate.getTime());
		
		return json;
	}
	
}
