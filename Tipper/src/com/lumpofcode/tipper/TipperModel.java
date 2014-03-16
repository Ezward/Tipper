package com.lumpofcode.tipper;

public final class TipperModel
{
	public static final String DEFAULT_VERY_HAPPY_PERCENTAGE = "20";
	public static final String DEFAULT_HAPPY_PERCENTAGE = "15";
	public static final String DEFAULT_NEUTRAL_PERCENTAGE = "10";
	public static final String DEFAULT_UNHAPPY_PERCENTAGE = "5";
	
	private String veryHappyPercent = DEFAULT_VERY_HAPPY_PERCENTAGE;
	private String happyPercent = DEFAULT_HAPPY_PERCENTAGE;
	private String neutralPercent = DEFAULT_NEUTRAL_PERCENTAGE;
	private String unhappyPercent = DEFAULT_UNHAPPY_PERCENTAGE;
	private String billAmount = "";
	
	public String getBillAmount()
	{
		return billAmount;
	}
	public void setBillAmount(String billAmount)
	{
		this.billAmount = billAmount;
	}
	
	public String getVeryHappyPercent()
	{
		return veryHappyPercent;
	}
	public void setVeryHappyPercent(String veryHappyPercent)
	{
		this.veryHappyPercent = veryHappyPercent;
	}
	
	public String getHappyPercent()
	{
		return happyPercent;
	}
	public void setHappyPercent(String happyPercent)
	{
		this.happyPercent = happyPercent;
	}
	
	public String getNeutralPercent()
	{
		return neutralPercent;
	}
	public void setNeutralPercent(String neutralPercent)
	{
		this.neutralPercent = neutralPercent;
	}
	
	public String getUnhappyPercent()
	{
		return unhappyPercent;
	}
	public void setUnhappyPercent(String unhappyPercent)
	{
		this.unhappyPercent = unhappyPercent;
	}
	
	
}
