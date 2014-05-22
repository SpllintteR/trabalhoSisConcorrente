package pkg;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import jomp.runtime.OMP;
import BaseDados.FamiliasManager;

public class Cidade {
	
	private static final String LINE_SEPARATOR = System.getProperty("line.separator");
	private StringBuilder estastistica = new StringBuilder();
	public long intervalo;
	public int quantMeses;
	private List familias;
	private long consumoAgua;
	private long consumoAlimentacao;
	private long consumoLuz;
	private int length;
	private Familia[] loadFamilys;
	private Familia familia;
	
	public Cidade(int quantMeses, long intervalo) {
		familias = new ArrayList();
		this.quantMeses = quantMeses;
		this.intervalo = intervalo;
		loadFamilys = FamiliasManager.loadFamilys();
		int i = 0;
		length = loadFamilys.length;
		
		OMP.setNumThreads(length);
		//omp parallel private(i)
		{
			for(i = 0; i < length; i++){
				familia = loadFamilys[i];
				if (familia != null) {
					familias.add(familia);
				}
			}
		}
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
				int size = familias.size();
				Familia familia;
				int internalConsumoLuz  = 0;
				long internalConsumoAlimentacao = 0;
				
				//omp parallel sections private(i,internalConsumoLuz,internalConsumoAlimentacao)
				{
					//omp section
					{
						for(i = 0; i < size; i++){
							//omp critical
							{
								Familia familiax = (Familia) familias.get(i);
								List pessoas = familiax.getIntegrantes();
								int qntPessoas = pessoas.size();
								for(int j = 0; j < qntPessoas; j++){
									internalConsumoAlimentacao += (long) (((Pessoa) pessoas.get(j)).getPeso() * FOODMAGICNUMBER * 30);
								}
								addConsumoAlimentacao(internalConsumoAlimentacao);
							}
						}
					}
					
					//omp section
					{
						for(i = 0; i < size; i++){
							//omp critical
							{
								familia = (Familia) familias.get(i);
								List pessoas = familia.getIntegrantes();
								int qntPessoas = pessoas.size();
								for(int j = 0; j < qntPessoas; j++){
									internalConsumoLuz += ((Pessoa) pessoas.get(j)).getRenda() * LIGHTMAGICNUMBER; 
								}
								addConsumoLuz(internalConsumoLuz);
							}
						}
					}
					
					//omp section
					{
						for(i = 0; i < size; i++){
							//omp critical
							{
								Familia familiax = (Familia) familias.get(i);
								addConsumoAgua(familiax.getIntegrantes().size() * AGUAPORDIA * 30);
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
				Familia familiax = (Familia) familias.get(familyRandom.nextInt(familias.size()));
				familiax.addNovoIntegrante();
			}
		}
	}

	public int getTamanhoPopulacao() {
		int i = 0;
		int size = familias.size();
		int tamPopulacao = 0;
		//omp parallel reduction(+:tamPopulacao)
		{
			//omp for
			for (i = 0; i < size; i++) {
				tamPopulacao += ((Familia) familias.get(i)).getPeopleCount();
			}
		}
		return tamPopulacao;
	}

}
