package jkind;

import java.io.IOException;

import jkind.lustre.LustreLexer;
import jkind.lustre.LustreParser;
import jkind.lustre.Node;
import jkind.processes.Director;
import jkind.slicing.Slicer;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

public class Main {
	public static void main(String args[]) throws IOException, RecognitionException, InterruptedException {
		Node node = parseLustre(args[0]);
		node = Slicer.slice(node);
		new Director(args[0], node).run();
		System.exit(0); // Kills all threads
	}

	private static Node parseLustre(String filename) throws IOException, RecognitionException {
		CharStream stream = new ANTLRFileStream(filename);
		LustreLexer lexer = new LustreLexer(stream);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		LustreParser parser = new LustreParser(tokens);
		Node node = parser.node();
		if (parser.getNumberOfSyntaxErrors() > 0) {
			System.out.println("Parse error in " + filename);
			System.exit(-1);
		}
		return node;
	}
}