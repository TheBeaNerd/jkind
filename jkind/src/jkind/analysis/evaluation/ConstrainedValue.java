package jkind.analysis.evaluation;

import java.util.TreeSet;

import jkind.lustre.Expr;
<<<<<<< Updated upstream
=======
import jkind.lustre.NamedType;
import jkind.lustre.Type;
>>>>>>> Stashed changes
import jkind.lustre.values.EvaluatableValue;
import jkind.lustre.values.ValueInterval;
import jkind.util.UnboundFraction;

public class ConstrainedValue extends BoundValue {

	private final static TreeSet<Integer> emptySet = new TreeSet<>();
<<<<<<< Updated upstream
	
	public ConstrainedValue(IndexedEvaluator evaluator) {
		super(evaluator,emptySet,emptySet);
=======
	private boolean polarity;
	
	public ConstrainedValue(boolean polarity, IndexedEvaluator evaluator) {
		super(evaluator,NamedType.BOOL,emptySet,emptySet);
		this.polarity = polarity;
>>>>>>> Stashed changes
	}

	@Override
	public EvaluatableValue getValue() {
<<<<<<< Updated upstream
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
=======
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
>>>>>>> Stashed changes
	}

	@Override
	public boolean setValue(EvaluatableValue value) {
<<<<<<< Updated upstream
		assert(false);
=======
		assert(checkValue(value));
>>>>>>> Stashed changes
		return false;
	}

}
