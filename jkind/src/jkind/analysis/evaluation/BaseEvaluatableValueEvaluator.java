package jkind.analysis.evaluation;

import jkind.lustre.BinaryExpr;
import jkind.lustre.BoolExpr;
import jkind.lustre.CastExpr;
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
		// System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx  | BinaryExpr : " + e);
		Expr leftExpr = e.left;
		Expr rightExpr = e.right;
		EvaluatableValue leftValue = (EvaluatableValue) leftExpr.accept(this);
		EvaluatableValue rightValue = (EvaluatableValue) rightExpr.accept(this);			
		Value res = leftValue.applyBinaryOp(e.op,rightValue);
		//System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx  | (" + leftValue + " " + e.op + " " + rightValue + ") = " + res);
		return res;
	}

	@Override
	public Value visit(UnaryExpr e) {
		//System.out.println("UnaryExpr : " + e);
		EvaluatableValue z = ((EvaluatableValue) e.expr.accept(this));
		Value res = z.applyUnaryOp(e.op);
		//System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx  | (" + e.op + " " + z + ") = " + res);
		return res;
	}

	@Override
	public Value visit(IfThenElseExpr e) {
		Expr testExpr = e.cond;
		Expr thenExpr = e.thenExpr;
		Expr elseExpr = e.elseExpr;
		EvaluatableValue testValue = (EvaluatableValue) testExpr.accept(this);
		EvaluatableValue thenValue = (EvaluatableValue) thenExpr.accept(this);
		EvaluatableValue elseValue = (EvaluatableValue) elseExpr.accept(this);
		Value res = testValue.ite(thenValue,elseValue);
		//System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx  | (" + testValue + " ? " + thenValue + " : " + elseValue + ") = " + res);
		return res;
	}
	
	@Override
	public EvaluatableValue visit(CastExpr e) {
		EvaluatableValue res = (EvaluatableValue) e.expr.accept(this);
		return res.cast(e.type);
	}
	
	@Override
	abstract public EvaluatableValue visit(IntExpr e);

	@Override
	abstract public EvaluatableValue visit(RealExpr e);

	@Override
	abstract public EvaluatableValue visit(BoolExpr e);
	
}
