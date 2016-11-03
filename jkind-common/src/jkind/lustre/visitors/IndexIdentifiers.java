package jkind.lustre.visitors;

import java.util.Map;

import jkind.lustre.Expr;
import jkind.lustre.IndexedIdExpr;
import jkind.lustre.IdExpr;
import jkind.lustre.Node;

public class IndexIdentifiers extends IndexedAstMapVisitor {

	private static Map<String,Integer> nameToIndex;
	
	public static Node indexIdentifiers(Node node, Map<String,Integer> nameToIndex) {
		IndexIdentifiers.nameToIndex = nameToIndex;
		//System.out.println("nameToIndex : " + nameToIndex.toString());
		return (Node) node.accept(new IndexIdentifiers());
	}

	public Expr visit(IdExpr e) {
		String name = e.id;
		Integer index = nameToIndex.get(name);
		if (index == null) System.out.println("IndexIdentifer Missing Name : " + name);
		assert(index != null);
		return new IndexedIdExpr((IdExpr) e,index);
	}
	
}
