package com.fullstack.sic.controller;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class UtilBean implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	public String formataData(Date data)
	{
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		
		return df.format(data); 
	}

}
