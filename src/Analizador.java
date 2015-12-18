import Lexer.AnalizadorLex;
import Parser.AnalizadorSin;
import TypeCheck.AnalizadorSem;

public class Analizador {

	public static void main(String[] args) {
		AnalizadorLex lexer = new AnalizadorLex();
		AnalizadorSin parser = new AnalizadorSin();
		AnalizadorSem typecheck = new AnalizadorSem();
		
		lexer.lex();
		parser.parse();		
		typecheck.typeCheck();
		

	}

}
