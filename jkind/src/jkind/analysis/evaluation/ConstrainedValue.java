package jkind.analysis.evaluation;

import java.util.TreeSet;

import jkind.lustre.Expr;
import jkind.lustre.NamedType;
import jkind.lustre.Type;
import jkind.lustre.values.EvaluatableValue;
import jkind.lustre.values.ValueInterval;
import jkind.util.UnboundFraction;

public class ConstrainedValue extends BoundValue {

	private final static TreeSet<Integer> emptySet = new TreeSet<>();
	private boolean polarity;
	
	public ConstrainedValue(boolean polarity, IndexedEvaluator evaluator) {
		super(evaluator,NamedType.BOOL,emptySet,emptySet);
		this.polarity = polarity;
	}

	@Override
	public EvaluatableValue getValue() {
		return polarity ? evaluator.trueValue() : evaluator.falseValue();
	}

	boolean checkValue(EvaluatableValue value) {
		return polarity ? value.isTrue() : value.isFalse();
	}
	
	@Override
	public int stepValue(Expr expr, ContainsEvaluatableValue[] preState, ContainsEvaluatableValue[] currState) {
		evaluator.updatePreState(preState);
		evaluator.updateCurrState(currState);
		EvaluatableValue newValue = (EvaluatableValue) expr.accept(evaluator);
		// Either no change or violation
		return checkValue(newValue) ? 0 : -1;
	}

	@Override
	public int initValue(Expr expr, ContainsEvaluatableValue[] preState) {
		evaluator.updatePreState(preState);
		EvaluatableValue newValue = (EvaluatableValue) expr.accept(evaluator.initExtendedEvaluator);
		// Either no change or violation
		return checkValue(newValue) ? 0 : -1;
	}

	@Override
	public boolean setValue(EvaluatableValue value) {
		assert(checkValue(value));
		return false;
	}

}
