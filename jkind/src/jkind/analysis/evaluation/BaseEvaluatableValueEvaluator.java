package jkind.analysis.evaluation;

import jkind.lustre.BinaryExpr;
import jkind.lustre.BoolExpr;
<<<<<<< Updated upstream
=======
import jkind.lustre.CastExpr;
>>>>>>> Stashed changes
import jkind.lustre.Expr;
import jkind.lustre.IfThenElseExpr;
import jkind.lustre.IntExpr;
import jkind.lustre.RealExpr;
import jkind.lustre.UnaryExpr;
import jkind.lustre.values.EvaluatableValue;
import jkind.lustre.values.Value;

public abstract class BaseEvaluatableValueEvaluator extends Evaluator {
	
	@Override
	public Value visit(BinaryExpr e) {
		//System.out.println("BinaryExpr : " + e);
		Expr leftExpr = e.left;
		Expr rightExpr = e.right;
		EvaluatableValue leftValue = (EvaluatableValue) leftExpr.accept(this);
		EvaluatableValue rightValue = (EvaluatableValue) rightExpr.accept(this);			
		return leftValue.applyBinaryOp(e.op,rightValue);
	}

	@Override
	public Value visit(UnaryExpr e) {
		//System.out.println("UnaryExpr : " + e);
		EvaluatableValue z = ((EvaluatableValue) e.expr.accept(this));
		return z.applyUnaryOp(e.op);
	}

	@Override
	public Value visit(IfThenElseExpr e) {
		Expr testExpr = e.cond;
		Expr thenExpr = e.thenExpr;
		Expr elseExpr = e.elseExpr;
		EvaluatableValue testValue = (EvaluatableValue) testExpr.accept(this);
		EvaluatableValue thenValue = (EvaluatableValue) thenExpr.accept(this);
		EvaluatableValue elseValue = (EvaluatableValue) elseExpr.accept(this);
		return testValue.ite(thenValue,elseValue);
	}
	
	@Override
<<<<<<< Updated upstream
=======
	public EvaluatableValue visit(CastExpr e) {
		EvaluatableValue res = (EvaluatableValue) e.expr.accept(this);
		return res.cast(e.type);
	}
	
	@Override
>>>>>>> Stashed changes
	abstract public EvaluatableValue visit(IntExpr e);

	@Override
	abstract public EvaluatableValue visit(RealExpr e);

	@Override
	abstract public EvaluatableValue visit(BoolExpr e);
	
}
