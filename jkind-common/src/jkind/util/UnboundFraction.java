package jkind.util;

import java.math.BigInteger;

public class UnboundFraction extends BigFraction {
	public static final UnboundFraction POSITIVE_INFINITY = new UnboundFraction(1);
	public static final UnboundFraction NEGATIVE_INFINITY = new UnboundFraction(-1);
	public static final UnboundFraction ZERO = new UnboundFraction(BigInteger.ZERO);
	public static final UnboundFraction ONE = new UnboundFraction(BigInteger.ONE);
	public static final UnboundFraction TWO = new UnboundFraction(BigInteger.valueOf(2));
	
	// An indeterminate value can be any value ..
	public static final UnboundFraction INDETERMINATE = new UnboundFraction(0);
	// The numerator and denominator are always stored in reduced form with the
	// denominator always positive	

	private UnboundFraction(int sign) {
		super(sign);
	}

	public static UnboundFraction unboundFraction(int sign) {
		if (sign == 0) {
			return INDETERMINATE;
		}
		if (sign < 0) {
			return NEGATIVE_INFINITY;
		}
		return POSITIVE_INFINITY;
	}
	
	public static UnboundFraction unboundFraction(long num, long denom) {
		if (denom == 0) {
			return unboundFraction(Long.signum(num));
		}
		return new UnboundFraction(BigInteger.valueOf(num),BigInteger.valueOf(denom));
	}
	
	public UnboundFraction(BigInteger num, BigInteger denom) {
		super(num,denom);
	}
	
	public UnboundFraction(BigInteger num) {
		super(num);
	}
	
	public UnboundFraction(BigFraction arg) {
		super(arg.num,arg.denom);		
	}

	public BigFraction toBigFraction() {
		assert(isFinite());
		return new BigFraction(num,denom);
	}
	
	@Override
	public int compareTo(BigFraction arg) {
		if (isFinite() && arg.isFinite()) {
			return super.compareTo(arg);
		}
		if (isFinite()) {
			return (- arg.num.signum());
		}
		if (arg.isFinite()) {
			return num.signum();
		}
		if (num == BigInteger.ZERO) return 0;
		if (arg.num == BigInteger.ZERO) return 0;
		return num.compareTo(arg.num);
	}
	
	public UnboundFraction max(UnboundFraction y) {
		UnboundFraction x = this;
		if (x == UnboundFraction.INDETERMINATE) return (y == UnboundFraction.POSITIVE_INFINITY) ? y : x;
		if (y == UnboundFraction.INDETERMINATE) return (x == UnboundFraction.POSITIVE_INFINITY) ? x : y;
		return (x.compareTo(y) < 0) ? y : x;
	}

	public UnboundFraction min(UnboundFraction y) {
		UnboundFraction x = this;
		if (x == UnboundFraction.INDETERMINATE) return (y == UnboundFraction.NEGATIVE_INFINITY) ? y : x;
		if (y == UnboundFraction.INDETERMINATE) return (x == UnboundFraction.NEGATIVE_INFINITY) ? x : y;
		return (x.compareTo(y) < 0) ? x : y;
	}
	

	@Override
	public UnboundFraction add(BigFraction arg) {
		if (isFinite() && arg.isFinite()) {
			return new UnboundFraction(super.add(arg));
		}
		if (isFinite()) {
			if (arg instanceof UnboundFraction)
				return (UnboundFraction) arg;
			return new UnboundFraction(arg);
		}
		if (arg.isFinite()) {
			return this;
		}
		if (this == arg) {
			return this;
		}
		return INDETERMINATE;		
	}
	 
	@Override
	public UnboundFraction multiply(BigFraction arg) {
		if (isFinite() && arg.isFinite()) {
			return new UnboundFraction(super.multiply(arg));
		}
		return(unboundFraction(arg.num.signum() * num.signum()));
	}
	
	public UnboundFraction inverse() {
		return inverseLimit(0);
	}

	// The inverseLimit(sign) method allows us to approach zero 
	// from below (- sign) or from above (+ sign).  From below
	// the inverse of 0 is negative infinity.  From above it is
	// positive infinity.  Zero sign also produces positive
	// infinity.  In practice, when we round the lower bound of
	// an integer range the sign should be positive and when we 
	// round the upper bound the sign should be negative.
	public UnboundFraction inverseLimit(int sign) {
		if (isFinite()) {
			if (num.equals(BigInteger.ZERO)) {
				return (sign == 0) ? INDETERMINATE : ((sign < 0) ? NEGATIVE_INFINITY : POSITIVE_INFINITY);
			}
			return new UnboundFraction(denom,num);
		}
		if (this == INDETERMINATE) return INDETERMINATE;
		return new UnboundFraction(BigInteger.ZERO);
	}
	
	@Override
	public UnboundFraction negate() {
		if (isFinite()) {
			return new UnboundFraction(num.negate(),denom);
		}
		return unboundFraction(num.signum() * -1);
	}

	@Override
	public UnboundFraction subtract(BigFraction arg) {
		return add(arg.negate());
	}
	
	public UnboundFraction truncateLimit(int sign) {
		if (! isFinite()) {
			return this;
		}
		BigInteger divAndRem[] = num.divideAndRemainder(denom);
		//          -1         num.sign           +1
        //      +1     -1        sign         +1       -1
        //       0     -1        round        +1        0
		//    |          |                   |           |
		//  X   X   .  X   X   .   0   .   X   X   .   X   X
        //      ^      ^                       ^       ^
		BigInteger round = (sign == num.signum() && (divAndRem[1].signum() != 0)) ? BigInteger.ONE : BigInteger.ZERO;
		if  (num.signum() < 0) {
			round = round.negate();
		}
		return new UnboundFraction(divAndRem[0].add(round));
	}
	
	@Override
	public UnboundFraction divide(BigFraction x) {
		return multiply(((UnboundFraction) x).inverse());
	}
	
	@Override
	public UnboundFraction divide(BigInteger x) {
		return divide(new UnboundFraction(x));
	}
	
	@Override
	public UnboundFraction subtract(BigInteger x) {
		return subtract(new UnboundFraction(x));
	}
	
	@Override
	public UnboundFraction add(BigInteger x) {
		return add(new UnboundFraction(x));
	}
	
	private static final UnboundFraction testValues[] = {
			unboundFraction(0,0),
			unboundFraction(-1,0),
			unboundFraction(-3,1),
			unboundFraction(-2,1),
			unboundFraction(-3,2),
			unboundFraction(-1,1),
			unboundFraction(-1,2),
			unboundFraction(-1,3),
			unboundFraction(0,1),
			unboundFraction(1,3),
			unboundFraction(1,2),
			unboundFraction(1,1),
			unboundFraction(3,2),
			unboundFraction(2,1),
			unboundFraction(3,1),
			unboundFraction(1,0),
			unboundFraction(0,0)		
	};

	private static final int negatedValues[][] = 
		{{  0,0}, {  1,0}, {  3,1}, {  2,1}, {  3,2}, {  1,1}, {  1,2}, {  1,3}, {  0,1}, { -1,3}, { -1,2}, { -1,1}, { -3,2}, { -2,1}, { -3,1}, { -1,0}, {  0,0}};

	private static final int inverseFromRightValues[][] = 
		{{  0,0}, {  0,1}, { -1,3}, { -1,2}, { -2,3}, { -1,1}, { -2,1}, { -3,1}, {  1,0}, {  3,1}, {  2,1}, {  1,1}, {  2,3}, {  1,2}, {  1,3}, {  0,1}, {  0,0}};

	private static final int inverseFromLeftValues[][] = 
		{{  0,0}, {  0,1}, { -1,3}, { -1,2}, { -2,3}, { -1,1}, { -2,1}, { -3,1}, { -1,0}, {  3,1}, {  2,1}, {  1,1}, {  2,3}, {  1,2}, {  1,3}, {  0,1}, {  0,0}};

	private static final int truncateFromRightValues[][] = 
		{{  0,0}, { -1,0}, { -3,1}, { -2,1}, { -1,1}, { -1,1}, {  0,1}, {  0,1}, {  0,1}, {  1,1}, {  1,1}, {  1,1}, {  2,1}, {  2,1}, {  3,1}, {  1,0}, {  0,0}};

	private static final int truncateFromLeftValues[][] = 
		{{  0,0}, { -1,0}, { -3,1}, { -2,1}, { -2,1}, { -1,1}, { -1,1}, { -1,1}, {  0,1}, {  0,1}, {  0,1}, {  1,1}, {  1,1}, {  2,1}, {  3,1}, {  1,0}, {  0,0}};

	private static final int addValues[][][] = {
			{{  0,0}, {  0,0}, {  0,0}, {  0,0}, {  0,0}, {  0,0}, {  0,0}, {  0,0}, {  0,0}, {  0,0}, {  0,0}, {  0,0}, {  0,0}, {  0,0}, {  0,0}, {  0,0}, {  0,0}},
			{{  0,0}, { -1,0}, { -1,0}, { -1,0}, { -1,0}, { -1,0}, { -1,0}, { -1,0}, { -1,0}, { -1,0}, { -1,0}, { -1,0}, { -1,0}, { -1,0}, { -1,0}, {  0,0}, {  0,0}},
			{{  0,0}, { -1,0}, { -6,1}, { -5,1}, { -9,2}, { -4,1}, { -7,2}, {-10,3}, { -3,1}, { -8,3}, { -5,2}, { -2,1}, { -3,2}, { -1,1}, {  0,1}, {  1,0}, {  0,0}},
			{{  0,0}, { -1,0}, { -5,1}, { -4,1}, { -7,2}, { -3,1}, { -5,2}, { -7,3}, { -2,1}, { -5,3}, { -3,2}, { -1,1}, { -1,2}, {  0,1}, {  1,1}, {  1,0}, {  0,0}},
			{{  0,0}, { -1,0}, { -9,2}, { -7,2}, { -3,1}, { -5,2}, { -2,1}, {-11,6}, { -3,2}, { -7,6}, { -1,1}, { -1,2}, {  0,1}, {  1,2}, {  3,2}, {  1,0}, {  0,0}},
			{{  0,0}, { -1,0}, { -4,1}, { -3,1}, { -5,2}, { -2,1}, { -3,2}, { -4,3}, { -1,1}, { -2,3}, { -1,2}, {  0,1}, {  1,2}, {  1,1}, {  2,1}, {  1,0}, {  0,0}},
			{{  0,0}, { -1,0}, { -7,2}, { -5,2}, { -2,1}, { -3,2}, { -1,1}, { -5,6}, { -1,2}, { -1,6}, {  0,1}, {  1,2}, {  1,1}, {  3,2}, {  5,2}, {  1,0}, {  0,0}},
			{{  0,0}, { -1,0}, {-10,3}, { -7,3}, {-11,6}, { -4,3}, { -5,6}, { -2,3}, { -1,3}, {  0,1}, {  1,6}, {  2,3}, {  7,6}, {  5,3}, {  8,3}, {  1,0}, {  0,0}},
			{{  0,0}, { -1,0}, { -3,1}, { -2,1}, { -3,2}, { -1,1}, { -1,2}, { -1,3}, {  0,1}, {  1,3}, {  1,2}, {  1,1}, {  3,2}, {  2,1}, {  3,1}, {  1,0}, {  0,0}},
			{{  0,0}, { -1,0}, { -8,3}, { -5,3}, { -7,6}, { -2,3}, { -1,6}, {  0,1}, {  1,3}, {  2,3}, {  5,6}, {  4,3}, { 11,6}, {  7,3}, { 10,3}, {  1,0}, {  0,0}},
			{{  0,0}, { -1,0}, { -5,2}, { -3,2}, { -1,1}, { -1,2}, {  0,1}, {  1,6}, {  1,2}, {  5,6}, {  1,1}, {  3,2}, {  2,1}, {  5,2}, {  7,2}, {  1,0}, {  0,0}},
			{{  0,0}, { -1,0}, { -2,1}, { -1,1}, { -1,2}, {  0,1}, {  1,2}, {  2,3}, {  1,1}, {  4,3}, {  3,2}, {  2,1}, {  5,2}, {  3,1}, {  4,1}, {  1,0}, {  0,0}},
			{{  0,0}, { -1,0}, { -3,2}, { -1,2}, {  0,1}, {  1,2}, {  1,1}, {  7,6}, {  3,2}, { 11,6}, {  2,1}, {  5,2}, {  3,1}, {  7,2}, {  9,2}, {  1,0}, {  0,0}},
			{{  0,0}, { -1,0}, { -1,1}, {  0,1}, {  1,2}, {  1,1}, {  3,2}, {  5,3}, {  2,1}, {  7,3}, {  5,2}, {  3,1}, {  7,2}, {  4,1}, {  5,1}, {  1,0}, {  0,0}},
			{{  0,0}, { -1,0}, {  0,1}, {  1,1}, {  3,2}, {  2,1}, {  5,2}, {  8,3}, {  3,1}, { 10,3}, {  7,2}, {  4,1}, {  9,2}, {  5,1}, {  6,1}, {  1,0}, {  0,0}},
			{{  0,0}, {  0,0}, {  1,0}, {  1,0}, {  1,0}, {  1,0}, {  1,0}, {  1,0}, {  1,0}, {  1,0}, {  1,0}, {  1,0}, {  1,0}, {  1,0}, {  1,0}, {  1,0}, {  0,0}},
			{{  0,0}, {  0,0}, {  0,0}, {  0,0}, {  0,0}, {  0,0}, {  0,0}, {  0,0}, {  0,0}, {  0,0}, {  0,0}, {  0,0}, {  0,0}, {  0,0}, {  0,0}, {  0,0}, {  0,0}}
	};

	private static final int multiplyValues[][][] = {
			{{  0,0}, {  0,0}, {  0,0}, {  0,0}, {  0,0}, {  0,0}, {  0,0}, {  0,0}, {  0,0}, {  0,0}, {  0,0}, {  0,0}, {  0,0}, {  0,0}, {  0,0}, {  0,0}, {  0,0}},
			{{  0,0}, {  1,0}, {  1,0}, {  1,0}, {  1,0}, {  1,0}, {  1,0}, {  1,0}, {  0,0}, { -1,0}, { -1,0}, { -1,0}, { -1,0}, { -1,0}, { -1,0}, { -1,0}, {  0,0}},
			{{  0,0}, {  1,0}, {  9,1}, {  6,1}, {  9,2}, {  3,1}, {  3,2}, {  1,1}, {  0,1}, { -1,1}, { -3,2}, { -3,1}, { -9,2}, { -6,1}, { -9,1}, { -1,0}, {  0,0}},
			{{  0,0}, {  1,0}, {  6,1}, {  4,1}, {  3,1}, {  2,1}, {  1,1}, {  2,3}, {  0,1}, { -2,3}, { -1,1}, { -2,1}, { -3,1}, { -4,1}, { -6,1}, { -1,0}, {  0,0}},
			{{  0,0}, {  1,0}, {  9,2}, {  3,1}, {  9,4}, {  3,2}, {  3,4}, {  1,2}, {  0,1}, { -1,2}, { -3,4}, { -3,2}, { -9,4}, { -3,1}, { -9,2}, { -1,0}, {  0,0}},
			{{  0,0}, {  1,0}, {  3,1}, {  2,1}, {  3,2}, {  1,1}, {  1,2}, {  1,3}, {  0,1}, { -1,3}, { -1,2}, { -1,1}, { -3,2}, { -2,1}, { -3,1}, { -1,0}, {  0,0}},
			{{  0,0}, {  1,0}, {  3,2}, {  1,1}, {  3,4}, {  1,2}, {  1,4}, {  1,6}, {  0,1}, { -1,6}, { -1,4}, { -1,2}, { -3,4}, { -1,1}, { -3,2}, { -1,0}, {  0,0}},
			{{  0,0}, {  1,0}, {  1,1}, {  2,3}, {  1,2}, {  1,3}, {  1,6}, {  1,9}, {  0,1}, { -1,9}, { -1,6}, { -1,3}, { -1,2}, { -2,3}, { -1,1}, { -1,0}, {  0,0}},
			{{  0,0}, {  0,0}, {  0,1}, {  0,1}, {  0,1}, {  0,1}, {  0,1}, {  0,1}, {  0,1}, {  0,1}, {  0,1}, {  0,1}, {  0,1}, {  0,1}, {  0,1}, {  0,0}, {  0,0}},
			{{  0,0}, { -1,0}, { -1,1}, { -2,3}, { -1,2}, { -1,3}, { -1,6}, { -1,9}, {  0,1}, {  1,9}, {  1,6}, {  1,3}, {  1,2}, {  2,3}, {  1,1}, {  1,0}, {  0,0}},
			{{  0,0}, { -1,0}, { -3,2}, { -1,1}, { -3,4}, { -1,2}, { -1,4}, { -1,6}, {  0,1}, {  1,6}, {  1,4}, {  1,2}, {  3,4}, {  1,1}, {  3,2}, {  1,0}, {  0,0}},
			{{  0,0}, { -1,0}, { -3,1}, { -2,1}, { -3,2}, { -1,1}, { -1,2}, { -1,3}, {  0,1}, {  1,3}, {  1,2}, {  1,1}, {  3,2}, {  2,1}, {  3,1}, {  1,0}, {  0,0}},
			{{  0,0}, { -1,0}, { -9,2}, { -3,1}, { -9,4}, { -3,2}, { -3,4}, { -1,2}, {  0,1}, {  1,2}, {  3,4}, {  3,2}, {  9,4}, {  3,1}, {  9,2}, {  1,0}, {  0,0}},
			{{  0,0}, { -1,0}, { -6,1}, { -4,1}, { -3,1}, { -2,1}, { -1,1}, { -2,3}, {  0,1}, {  2,3}, {  1,1}, {  2,1}, {  3,1}, {  4,1}, {  6,1}, {  1,0}, {  0,0}},
			{{  0,0}, { -1,0}, { -9,1}, { -6,1}, { -9,2}, { -3,1}, { -3,2}, { -1,1}, {  0,1}, {  1,1}, {  3,2}, {  3,1}, {  9,2}, {  6,1}, {  9,1}, {  1,0}, {  0,0}},
			{{  0,0}, { -1,0}, { -1,0}, { -1,0}, { -1,0}, { -1,0}, { -1,0}, { -1,0}, {  0,0}, {  1,0}, {  1,0}, {  1,0}, {  1,0}, {  1,0}, {  1,0}, {  1,0}, {  0,0}},
			{{  0,0}, {  0,0}, {  0,0}, {  0,0}, {  0,0}, {  0,0}, {  0,0}, {  0,0}, {  0,0}, {  0,0}, {  0,0}, {  0,0}, {  0,0}, {  0,0}, {  0,0}, {  0,0}, {  0,0}}
	};
	
	private static final int compareToValues[][] = {
			{  0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0},
			{  0,   0,  -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1,   0},
			{  0,   1,   0,  -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1,   0},
			{  0,   1,   1,   0,  -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1,   0},
			{  0,   1,   1,   1,   0,  -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1,   0},
			{  0,   1,   1,   1,   1,   0,  -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1,   0},
			{  0,   1,   1,   1,   1,   1,   0,  -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1,   0},
			{  0,   1,   1,   1,   1,   1,   1,   0,  -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1,   0},
			{  0,   1,   1,   1,   1,   1,   1,   1,   0,  -1,  -1,  -1,  -1,  -1,  -1,  -1,   0},
			{  0,   1,   1,   1,   1,   1,   1,   1,   1,   0,  -1,  -1,  -1,  -1,  -1,  -1,   0},
			{  0,   1,   1,   1,   1,   1,   1,   1,   1,   1,   0,  -1,  -1,  -1,  -1,  -1,   0},
			{  0,   1,   1,   1,   1,   1,   1,   1,   1,   1,   1,   0,  -1,  -1,  -1,  -1,   0},
			{  0,   1,   1,   1,   1,   1,   1,   1,   1,   1,   1,   1,   0,  -1,  -1,  -1,   0},
			{  0,   1,   1,   1,   1,   1,   1,   1,   1,   1,   1,   1,   1,   0,  -1,  -1,   0},
			{  0,   1,   1,   1,   1,   1,   1,   1,   1,   1,   1,   1,   1,   1,   0,  -1,   0},
			{  0,   1,   1,   1,   1,   1,   1,   1,   1,   1,   1,   1,   1,   1,   1,   0,   0},
			{  0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0}
	};

	private String asArray() {
		String ns = String.format("%3d",num);
		String ds = String.format("%1d",denom);
		return "{"+ns+","+ds+"}";
	}
	
	private static String generateExpectedResults() {
		String rowDelimitor = "";
		String colDelimitor = "";
		String res = "";

		res += "private static final int negatedValues[][] = \n{";
		rowDelimitor = "";
		for (UnboundFraction x : testValues) {
			res += rowDelimitor + x.negate().asArray();
			rowDelimitor = ", ";
		}
		res += "};\n\n";

		res += "private static final int inverseFromRightValues[][] = \n{";
		rowDelimitor = "";
		for (UnboundFraction x : testValues) {
			res += rowDelimitor + x.inverseLimit(1).asArray();
			rowDelimitor = ", ";
		}
		res += "};\n\n";
		
		res += "private static final int inverseFromLeftValues[][] = \n{";
		rowDelimitor = "";
		for (UnboundFraction x : testValues) {
			res += rowDelimitor + x.inverseLimit(-1).asArray();
			rowDelimitor = ", ";
		}
		res += "};\n\n";
		
		res += "private static final int truncateFromRightValues[][] = \n{";
		rowDelimitor = "";
		for (UnboundFraction x : testValues) {
			res += rowDelimitor + x.truncateLimit(1).asArray();
			rowDelimitor = ", ";
		}
		res += "};\n\n";
		
		res += "private static final int truncateFromLeftValues[][] = \n{";
		rowDelimitor = "";
		for (UnboundFraction x : testValues) {
			res += rowDelimitor + x.truncateLimit(-1).asArray();
			rowDelimitor = ", ";
		}
		res += "};\n\n";
		
		res += "private static final int addValues[][][] = {\n";
		colDelimitor = "";
		for (UnboundFraction x : testValues) {
			res += colDelimitor + "{";
			rowDelimitor = "";
			for (UnboundFraction y : testValues) {
				res += rowDelimitor + x.add(y).asArray();
				rowDelimitor = ", ";
			}
			res += "}";
			colDelimitor = ",\n";
		}
		res += "\n};\n\n";
		
		res += "private static final int multiplyValues[][][] = {\n";
		colDelimitor = "";
		for (UnboundFraction x : testValues) {
			res += colDelimitor + "{";
			rowDelimitor = "";
			for (UnboundFraction y : testValues) {
				res += rowDelimitor + x.multiply(y).asArray();
				rowDelimitor = ", ";
			}
			res += "}";
			colDelimitor = ",\n";
		}
		res += "\n};\n\n";
		
		res += "private static final int compareToValues[][] = {\n";
		colDelimitor = "";
		for (UnboundFraction x : testValues) {
			res += colDelimitor + "{";
			rowDelimitor = "";
			for (UnboundFraction y : testValues) {
				res += rowDelimitor + String.format("%3d",x.compareTo(y));
				rowDelimitor = ", ";
			}
			res += "}";
			colDelimitor = ",\n";
		}
		res += "\n};\n\n";
		
		return res;
		
	}
	
	private static UnboundFraction unboundFraction(int value[]) {
		return unboundFraction(value[0],value[1]);
	}
	
	private static void testExpectedResults() {
		int i,j;
		i = 0;
		for (UnboundFraction x : testValues) {
			assert(x.negate().equals(unboundFraction(negatedValues[i++])));
		}
		
		i = 0;
		for (UnboundFraction x : testValues) {
			assert(x.inverseLimit(1).equals(unboundFraction(inverseFromRightValues[i++])));
		}
		
		i = 0;
		for (UnboundFraction x : testValues) {
			assert(x.inverseLimit(-1).equals(unboundFraction(inverseFromLeftValues[i++])));
		}
		
		i = 0;
		for (UnboundFraction x : testValues) {
			assert(x.truncateLimit(1).equals(unboundFraction(truncateFromRightValues[i++])));
		}
		
		i = 0;
		for (UnboundFraction x : testValues) {
			assert(x.truncateLimit(-1).equals(unboundFraction(truncateFromLeftValues[i++])));
		}
		
		i = 0;
		j = 0;
		for (UnboundFraction x : testValues) {
			for (UnboundFraction y: testValues) {
				assert(x.add(y).equals(unboundFraction(addValues[i][j])));
				j++;
			}
			j = 0;
			i++;
		}
		
		i = 0;
		j = 0;
		for (UnboundFraction x : testValues) {
			for (UnboundFraction y: testValues) {
				assert(x.multiply(y).equals(unboundFraction(multiplyValues[i][j])));
				j++;
			}
			j = 0;
			i++;
		}
		
		i = 0;
		j = 0;
		for (UnboundFraction x : testValues) {
			for (UnboundFraction y: testValues) {
				assert(x.compareTo(y) == compareToValues[i][j]);
				j++;
			}
			j = 0;
			i++;
		}
		
	}
	
	public static void main(String[] args) {
		System.out.print(generateExpectedResults());
		testExpectedResults();
	}

}