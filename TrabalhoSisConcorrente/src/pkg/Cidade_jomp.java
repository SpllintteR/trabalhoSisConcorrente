package pkg;

import java.util.Random;

import jomp.runtime.OMP;
import BaseDados.FamiliasManager;

public class Cidade_jomp {

	
	private static final String	LINE_SEPARATOR	= System
														.getProperty("line.separator");
	private StringBuilder		estastistica	= new StringBuilder();
	public int					quantMeses;
	private final Familia[]		familias;
	private long				consumoAgua;
	private long				consumoAlimentacao;
	private long				consumoLuz;
	
	public Cidade_jomp(final int quantMeses) {
		familias = FamiliasManager.loadFamilys();
		this.quantMeses = quantMeses;
	}
	
	public Cidade_jomp() {
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

// OMP PARALLEL BLOCK BEGINS
{
  __omp_Class0 __omp_Object0 = new __omp_Class0();
  // shared variables
  __omp_Object0.internalConsumoAlimentacao = internalConsumoAlimentacao;
  __omp_Object0.internalConsumoLuz = internalConsumoLuz;
  __omp_Object0.size = size;
  __omp_Object0.i = i;
  __omp_Object0.mortalidadeMes = mortalidadeMes;
  __omp_Object0.quantMeses = quantMeses;
  // firstprivate variables
  try {
    jomp.runtime.OMP.doParallel(__omp_Object0);
  } catch(Throwable __omp_exception) {
    System.err.println("OMP Warning: Illegal thread exception ignored!");
    __omp_exception.printStackTrace();
    __omp_exception.printStackTrace();
  }
  // reduction variables
  // shared variables
  internalConsumoAlimentacao = __omp_Object0.internalConsumoAlimentacao;
  internalConsumoLuz = __omp_Object0.internalConsumoLuz;
  size = __omp_Object0.size;
  i = __omp_Object0.i;
  mortalidadeMes = __omp_Object0.mortalidadeMes;
  quantMeses = __omp_Object0.quantMeses;
}
// OMP PARALLEL BLOCK ENDS

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

// OMP PARALLEL BLOCK BEGINS
{
  __omp_Class2 __omp_Object2 = new __omp_Class2();
  // shared variables
  __omp_Object2.quantMortes = quantMortes;
  __omp_Object2.tamanhaPopulacao = tamanhaPopulacao;
  __omp_Object2.random = random;
  __omp_Object2.quantMeses = quantMeses;
  // firstprivate variables
  try {
    jomp.runtime.OMP.doParallel(__omp_Object2);
  } catch(Throwable __omp_exception) {
    System.err.println("OMP Warning: Illegal thread exception ignored!");
    __omp_exception.printStackTrace();
  }
  // reduction variables
  // shared variables
  quantMortes = __omp_Object2.quantMortes;
  tamanhaPopulacao = __omp_Object2.tamanhaPopulacao;
  random = __omp_Object2.random;
  quantMeses = __omp_Object2.quantMeses;
}
// OMP PARALLEL BLOCK ENDS

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

// OMP PARALLEL BLOCK BEGINS
{
  __omp_Class3 __omp_Object3 = new __omp_Class3();
  // shared variables
  __omp_Object3.i = i;
  __omp_Object3.familyRandom = familyRandom;
  __omp_Object3.cresimentoPop = cresimentoPop;
  __omp_Object3.quantMeses = quantMeses;
  // firstprivate variables
  try {
    jomp.runtime.OMP.doParallel(__omp_Object3);
  } catch(Throwable __omp_exception) {
    System.err.println("OMP Warning: Illegal thread exception ignored!");
    __omp_exception.printStackTrace();
  }
  // reduction variables
  // shared variables
  i = __omp_Object3.i;
  familyRandom = __omp_Object3.familyRandom;
  cresimentoPop = __omp_Object3.cresimentoPop;
  quantMeses = __omp_Object3.quantMeses;
}
// OMP PARALLEL BLOCK ENDS

	}
	
	public int getTamanhoPopulacao() {
		int i = 0;
		int size = familias.length;
		int tamPopulacao = 0;

// OMP PARALLEL BLOCK BEGINS
{
  __omp_Class7 __omp_Object7 = new __omp_Class7();
  // shared variables
  __omp_Object7.size = size;
  __omp_Object7.i = i;
  __omp_Object7.quantMeses = quantMeses;
  // firstprivate variables
  try {
    jomp.runtime.OMP.doParallel(__omp_Object7);
  } catch(Throwable __omp_exception) {
    System.err.println("OMP Warning: Illegal thread exception ignored!");
    __omp_exception.printStackTrace();
  }
  // reduction variables
  tamPopulacao  += __omp_Object7._rd_tamPopulacao;
  // shared variables
  size = __omp_Object7.size;
  i = __omp_Object7.i;
  quantMeses = __omp_Object7.quantMeses;
}
// OMP PARALLEL BLOCK ENDS

		return tamPopulacao;
	}

// OMP PARALLEL REGION INNER CLASS DEFINITION BEGINS
private class __omp_Class7 extends jomp.runtime.BusyTask {
  // shared variables
  int size;
  int i;
  int quantMeses;
  // firstprivate variables
  // variables to hold results of reduction
  int _rd_tamPopulacao;

  public void go(int __omp_me) throws Throwable {
  // firstprivate variables + init
  // private variables
  // reduction variables, init to default
  int tamPopulacao = 0;
    // OMP USER CODE BEGINS

		{
                         { // OMP FOR BLOCK BEGINS
                         // copy of firstprivate variables, initialized
                         // copy of lastprivate variables
                         // variables to hold result of reduction
                         boolean amLast=false;
                         {
                           // firstprivate variables + init
                           // [last]private variables
                           // reduction variables + init to default
                           // -------------------------------------
                           jomp.runtime.LoopData __omp_WholeData9 = new jomp.runtime.LoopData();
                           jomp.runtime.LoopData __omp_ChunkData8 = new jomp.runtime.LoopData();
                           __omp_WholeData9.start = (long)( 0);
                           __omp_WholeData9.stop = (long)( size);
                           __omp_WholeData9.step = (long)(1);
                           jomp.runtime.OMP.setChunkStatic(__omp_WholeData9);
                           while(!__omp_ChunkData8.isLast && jomp.runtime.OMP.getLoopStatic(__omp_me, __omp_WholeData9, __omp_ChunkData8)) {
                           for(;;) {
                             if(__omp_WholeData9.step > 0) {
                                if(__omp_ChunkData8.stop > __omp_WholeData9.stop) __omp_ChunkData8.stop = __omp_WholeData9.stop;
                                if(__omp_ChunkData8.start >= __omp_WholeData9.stop) break;
                             } else {
                                if(__omp_ChunkData8.stop < __omp_WholeData9.stop) __omp_ChunkData8.stop = __omp_WholeData9.stop;
                                if(__omp_ChunkData8.start > __omp_WholeData9.stop) break;
                             }
                             for(int i = (int)__omp_ChunkData8.start; i < __omp_ChunkData8.stop; i += __omp_ChunkData8.step) {
                               // OMP USER CODE BEGINS
 {
				Familia familiax = familias[i];
				if (familiax != null) {
					tamPopulacao += familiax.getPeopleCount();
				}
			}
                               // OMP USER CODE ENDS
                               if (i == (__omp_WholeData9.stop-1)) amLast = true;
                             } // of for 
                             if(__omp_ChunkData8.startStep == 0)
                               break;
                             __omp_ChunkData8.start += __omp_ChunkData8.startStep;
                             __omp_ChunkData8.stop += __omp_ChunkData8.startStep;
                           } // of for(;;)
                           } // of while
                           // call reducer
                           jomp.runtime.OMP.doBarrier(__omp_me);
                           // copy lastprivate variables out
                           if (amLast) {
                           }
                         }
                         // set global from lastprivate variables
                         if (amLast) {
                         }
                         // set global from reduction variables
                         if (jomp.runtime.OMP.getThreadNum(__omp_me) == 0) {
                         }
                         } // OMP FOR BLOCK ENDS

		}
    // OMP USER CODE ENDS
  // call reducer
  tamPopulacao = (int) jomp.runtime.OMP.doPlusReduce(__omp_me, tamPopulacao);
  // output to _rd_ copy
  if (jomp.runtime.OMP.getThreadNum(__omp_me) == 0) {
    _rd_tamPopulacao = tamPopulacao;
  }
  }
}
// OMP PARALLEL REGION INNER CLASS DEFINITION ENDS



// OMP PARALLEL REGION INNER CLASS DEFINITION BEGINS
private class __omp_Class3 extends jomp.runtime.BusyTask {
  // shared variables
  int i;
  Random familyRandom;
  int cresimentoPop;
  int quantMeses;
  // firstprivate variables
  // variables to hold results of reduction

  public void go(int __omp_me) throws Throwable {
  // firstprivate variables + init
  // private variables
  // reduction variables, init to default
    // OMP USER CODE BEGINS

		{
                         { // OMP FOR BLOCK BEGINS
                         // copy of firstprivate variables, initialized
                         // copy of lastprivate variables
                         // variables to hold result of reduction
                         boolean amLast=false;
                         {
                           // firstprivate variables + init
                           // [last]private variables
                           // reduction variables + init to default
                           // -------------------------------------
                           jomp.runtime.LoopData __omp_WholeData5 = new jomp.runtime.LoopData();
                           jomp.runtime.LoopData __omp_ChunkData4 = new jomp.runtime.LoopData();
                           __omp_WholeData5.start = (long)( 0);
                           __omp_WholeData5.stop = (long)( cresimentoPop);
                           __omp_WholeData5.step = (long)(1);
                           jomp.runtime.OMP.setChunkStatic(__omp_WholeData5);
                           while(!__omp_ChunkData4.isLast && jomp.runtime.OMP.getLoopStatic(__omp_me, __omp_WholeData5, __omp_ChunkData4)) {
                           for(;;) {
                             if(__omp_WholeData5.step > 0) {
                                if(__omp_ChunkData4.stop > __omp_WholeData5.stop) __omp_ChunkData4.stop = __omp_WholeData5.stop;
                                if(__omp_ChunkData4.start >= __omp_WholeData5.stop) break;
                             } else {
                                if(__omp_ChunkData4.stop < __omp_WholeData5.stop) __omp_ChunkData4.stop = __omp_WholeData5.stop;
                                if(__omp_ChunkData4.start > __omp_WholeData5.stop) break;
                             }
                             for(int i = (int)__omp_ChunkData4.start; i < __omp_ChunkData4.stop; i += __omp_ChunkData4.step) {
                               // OMP USER CODE BEGINS
 {
				Familia familiax = familias[familyRandom
						.nextInt(familias.length)];
				if (familiax != null) {
					familiax.addNovoIntegrante();
				}
			}
                               // OMP USER CODE ENDS
                               if (i == (__omp_WholeData5.stop-1)) amLast = true;
                             } // of for 
                             if(__omp_ChunkData4.startStep == 0)
                               break;
                             __omp_ChunkData4.start += __omp_ChunkData4.startStep;
                             __omp_ChunkData4.stop += __omp_ChunkData4.startStep;
                           } // of for(;;)
                           } // of while
                           // call reducer
                           jomp.runtime.OMP.doBarrier(__omp_me);
                           // copy lastprivate variables out
                           if (amLast) {
                           }
                         }
                         // set global from lastprivate variables
                         if (amLast) {
                         }
                         // set global from reduction variables
                         if (jomp.runtime.OMP.getThreadNum(__omp_me) == 0) {
                         }
                         } // OMP FOR BLOCK ENDS

		}
    // OMP USER CODE ENDS
  // call reducer
  // output to _rd_ copy
  if (jomp.runtime.OMP.getThreadNum(__omp_me) == 0) {
  }
  }
}
// OMP PARALLEL REGION INNER CLASS DEFINITION ENDS



// OMP PARALLEL REGION INNER CLASS DEFINITION BEGINS
private class __omp_Class2 extends jomp.runtime.BusyTask {
  // shared variables
  int quantMortes;
  int tamanhaPopulacao;
  Random random;
  int quantMeses;
  // firstprivate variables
  // variables to hold results of reduction

  public void go(int __omp_me) throws Throwable {
  // firstprivate variables + init
  // private variables
  int i;
  // reduction variables, init to default
    // OMP USER CODE BEGINS

		{
			for (i = 0; i < quantMortes; i++) {
				boolean matou = false;
				while (!matou) {
                                         // OMP CRITICAL BLOCK BEGINS
                                         synchronized (jomp.runtime.OMP.getLockByName("")) {
                                         // OMP USER CODE BEGINS

					{
						int quantFamilias = random.nextInt(familias.length);
						matou = familias[quantFamilias].matarPessoa();
					}
                                         // OMP USER CODE ENDS
                                         }
                                         // OMP CRITICAL BLOCK ENDS

				}
			}
		}
    // OMP USER CODE ENDS
  // call reducer
  // output to _rd_ copy
  if (jomp.runtime.OMP.getThreadNum(__omp_me) == 0) {
  }
  }
}
// OMP PARALLEL REGION INNER CLASS DEFINITION ENDS



// OMP PARALLEL REGION INNER CLASS DEFINITION BEGINS
private class __omp_Class0 extends jomp.runtime.BusyTask {
  // shared variables
  long internalConsumoAlimentacao;
  int internalConsumoLuz;
  int size;
  int i;
  int mortalidadeMes;
  int quantMeses;
  // firstprivate variables
  // variables to hold results of reduction

  public void go(int __omp_me) throws Throwable {
  // firstprivate variables + init
  // private variables
  // reduction variables, init to default
    // OMP USER CODE BEGINS

                                          { // OMP SECTIONS BLOCK BEGINS
                                          // copy of firstprivate variables, initialized
                                          // copy of lastprivate variables
                                          // variables to hold result of reduction
                                          boolean amLast=false;
                                          {
                                            // firstprivate variables + init
                                            // [last]private variables
                                            // reduction variables + init to default
                                            // -------------------------------------
                                            __ompName_1: while(true) {
                                            switch((int)jomp.runtime.OMP.getTicket(__omp_me)) {
                                            // OMP SECTION BLOCK 0 BEGINS
                                              case 0: {
                                            // OMP USER CODE BEGINS

					{
						for (i = 0; i < size; i++) {
                                                         // OMP CRITICAL BLOCK BEGINS
                                                         synchronized (jomp.runtime.OMP.getLockByName("")) {
                                                         // OMP USER CODE BEGINS

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
                                                         // OMP USER CODE ENDS
                                                         }
                                                         // OMP CRITICAL BLOCK ENDS

						}
					}
                                            // OMP USER CODE ENDS
                                              };  break;
                                            // OMP SECTION BLOCK 0 ENDS
                                            // OMP SECTION BLOCK 1 BEGINS
                                              case 1: {
                                            // OMP USER CODE BEGINS

					{
						for (i = 0; i < size; i++) {
                                                         // OMP CRITICAL BLOCK BEGINS
                                                         synchronized (jomp.runtime.OMP.getLockByName("")) {
                                                         // OMP USER CODE BEGINS

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
                                                         // OMP USER CODE ENDS
                                                         }
                                                         // OMP CRITICAL BLOCK ENDS

						}
					}
                                            // OMP USER CODE ENDS
                                              };  break;
                                            // OMP SECTION BLOCK 1 ENDS
                                            // OMP SECTION BLOCK 2 BEGINS
                                              case 2: {
                                            // OMP USER CODE BEGINS

					{
						for (i = 0; i < size; i++) {
                                                         // OMP CRITICAL BLOCK BEGINS
                                                         synchronized (jomp.runtime.OMP.getLockByName("")) {
                                                         // OMP USER CODE BEGINS

							{
								Familia familiax = familias[i];
								if (familiax != null) {
									addConsumoAgua(familiax.getIntegrantes()
											.size() * AGUAPORDIA * 30);
								}
							}
                                                         // OMP USER CODE ENDS
                                                         }
                                                         // OMP CRITICAL BLOCK ENDS

						}
					}
                                            // OMP USER CODE ENDS
                                            amLast = true;
                                              };  break;
                                            // OMP SECTION BLOCK 2 ENDS

                                              default: break __ompName_1;
                                            } // of switch
                                            } // of while
                                            // call reducer
                                            jomp.runtime.OMP.resetTicket(__omp_me);
                                            jomp.runtime.OMP.doBarrier(__omp_me);
                                            // copy lastprivate variables out
                                            if (amLast) {
                                            }
                                          }
                                          // update lastprivate variables
                                          if (amLast) {
                                          }
                                          // update reduction variables
                                          if (jomp.runtime.OMP.getThreadNum(__omp_me) == 0) {
                                          }
                                          } // OMP SECTIONS BLOCK ENDS

    // OMP USER CODE ENDS
  // call reducer
  // output to _rd_ copy
  if (jomp.runtime.OMP.getThreadNum(__omp_me) == 0) {
  }
  }
}
// OMP PARALLEL REGION INNER CLASS DEFINITION ENDS

}

