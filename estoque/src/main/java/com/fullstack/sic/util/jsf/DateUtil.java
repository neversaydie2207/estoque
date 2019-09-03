package com.fullstack.sic.util.jsf;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil
{
	public static String formataData(Date data)
	{
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		
		return df.format(data); 
	}
	
	public static String formataDataEEE(Date data)
	{
		SimpleDateFormat df = new SimpleDateFormat("EEE dd/MM/yyyy");
		
		return df.format(data); 
	}
	
	
	public static String formataHora(Date hora)
	{
		SimpleDateFormat df = new SimpleDateFormat("HH:mm");
		
		return df.format(hora); 
	}
	
	public static Date formataDataHora(Date data, String hora ) throws ParseException
	{
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		return df.parse(formataData(data).toString() + " " + hora); 
	}
	
	
	public static Date parseStringToDate(String data) throws ParseException
	{
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		
		return (Date) df.parse(data);
	}
	
	public static java.sql.Date ultimoDiaMes(int mes)
	{
		Calendar instance = Calendar.getInstance();
		instance.set(Calendar.MONTH, mes);
		instance.set(Calendar.DAY_OF_MONTH, instance.getActualMaximum(Calendar.DAY_OF_MONTH));
		
		java.sql.Date dataSQL = new java.sql.Date(instance.getTime().getTime());
		
		System.out.println("data ultimo: " + dataSQL);
		
		return dataSQL;
	}
	
	public static java.sql.Date primeiroDiaMes(int mes)
	{
		Calendar instance = Calendar.getInstance();
		instance.set(Calendar.MONTH, mes);
		instance.set(Calendar.DAY_OF_MONTH, instance.getActualMinimum(Calendar.DAY_OF_MONTH));
		
		java.sql.Date dataSQL = new java.sql.Date(instance.getTime().getTime());
		
		System.out.println("data primeiro: " + dataSQL);
		
		return dataSQL;
		
		//return instance.getTime();
	}
}
