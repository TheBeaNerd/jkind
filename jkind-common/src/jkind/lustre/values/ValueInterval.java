package jkind.lustre.values;

import java.math.BigInteger;

import jkind.lustre.NamedType;
import jkind.lustre.Type;
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

	private boolean isBoolean() {
		return isBoolean(min) && isBoolean(max);
	}
	
	public ValueInterval(UnboundFraction value) {
		this(value,value);
	}

	public ValueInterval(ValueInterval arg) {
		this(arg.min,arg.max);
	}
	
	public UnboundFraction getHigh() {
		return max;
	}
	
	public UnboundFraction getLow() {
		return min;
	}
	
	private static boolean isBoolean(UnboundFraction x) {
		return x.equals(UnboundFraction.ONE) || x.equals(UnboundFraction.ZERO);
	}
	
	private static UnboundFraction not(UnboundFraction x) {
		assert(isBoolean(x));
		return (x.signum() == 0)  ? UnboundFraction.ONE : UnboundFraction.ZERO;
	}
	
	private static UnboundFraction or(UnboundFraction x, UnboundFraction y) {
		assert(isBoolean(x));
		assert(isBoolean(y));
		return (x.signum() == 0) && (y.signum() == 0) ? UnboundFraction.ZERO : UnboundFraction.ONE;
	}
	
	private static UnboundFraction and(UnboundFraction x, UnboundFraction y) {
		assert(isBoolean(x));
		assert(isBoolean(y));
		return (x.signum() == 0) || (y.signum() == 0) ? UnboundFraction.ZERO : UnboundFraction.ONE;
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
		//System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx  | (" + this + " and " + arg + ") = " + "(" + rmin + "," + rmax + ")");
		ValueInterval res = new ValueInterval(rmin,rmax);
		//System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx  | (" + this + " and " + arg + ") = " + res);
		assert(res.isBoolean());
		return res;
	}
	
	@Override
	public ValueInterval or(EvaluatableValue arg) {
		ValueInterval right = (ValueInterval) arg;
		UnboundFraction rmin = or(min,right.min);
		UnboundFraction rmax = or(max,right.max);
		ValueInterval res = new ValueInterval(rmin,rmax);
		//System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx  | (" + this + " or " + arg + ") = " + res);
		assert(res.isBoolean());
		return res;
	}
	
	@Override
	public ValueInterval not() {
		ValueInterval res = new ValueInterval(not(max),not(min));
		//System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx  | (not " + this + ") = " + res);
		assert(res.isBoolean());
		return res;
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
		// (this == right) always if min=max always
		UnboundFraction rmin = ((min.compareTo(right.min) == 0) &&
				                (max.compareTo(right.max) == 0) &&
				                (min.compareTo(max) == 0)) ? 
						          UnboundFraction.ONE : UnboundFraction.ZERO;
		// (this != right) always if the ranges never overlap
		UnboundFraction rmax = ((max.compareTo(right.min) < 0) || (right.max.compareTo(min) < 0)) ?
				               UnboundFraction.ZERO : UnboundFraction.ONE;
		ValueInterval res = new ValueInterval(rmin,rmax);
		//System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx  | (" + this + " == " + arg + ") = " + res);
		assert(res.isBoolean());
		return res;
	}

	@Override
	public ValueInterval less(EvaluatableValue arg) {
		ValueInterval right = (ValueInterval) arg;
		// (this < right)  if this.max < right.min
		UnboundFraction rmin = (max.compareTo(right.min) <  0) ? UnboundFraction.ONE : UnboundFraction.ZERO;
		// !(this < right)  if right.max <= this.min
		UnboundFraction rmax = (right.max.compareTo(min) <= 0) ? UnboundFraction.ZERO : UnboundFraction.ONE;
		ValueInterval res = new ValueInterval(rmin,rmax);
		//System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx  | (" + this + " < " + arg + ") = " + res);
		assert(res.isBoolean());
		return res;
	}

	@Override
	public ValueInterval greater(EvaluatableValue arg) {
		ValueInterval right = (ValueInterval) arg;
		// (this > right)  if right.max < this.min
		UnboundFraction rmin = (right.max.compareTo(min) <  0) ? UnboundFraction.ONE : UnboundFraction.ZERO;
		// !(this > right)  if this.max <= right.min
		UnboundFraction rmax = (max.compareTo(right.min) <= 0) ? UnboundFraction.ZERO : UnboundFraction.ONE;
		ValueInterval res = new ValueInterval(rmin,rmax);
		//System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx  | (" + this + " > " + arg + ") = " + res);
		assert(res.isBoolean());
		return res;
	}

	@Override
	public ValueInterval truncate() {
		//System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx  | (" + min + " <= " + max + ")");
		UnboundFraction newmin = min.truncate();
		UnboundFraction newmax = max.truncate();
		//System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx  | (" + newmin + " <= " + newmax + ")");
		return new ValueInterval(newmin,newmax);
	}
	
	@Override
	public ValueInterval modulus(EvaluatableValue modArg) {
		ValueInterval modulusInterval = (ValueInterval) modArg;
		if (modulusInterval.min.equals(modulusInterval.max)) {
			if (min.equals(max)) {
				BigInteger val = min.floor();
				BigInteger mod = modulusInterval.min.floor();
				return new ValueInterval(new UnboundFraction(val.mod(mod)));
			}
			UnboundFraction modulus = modulusInterval.min;
			if (modulus.compareTo(UnboundFraction.ZERO) < 0) {
				if ((modulus.compareTo(min) < 0) && (max.compareTo(UnboundFraction.ZERO) <= 0)) {
					return this;
				} else {
					return new ValueInterval(modulus.add(UnboundFraction.ONE),UnboundFraction.ZERO);
				}
			} else {
				if ((max.compareTo(modulus) < 0) && (UnboundFraction.ZERO.compareTo(min) <= 0)) {
					return this;
				} else {
					return new ValueInterval(UnboundFraction.ZERO,modulus.subtract(UnboundFraction.ONE));
				}
			}
		}
		UnboundFraction modmin = modulusInterval.min.min(UnboundFraction.ZERO);
		UnboundFraction modmax = modulusInterval.max.max(UnboundFraction.ZERO);
		UnboundFraction minsign = (modmin.signum() < 0) ? UnboundFraction.ONE.negate() : UnboundFraction.ONE;
		UnboundFraction maxsign = (modmax.signum() < 0) ? UnboundFraction.ONE.negate() : UnboundFraction.ONE;
		UnboundFraction minmin = modmin.subtract(minsign);
		UnboundFraction maxmax = modmax.subtract(maxsign);
		return new ValueInterval(minmin,maxmax);
	}
	
	@Override
	public ValueInterval ite(EvaluatableValue arg1, EvaluatableValue arg2) {
		assert(isBoolean());
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

	@Override
	public EvaluatableValue cast(Type type) {
		if (type.equals(NamedType.INT)) {
			return new ValueInterval(new UnboundFraction(min.floor()),new UnboundFraction(max.floor()));
		}
		return this;
	}

}
