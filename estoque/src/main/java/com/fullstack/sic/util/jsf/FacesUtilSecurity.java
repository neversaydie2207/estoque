package com.fullstack.sic.util.jsf;

import org.apache.commons.codec.binary.Base64;

public class FacesUtilSecurity
{
	public static String encodeBase64(String value)
	{
		return Base64.encodeBase64String(value.getBytes());
	}
	
	public static String decodeBase64(String value)
	{
		byte[] decoded = Base64.decodeBase64(value.getBytes());
		
		return new String(decoded);
		
	}

}
