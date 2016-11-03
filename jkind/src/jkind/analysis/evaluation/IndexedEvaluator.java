package jkind.analysis.evaluation;

import jkind.lustre.IndexedIdExpr;

import java.util.SortedSet;

import jkind.lustre.IdExpr;
import jkind.lustre.values.EvaluatableValue;
import jkind.lustre.values.Value;

public abstract class IndexedEvaluator extends EvaluatableValueEvaluator {

	private ContainsEvaluatableValue currState[];
	
	public IndexedEvaluator() {
		initExtendedEvaluator = new InitIndexedEvaluator(this);
	}

	@Override
	public Value visit(IdExpr e) {
		Value res = currState[((IndexedIdExpr) e).index].getValue();
		assert(res != null);
		return res;
	}

	public void updateCurrState(ContainsEvaluatableValue currState[]) {
		this.currState = currState;
	}
	
	public void updatePreState(ContainsEvaluatableValue preState[]) {
		((InitIndexedEvaluator) this.initExtendedEvaluator).preState = preState;
	}
	
	public InputValue inputValue(EvaluatableValue value, SortedSet<Integer> defSet, SortedSet<Integer> nextSet) {
		return new InputValue(this,value,defSet,nextSet);
	}
	
	public ComputedValue computedValue(SortedSet<Integer> defSet, SortedSet<Integer> nextSet) {
		return new ComputedValue(this,defSet,nextSet);		
	}
	
	public ConstrainedValue constrainedValue() {
		return new ConstrainedValue(this);
	}
	
	abstract public EvaluatableValue trueValue();
	
}
