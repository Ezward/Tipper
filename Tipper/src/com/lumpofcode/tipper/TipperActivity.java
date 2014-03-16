package com.lumpofcode.tipper;

import java.util.Currency;
import java.util.Locale;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class TipperActivity extends Activity 
{
	public static final int	CUSTOMIZE_REQUEST_CODE	= 1;

	private TextView thisTxtCurrency;
	private EditText thisEdBillAmount;
	private TextView thisTxtVeryHappyTip;
	private TextView thisTxtHappyTip;
	private TextView thisTxtNeutralTip;
	private TextView thisTxtUnhappyTip;
	private Button thisBtnCustomize;
	private TipperModel thisTipperModel;
	private Locale thisLocale;
	private Currency thisCurrency;
	private String thisCurrencySymbol;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tipper);
		
		thisLocale = Locale.getDefault();
		thisCurrency = Currency.getInstance(thisLocale);
		thisCurrencySymbol = thisCurrency.getSymbol(thisLocale);
		
		// find our Views
		thisTxtCurrency = (TextView)findViewById(R.id.txtCurrency);
		thisEdBillAmount = (EditText)findViewById(R.id.edBillAmount);
		thisTxtVeryHappyTip = (TextView)findViewById(R.id.txtVeryHappyAmount);
		thisTxtHappyTip = (TextView)findViewById(R.id.txtHappyAmount);
		thisTxtNeutralTip = (TextView)findViewById(R.id.txtNeutralAmount);
		thisTxtUnhappyTip = (TextView)findViewById(R.id.txtUnhappyAmount);
		thisBtnCustomize = (Button)findViewById(R.id.btnCustomize);
		
		// new model with defaults
		thisTipperModel = new TipperModel();
		
		thisTxtCurrency.setText(thisCurrencySymbol);
		enforceHints();
		enforceTips();
		startListening();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private void enforceHints()
	{
		thisTxtVeryHappyTip.setHint(thisTipperModel.getVeryHappyPercent() + "%");
		thisTxtHappyTip.setHint(thisTipperModel.getHappyPercent() + "%");
		thisTxtNeutralTip.setHint(thisTipperModel.getNeutralPercent() + "%");
		thisTxtUnhappyTip.setHint(thisTipperModel.getUnhappyPercent() + "%");
	}
	private void enforceTips()
	{
		// calculate each tip and set the text view
		enforceOneTip(thisTxtVeryHappyTip, TipperCalculator.veryHappyTip(thisTipperModel));
		enforceOneTip(thisTxtHappyTip, TipperCalculator.happyTip(thisTipperModel));
		enforceOneTip(thisTxtNeutralTip, TipperCalculator.neutralTip(thisTipperModel));
		enforceOneTip(thisTxtUnhappyTip, TipperCalculator.unhappyTip(thisTipperModel));
	}
	
	private void enforceOneTip(final TextView theTipView, final String theTipText)
	{
		theTipView.setText(((null == theTipText) || theTipText.isEmpty()) ? "" : (thisCurrencySymbol + theTipText));
	}
	
	private void startListening()
	{
		thisEdBillAmount.addTextChangedListener(new TextWatcher()
		{
			@Override
			public final void afterTextChanged(final Editable s)
			{
				// we only watch our bill amount
				try
				{
					final String theText = s.toString();
					final Double theAmount = Double.valueOf(theText);
					
					// update the bill amount, the recalculate the tips
					thisTipperModel.setBillAmount(Double.isNaN(theAmount) ? "" : theText);

				}
				catch(NumberFormatException e)
				{
					thisTipperModel.setBillAmount("");
				}
				
				// put it into the view 
				enforceTips();
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after)
			{
				// do nothing
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count)
			{
				// do nothing
			}
			
		});
		
		thisBtnCustomize.setOnClickListener(new CustomizeOnClick());
	}
	
	/**
	 * Handler for click on the Customize Button
	 *
	 */
	private final class CustomizeOnClick implements OnClickListener
	{
		@Override
		public void onClick(View theView)
		{
			// start item edit EditItemActivity
			Intent i = new Intent(TipperActivity.this, CustomPercentActivity.class);
			i.putExtra(CustomPercentActivity.VERY_HAPPY_PERCENT, thisTipperModel.getVeryHappyPercent());
			i.putExtra(CustomPercentActivity.HAPPY_PERCENT, thisTipperModel.getHappyPercent());
			i.putExtra(CustomPercentActivity.NEUTRAL_PERCENT, thisTipperModel.getNeutralPercent());
			i.putExtra(CustomPercentActivity.UNHAPPY_PERCENT, thisTipperModel.getUnhappyPercent());

			startActivityForResult(i, CUSTOMIZE_REQUEST_CODE);

		}
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		// EDIT_ITEM_REQUEST_CODE is defined above
		if (resultCode == RESULT_OK && requestCode == CUSTOMIZE_REQUEST_CODE)
		{
			// Extract value from result extras
			final Bundle theCustomData = data.getExtras();
			thisTipperModel.setVeryHappyPercent(theCustomData.getString(CustomPercentActivity.VERY_HAPPY_PERCENT));
			thisTipperModel.setHappyPercent(theCustomData.getString(CustomPercentActivity.HAPPY_PERCENT));
			thisTipperModel.setNeutralPercent(theCustomData.getString(CustomPercentActivity.NEUTRAL_PERCENT));
			thisTipperModel.setUnhappyPercent(theCustomData.getString(CustomPercentActivity.UNHAPPY_PERCENT));
			enforceHints();
			enforceTips();
		}
	}

}
