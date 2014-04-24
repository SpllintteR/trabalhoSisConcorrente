package pkg;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

<<<<<<< HEAD
public class Familia implements Serializable {
	

	private static final long	serialVersionUID	= -5070026948247923813L;
=======
public class Familia extends Thread implements Serializable {

	private static final long serialVersionUID = -5070026948247923813L;

>>>>>>> branch 'master' of https://github.com/johnnysouza/trabalhoSisConcorrente.git
	private Cidade cidade;
	private final List<Pessoa> integrantes = new ArrayList<>();
<<<<<<< HEAD
	
=======

	public void calcularConsumo�gua() throws InterruptedException {
		int consumoAgua = 0;
		while (true) {
			// TODO calculo
			cidade.addConsumoAgua(consumoAgua);
			wait();
		}
	}

	public void calcularAlimentacao() throws InterruptedException {
		int consumoAlimentacao = 0;
		while (true) {
			// TODO calculo
			cidade.addConsumoAlimentacao(consumoAlimentacao);
			wait();
		}
	}

	public void calcularConsumoLuz() throws InterruptedException {
		int consumoLuz = 0;
		while (true) {
			// TODO calculo
			cidade.addConsumoLuz(consumoLuz);
			wait();
		}
	}

>>>>>>> branch 'master' of https://github.com/johnnysouza/trabalhoSisConcorrente.git
	public int getPeopleCount() {
		return integrantes.size();
	}

	public void addPessoa(final Pessoa pessoa) {
		integrantes.add(pessoa);
	}

	public void addNovoIntegrante() {
		int tamFamilia = integrantes.size();
		int totalEscolaridade = 0;
		double totalRenda = 0;
		double totalPeso = 0;
		for (int i = 0; i < tamFamilia; i++) {
			Pessoa pessoa = integrantes.get(i);
			totalEscolaridade += pessoa.getEscolaridade().ordinal();
			totalRenda += pessoa.getRenda();
			totalPeso += pessoa.getPeso();
		}
		int mediaEscolaridade = totalEscolaridade / tamFamilia;
		double mediaRenda = totalRenda / tamFamilia;
		double mediaPeso = totalPeso / tamFamilia;
		integrantes.add(new Pessoa(Escolaridade.values()[mediaEscolaridade], mediaRenda, mediaPeso));
	}
<<<<<<< HEAD
	
	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public List<Pessoa> getIntegrantes() {
		return integrantes;
	}
=======
>>>>>>> branch 'master' of https://github.com/johnnysouza/trabalhoSisConcorrente.git
}
