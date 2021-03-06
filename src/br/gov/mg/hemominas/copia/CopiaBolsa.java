package br.gov.mg.hemominas.copia;

import java.io.File;
import java.io.FileFilter;
import java.util.Date;

import br.gov.mg.hemominas.modelo.Unidade;
import br.gov.mg.hemominas.principal.Principal;

public class CopiaBolsa implements CopiaInterface {

	@Override
	public void copia(Unidade unidade, boolean substituirArquivosNoDestino) {
		System.out.println(" Iniciando c�pia dos arquivos de BOLSAS: "
				+ Principal.formatador.format(new Date()));
		System.out.println(" Substituir os arquivos do destino: "
				+ substituirArquivosNoDestino);

		File pasta = new File("//" + unidade.getIp() + "/ENVIO");
		if (pasta.exists()) {
			File[] listaDeArquivos = pasta.listFiles(new FileFilter() {
				@Override
				public boolean accept(File pathname) {
					if (pathname.getName().endsWith(".txt")
							|| pathname.getName().endsWith(".TXT")) {
						return true;
					} else
						return false;
				}
			});

			File origem;
			File destino;

			if (listaDeArquivos == null) {
				return;
			}

			for (File file : listaDeArquivos) {
				origem = new File(file.getAbsolutePath());
				destino = new File(Principal.SERVIDOR_HBH + unidade.getSigla()
						+ "req/" + file.getName());

				new RealizaCopia(origem, destino, substituirArquivosNoDestino);
			}
		} else {
			System.out.println(" Diret�rio de origem " + pasta.getPath()
					+ " n�o existe!");
		}

		System.out.println(" Finalizando c�pia dos arquivos de BOLSAS: "
				+ Principal.formatador.format(new Date()) + "\n");
	}

}
