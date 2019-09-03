package com.fullstack.sic.security;

import java.io.Serializable;

import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCrypt;


@SuppressWarnings("deprecation")
public class Encoder implements PasswordEncoder, Serializable
{
	private static final long serialVersionUID = 1L;

	@Override
	public String encodePassword(String rawPass, Object salt) {
		/*System.out.println("Codificando password: "+rawPass);*/
		return BCrypt.hashpw(rawPass, BCrypt.gensalt());
	}

	@Override
	public boolean isPasswordValid(String encPass, String rawPass, Object salt) {
		//System.out.println("Validando password: "+encPass);
		return BCrypt.checkpw(rawPass, encPass);
	}
	
}