package jkind.analysis.evaluation;

import java.util.SortedSet;
import java.util.TreeSet;

import jkind.lustre.Expr;
import jkind.lustre.values.EvaluatableValue;

public abstract class BoundValue implements ContainsEvaluatableValue {
	// Before use the visitor must be bound;
	protected IndexedEvaluator evaluator;
	public final SortedSet<Integer> defSet;
	public final SortedSet<Integer> nextSet;
	public static final SortedSet<Integer> EMPTY = new TreeSet<Integer>();
	public BoundValue(IndexedEvaluator evaluator, SortedSet<Integer> defSet, SortedSet<Integer> nextSet) {
		this.evaluator = evaluator;
		this.defSet = (defSet == null) ? EMPTY : defSet;
		this.nextSet = (nextSet == null) ? EMPTY : nextSet;
	}
	abstract public boolean stepValue(Expr expr, ContainsEvaluatableValue preState[], ContainsEvaluatableValue currState[]);
	abstract public boolean initValue(Expr expr, ContainsEvaluatableValue binding[]);
	abstract public EvaluatableValue getValue();
	abstract public boolean setValue(EvaluatableValue value);

	@Override
	public String toString() {
		return getValue().toString();
	}

}

