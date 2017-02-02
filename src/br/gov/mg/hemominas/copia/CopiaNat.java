package br.gov.mg.hemominas.copia;

import java.io.File;
import java.io.FileFilter;
import java.util.Date;

import br.gov.mg.hemominas.modelo.Unidade;
import br.gov.mg.hemominas.principal.Principal;

public class CopiaNat implements CopiaInterface {

	@Override
	public void copia(Unidade unidade, boolean substituirArquivosNoDestino) {
		System.out.println(" Iniciando cópia dos arquivos da NAT: "
				+ Principal.formatador.format(new Date()));
		System.out.println(" Substituir os arquivos do destino: "
				+ substituirArquivosNoDestino);

		File pasta = new File(Principal.SERVIDOR_HBH + "/" + unidade.getSigla()
				+ "NAT");
		if (pasta.exists()) {
			File[] listaDeArquivos = pasta.listFiles(new FileFilter() {
				@Override
				public boolean accept(File pathname) {
					if (pathname.getName().endsWith(".env")
							|| pathname.getName().endsWith(".ENV")) {
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
				destino = new File("//" + unidade.getIp() + "/e/INT_NAT/ENV/"
						+ origem.getName());

				new RealizaCopia(origem, destino, substituirArquivosNoDestino);
			}
		} else {
			System.out.println(" Diretório de origem " + pasta.getPath()
					+ " não existe!");
		}

		System.out.println(" Finalizando cópia dos arquivos da NAT: "
				+ Principal.formatador.format(new Date()) + "\n");
	}

}
