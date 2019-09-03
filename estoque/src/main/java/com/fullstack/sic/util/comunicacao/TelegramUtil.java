package com.fullstack.sic.util.comunicacao;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;
import com.pengrad.telegrambot.request.SendMessage;

public class TelegramUtil
{

	private static final String TELEGRAMTOKEN = "202441341:AAHKqO-UZZa4N6Fnp0HUY5VLeNN1MPfVhN0";
	
	public static void enviarMensagem(String message, String[] contatos)
	{
		TelegramBot payperBot = TelegramBotAdapter.build(TELEGRAMTOKEN);
		
		for(int i = 0; i<contatos.length; i++)
		{
			payperBot.execute(new SendMessage(contatos[i], message));
		}
	
	}
	
	public static void enviarMensagemEquipeAdmin(String message)
	{
		TelegramBot payperBot = TelegramBotAdapter.build(TELEGRAMTOKEN);
		
		String[] contatos = {"98117598", "94223046"}; // Giordano , Lutiane, Fernando
		
		for(int i = 0; i<contatos.length; i++)
		{
			payperBot.execute(new SendMessage(contatos[i], message));
		}
	
	}

}