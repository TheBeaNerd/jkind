package jkind.analysis.evaluation;

import jkind.lustre.IndexedIdExpr;
import jkind.lustre.Type;

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
		assert(false);
		//System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx  | " + "  " + e.id + " = " + res);
		return res;
	}

	public void updateCurrState(ContainsEvaluatableValue currState[]) {
		this.currState = currState;
	}
	
	public void updatePreState(ContainsEvaluatableValue preState[]) {
		((InitIndexedEvaluator) this.initExtendedEvaluator).preState = preState;
	}
	
	public InputValue inputValue(EvaluatableValue value, Type type, SortedSet<Integer> defSet, SortedSet<Integer> nextSet) {
		return new InputValue(this,value,type,defSet,nextSet);
	}
	
	public ComputedValue computedValue(Type type, SortedSet<Integer> defSet, SortedSet<Integer> nextSet) {
		return new ComputedValue(this,type,defSet,nextSet);		
	}
	
	public ConstrainedValue constrainedValue(boolean polarity) {
		return new ConstrainedValue(polarity,this);
	}
	
	abstract public EvaluatableValue trueValue();
	abstract public EvaluatableValue falseValue();
	
}
