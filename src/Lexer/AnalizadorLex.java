package Lexer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class AnalizadorLex {

	private int linea;

	public void lex() {

		ComparadorLex comparador = new ComparadorLex();

		BufferedReader input = null;
		BufferedWriter output = null;

		linea = 0;

		try {

			File inputFile = new File("ejemplo.c");

			input = new BufferedReader(new FileReader(inputFile));

			File outputFile = new File("output.csv");

			output = new BufferedWriter(new FileWriter(outputFile));

			while (input.ready()) {
				output.write(comparador.analizarLinea(input.readLine()));
				linea++;
				if (comparador.hayError()) {
					System.out.println("Error lexico en la linea " + linea);
					comparador.setError(false);
				}
			}
			
			output.write("\"\",EOF");

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (input != null)
					input.close();
				if (output != null)
					output.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

	}

}
