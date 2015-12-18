package TypeCheck;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class AnalizadorSem {
	
	private int linea;
	
	public void typeCheck(){
		
		BufferedReader input = null;
		BufferedReader simbolos = null;

		linea = 1;

		try {

			File inputFile = new File("output.csv");

			input = new BufferedReader(new FileReader(inputFile));

			File tablas = new File("simbolos.csv");

			simbolos = new BufferedReader(new FileReader(tablas));

			while (input.ready()) {
				String tokens[] = input.readLine().split("\",");
				
				if(tokens[1].matches("EOL")){
					linea++;
				}
				
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (input != null)
					input.close();
				if (simbolos != null)
					simbolos.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

}
