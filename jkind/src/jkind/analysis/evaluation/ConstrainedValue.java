package jkind.analysis.evaluation;

import java.util.TreeSet;

import jkind.lustre.Expr;
import jkind.lustre.values.EvaluatableValue;
import jkind.lustre.values.ValueInterval;
import jkind.util.UnboundFraction;

public class ConstrainedValue extends BoundValue {

	private final static TreeSet<Integer> emptySet = new TreeSet<>();
	
	public ConstrainedValue(IndexedEvaluator evaluator) {
		super(evaluator,emptySet,emptySet);
	}

	@Override
	public EvaluatableValue getValue() {
		return evaluator.trueValue();
	}

	@Override
	public boolean stepValue(Expr expr, ContainsEvaluatableValue[] preState, ContainsEvaluatableValue[] currState) {
		evaluator.updatePreState(preState);
		evaluator.updateCurrState(currState);
		EvaluatableValue newValue = (EvaluatableValue) expr.accept(evaluator);
		return (! newValue.isTrue());
	}

	@Override
	public boolean initValue(Expr expr, ContainsEvaluatableValue[] preState) {
		evaluator.updatePreState(preState);
		EvaluatableValue newValue = (EvaluatableValue) expr.accept(evaluator.initExtendedEvaluator);
		return (! newValue.isTrue());
	}

	@Override
	public boolean setValue(EvaluatableValue value) {
		assert(false);
		return false;
	}

}
