package jkind.analysis.evaluation;

import jkind.lustre.BinaryExpr;
import jkind.lustre.BinaryOp;
import jkind.lustre.UnaryExpr;
import jkind.lustre.UnaryOp;
import jkind.lustre.values.Value;

public abstract class EvaluatableValueEvaluator extends BaseEvaluatableValueEvaluator {

	protected InitEvaluatableValueEvaluator initExtendedEvaluator;
	
	public EvaluatableValueEvaluator() {
		this.initExtendedEvaluator = new InitEvaluatableValueEvaluator(this);
	}
	
	@Override
	public Value visit(BinaryExpr e) {
		if (e.op.equals(BinaryOp.ARROW)) {
			return e.right.accept(this);
		}
		return super.visit(e);
	}

	@Override
	public Value visit(UnaryExpr e) {
		if (e.op.equals(UnaryOp.PRE)) {
			return e.expr.accept(initExtendedEvaluator);
		}
		return super.visit(e);
	}

}
