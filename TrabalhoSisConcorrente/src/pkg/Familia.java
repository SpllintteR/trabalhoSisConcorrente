package pkg;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Familia implements Serializable {
	
	private static final long	serialVersionUID	= -5070026948247923813L;
	private final List<Pessoa>	integrantes			= new ArrayList<>();
	
	public int getPeopleCount() {
		return integrantes.size();
	}
	
	public void addPessoa(final Pessoa pessoa) {
		if (pessoa == null) { throw new RuntimeException("Para tudo essa porra"); }
		integrantes.add(pessoa);
	}
	
	public void addNovoIntegrante() {
		Random random = new Random();
		int	mediaEscolaridade = random.nextInt(5);
		double mediaRenda = random.nextDouble() * 200;
		double mediaPeso = random.nextFloat() * 200;
		integrantes.add(new Pessoa(Escolaridade.values()[mediaEscolaridade], mediaRenda, mediaPeso));
	}
	
	public List<Pessoa> getIntegrantes() {
		return integrantes;
	}
	
	public boolean matarPessoa() {
		if (integrantes.size() == 0) { return false; }
		integrantes.remove(0);
		return true;
	}
}
