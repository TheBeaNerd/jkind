package jkind.analysis.evaluation;

import java.util.SortedSet;

import jkind.lustre.Expr;
import jkind.lustre.values.EvaluatableValue;

public class ComputedValue extends InputValue {

	public ComputedValue(IndexedEvaluator evaluator, SortedSet<Integer> defSet, SortedSet<Integer> nextSet) {
		super(evaluator,null,defSet,nextSet);
	}

	@Override
	public boolean setValue(EvaluatableValue value) {
		assert(false);
		return false;
	}
	
	@Override
	public boolean stepValue(Expr expr, ContainsEvaluatableValue[] preState, ContainsEvaluatableValue[] currState) {
		evaluator.updatePreState(preState);
		evaluator.updateCurrState(currState);
		EvaluatableValue newValue = (EvaluatableValue) expr.accept(evaluator);
		EvaluatableValue oldValue = value;
		value = newValue;
		return (! newValue.equals(oldValue));
	}

	@Override
	public boolean initValue(Expr expr, ContainsEvaluatableValue[] preState) {
		evaluator.updatePreState(preState);
		EvaluatableValue newValue = (EvaluatableValue) expr.accept(evaluator.initExtendedEvaluator);
		EvaluatableValue oldValue = value;
		value = newValue;
		return (! newValue.equals(oldValue));
	}

}

