/**
 * (c) 2009 Lehrstuhl fuer Softwaretechnik und Programmiersprachen, Heinrich
 * Heine Universitaet Duesseldorf This software is licenced under EPL 1.0
 * (http://www.eclipse.org/org/documents/epl-v10.html)
 * */

/**
 * 
 */
package de.prob.prolog.term;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import de.prob.prolog.output.IPrologTermOutput;
import de.prob.prolog.output.PrologTermOutput;

/**
 * This is the abstract base class for Prolog terms
 * 
 * @author plagge
 */
public abstract class PrologTerm implements Serializable {
	private static final long serialVersionUID = 7974875342517963149L;

	public boolean isTerm() {
		return false;
	}

	public boolean isAtom() {
		return false;
	}

	public boolean isList() {
		return false;
	}

	public boolean isNumber() {
		return false;
	}

	public boolean isVariable() {
		return false;
	}

	public boolean hasFunctor(final String functor, final int arity) {
		return false;
	}

	public abstract void toTermOutput(IPrologTermOutput pto);

	@Override
	public String toString() {
		StringWriter sWriter = new StringWriter();
		PrologTermOutput pto = new PrologTermOutput(new PrintWriter(sWriter),
				false);
		toTermOutput(pto);
		return sWriter.toString();
	}

	public static String atomicString(final PrologTerm term) {
		if (term.isAtom())
			return ((CompoundPrologTerm) term).getFunctor();
		else
			throw new IllegalArgumentException(
					"Expected an atomic prolog term, but was "
							+ term.toString());
	}

	public static List<String> atomicStrings(final Iterable<PrologTerm> terms) {
		List<String> result = new ArrayList<String>();
		for (final PrologTerm term : terms) {
			result.add(atomicString(term));
		}
		return result;
	}

}