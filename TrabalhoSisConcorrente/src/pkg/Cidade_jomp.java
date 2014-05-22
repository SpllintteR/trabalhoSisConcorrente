package pkg;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import jomp.runtime.OMP;
import BaseDados.FamiliasManager;

public class Cidade_jomp {

	
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
	
	public Cidade_jomp(int quantMeses, long intervalo) {
		familias = new ArrayList();
		this.quantMeses = quantMeses;
		this.intervalo = intervalo;
		loadFamilys = FamiliasManager.loadFamilys();
		int i = 0;
		length = loadFamilys.length;
		
		OMP.setNumThreads(length);

// OMP PARALLEL BLOCK BEGINS
{
  __omp_Class0 __omp_Object0 = new __omp_Class0();
  // shared variables
  __omp_Object0.intervalo = intervalo;
  __omp_Object0.quantMeses = quantMeses;
  // firstprivate variables
  try {
    jomp.runtime.OMP.doParallel(__omp_Object0);
  } catch(Throwable __omp_exception) {
	  __omp_exception.printStackTrace();
  }
  // reduction variables
  // shared variables
  intervalo = __omp_Object0.intervalo;
  quantMeses = __omp_Object0.quantMeses;
}
// OMP PARALLEL BLOCK ENDS

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
				int size = familias.size();
				Familia familia;
				int internalConsumoLuz  = 0;
				long internalConsumoAlimentacao = 0;

// OMP PARALLEL BLOCK BEGINS
{
  __omp_Class1 __omp_Object1 = new __omp_Class1();
  // shared variables
  __omp_Object1.size = size;
  __omp_Object1.intervalo = intervalo;
  __omp_Object1.quantMeses = quantMeses;
  // firstprivate variables
  try {
    jomp.runtime.OMP.doParallel(__omp_Object1);
  } catch(Throwable __omp_exception) {
	  __omp_exception.printStackTrace();
  }
  // reduction variables
  // shared variables
  familia = __omp_Object1.familia;
  size = __omp_Object1.size;
  intervalo = __omp_Object1.intervalo;
  quantMeses = __omp_Object1.quantMeses;
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
  __omp_Class3 __omp_Object3 = new __omp_Class3();
  // shared variables
  __omp_Object3.i = i;
  __omp_Object3.familyRandom = familyRandom;
  __omp_Object3.cresimentoPop = cresimentoPop;
  __omp_Object3.intervalo = intervalo;
  __omp_Object3.quantMeses = quantMeses;
  // firstprivate variables
  try {
    jomp.runtime.OMP.doParallel(__omp_Object3);
  } catch(Throwable __omp_exception) {
	  __omp_exception.printStackTrace();
  }
  // reduction variables
  // shared variables
  i = __omp_Object3.i;
  familyRandom = __omp_Object3.familyRandom;
  cresimentoPop = __omp_Object3.cresimentoPop;
  intervalo = __omp_Object3.intervalo;
  quantMeses = __omp_Object3.quantMeses;
}
// OMP PARALLEL BLOCK ENDS

	}

	public int getTamanhoPopulacao() {
		int i = 0;
		int size = familias.size();
		int tamPopulacao = 0;

// OMP PARALLEL BLOCK BEGINS
{
  __omp_Class7 __omp_Object7 = new __omp_Class7();
  // shared variables
  __omp_Object7.size = size;
  __omp_Object7.i = i;
  __omp_Object7.intervalo = intervalo;
  __omp_Object7.quantMeses = quantMeses;
  // firstprivate variables
  try {
    jomp.runtime.OMP.doParallel(__omp_Object7);
  } catch(Throwable __omp_exception) {
	  __omp_exception.printStackTrace();
  }
  // reduction variables
  tamPopulacao  += __omp_Object7._rd_tamPopulacao;
  // shared variables
  size = __omp_Object7.size;
  i = __omp_Object7.i;
  intervalo = __omp_Object7.intervalo;
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
				tamPopulacao += ((Familia) familias.get(i)).getPeopleCount();
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
				Familia familiax = (Familia) familias.get(familyRandom.nextInt(familias.size()));
				familiax.addNovoIntegrante();
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
private class __omp_Class1 extends jomp.runtime.BusyTask {
  // shared variables
  Familia familia;
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
                                            __ompName_2: while(true) {
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
								Familia familiax = (Familia) familias.get(i);
								List pessoas = familiax.getIntegrantes();
								int qntPessoas = pessoas.size();
								for(int j = 0; j < qntPessoas; j++){
									internalConsumoAlimentacao += (long) (((Pessoa) pessoas.get(j)).getPeso() * FOODMAGICNUMBER * 30);
								}
								addConsumoAlimentacao(internalConsumoAlimentacao);
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
								familia = (Familia) familias.get(i);
								List pessoas = familia.getIntegrantes();
								int qntPessoas = pessoas.size();
								for(int j = 0; j < qntPessoas; j++){
									internalConsumoLuz += ((Pessoa) pessoas.get(j)).getRenda() * LIGHTMAGICNUMBER; 
								}
								addConsumoLuz(internalConsumoLuz);
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
								Familia familiax = (Familia) familias.get(i);
								addConsumoAgua(familiax.getIntegrantes().size() * AGUAPORDIA * 30);
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

                                              default: break __ompName_2;
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



// OMP PARALLEL REGION INNER CLASS DEFINITION BEGINS
private class __omp_Class0 extends jomp.runtime.BusyTask {
  // shared variables
  long intervalo;
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
			for(i = 0; i < length; i++){
				familia = loadFamilys[i];
				if (familia != null) {
					familias.add(familia);
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

}

