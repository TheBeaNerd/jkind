package jkind.lustre.values;

import java.math.BigInteger;

import jkind.lustre.NamedType;
import jkind.lustre.Type;
import jkind.util.BigFraction;
import jkind.util.UnboundFraction;
import jkind.util.Util;

public class RatValue extends EvaluatableValue {

	BigFraction value;
	
	public static final RatValue INSTANCE = new RatValue(BigFraction.ZERO);
	public static final RatValue ZERO = new RatValue(BigFraction.ZERO);
	public static final RatValue ONE  = new RatValue(BigFraction.ONE);
	
	public RatValue(BigFraction value) {
		this.value = value;
	}
	
	@Override
	public EvaluatableValue ite(EvaluatableValue left, EvaluatableValue right) {
		return value.equals(BigFraction.ZERO) ? right : left;
	}

	@Override
	public EvaluatableValue multiply(EvaluatableValue right) {
		return new RatValue(value.multiply(((RatValue) right).value));
	}

	@Override
	public EvaluatableValue equalop(EvaluatableValue right) {
		return value.equals(((RatValue) right).value) ? ONE : ZERO;
	}

	@Override
	public EvaluatableValue less(EvaluatableValue right) {
		return (value.compareTo(((RatValue) right).value) < 0) ? ONE : ZERO;
	}

	@Override
	public EvaluatableValue greater(EvaluatableValue right) {
		return (value.compareTo(((RatValue) right).value) > 0) ? ONE : ZERO;
	}

	@Override
	public EvaluatableValue plus(EvaluatableValue right) {
		return new RatValue(value.add(((RatValue) right).value));
	}

	@Override
	public EvaluatableValue and(EvaluatableValue right) {
		return new RatValue(value.multiply(((RatValue) right).value));
	}

	@Override
	public EvaluatableValue or(EvaluatableValue right) {
		return value.add(((RatValue) right).value).compareTo(BigFraction.ZERO) > 0 ? ONE : ZERO;
	}

	@Override
	public boolean isTrue() {
		return value.equals(BigFraction.ONE);
	}

	@Override
	public boolean isFalse() {
		return value.equals(BigFraction.ZERO);
	}

	@Override
	public EvaluatableValue truncate() {
		BigInteger num = value.getNumerator();
		BigInteger den = value.getDenominator();
		BigInteger divAndRem[] = num.divideAndRemainder(den);	
		return new RatValue(new BigFraction(divAndRem[0]));
	}

	@Override
	public EvaluatableValue inverse() {
		return new RatValue(BigFraction.ONE.divide(value));
	}

	@Override
	public EvaluatableValue negate() {
		return new RatValue(value.negate());
	}

	@Override
	public EvaluatableValue not() {
		return value.equals(BigFraction.ZERO) ? ONE : ZERO;
	}
	
	@Override
	public EvaluatableValue modulus(EvaluatableValue right) {
		BigInteger num = value.floor();
		BigInteger den = ((RatValue) right).value.floor();
		return new RatValue(new BigFraction(num.mod(den)));
	}
	
	@Override
	public EvaluatableValue pre() {
		assert(false);
		return null;
	}

	@Override
	public EvaluatableValue arrow(EvaluatableValue right) {
		assert(false);
		return null;
	}

	@Override
	public String toString() {
		return value.toString();
	}

	@Override
	public EvaluatableValue valueOf(BigInteger value) {
		return new RatValue(new BigFraction(value));
	}

	@Override
	public EvaluatableValue valueOf(BigFraction value) {
		return new RatValue(value);
	}

	@Override
	public EvaluatableValue valueOf(boolean value) {
		return new RatValue(value ? BigFraction.ONE : BigFraction.ZERO);
	}

	@Override
	public EvaluatableValue cast(Type type) {
		if (type.equals(NamedType.INT)) {
			return new RatValue(new BigFraction(value.floor()));
		}
		return this;
	}

	@Override
	EvaluatableValue int_divide(EvaluatableValue right) {
		BigInteger den = ((RatValue) right).value.floor();
		BigInteger num = value.floor();
		EvaluatableValue res = new RatValue(new BigFraction(Util.smtDivide(num,den)));
		return res;
	}
	
}
