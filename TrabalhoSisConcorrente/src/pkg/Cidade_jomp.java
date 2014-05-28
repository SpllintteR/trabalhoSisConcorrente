package pkg;

import java.util.List;
import java.util.Random;

import BaseDados.FamiliasManager;

public class Cidade_jomp {

	
	private static final String LINE_SEPARATOR = System.getProperty("line.separator");
	private StringBuilder estastistica = new StringBuilder();
	public long intervalo;
	public int quantMeses;
	private Familia[] familias;
	private long consumoAgua;
	private long consumoAlimentacao;
	private long consumoLuz;
	
	public Cidade_jomp(int quantMeses, long intervalo) {
		familias = FamiliasManager.loadFamilys();
		this.quantMeses = quantMeses;
		this.intervalo = intervalo;
	}

	public Cidade_jomp() {
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

// OMP PARALLEL BLOCK BEGINS
{
  __omp_Class0 __omp_Object0 = new __omp_Class0();
  // shared variables
  __omp_Object0.size = size;
  __omp_Object0.intervalo = intervalo;
  __omp_Object0.quantMeses = quantMeses;
  // firstprivate variables
  try {
    jomp.runtime.OMP.doParallel(__omp_Object0);
  } catch(Throwable __omp_exception) {
    System.err.println("OMP Warning: Illegal thread exception ignored!");
    System.err.println(__omp_exception);
  }
  // reduction variables
  // shared variables
  size = __omp_Object0.size;
  intervalo = __omp_Object0.intervalo;
  quantMeses = __omp_Object0.quantMeses;
}
// OMP PARALLEL BLOCK ENDS

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

// OMP PARALLEL BLOCK BEGINS
{
  __omp_Class2 __omp_Object2 = new __omp_Class2();
  // shared variables
  __omp_Object2.i = i;
  __omp_Object2.familyRandom = familyRandom;
  __omp_Object2.cresimentoPop = cresimentoPop;
  __omp_Object2.intervalo = intervalo;
  __omp_Object2.quantMeses = quantMeses;
  // firstprivate variables
  try {
    jomp.runtime.OMP.doParallel(__omp_Object2);
  } catch(Throwable __omp_exception) {
    System.err.println("OMP Warning: Illegal thread exception ignored!");
    System.err.println(__omp_exception);
  }
  // reduction variables
  // shared variables
  i = __omp_Object2.i;
  familyRandom = __omp_Object2.familyRandom;
  cresimentoPop = __omp_Object2.cresimentoPop;
  intervalo = __omp_Object2.intervalo;
  quantMeses = __omp_Object2.quantMeses;
}
// OMP PARALLEL BLOCK ENDS

	}

	public int getTamanhoPopulacao() {
		int i = 0;
		int size = familias.length;
		int tamPopulacao = 0;

// OMP PARALLEL BLOCK BEGINS
{
  __omp_Class6 __omp_Object6 = new __omp_Class6();
  // shared variables
  __omp_Object6.size = size;
  __omp_Object6.i = i;
  __omp_Object6.intervalo = intervalo;
  __omp_Object6.quantMeses = quantMeses;
  // firstprivate variables
  try {
    jomp.runtime.OMP.doParallel(__omp_Object6);
  } catch(Throwable __omp_exception) {
    System.err.println("OMP Warning: Illegal thread exception ignored!");
    System.err.println(__omp_exception);
  }
  // reduction variables
  tamPopulacao  += __omp_Object6._rd_tamPopulacao;
  // shared variables
  size = __omp_Object6.size;
  i = __omp_Object6.i;
  intervalo = __omp_Object6.intervalo;
  quantMeses = __omp_Object6.quantMeses;
}
// OMP PARALLEL BLOCK ENDS

		return tamPopulacao;
	}

// OMP PARALLEL REGION INNER CLASS DEFINITION BEGINS
private class __omp_Class6 extends jomp.runtime.BusyTask {
  // shared variables
  int size;
  int i;
  long intervalo;
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
                           jomp.runtime.LoopData __omp_WholeData8 = new jomp.runtime.LoopData();
                           jomp.runtime.LoopData __omp_ChunkData7 = new jomp.runtime.LoopData();
                           __omp_WholeData8.start = (long)( 0);
                           __omp_WholeData8.stop = (long)( size);
                           __omp_WholeData8.step = (long)(1);
                           jomp.runtime.OMP.setChunkStatic(__omp_WholeData8);
                           while(!__omp_ChunkData7.isLast && jomp.runtime.OMP.getLoopStatic(__omp_me, __omp_WholeData8, __omp_ChunkData7)) {
                           for(;;) {
                             if(__omp_WholeData8.step > 0) {
                                if(__omp_ChunkData7.stop > __omp_WholeData8.stop) __omp_ChunkData7.stop = __omp_WholeData8.stop;
                                if(__omp_ChunkData7.start >= __omp_WholeData8.stop) break;
                             } else {
                                if(__omp_ChunkData7.stop < __omp_WholeData8.stop) __omp_ChunkData7.stop = __omp_WholeData8.stop;
                                if(__omp_ChunkData7.start > __omp_WholeData8.stop) break;
                             }
                             for(int i = (int)__omp_ChunkData7.start; i < __omp_ChunkData7.stop; i += __omp_ChunkData7.step) {
                               // OMP USER CODE BEGINS
 {
				Familia familiax = (Familia) familias[i];
				if (familiax != null) {
					tamPopulacao += familiax.getPeopleCount();
				}
			}
                               // OMP USER CODE ENDS
                               if (i == (__omp_WholeData8.stop-1)) amLast = true;
                             } // of for 
                             if(__omp_ChunkData7.startStep == 0)
                               break;
                             __omp_ChunkData7.start += __omp_ChunkData7.startStep;
                             __omp_ChunkData7.stop += __omp_ChunkData7.startStep;
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
private class __omp_Class2 extends jomp.runtime.BusyTask {
  // shared variables
  int i;
  Random familyRandom;
  int cresimentoPop;
  long intervalo;
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
                           jomp.runtime.LoopData __omp_WholeData4 = new jomp.runtime.LoopData();
                           jomp.runtime.LoopData __omp_ChunkData3 = new jomp.runtime.LoopData();
                           __omp_WholeData4.start = (long)( 0);
                           __omp_WholeData4.stop = (long)( cresimentoPop);
                           __omp_WholeData4.step = (long)(1);
                           jomp.runtime.OMP.setChunkStatic(__omp_WholeData4);
                           while(!__omp_ChunkData3.isLast && jomp.runtime.OMP.getLoopStatic(__omp_me, __omp_WholeData4, __omp_ChunkData3)) {
                           for(;;) {
                             if(__omp_WholeData4.step > 0) {
                                if(__omp_ChunkData3.stop > __omp_WholeData4.stop) __omp_ChunkData3.stop = __omp_WholeData4.stop;
                                if(__omp_ChunkData3.start >= __omp_WholeData4.stop) break;
                             } else {
                                if(__omp_ChunkData3.stop < __omp_WholeData4.stop) __omp_ChunkData3.stop = __omp_WholeData4.stop;
                                if(__omp_ChunkData3.start > __omp_WholeData4.stop) break;
                             }
                             for(int i = (int)__omp_ChunkData3.start; i < __omp_ChunkData3.stop; i += __omp_ChunkData3.step) {
                               // OMP USER CODE BEGINS
 {
				Familia familiax = (Familia) familias[familyRandom.nextInt(familias.length)];
				if (familiax != null) {
					familiax.addNovoIntegrante();
				}
			}
                               // OMP USER CODE ENDS
                               if (i == (__omp_WholeData4.stop-1)) amLast = true;
                             } // of for 
                             if(__omp_ChunkData3.startStep == 0)
                               break;
                             __omp_ChunkData3.start += __omp_ChunkData3.startStep;
                             __omp_ChunkData3.stop += __omp_ChunkData3.startStep;
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
private class __omp_Class0 extends jomp.runtime.BusyTask {
  // shared variables
  int size;
  long intervalo;
  int quantMeses;
  // firstprivate variables
  // variables to hold results of reduction

  public void go(int __omp_me) throws Throwable {
  // firstprivate variables + init
  // private variables
  int i;
  int internalConsumoLuz = 0;
  long internalConsumoAlimentacao = 0;
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
						for(i = 0; i < size; i++){
                                                         // OMP CRITICAL BLOCK BEGINS
                                                         synchronized (jomp.runtime.OMP.getLockByName("")) {
                                                         // OMP USER CODE BEGINS

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
						for(i = 0; i < size; i++){
                                                         // OMP CRITICAL BLOCK BEGINS
                                                         synchronized (jomp.runtime.OMP.getLockByName("")) {
                                                         // OMP USER CODE BEGINS

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
						for(i = 0; i < size; i++){
                                                         // OMP CRITICAL BLOCK BEGINS
                                                         synchronized (jomp.runtime.OMP.getLockByName("")) {
                                                         // OMP USER CODE BEGINS

							{
								Familia familiax = (Familia) familias[i];
								if (familiax != null) {
									addConsumoAgua(familiax.getIntegrantes().size() * AGUAPORDIA * 30);
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

