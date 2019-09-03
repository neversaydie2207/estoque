package com.fullstack.sic.util.jsf;

public class MatriculaUtil
{
	public static String geraMatricula(String ultimaMatricula)
	{
		 /*String matricula = "9038409";*/
		/*String matricula = "9010109";*/
		String matricula = ultimaMatricula;
        
        String novaMatricula = ""; 
        
        String digito = "1";
        int quantidade = 1;
        int contador = 0;
        
            // retorna desde o primeiro digito até o sexto;
        String prefixoString = matricula.substring(0, 5);
    
            // retorna o último digito independente do tamanho da string
        String sufixoString = matricula.substring(matricula.length() - 1);
    
            // convertemos prefixo de string para int para poder incrementá-lo
            // caso sufixoString seja igual a "X"
        int prefixoInt = Integer.parseInt(prefixoString);
        
        while (contador < quantidade) 
        {
            contador ++;

            switch (sufixoString) 
            {
                case "9":
                    sufixoString = "X";
                    break;
                
                case "X":
                    sufixoString = "0";
                    prefixoInt += 1;
                    break;
                default:
                    // convertemos sufixoString para int para poder somá-lo com um
                    // e após a soma o transformamos de novo em string
                    int sufixoInt = Integer.parseInt(sufixoString);
                    sufixoString = String.valueOf(sufixoInt + 1);
                    break;
            }
                
            novaMatricula = prefixoInt + digito + "-" + sufixoString;
            
        }
        
		return novaMatricula;
	}
	
	public static String geraMatriculaSufixoX(String ultimaMatricula)
	{
		String matricula = ultimaMatricula;
        
        String novaMatricula = ""; 
        
        String digito = "1";
        int quantidade = 1;
        int contador = 0;
        
            // retorna desde o primeiro digito até o sexto;
        String prefixoString = matricula.substring(0, 5);
    
            // retorna o último digito independente do tamanho da string
        String sufixoString = "X";
    
            // convertemos prefixo de string para int para poder incrementá-lo
            // caso sufixoString seja igual a "X"
        int prefixoInt = Integer.parseInt(prefixoString);
        
        while (contador < quantidade) 
        {
            contador ++;
            prefixoInt +=1;
            
            /*
            switch (sufixoString) 
            {
                case "9":
                    sufixoString = "X";
                    break;
                
                case "X":
                    //sufixoString = "0";
                    prefixoInt += 1;
                    break;
                default:
                    // convertemos sufixoString para int para poder somá-lo com um
                    // e após a soma o transformamos de novo em string
                    int sufixoInt = Integer.parseInt(sufixoString);
                    sufixoString = String.valueOf(sufixoInt + 1);
                    break;
            }
            */
            
            novaMatricula = prefixoInt + digito + "-" + sufixoString;
            
        }
        
		return novaMatricula;
	}
}
