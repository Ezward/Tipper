package com.lumpofcode.tipper;

import java.math.BigDecimal;

public final class TipperCalculator
{
	public static final String veryHappyTip(final TipperModel theTipperModel)
	{
		return calc(theTipperModel, theTipperModel.getVeryHappyPercent());
	}
	
	public static final String happyTip(final TipperModel theTipperModel)
	{
		return calc(theTipperModel, theTipperModel.getHappyPercent());
	}
	
	public static final String neutralTip(final TipperModel theTipperModel)
	{
		return calc(theTipperModel, theTipperModel.getNeutralPercent());
	}
	
	public static final String unhappyTip(final TipperModel theTipperModel)
	{
		return calc(theTipperModel, theTipperModel.getUnhappyPercent());
	}
	
	private static final String calc(final TipperModel theTipperModel, final String theTipPercent)
	{
		try
		{
			final BigDecimal theBillAmount = new BigDecimal(theTipperModel.getBillAmount());
			final BigDecimal theTipDecimal = new BigDecimal(theTipPercent);
			final BigDecimal theTipAmount = theBillAmount.multiply(theTipDecimal).scaleByPowerOfTen(-2).setScale(2, BigDecimal.ROUND_HALF_UP);
			return theTipAmount.toString();
		}
		catch(NumberFormatException e)
		{
			return "";
		}
	}
}
