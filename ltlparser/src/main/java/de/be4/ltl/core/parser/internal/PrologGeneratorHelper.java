/**
 * 
 */
package de.be4.ltl.core.parser.internal;

import java.util.Locale;

import de.be4.ltl.core.parser.LtlParseException;
import de.prob.parserbase.ProBParseException;
import de.prob.parserbase.ProBParserBase;
import de.prob.prolog.output.IPrologTermOutput;

/**
 * @author plagge
 * 
 */
final class PrologGeneratorHelper {
	private final IPrologTermOutput pto;
	private final String currentStateID;
	private final ProBParserBase specParser;

	public PrologGeneratorHelper(final IPrologTermOutput pto,
			final String currentStateID, final ProBParserBase specParser) {
		super();
		this.pto = pto;
		this.currentStateID = currentStateID;
		this.specParser = specParser;
	}

	public void defaultIn(Class<?> clazz) {
		StringBuffer sb = new StringBuffer(clazz.getSimpleName());
		sb.setLength(sb.length() - 3);
		sb.deleteCharAt(0);
		String term = sb.toString().toLowerCase(Locale.ENGLISH);
		pto.openTerm(term);
	}

	public void defaultOut() {
		pto.closeTerm();
	}

	public void caseUnparsed(final UniversalToken token) {
		pto.openTerm("ap");
		try {
			specParser.parsePredicate(pto, token.getText(), true);
		} catch (ProBParseException e) {
			throw createAdapterException(token, e);
		} catch (UnsupportedOperationException e) {
			throw createAdapterException(token, e);
		}
		pto.closeTerm();
	}

	public void enabled(final UniversalToken token) {
		pto.openTerm("ap");
		pto.openTerm("enabled");
		parseTransitionPredicate(token);
		pto.closeTerm();
		pto.closeTerm();
	}

	public void parseTransitionPredicate(final UniversalToken token) {
		try {
			specParser.parseTransitionPredicate(pto, token.getText(), true);
		} catch (ProBParseException e) {
			throw createAdapterException(token, e);
		} catch (UnsupportedOperationException e) {
			throw createAdapterException(token, e);
		}
	}

	public void sink() {
		pto.openTerm("ap");
		pto.printAtom("sink");
		pto.closeTerm();
	}

	public void deadlock() {
		pto.openTerm("ap");
		pto.printAtom("deadlock");
		pto.closeTerm();
	}

	public void current() {
		pto.openTerm("ap");
		if (currentStateID != null) {
			pto.openTerm("stateid");
			pto.printAtomOrNumber(currentStateID);
			pto.closeTerm();
		} else {
			pto.printAtom("current");
		}
		pto.closeTerm();
	}

	private LtlAdapterException createAdapterException(
			final UniversalToken token, final Throwable orig) {
		final LtlParseException ex = new LtlParseException(token,
				orig.getMessage());
		return new LtlAdapterException(ex);
	}

}
