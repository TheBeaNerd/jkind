package jkind.analysis.evaluation;

import java.util.SortedSet;

import jkind.lustre.Expr;
import jkind.lustre.values.EvaluatableValue;

public class InputValue extends BoundValue {

	protected EvaluatableValue value;

	public InputValue(IndexedEvaluator evaluator, EvaluatableValue value, SortedSet<Integer> defSet, SortedSet<Integer> nextSet) {
		super(evaluator,defSet,nextSet);
		this.value = value;
	}

	@Override
	public EvaluatableValue getValue() {
		assert(value != null);
		return value;
	}

	@Override
	public boolean stepValue(Expr expr, ContainsEvaluatableValue[] preState, ContainsEvaluatableValue[] currState) {
		return true;
	}

	@Override
	public boolean initValue(Expr expr, ContainsEvaluatableValue[] binding) {
		return true;
	}

	@Override
	public boolean setValue(EvaluatableValue value) {
		assert(value != null);
		this.value = value;
		return true;
	}
	
}
