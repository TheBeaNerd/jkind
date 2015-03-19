package jkind.translation;

import static jkind.lustre.LustreUtil.arrow;
import static jkind.lustre.LustreUtil.pre;
import jkind.lustre.BinaryExpr;
import jkind.lustre.BinaryOp;
import jkind.lustre.CondactExpr;
import jkind.lustre.Expr;
import jkind.lustre.IdExpr;
import jkind.lustre.Node;
import jkind.lustre.NodeCallExpr;
import jkind.lustre.UnaryExpr;
import jkind.lustre.UnaryOp;
import jkind.lustre.visitors.AstMapVisitor;

public class DistributePre extends AstMapVisitor {
	private int pres = 0;
	
	public static Node node(Node node) {
		return new DistributePre().visit(node);
	}
	
	private Expr applyPres(Expr e) {
		Expr result = e;
		for (int i = 0; i < pres; i++) {
			result = pre(result);
		}
		return result;
	}
	
	@Override
	public Expr visit(BinaryExpr e) {
		if (e.op == BinaryOp.ARROW) {
			Expr left = e.left.accept(new DistributePre());
			Expr right = e.right.accept(new DistributePre());
			return applyPres(arrow(left, right));
		} else {
			return super.visit(e);
		}
	}

	@Override
	public Expr visit(CondactExpr e) {
		return applyPres(e);
	}

	@Override
	public Expr visit(IdExpr e) {
		return applyPres(e);
	}

	@Override
	public Expr visit(NodeCallExpr e) {
		return applyPres(e);
	}

	@Override
	public Expr visit(UnaryExpr e) {
		if (e.op == UnaryOp.PRE) {
			pres++;
			Expr result = e.expr.accept(this);
			pres--;
			return result;
		} else {
			return super.visit(e);
		}
	}
}
