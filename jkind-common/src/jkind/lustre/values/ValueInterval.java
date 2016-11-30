package jkind.lustre.values;

import java.math.BigInteger;

import jkind.interval.NumericEndpoint;
import jkind.lustre.NamedType;
import jkind.lustre.Type;
import jkind.util.BigFraction;
import jkind.util.UnboundFraction;
import jkind.util.Util;

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
		UnboundFraction v1 = min.multiply(right.max);
		UnboundFraction v2 = min.multiply(right.min);
		UnboundFraction v3 = max.multiply(right.max);
		UnboundFraction v4 = max.multiply(right.max);
		return new ValueInterval(min(v1,v2,v3,v4),max(v1,v2,v3,v4));
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
		assert(modulusInterval.min.equals(modulusInterval.max));
		if (min.equals(max)) {
			BigInteger val = min.floor();
			BigInteger mod = modulusInterval.min.floor();
			return new ValueInterval(new UnboundFraction(val.mod(mod)));
		}
		return new ValueInterval(UnboundFraction.ZERO,modulusInterval.min.subtract(UnboundFraction.ONE));
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
	
	private static UnboundFraction min(UnboundFraction val, UnboundFraction... others) {
		UnboundFraction result = val;
		for (UnboundFraction other : others) {
			result = result.min(other);
		}
		return result;
	}
	
	private static UnboundFraction max(UnboundFraction val, UnboundFraction... others) {
		UnboundFraction result = val;
		for (UnboundFraction other : others) {
			result = result.max(other);
		}
		return result;
	}
	
	private static UnboundFraction div(UnboundFraction num, UnboundFraction den) {
		assert(den.isFinite());
		if (! num.isFinite()) {
			return (num.signum() * den.signum() < 0) ? UnboundFraction.NEGATIVE_INFINITY : UnboundFraction.POSITIVE_INFINITY;
		}
		return new UnboundFraction(Util.smtDivide(num.floor(), den.floor()));
	}
	
	@Override
	EvaluatableValue int_divide(EvaluatableValue right) {
		ValueInterval rv = (ValueInterval) right;
		UnboundFraction v1 = div(min,rv.min);
		UnboundFraction v2 = div(min,rv.max);
		UnboundFraction v3 = div(max,rv.min);
		UnboundFraction v4 = div(max,rv.max);
		return new ValueInterval(min(v1,v2,v3,v4),max(v1,v2,v3,v4));
	}

}
