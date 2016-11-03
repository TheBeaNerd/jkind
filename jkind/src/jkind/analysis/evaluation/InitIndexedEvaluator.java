package jkind.analysis.evaluation;

import jkind.lustre.IndexedIdExpr;

import java.util.Arrays;
import jkind.lustre.IdExpr;
import jkind.lustre.values.Value;

public class InitIndexedEvaluator extends InitEvaluatableValueEvaluator {

	public InitIndexedEvaluator(BaseEvaluatableValueEvaluator evaluator) {
		super(evaluator);
	}

	ContainsEvaluatableValue preState[];
	
	@Override
	public Value visit(IdExpr e) {
		Value res = preState[((IndexedIdExpr) e).index].getValue();
		if (res == null) {
			System.out.println("Unbound Variable : " + e.id + " with index " + ((IndexedIdExpr) e).index);
			System.out.println("Current State : " + Arrays.toString(preState));
			assert(false);
		}
		return res;
	}

}
