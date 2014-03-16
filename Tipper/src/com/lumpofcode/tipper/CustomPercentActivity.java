package com.lumpofcode.tipper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class CustomPercentActivity extends Activity
{
	public static final String VERY_HAPPY_PERCENT = "veryHappyPercent";
	public static final String HAPPY_PERCENT = "happyPercent";
	public static final String NEUTRAL_PERCENT = "neutralPercent";
	public static final String UNHAPPY_PERCENT = "unhappyPercent";
	
	private EditText thisEdVeryHappyTip;
	private EditText thisEdHappyTip;
	private EditText thisEdNeutralTip;
	private EditText thisEdUnhappyTip;
	private TipperModel thisTipperModel;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_custom_percent);
		
		thisEdVeryHappyTip = (EditText)findViewById(R.id.edVeryHappyCustom);
		thisEdHappyTip = (EditText)findViewById(R.id.edHappyCustom);
		thisEdNeutralTip = (EditText)findViewById(R.id.edNeutralCustom);
		thisEdUnhappyTip = (EditText)findViewById(R.id.edUnhappyCustom);
		
		// setup new model
		thisTipperModel = new TipperModel();
		thisTipperModel.setVeryHappyPercent(getIntent().getStringExtra(VERY_HAPPY_PERCENT));
		thisTipperModel.setHappyPercent(getIntent().getStringExtra(HAPPY_PERCENT));
		thisTipperModel.setNeutralPercent(getIntent().getStringExtra(NEUTRAL_PERCENT));
		thisTipperModel.setUnhappyPercent(getIntent().getStringExtra(UNHAPPY_PERCENT));
		
		enforceModel();
	}
	
	private void enforceModel()
	{
		thisEdVeryHappyTip.setText(thisTipperModel.getVeryHappyPercent());
		thisEdHappyTip.setText(thisTipperModel.getHappyPercent());
		thisEdNeutralTip.setText(thisTipperModel.getNeutralPercent());
		thisEdUnhappyTip.setText(thisTipperModel.getUnhappyPercent());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.custom_percent, menu);
		return true;
	}
	
	/**
	 * Determine if the view's value should be put into the model.
	 * 
	 * @param theView
	 * @param theDefault
	 * @return the value that should go into the model.
	 */
	private String updateModelFromView(final EditText theView, final String theDefault)
	{
		String theUpdatedValue = theDefault;
		try
		{
			final String theViewString = theView.getText().toString();
			final Double thePercent = Double.valueOf(theViewString);
			if(!Double.isInfinite(thePercent))
			{
				theUpdatedValue = theViewString;
			}

		}
		catch(NumberFormatException e)
		{
			// do nothing;
		}

		return theUpdatedValue;
	}
	public void onCustomizeOk(View v)
	{
		// update the model with values from the view
		thisTipperModel.setVeryHappyPercent(updateModelFromView(thisEdVeryHappyTip, thisTipperModel.getVeryHappyPercent()));
		thisTipperModel.setHappyPercent(updateModelFromView(thisEdHappyTip, thisTipperModel.getHappyPercent()));
		thisTipperModel.setNeutralPercent(updateModelFromView(thisEdNeutralTip, thisTipperModel.getNeutralPercent()));
		thisTipperModel.setUnhappyPercent(updateModelFromView(thisEdUnhappyTip, thisTipperModel.getUnhappyPercent()));
		
		// send back Ok result
		Intent i = new Intent();
		i.putExtra(VERY_HAPPY_PERCENT, thisTipperModel.getVeryHappyPercent());
		i.putExtra(HAPPY_PERCENT, thisTipperModel.getHappyPercent());
		i.putExtra(NEUTRAL_PERCENT, thisTipperModel.getNeutralPercent());
		i.putExtra(UNHAPPY_PERCENT, thisTipperModel.getUnhappyPercent());
		setResult(RESULT_OK, i); // set result code and bundle data for response
		finish(); // closes the activity, pass data to parent
	}
	public void onCustomizeCancel(View v)
	{
		// send back Cancel result
		setResult(android.app.Activity.RESULT_CANCELED); // set result code
		finish();
	}

}
