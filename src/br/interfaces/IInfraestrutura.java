package br.interfaces;

import java.util.ArrayList;

import br.model.Infraestrutura;

public interface IInfraestrutura {
	void incluirInfraestrutura(Infraestrutura infraestrutura) throws Exception;
    public ArrayList<Infraestrutura> listarInfraestruturas() throws Exception;
    public void excluirInfraestrutura(int codInfraestrutura) throws Exception;
    public void alterarInfraestrutura(int codInfraestrutura, Infraestrutura infraestrutura) throws Exception;
    public int identity() throws Exception;
}
