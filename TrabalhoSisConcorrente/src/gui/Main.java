package gui;

import java.util.Scanner;

import pkg.Cidade_jomp;

public class Main {

	public static void main(final String[] args) {
		Scanner scan = new Scanner(System.in);
		try {
				System.out.print("Digite a quantidade de meses a serem simulados: ");
				int quantMeses = Integer.parseInt(scan.nextLine());
				Cidade_jomp cidade = new Cidade_jomp(quantMeses);
				cidade.execute();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			scan.close();
		}
	}
	
}
