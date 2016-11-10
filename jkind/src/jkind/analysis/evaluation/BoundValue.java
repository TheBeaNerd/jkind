package jkind.analysis.evaluation;

import java.util.SortedSet;
import java.util.TreeSet;

import jkind.lustre.Expr;
<<<<<<< Updated upstream
=======
import jkind.lustre.Type;
>>>>>>> Stashed changes
import jkind.lustre.values.EvaluatableValue;

public abstract class BoundValue implements ContainsEvaluatableValue {
	// Before use the visitor must be bound;
	protected IndexedEvaluator evaluator;
	public final SortedSet<Integer> defSet;
	public final SortedSet<Integer> nextSet;
<<<<<<< Updated upstream
	public static final SortedSet<Integer> EMPTY = new TreeSet<Integer>();
	public BoundValue(IndexedEvaluator evaluator, SortedSet<Integer> defSet, SortedSet<Integer> nextSet) {
		this.evaluator = evaluator;
		this.defSet = (defSet == null) ? EMPTY : defSet;
		this.nextSet = (nextSet == null) ? EMPTY : nextSet;
	}
	abstract public boolean stepValue(Expr expr, ContainsEvaluatableValue preState[], ContainsEvaluatableValue currState[]);
	abstract public boolean initValue(Expr expr, ContainsEvaluatableValue binding[]);
=======
	public final Type type;
	public static final SortedSet<Integer> EMPTY = new TreeSet<Integer>();
	public BoundValue(IndexedEvaluator evaluator, Type type, SortedSet<Integer> defSet, SortedSet<Integer> nextSet) {
		this.evaluator = evaluator;
		this.type = type;
		this.defSet = (defSet == null) ? EMPTY : defSet;
		this.nextSet = (nextSet == null) ? EMPTY : nextSet;
	}

	// -1 : violation
	//  0 : no change
	// +1 : change
	abstract public int stepValue(Expr expr, ContainsEvaluatableValue preState[], ContainsEvaluatableValue currState[]);
	abstract public int initValue(Expr expr, ContainsEvaluatableValue binding[]);

>>>>>>> Stashed changes
	abstract public EvaluatableValue getValue();
	abstract public boolean setValue(EvaluatableValue value);

	@Override
	public String toString() {
		return getValue().toString();
	}

}

