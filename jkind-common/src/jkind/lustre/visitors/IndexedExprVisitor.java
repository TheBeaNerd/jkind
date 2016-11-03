package jkind.lustre.visitors;

import jkind.lustre.IndexedIdExpr;

public interface IndexedExprVisitor<T> extends ExprVisitor<T> {
	public T visit(IndexedIdExpr e);
}
