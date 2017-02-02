package br.gov.mg.hemominas.copia;

import br.gov.mg.hemominas.modelo.Unidade;

public class Copia{
	
	public void copiaExp(Unidade unidade, boolean substituirArquivosNoDestino){
		new CopiaExp().copia(unidade, substituirArquivosNoDestino);
	}
	
	public void copiaBolsa(Unidade unidade, boolean substituirArquivosNoDestino){
		new CopiaBolsa().copia(unidade, substituirArquivosNoDestino);
	}
	
	public void copiaImuno(Unidade unidade, boolean substituirArquivosNoDestino){
		new CopiaImuno().copia(unidade, substituirArquivosNoDestino);
	}

	public void copiaNat(Unidade unidade, boolean substituirArquivosNoDestino){
		new CopiaNat().copia(unidade, substituirArquivosNoDestino);
	}
	
	public void copiaSorologia(Unidade unidade, boolean substituirArquivosNoDestino){
		new CopiaSorologia().copia(unidade, substituirArquivosNoDestino);
	}
}
