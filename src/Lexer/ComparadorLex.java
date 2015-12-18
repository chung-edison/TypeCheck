package Lexer;

public class ComparadorLex {
	
	//diccionarios
	private static final String tipos = "int|float|bool|char|string";

	private static final String signos = "\\+|-|\\*|/";

	private static final String compuestos = "\\+\\+|--";
	
	private static final String texto = "(?:\".*\")|([0-9]|[a-z]|[A-Z]|_)+";
	
	private boolean error;  //bandera de error para determinar la linea donde ocurrio el error
	
	private boolean comment;	//bandera para ignorar comentarios
	
	private String n = System.getProperty("line.separator"); //obtiene el separador de linea del sistema
	
	public boolean hayError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public String analizarLinea(String line){
		if(line.matches(".*\\*/")) {
			comment = false;
			return "";
		}
		if(line.matches("//.*")||comment) return "";
		if(line.matches("/\\*.*")) comment = true;
		line = line.replaceAll(texto, " $0 ").trim(); 
			//agrega espacios antes de cada id y num, es decir, identifica tokens y separa
		
		//System.out.println(line); //muestra la linea luego de haber añadido espacios
		String[] tokens = line.split("\\s+(?=([^\"]*\"[^\"]*\")*[^\"]*$)"); //separa el string en varios strings segun los espacios
																			//toma en cuenta que haya 0 o un numero par de comillas
		
		String result = "";
		for(String token : tokens){
			result += "\"" + token + "\"" + "," + tokenClass(token) + n; //formato de CSV
		}
		result += "\"EOL\",EOL" + n;
		return result;
	}
	
	public String tokenClass(String token) {
		if (token.matches(n)) { // fin de linea
			return "EOL";
		}else if (token.matches(tipos)) { // palabras reservadas
			return "tipo";
		} else if (token.matches(compuestos)) { // operadores compuestos
			return "unario";
		} else if (token.matches(":|;|=")) { //especiales
			return token;
		} else if (token.matches(signos)) { // signos
			return "signo";
		} else if (token.matches("[a-z]([0-9]|[a-z]|[A-Z]|_)*")) { // identificadores
			return "ident";
		}
		error = true;
		return "errorlex";
	}

}
