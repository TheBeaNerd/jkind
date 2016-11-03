package jkind.lustre.visitors;

import jkind.lustre.Expr;
import jkind.lustre.IndexedIdExpr;
import jkind.lustre.IdExpr;

public class IndexedAstMapVisitor extends AstMapVisitor implements IndexedExprVisitor<Expr> {
	public Expr visit(IndexedIdExpr e) {
		Expr res = visit((IdExpr) e);
		if (res instanceof IdExpr) {
			res = new IndexedIdExpr((IdExpr) res,e.index);
		}
		return res;
	}
}
