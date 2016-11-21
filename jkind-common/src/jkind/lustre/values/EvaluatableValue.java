package jkind.lustre.values;

import java.math.BigInteger;

import jkind.lustre.BinaryOp;
import jkind.lustre.Type;
import jkind.lustre.UnaryOp;
import jkind.util.BigFraction;

public abstract class EvaluatableValue extends Value {

	@Override
	public EvaluatableValue applyBinaryOp(BinaryOp op, Value arg) {
		EvaluatableValue right = (EvaluatableValue) arg;
		switch (op) {
	    case PLUS:
	    	return plus(right);
	    case MINUS:
	    	return minus(right);
	    case MULTIPLY:
	    	return multiply(right);
	    case DIVIDE:
	    	return divide(right);
	    case INT_DIVIDE:
	    	return int_divide(right);
	    case MODULUS:
	    	return modulus(right);
	    case EQUAL:
	    	return equalop(right);
	    case NOTEQUAL:
	    	return notequal(right);
	    case GREATER:
	    	return greater(right);
	    case LESS:
	    	return less(right);
	    case GREATEREQUAL:
	    	return greaterequal(right);
	    case LESSEQUAL:
	    	return lessequal(right);
	    case OR:
	    	return or(right);
	    case AND:
	    	return and(right);
	    case XOR:
	    	return xor(right);
	    case IMPLIES:
	    	return implies(right);
	    case ARROW:
	    	return arrow(right);
		}
		assert(false);
		return this;
	}

	@Override
	public EvaluatableValue applyUnaryOp(UnaryOp op) {
		switch (op) {
		case NEGATIVE: {
			return negate();
		}
		case NOT: {
			return not();
		}
		case PRE: {
			return pre();
		}
		}
		assert(false);
		return this;
	}
	
	public EvaluatableValue divide(EvaluatableValue right) {
		return multiply(right.inverse());
	}
	
	public EvaluatableValue minus(EvaluatableValue right) {
		return plus(right.negate());
	}
	
	public EvaluatableValue int_divide(EvaluatableValue right) {
		EvaluatableValue x = right.truncate();
		EvaluatableValue y = x.inverse();
		return multiply(y).truncate();
	}

	public EvaluatableValue xor(EvaluatableValue right) {
		return equalop(right).not();
	}

	public EvaluatableValue notequal(EvaluatableValue right) {
		return equalop(right).not();
	}

	public EvaluatableValue implies(EvaluatableValue right) {
		return not().or(right);
	}
	
	public EvaluatableValue greaterequal(EvaluatableValue right) {
		return less(right).not();
	}

	public EvaluatableValue lessequal(EvaluatableValue right) {
		return greater(right).not();
	}
	
	abstract public EvaluatableValue ite(EvaluatableValue left, EvaluatableValue right);
	abstract public EvaluatableValue multiply(EvaluatableValue right);
	abstract public EvaluatableValue modulus(EvaluatableValue right);	
	abstract public EvaluatableValue arrow(EvaluatableValue right);	
	abstract public EvaluatableValue equalop(EvaluatableValue right);	
	abstract public EvaluatableValue less(EvaluatableValue right);
	abstract public EvaluatableValue greater(EvaluatableValue right);
	abstract public EvaluatableValue plus(EvaluatableValue right);	
	abstract public EvaluatableValue and(EvaluatableValue right);	
	abstract public EvaluatableValue or(EvaluatableValue right);
	abstract public EvaluatableValue truncate();
	abstract public EvaluatableValue inverse();
	abstract public EvaluatableValue negate();
	abstract public EvaluatableValue not();
	abstract public EvaluatableValue pre();
	abstract public boolean isTrue();
	abstract public boolean isFalse();
	abstract public EvaluatableValue cast(Type type);
	abstract public EvaluatableValue valueOf(BigInteger value);
	abstract public EvaluatableValue valueOf(BigFraction value);
	abstract public EvaluatableValue valueOf(boolean value);
	
}
