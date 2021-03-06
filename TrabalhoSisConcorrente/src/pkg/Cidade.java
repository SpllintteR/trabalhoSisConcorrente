package pkg;

import java.util.Random;

import jomp.runtime.OMP;
import BaseDados.FamiliasManager;

public class Cidade {
	
	//teste
	
	private static final String	LINE_SEPARATOR	= System
														.getProperty("line.separator");
	private StringBuilder		estastistica	= new StringBuilder();
	public int					quantMeses;
	private final Familia[]		familias;
	private long				consumoAgua;
	private long				consumoAlimentacao;
	private long				consumoLuz;
	
	public Cidade(final int quantMeses) {
		familias = FamiliasManager.loadFamilys();
		this.quantMeses = quantMeses;
	}
	
	public Cidade() {
		this(10);
	}
	
	private static int		AGUAPORDIA			= 2;
	private static double	FOODMAGICNUMBER		= 0.0255;
	private static double	LIGHTMAGICNUMBER	= 0.04;
	
	public void execute() {
		try {
			estastistica.append("Tamanho");
			estastistica.append("\t\t");
			estastistica.append("Agua");
			estastistica.append("\t\t");
			estastistica.append("Luz");
			estastistica.append("\t\t");
			estastistica.append("Alimento");
			estastistica.append(LINE_SEPARATOR);
			int mortalidadeMes = 6;
			OMP.setNumThreads(6);
			while (quantMeses > 0) {
				consumoAgua = 0;
				consumoAlimentacao = 0;
				consumoLuz = 0;
				int i = 0;
				int size = familias.length;
				int internalConsumoLuz = 0;
				long internalConsumoAlimentacao = 0;
				
				//omp parallel sections private(i,internalConsumoLuz,internalConsumoAlimentacao)
				{
					//omp section
					{
						for (i = 0; i < size; i++) {
							//omp critical
							{
								Familia familiax = familias[i];
								if (familiax != null) {
									Object[] pessoas = familiax.getIntegrantes().toArray();
									int qntPessoas = pessoas.length;
									for (int j = 0; j < qntPessoas; j++) {
										Pessoa pessoa = (Pessoa) pessoas[j];
										if (pessoa != null) {
											internalConsumoAlimentacao += (long) (pessoa.getPeso()
													* FOODMAGICNUMBER * 30);
										}
									}
									addConsumoAlimentacao(internalConsumoAlimentacao);
								}
							}
						}
					}
					
					//omp section
					{
						for (i = 0; i < size; i++) {
							//omp critical
							{
								Familia familiax = familias[i];
								if (familiax != null) {
									Object[] pessoas = familiax.getIntegrantes().toArray();
									int qntPessoas = pessoas.length;
									for (int j = 0; j < qntPessoas; j++) {
										Pessoa pessoa = (Pessoa) pessoas[j];
										if (pessoa != null){
											internalConsumoLuz += pessoa.getRenda() * LIGHTMAGICNUMBER;
										}
									}
									addConsumoLuz(internalConsumoLuz);
								}
							}
						}
					}
					
					//omp section
					{
						for (i = 0; i < size; i++) {
							//omp critical
							{
								Familia familiax = familias[i];
								if (familiax != null) {
									addConsumoAgua(familiax.getIntegrantes()
											.size() * AGUAPORDIA * 30);
								}
							}
						}
					}
				}
				if (mortalidadeMes == 0) {
					mortalidade();
					mortalidadeMes = 6;
				} else {
					mortalidadeMes--;
				}
				quantMeses--;
				showStatus();
				addPopulacao();
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	private void mortalidade() {
		Random random = new Random();
		int tamanhaPopulacao = getTamanhoPopulacao();
		int quantMortes = 0;
		while (quantMortes == 0) {
			quantMortes = random.nextInt((int) (tamanhaPopulacao * 0.05));
		}
		int i;
		//omp parallel private(i)
		{
			for (i = 0; i < quantMortes; i++) {
				boolean matou = false;
				while (!matou) {
					//omp critical
					{
						int quantFamilias = random.nextInt(familias.length);
						matou = familias[quantFamilias].matarPessoa();
					}
				}
			}
		}
	}
	
	private void showStatus() {
		estastistica.append(getTamanhoPopulacao());
		estastistica.append("\t\t");
		estastistica.append(consumoAgua);
		estastistica.append("\t\t");
		estastistica.append(consumoLuz);
		estastistica.append("\t\t");
		estastistica.append(consumoAlimentacao);
		System.out.println(estastistica.toString());
		estastistica = new StringBuilder();
	}
	
	public void addConsumoAgua(final long consumoAgua) {
		this.consumoAgua += consumoAgua;
	}
	
	public void addConsumoAlimentacao(final long consumoAlimentos) {
		consumoAlimentacao += consumoAlimentos;
	}
	
	public void addConsumoLuz(final long consumoLuz) {
		this.consumoLuz += consumoLuz;
	}
	
	public void addPopulacao() {
		int cresimentoPop = (int) (getTamanhoPopulacao() * 0.03);
		if (cresimentoPop == 0) {
			cresimentoPop = 1;
		}
		Random familyRandom = new Random();
		
		int i = 0;
		//omp parallel
		{
			//omp for
			for (i = 0; i < cresimentoPop; i++) {
				Familia familiax = familias[familyRandom.nextInt(familias.length)];
				if (familiax != null) {
					//omp critical
					{
						familiax.addNovoIntegrante();
					}
				}
			}
		}
	}
	
	public int getTamanhoPopulacao() {
		int i = 0;
		int size = familias.length;
		int tamPopulacao = 0;
		//omp parallel reduction(+:tamPopulacao)
		{
			//omp for
			for (i = 0; i < size; i++) {
				Familia familiax = familias[i];
				if (familiax != null) {
					tamPopulacao += familiax.getPeopleCount();
				}
			}
		}
		return tamPopulacao;
	}
	
}
