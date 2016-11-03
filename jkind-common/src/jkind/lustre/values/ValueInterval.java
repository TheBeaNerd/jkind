package jkind.lustre.values;

import java.math.BigInteger;

import jkind.util.BigFraction;
import jkind.util.UnboundFraction;

public class ValueInterval extends EvaluatableValue {

	UnboundFraction min;
	UnboundFraction max;
	
	public static final ValueInterval UnboundInterval = new ValueInterval(UnboundFraction.NEGATIVE_INFINITY,UnboundFraction.POSITIVE_INFINITY);
	
	public ValueInterval(UnboundFraction min, UnboundFraction max) {
		assert(min.compareTo(max) <= 0);
		assert(min != UnboundFraction.INDETERMINATE);
		assert(max != UnboundFraction.INDETERMINATE);
		this.min = min;
		this.max = max;
	}

	public ValueInterval(UnboundFraction value) {
		this(value,value);
	}

	public ValueInterval(ValueInterval arg) {
		this(arg.min,arg.max);
	}
	
	private static UnboundFraction not(UnboundFraction x) {
		return UnboundFraction.ONE.subtract(x);
	}
	
	private static UnboundFraction or(UnboundFraction x, UnboundFraction y) {
		UnboundFraction sum = x.add(y);	
		return sum.equals(UnboundFraction.ZERO) ? UnboundFraction.ZERO : UnboundFraction.ONE;
	}
	
	private static UnboundFraction and(UnboundFraction x, UnboundFraction y) {
		return not(or(not(x),not(x)));
	}	
	
	@Override
	public String toString() {
		return "[" + min + "," + max + "]";
	}
	
	//
	// == Abstract Method Implementations ===================================
	//
	
	@Override
	public ValueInterval plus(EvaluatableValue arg) {
		ValueInterval right = (ValueInterval) arg;
		UnboundFraction rmin = min.add(right.min);
		UnboundFraction rmax = max.add(right.max);
		return new ValueInterval(rmin,rmax);
	}
	
	@Override
	public ValueInterval and(EvaluatableValue arg) {
		ValueInterval right = (ValueInterval) arg;
		UnboundFraction rmin = and(min,right.min);
		UnboundFraction rmax = and(max,right.max);
		return new ValueInterval(rmin,rmax);
	}
	
	@Override
	public ValueInterval or(EvaluatableValue arg) {
		ValueInterval right = (ValueInterval) arg;
		UnboundFraction rmin = or(min,right.min);
		UnboundFraction rmax = or(max,right.max);
		return new ValueInterval(rmin,rmax);
	}
	
	@Override
	public ValueInterval not() {
		return new ValueInterval(not(max),not(min));
	}
	
	@Override
	public ValueInterval negate() {
		return new ValueInterval(max.negate(),min.negate());
	}

	@Override
	public ValueInterval multiply(EvaluatableValue arg) {
		ValueInterval right = (ValueInterval) arg;
		UnboundFraction minmax = min.multiply(right.max);
		UnboundFraction minmin = min.multiply(right.min);
		UnboundFraction maxmax = max.multiply(right.max);
		UnboundFraction maxmin = max.multiply(right.max);
		UnboundFraction minres = minmax.min(minmin).min(maxmax).min(maxmin);
		UnboundFraction maxres = minmax.max(minmin).max(maxmax).max(maxmin);
		return new ValueInterval(minres,maxres);
	}

	@Override
	public ValueInterval inverse() {
		int minsign = min.compareTo(UnboundFraction.ZERO);
		int maxsign = UnboundFraction.ZERO.compareTo(max);
		if ((minsign <= 0) && (0 <= maxsign)) {
			return UnboundInterval;
		}
		return new ValueInterval(max.inverseLimit(-1),min.inverseLimit(+1));
	}
	
	@Override
	public ValueInterval equalop(EvaluatableValue arg) {
		ValueInterval right = (ValueInterval) arg;
		// If the ranges no not overlap, then our max is zero.  Else it includes one.
		UnboundFraction rmax = ((max.compareTo(right.min) < 0) || (right.max.compareTo(min) < 0)) ?
				               UnboundFraction.ZERO : UnboundFraction.ONE;
		// If they are all exactly the same, then our minimum is one.  Else it includes zero.
		UnboundFraction rmin = ((min.compareTo(right.min) == 0) &&
						        (max.compareTo(right.max) == 0) &&
						        (min.compareTo(max) == 0)) ? 
						        UnboundFraction.ONE : UnboundFraction.ZERO;
		return new ValueInterval(rmin,rmax);
	}

	@Override
	public ValueInterval less(EvaluatableValue arg) {
		ValueInterval right = (ValueInterval) arg;
		// If the right.max is always less than this.min our max is zero.  Else it includes one.
		UnboundFraction rmax = (right.max.compareTo(min) < 0) ? UnboundFraction.ZERO : UnboundFraction.ONE;
		// If this.max is always less than right.min then our min is true.  Else it includes zero.
		UnboundFraction rmin = (max.compareTo(right.min) < 0) ? UnboundFraction.ONE : UnboundFraction.ZERO;
		return new ValueInterval(rmin,rmax);
	}

	@Override
	public ValueInterval greater(EvaluatableValue arg) {
		ValueInterval right = (ValueInterval) arg;
		// If the right.min is always greater than this.max our max is zero.  Else it includes one.
		UnboundFraction rmax = (right.min.compareTo(max) > 0) ? UnboundFraction.ZERO : UnboundFraction.ONE;
		// If this.min is always greater than right.max then our min is true.  Else it includes zero.
		UnboundFraction rmin = (max.compareTo(right.min) < 0) ? UnboundFraction.ONE : UnboundFraction.ZERO;
		return new ValueInterval(rmin,rmax);
	}

	@Override
	public ValueInterval truncate() {
		return new ValueInterval(min.truncateLimit(+1),max.truncateLimit(-1));
	}
	
	@Override
	public ValueInterval modulus(EvaluatableValue modArg) {
		ValueInterval modulusInterval = (ValueInterval) modArg;
		if (modulusInterval.min.equals(modulusInterval.max)) {
			UnboundFraction modulus = modulusInterval.min;
			if (modulus.compareTo(UnboundFraction.ZERO) < 0) {
				if ((modulus.compareTo(min) < 0) && (max.compareTo(UnboundFraction.ZERO) <= 0)) {
					return this;
				} else {
					return new ValueInterval(modulus,UnboundFraction.ZERO);
				}
			} else {
				if ((max.compareTo(modulus) < 0) && (UnboundFraction.ZERO.compareTo(min) <= 0)) {
					return this;
				} else {
					return new ValueInterval(UnboundFraction.ZERO,modulus);
				}
			}
		}
		return new ValueInterval(modulusInterval.min.min(UnboundFraction.ZERO),modulusInterval.max.max(UnboundFraction.ZERO));
	}
	
	@Override
	public ValueInterval ite(EvaluatableValue arg1, EvaluatableValue arg2) {
		ValueInterval left = (ValueInterval) arg1;
		ValueInterval right = (ValueInterval) arg2;
		if (min.equals(max)) {
			return (min.equals(UnboundFraction.ZERO)) ? right : left;
		}
		return new ValueInterval(right.min.min(left.min),right.max.max(left.max));
	}

	@Override
	public ValueInterval pre() {
		assert(false);
		return this;
	}

	@Override
	public ValueInterval arrow(EvaluatableValue right) {
		assert(false);
		return this;
	}

	@Override
	public boolean isTrue() {
		return (min.equals(UnboundFraction.ONE) && max.equals(UnboundFraction.ONE));
	}

	@Override
	public boolean isFalse() {
		return (min.equals(UnboundFraction.ZERO) && max.equals(UnboundFraction.ZERO));
	}

	@Override
	public EvaluatableValue valueOf(BigInteger value) {
		return new ValueInterval(new UnboundFraction(value));
	}

	@Override
	public EvaluatableValue valueOf(BigFraction value) {
		return new ValueInterval(new UnboundFraction(value));
	}

	@Override
	public EvaluatableValue valueOf(boolean value) {
		return new ValueInterval(new UnboundFraction(value ? BigFraction.ONE : BigFraction.ZERO));
	}

}
