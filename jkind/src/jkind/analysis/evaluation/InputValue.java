package jkind.analysis.evaluation;

import java.util.SortedSet;

import jkind.lustre.Expr;
<<<<<<< Updated upstream
=======
import jkind.lustre.Type;
>>>>>>> Stashed changes
import jkind.lustre.values.EvaluatableValue;

public class InputValue extends BoundValue {

	protected EvaluatableValue value;

<<<<<<< Updated upstream
	public InputValue(IndexedEvaluator evaluator, EvaluatableValue value, SortedSet<Integer> defSet, SortedSet<Integer> nextSet) {
		super(evaluator,defSet,nextSet);
=======
	public InputValue(IndexedEvaluator evaluator, EvaluatableValue value, Type type, SortedSet<Integer> defSet, SortedSet<Integer> nextSet) {
		super(evaluator,type,defSet,nextSet);
>>>>>>> Stashed changes
		this.value = value;
	}

	@Override
	public EvaluatableValue getValue() {
		assert(value != null);
		return value;
	}

	@Override
<<<<<<< Updated upstream
	public boolean stepValue(Expr expr, ContainsEvaluatableValue[] preState, ContainsEvaluatableValue[] currState) {
		return true;
	}

	@Override
	public boolean initValue(Expr expr, ContainsEvaluatableValue[] binding) {
		return true;
=======
	public int stepValue(Expr expr, ContainsEvaluatableValue[] preState, ContainsEvaluatableValue[] currState) {
		// Always changes
		return 1;
	}

	@Override
	public int initValue(Expr expr, ContainsEvaluatableValue[] binding) {
		// Always changes
		return 1;
>>>>>>> Stashed changes
	}

	@Override
	public boolean setValue(EvaluatableValue value) {
		assert(value != null);
		this.value = value;
		return true;
	}
	
}
