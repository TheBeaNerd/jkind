package jkind.analysis.evaluation;

import java.util.SortedSet;

import jkind.lustre.Expr;
import jkind.lustre.Type;
import jkind.lustre.values.EvaluatableValue;

public class InputValue extends BoundValue {

	protected EvaluatableValue value;

	public InputValue(IndexedEvaluator evaluator, EvaluatableValue value, Type type, SortedSet<Integer> defSet, SortedSet<Integer> nextSet) {
		super(evaluator,type,defSet,nextSet);
		this.value = value;
	}

	@Override
	public EvaluatableValue getValue() {
		assert(value != null);
		return value;
	}

	@Override
	public int stepValue(Expr expr, ContainsEvaluatableValue[] preState, ContainsEvaluatableValue[] currState) {
		// Always changes
		return 1;
	}

	@Override
	public int initValue(Expr expr, ContainsEvaluatableValue[] binding) {
		// Always changes
		return 1;
	}

	@Override
	public boolean setValue(EvaluatableValue value) {
		assert(value != null);
		this.value = value;
		return true;
	}
	
}
