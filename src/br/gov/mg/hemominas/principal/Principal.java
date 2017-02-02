package br.gov.mg.hemominas.principal;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import br.gov.mg.hemominas.DAO.CarregaBanco;
import br.gov.mg.hemominas.copia.Copia;
import br.gov.mg.hemominas.modelo.Unidade;

public class Principal {
	
	public static final String SERVIDOR_HBH = "//10.14.124.90/";
	public static final SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	public static final long INTERVALO_DE_EXECUCAO = 900000; //15 minutos = 900000 milisssegundos

	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		long diferenca;
		Date dataDeInicio = new Date();
		Date dataDeFim;
		
		try {
			//saida em arquivo - comentar linha para saida em tela
			System.setOut(new PrintStream(new File("LOG/interfaceamento" + dataDeInicio.getTime() + ".log")));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		formatador.setTimeZone(TimeZone.getTimeZone("Brazil/East"));
		
		try {
			ArrayList<Unidade> unidades = new CarregaBanco().carrega();
			System.out.println("Iniciando interfaceamento em " + formatador.format(dataDeInicio)+ "\n\n");
			
			for (Unidade unidade : unidades) {
				System.out.println("*****************************");
				System.out.println("Unidade: " + unidade.getSigla() + " - IP: " + unidade.getIp());
				new Copia().copiaExp(unidade, true);
				new Copia().copiaBolsa(unidade, true);
				new Copia().copiaImuno(unidade, false);
				new Copia().copiaNat(unidade, false);
				new Copia().copiaSorologia(unidade, false);
				System.out.println("");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			dataDeFim = new Date(System.currentTimeMillis());
			diferenca = dataDeFim.getTime() - dataDeInicio.getTime();
			SimpleDateFormat formataHora = new SimpleDateFormat("mm:ss");
			System.out.println("Interfaceamento finalizado em " + formatador.format(dataDeFim));
			System.out.println("\n\n\n***********************************************");
			System.out.println("*                 RESUMO                      *");
			System.out.println("*                                             *");
			System.out.println("*      INICIO: " + formatador.format(dataDeInicio) + "            *");
			System.out.println("*      FIM   : " + formatador.format(dataDeFim) + "            *");
			System.out.println("*      TEMPO : " + formataHora.format(diferenca) + "                          *");
			System.out.println("*                                             *");
			System.out.println("***********************************************");
		}
		
		/*
		 * SE A EXECUÇÃO TIVER DEMORADO 15 MINUTOS OU MAIS, EXECUTA NOVAMENTE IMEDIATAMENTE
		 * CASO NÃO TENHA DEMORADO 15 MINUTOS, AGUARDA O RESTANTE DO TEMPO PARA INICIAR NOVAMENTE.
		 */
		if(diferenca >= INTERVALO_DE_EXECUCAO){
			new Principal().main(null);
		}else{
			try {
				Thread.sleep(INTERVALO_DE_EXECUCAO - diferenca);
				new Principal().main(null);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		
	}
}
