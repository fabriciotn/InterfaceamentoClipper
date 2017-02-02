package br.gov.mg.hemominas.copia;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class RealizaCopia {
	
	private File origem;
	private File destino;

	public RealizaCopia(File origem, File destino, boolean substituirArquivosNoDestino) {
		this.origem = origem;
		this.destino = destino;
		
		if(destino.exists()){
			if(substituirArquivosNoDestino && origem.lastModified() != destino.lastModified()){
				copia();
			}
		}else {
			copia();
		}
	}
	
	private void copia(){
		System.out.print("    --> Arquivo " + origem + " para " + destino + "...");
		
		FileChannel origemChannel = null;
		FileChannel destinoChannel = null;
		try {
			origemChannel = new FileInputStream(origem).getChannel();
			destinoChannel = new FileOutputStream(destino).getChannel();
			origemChannel.transferTo(0, origemChannel.size(), destinoChannel);
		}catch(IOException e){
			System.out.println("ERRO");
		}
		finally {
			if (origemChannel != null && origemChannel.isOpen())
				try {
					origemChannel.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			if (destinoChannel != null && destinoChannel.isOpen())
				try {
					destinoChannel.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		destino.setLastModified(origem.lastModified());
		System.out.println("OK");
	}
}
