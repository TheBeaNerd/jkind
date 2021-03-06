// Generated from SmtLib2.g4 by ANTLR 4.4
package jkind.solvers.smtlib2;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class SmtLib2Parser extends Parser {
	static { RuntimeMetaData.checkVersion("4.4", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__17=1, T__16=2, T__15=3, T__14=4, T__13=5, T__12=6, T__11=7, T__10=8, 
		T__9=9, T__8=10, T__7=11, T__6=12, T__5=13, T__4=14, T__3=15, T__2=16, 
		T__1=17, T__0=18, BOOL=19, INT=20, REAL=21, ID=22, WS=23, ERROR=24;
	public static final String[] tokenNames = {
		"<INVALID>", "'/'", "'Real'", "'model'", "'Int'", "'Bool'", "'>='", "'|'", 
		"'<'", "'='", "'>'", "'<='", "'('", "')'", "'and'", "'ite'", "'define-fun'", 
		"'not'", "'-'", "BOOL", "INT", "REAL", "ID", "WS", "ERROR"
	};
	public static final int
		RULE_model = 0, RULE_define = 1, RULE_arg = 2, RULE_type = 3, RULE_body = 4, 
		RULE_fn = 5, RULE_symbol = 6, RULE_id = 7, RULE_qid = 8;
	public static final String[] ruleNames = {
		"model", "define", "arg", "type", "body", "fn", "symbol", "id", "qid"
	};

	@Override
	public String getGrammarFileName() { return "SmtLib2.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public SmtLib2Parser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ModelContext extends ParserRuleContext {
		public DefineContext define(int i) {
			return getRuleContext(DefineContext.class,i);
		}
		public List<DefineContext> define() {
			return getRuleContexts(DefineContext.class);
		}
		public TerminalNode EOF() { return getToken(SmtLib2Parser.EOF, 0); }
		public ModelContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_model; }
	}

	public final ModelContext model() throws RecognitionException {
		ModelContext _localctx = new ModelContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_model);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(18); match(T__6);
			setState(19); match(T__15);
			setState(23);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__6) {
				{
				{
				setState(20); define();
				}
				}
				setState(25);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(26); match(T__5);
			setState(27); match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DefineContext extends ParserRuleContext {
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public BodyContext body() {
			return getRuleContext(BodyContext.class,0);
		}
		public ArgContext arg() {
			return getRuleContext(ArgContext.class,0);
		}
		public DefineContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_define; }
	}

	public final DefineContext define() throws RecognitionException {
		DefineContext _localctx = new DefineContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_define);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(29); match(T__6);
			setState(30); match(T__2);
			setState(31); id();
			setState(32); match(T__6);
			setState(34);
			_la = _input.LA(1);
			if (_la==T__6) {
				{
				setState(33); arg();
				}
			}

			setState(36); match(T__5);
			setState(37); type();
			setState(38); body();
			setState(39); match(T__5);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArgContext extends ParserRuleContext {
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public ArgContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arg; }
	}

	public final ArgContext arg() throws RecognitionException {
		ArgContext _localctx = new ArgContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_arg);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(41); match(T__6);
			setState(42); id();
			setState(43); type();
			setState(44); match(T__5);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeContext extends ParserRuleContext {
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
	}

	public final TypeContext type() throws RecognitionException {
		TypeContext _localctx = new TypeContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_type);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(46);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__16) | (1L << T__14) | (1L << T__13))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			consume();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BodyContext extends ParserRuleContext {
		public BodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_body; }
	 
		public BodyContext() { }
		public void copyFrom(BodyContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ConsBodyContext extends BodyContext {
		public BodyContext body(int i) {
			return getRuleContext(BodyContext.class,i);
		}
		public FnContext fn() {
			return getRuleContext(FnContext.class,0);
		}
		public List<BodyContext> body() {
			return getRuleContexts(BodyContext.class);
		}
		public ConsBodyContext(BodyContext ctx) { copyFrom(ctx); }
	}
	public static class SymbolBodyContext extends BodyContext {
		public SymbolContext symbol() {
			return getRuleContext(SymbolContext.class,0);
		}
		public SymbolBodyContext(BodyContext ctx) { copyFrom(ctx); }
	}

	public final BodyContext body() throws RecognitionException {
		BodyContext _localctx = new BodyContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_body);
		int _la;
		try {
			setState(59);
			switch (_input.LA(1)) {
			case T__11:
			case BOOL:
			case INT:
			case REAL:
			case ID:
				_localctx = new SymbolBodyContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(48); symbol();
				}
				break;
			case T__6:
				_localctx = new ConsBodyContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(49); match(T__6);
				setState(50); fn();
				setState(54);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__11) | (1L << T__6) | (1L << BOOL) | (1L << INT) | (1L << REAL) | (1L << ID))) != 0)) {
					{
					{
					setState(51); body();
					}
					}
					setState(56);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(57); match(T__5);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FnContext extends ParserRuleContext {
		public FnContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fn; }
	}

	public final FnContext fn() throws RecognitionException {
		FnContext _localctx = new FnContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_fn);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(61);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__17) | (1L << T__12) | (1L << T__10) | (1L << T__9) | (1L << T__8) | (1L << T__7) | (1L << T__4) | (1L << T__3) | (1L << T__1) | (1L << T__0))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			consume();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SymbolContext extends ParserRuleContext {
		public TerminalNode BOOL() { return getToken(SmtLib2Parser.BOOL, 0); }
		public TerminalNode REAL() { return getToken(SmtLib2Parser.REAL, 0); }
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public TerminalNode INT() { return getToken(SmtLib2Parser.INT, 0); }
		public SymbolContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_symbol; }
	}

	public final SymbolContext symbol() throws RecognitionException {
		SymbolContext _localctx = new SymbolContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_symbol);
		try {
			setState(67);
			switch (_input.LA(1)) {
			case T__11:
			case ID:
				enterOuterAlt(_localctx, 1);
				{
				setState(63); id();
				}
				break;
			case BOOL:
				enterOuterAlt(_localctx, 2);
				{
				setState(64); match(BOOL);
				}
				break;
			case INT:
				enterOuterAlt(_localctx, 3);
				{
				setState(65); match(INT);
				}
				break;
			case REAL:
				enterOuterAlt(_localctx, 4);
				{
				setState(66); match(REAL);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IdContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(SmtLib2Parser.ID, 0); }
		public QidContext qid() {
			return getRuleContext(QidContext.class,0);
		}
		public IdContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_id; }
	}

	public final IdContext id() throws RecognitionException {
		IdContext _localctx = new IdContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_id);
		try {
			setState(71);
			switch (_input.LA(1)) {
			case T__11:
				enterOuterAlt(_localctx, 1);
				{
				setState(69); qid();
				}
				break;
			case ID:
				enterOuterAlt(_localctx, 2);
				{
				setState(70); match(ID);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class QidContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(SmtLib2Parser.ID, 0); }
		public QidContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_qid; }
	}

	public final QidContext qid() throws RecognitionException {
		QidContext _localctx = new QidContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_qid);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(73); match(T__11);
			setState(74); match(ID);
			setState(75); match(T__11);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\32P\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\3\2\3\2\3\2"+
		"\7\2\30\n\2\f\2\16\2\33\13\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\5\3%\n\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\6\3\6\3\6\3\6\7\6\67"+
		"\n\6\f\6\16\6:\13\6\3\6\3\6\5\6>\n\6\3\7\3\7\3\b\3\b\3\b\3\b\5\bF\n\b"+
		"\3\t\3\t\5\tJ\n\t\3\n\3\n\3\n\3\n\3\n\2\2\13\2\4\6\b\n\f\16\20\22\2\4"+
		"\4\2\4\4\6\7\7\2\3\3\b\b\n\r\20\21\23\24N\2\24\3\2\2\2\4\37\3\2\2\2\6"+
		"+\3\2\2\2\b\60\3\2\2\2\n=\3\2\2\2\f?\3\2\2\2\16E\3\2\2\2\20I\3\2\2\2\22"+
		"K\3\2\2\2\24\25\7\16\2\2\25\31\7\5\2\2\26\30\5\4\3\2\27\26\3\2\2\2\30"+
		"\33\3\2\2\2\31\27\3\2\2\2\31\32\3\2\2\2\32\34\3\2\2\2\33\31\3\2\2\2\34"+
		"\35\7\17\2\2\35\36\7\2\2\3\36\3\3\2\2\2\37 \7\16\2\2 !\7\22\2\2!\"\5\20"+
		"\t\2\"$\7\16\2\2#%\5\6\4\2$#\3\2\2\2$%\3\2\2\2%&\3\2\2\2&\'\7\17\2\2\'"+
		"(\5\b\5\2()\5\n\6\2)*\7\17\2\2*\5\3\2\2\2+,\7\16\2\2,-\5\20\t\2-.\5\b"+
		"\5\2./\7\17\2\2/\7\3\2\2\2\60\61\t\2\2\2\61\t\3\2\2\2\62>\5\16\b\2\63"+
		"\64\7\16\2\2\648\5\f\7\2\65\67\5\n\6\2\66\65\3\2\2\2\67:\3\2\2\28\66\3"+
		"\2\2\289\3\2\2\29;\3\2\2\2:8\3\2\2\2;<\7\17\2\2<>\3\2\2\2=\62\3\2\2\2"+
		"=\63\3\2\2\2>\13\3\2\2\2?@\t\3\2\2@\r\3\2\2\2AF\5\20\t\2BF\7\25\2\2CF"+
		"\7\26\2\2DF\7\27\2\2EA\3\2\2\2EB\3\2\2\2EC\3\2\2\2ED\3\2\2\2F\17\3\2\2"+
		"\2GJ\5\22\n\2HJ\7\30\2\2IG\3\2\2\2IH\3\2\2\2J\21\3\2\2\2KL\7\t\2\2LM\7"+
		"\30\2\2MN\7\t\2\2N\23\3\2\2\2\b\31$8=EI";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}