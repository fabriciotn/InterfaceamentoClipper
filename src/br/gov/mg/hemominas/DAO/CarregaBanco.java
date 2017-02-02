package br.gov.mg.hemominas.DAO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

import br.gov.mg.hemominas.modelo.Unidade;

public class CarregaBanco {

	public ArrayList<Unidade> carrega() throws IOException {
		ArrayList<Unidade> unidades = new ArrayList<Unidade>();
		File arquivo = new File("configuracao.bin");
        FileInputStream fis = new FileInputStream(arquivo);
        @SuppressWarnings("resource")
		BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
        String line = "";
        while((line = reader.readLine()) != null) {
            StringTokenizer token = new StringTokenizer(line, ";");
            while (token.hasMoreElements()) {
            	Unidade unidade = new Unidade();
            	unidade.setNome(token.nextToken());
                unidade.setSigla(token.nextToken());
                unidade.setIp(token.nextToken());
                unidades.add(unidade);
                break;
            }
        }
        
        return unidades;
	}
}
