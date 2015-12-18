package Parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Parser {
	
	private static ArrayList<Nodo> cola;
	private static String[] handles;
		
	public Parser() {
		super();
		
		cola = new ArrayList<Nodo>();
		
		BufferedReader input = null;

		try {

			File inputFile = new File("handles.txt");

			input = new BufferedReader(new FileReader(inputFile));

			String toHand = "";
			while (input.ready()) {
				toHand += input.readLine() + "|";
			}
			handles = toHand.split("\\|");

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (input != null)
					input.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	public Nodo pop(){
		return cola.remove(0);
	}
	
	public void push(Nodo nodo){
		cola.add(nodo);
	}
	
	public Nodo shift(String dato, String tokenclass){

		if(tokenclass.matches("EOL"))
			return new Nodo(null,null);
		Nodo nuevo = new Nodo(dato.substring(1), tokenclass);
		cola.add(nuevo);
		
		Nodo exp = reduce(cola);
		if(exp.getInfo() != null) {
			for(Nodo nodo:cola){
				nodo.setPadre(exp);
				exp.addHijo(nodo);
			}
			cola.clear();
		}
		if(nuevo.getInfo().matches(";|EOF") && exp.getInfo() == null){
			exp = new Nodo(cola.get(cola.size() - 2).getDato(), "error");
			cola.clear();
		}
		return exp;
	}

	public Nodo reduce(ArrayList<Nodo> cola){
		String produccion = "";
		for(Nodo nodo : cola){
			produccion += nodo.getInfo() + " ";
		}
		
		String expresion = "";
		expresion = check(produccion);
		
		if(expresion != ""){
			Nodo padre = new Nodo(null, expresion);	
			return padre;
		}
		return new Nodo(null,null);
	}
	
	public String check(String toReduce){
		String expresion = null;
		for(String produccion:handles){
			if(produccion.matches("#.*"))
				expresion = produccion;
			if(toReduce.trim().equals(produccion))
				return expresion;
		}
		return null;
	}

}
