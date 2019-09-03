package com.fullstack.sic.util.report;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.hibernate.jdbc.Work;

import com.lowagie.text.pdf.PdfWriter;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;

public class ExecutorRelatorio implements Work
{

	private String caminhoRelatorio;
	private HttpServletResponse response;
	private Map<String, Object> parametros;
	private String nomeArquivoSaida;
	private boolean relatorioGerado;
	private boolean impressaoDireta;
	private boolean impressaoSegura;

	public ExecutorRelatorio(String caminhoRelatorio, HttpServletResponse response, Map<String, Object> parametros,
			String nomeArquivoSaida, boolean impressaoDireta, boolean impressaoSegura)
	{
		this.caminhoRelatorio = caminhoRelatorio;
		this.response = response;
		this.parametros = parametros;
		this.nomeArquivoSaida = nomeArquivoSaida;
		this.impressaoDireta = impressaoDireta;
		this.impressaoSegura = impressaoSegura;
		
		this.parametros.put(JRParameter.REPORT_LOCALE, new Locale("pt", "BR"));
	}

	@Override
	public void execute(Connection connection) throws SQLException
	{
		InputStream relatorioStream = this.getClass().getResourceAsStream(this.caminhoRelatorio);
		
		try
		{
			JasperPrint print = JasperFillManager.fillReport(relatorioStream, this.parametros, connection);
			
			this.relatorioGerado = print.getPages().size() > 0;
			
			//IMPRIME DIRETO PARA IMPRESSORA
			//JasperPrintManager.printPage(print, 0, false);
			
			
			if(this.relatorioGerado)
			{
				if(impressaoDireta == true)
				{
					JasperPrintManager.printReport(print, false);
				}
				else
				{
					response.setContentType("application/pdf");
					response.setHeader("Content-Disposition", "attachment; filename=\"" 
							+ this.nomeArquivoSaida +".pdf"  + "\"");
					
					/* RETIRADO FORMA 1 RESUMADA
					 
					 JasperExportManager.exportReportToPdfStream(print, response.getOutputStream());
					
					*/
					
					
					// COLOCADO FORMA 2 DETALHADA
					JRPdfExporter exporter = new JRPdfExporter();
					   exporter.setExporterInput(new SimpleExporterInput(print));
					   exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
					   
					   SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
					   
					   if(impressaoSegura)
					   {
						   /* COM SENHA */
						   configuration.setEncrypted(true);
						   configuration.set128BitKey(true);
						   configuration.setUserPassword("jasper");
						   configuration.setOwnerPassword("@2019#SecSaude1");
						   configuration.setPermissions(PdfWriter.ALLOW_COPY | PdfWriter.ALLOW_PRINTING);
					   }
					   
					   
					   exporter.setConfiguration(configuration);

					   exporter.exportReport();
				}
			
			
				
			}
			else
			{
				System.out.println("Relatório não gerado");
			}
			
			
		} 
		catch (JRException e)
		{
			throw new SQLException("Erro ao executar relatório " + this.caminhoRelatorio, e);
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public String getCaminhoRelatorio()
	{
		return caminhoRelatorio;
	}

	public void setCaminhoRelatorio(String caminhoRelatorio)
	{
		this.caminhoRelatorio = caminhoRelatorio;
	}

	public HttpServletResponse getResponse()
	{
		return response;
	}

	public void setResponse(HttpServletResponse response)
	{
		this.response = response;
	}

	public Map<String, Object> getParametros()
	{
		return parametros;
	}

	public void setParametros(Map<String, Object> parametro)
	{
		this.parametros = parametro;
	}

	public String getNomeArquivoSaida()
	{
		return nomeArquivoSaida;
	}

	public void setNomeArquivoSaida(String nomeArquivoSaida)
	{
		this.nomeArquivoSaida = nomeArquivoSaida;
	}

	public boolean isRelatorioGerado()
	{
		return relatorioGerado;
	}

	public void setRelatorioGerado(boolean relatorioGerado)
	{
		this.relatorioGerado = relatorioGerado;
	}

}
