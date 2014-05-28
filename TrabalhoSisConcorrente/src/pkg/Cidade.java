package pkg;

import java.util.List;
import java.util.Random;

import BaseDados.FamiliasManager;

public class Cidade {
	
	private static final String LINE_SEPARATOR = System.getProperty("line.separator");
	private StringBuilder estastistica = new StringBuilder();
	public long intervalo;
	public int quantMeses;
	private Familia[] familias;
	private long consumoAgua;
	private long consumoAlimentacao;
	private long consumoLuz;
	
	public Cidade(int quantMeses, long intervalo) {
		familias = FamiliasManager.loadFamilys();
		this.quantMeses = quantMeses;
		this.intervalo = intervalo;
	}

	public Cidade() {
		this(10, 1000);
	}

	private static int AGUAPORDIA = 2;
	private static double FOODMAGICNUMBER = 0.0255;
	private static double LIGHTMAGICNUMBER = 0.04;
	
	public void execute() {
		try {
			while (quantMeses > 0) {
				consumoAgua = 0;
				consumoAlimentacao = 0;
				consumoLuz = 0;
				int i = 0;
				int size = familias.length;
				int internalConsumoLuz  = 0;
				long internalConsumoAlimentacao = 0;
				
				//omp parallel sections private(i,internalConsumoLuz,internalConsumoAlimentacao)
				{
					//omp section
					{
						for(i = 0; i < size; i++){
							//omp critical
							{
								Familia familiax = (Familia) familias[i];
								if (familiax != null) {
									List pessoas = familiax.getIntegrantes();
									int qntPessoas = pessoas.size();
									for(int j = 0; j < qntPessoas; j++){
										internalConsumoAlimentacao += (long) (((Pessoa) pessoas.get(j)).getPeso() * FOODMAGICNUMBER * 30);
									}
									addConsumoAlimentacao(internalConsumoAlimentacao);
								}
							}
						}
					}
					
					//omp section
					{
						for(i = 0; i < size; i++){
							//omp critical
							{
								Familia familiax = (Familia) familias[i];
								if (familiax != null) {
									List pessoas = familiax.getIntegrantes();
									int qntPessoas = pessoas.size();
									for(int j = 0; j < qntPessoas; j++){
										internalConsumoLuz += ((Pessoa) pessoas.get(j)).getRenda() * LIGHTMAGICNUMBER; 
									}
									addConsumoLuz(internalConsumoLuz);
								}
							}
						}
					}
					
					//omp section
					{
						for(i = 0; i < size; i++){
							//omp critical
							{
								Familia familiax = (Familia) familias[i];
								if (familiax != null) {
									addConsumoAgua(familiax.getIntegrantes().size() * AGUAPORDIA * 30);
								}
							}
						}
					}
				}
				quantMeses--;
				showStatus();
				addPopulacao();
			}
		} catch (Throwable e) {
			e.printStackTrace();
 		}
		System.out.println(estastistica.toString());
	}
	
	private void showStatus() {
		estastistica.append(getTamanhoPopulacao());
		estastistica.append("\t\t");
		estastistica.append(consumoAgua);
		estastistica.append("\t\t");
		estastistica.append(consumoLuz);
		estastistica.append("\t\t");
		estastistica.append(consumoAlimentacao);
		estastistica.append(LINE_SEPARATOR);
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
			for ( i = 0; i < cresimentoPop; i++) {
				Familia familiax = (Familia) familias[familyRandom.nextInt(familias.length)];
				if (familiax != null) {
					familiax.addNovoIntegrante();
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
				Familia familiax = (Familia) familias[i];
				if (familiax != null) {
					tamPopulacao += familiax.getPeopleCount();
				}
			}
		}
		return tamPopulacao;
	}

}
