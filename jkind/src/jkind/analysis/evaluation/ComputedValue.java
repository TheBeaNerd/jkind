package jkind.analysis.evaluation;

import java.util.SortedSet;

import jkind.lustre.Expr;
<<<<<<< Updated upstream
=======
import jkind.lustre.Type;
>>>>>>> Stashed changes
import jkind.lustre.values.EvaluatableValue;

public class ComputedValue extends InputValue {

<<<<<<< Updated upstream
	public ComputedValue(IndexedEvaluator evaluator, SortedSet<Integer> defSet, SortedSet<Integer> nextSet) {
		super(evaluator,null,defSet,nextSet);
=======
	public ComputedValue(IndexedEvaluator evaluator, Type type, SortedSet<Integer> defSet, SortedSet<Integer> nextSet) {
		super(evaluator,null,type,defSet,nextSet);
>>>>>>> Stashed changes
	}

	@Override
	public boolean setValue(EvaluatableValue value) {
		assert(false);
		return false;
	}
	
	@Override
<<<<<<< Updated upstream
	public boolean stepValue(Expr expr, ContainsEvaluatableValue[] preState, ContainsEvaluatableValue[] currState) {
=======
	public int stepValue(Expr expr, ContainsEvaluatableValue[] preState, ContainsEvaluatableValue[] currState) {
>>>>>>> Stashed changes
		evaluator.updatePreState(preState);
		evaluator.updateCurrState(currState);
		EvaluatableValue newValue = (EvaluatableValue) expr.accept(evaluator);
		EvaluatableValue oldValue = value;
		value = newValue;
<<<<<<< Updated upstream
		return (! newValue.equals(oldValue));
	}

	@Override
	public boolean initValue(Expr expr, ContainsEvaluatableValue[] preState) {
=======
		return (! newValue.equals(oldValue)) ? 1 : 0;
	}

	@Override
	public int initValue(Expr expr, ContainsEvaluatableValue[] preState) {
>>>>>>> Stashed changes
		evaluator.updatePreState(preState);
		EvaluatableValue newValue = (EvaluatableValue) expr.accept(evaluator.initExtendedEvaluator);
		EvaluatableValue oldValue = value;
		value = newValue;
<<<<<<< Updated upstream
		return (! newValue.equals(oldValue));
=======
		return (! newValue.equals(oldValue)) ? 1 : 0;
>>>>>>> Stashed changes
	}

}

