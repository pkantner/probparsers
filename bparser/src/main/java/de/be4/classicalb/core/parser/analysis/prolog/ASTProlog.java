package de.be4.classicalb.core.parser.analysis.prolog;

import java.io.StringWriter;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import de.be4.classicalb.core.parser.Utils;
import de.be4.classicalb.core.parser.analysis.DepthFirstAdapter;
import de.be4.classicalb.core.parser.node.AAbstractConstantsMachineClause;
import de.be4.classicalb.core.parser.node.AAbstractMachineParseUnit;
import de.be4.classicalb.core.parser.node.AAnySubstitution;
import de.be4.classicalb.core.parser.node.AAssertionsMachineClause;
import de.be4.classicalb.core.parser.node.AAssignSubstitution;
import de.be4.classicalb.core.parser.node.AAxiomsContextClause;
import de.be4.classicalb.core.parser.node.ABecomesElementOfSubstitution;
import de.be4.classicalb.core.parser.node.ABecomesSuchSubstitution;
import de.be4.classicalb.core.parser.node.ACaseOrSubstitution;
import de.be4.classicalb.core.parser.node.ACaseSubstitution;
import de.be4.classicalb.core.parser.node.AChoiceSubstitution;
import de.be4.classicalb.core.parser.node.AComprehensionSetExpression;
import de.be4.classicalb.core.parser.node.AConcreteVariablesMachineClause;
import de.be4.classicalb.core.parser.node.AConstantsContextClause;
import de.be4.classicalb.core.parser.node.AConstantsMachineClause;
import de.be4.classicalb.core.parser.node.ACoupleExpression;
import de.be4.classicalb.core.parser.node.ADefinitionExpression;
import de.be4.classicalb.core.parser.node.ADefinitionPredicate;
import de.be4.classicalb.core.parser.node.ADefinitionSubstitution;
import de.be4.classicalb.core.parser.node.ADefinitionsMachineClause;
import de.be4.classicalb.core.parser.node.AEnumeratedSet;
import de.be4.classicalb.core.parser.node.AEvent;
import de.be4.classicalb.core.parser.node.AEventBComprehensionSetExpression;
import de.be4.classicalb.core.parser.node.AEventBContextParseUnit;
import de.be4.classicalb.core.parser.node.AEventBModelParseUnit;
import de.be4.classicalb.core.parser.node.AEventsModelClause;
import de.be4.classicalb.core.parser.node.AExistentialQuantificationPredicate;
import de.be4.classicalb.core.parser.node.AExpressionDefinition;
import de.be4.classicalb.core.parser.node.AExpressionParseUnit;
import de.be4.classicalb.core.parser.node.AExtendsContextClause;
import de.be4.classicalb.core.parser.node.AExtendsMachineClause;
import de.be4.classicalb.core.parser.node.AFalseExpression;
import de.be4.classicalb.core.parser.node.AFalsePredicate;
import de.be4.classicalb.core.parser.node.AFunctionExpression;
import de.be4.classicalb.core.parser.node.AGeneralProductExpression;
import de.be4.classicalb.core.parser.node.AGeneralSumExpression;
import de.be4.classicalb.core.parser.node.AIdentifierExpression;
import de.be4.classicalb.core.parser.node.AIfSubstitution;
import de.be4.classicalb.core.parser.node.AImplementationMachineParseUnit;
import de.be4.classicalb.core.parser.node.AImportsMachineClause;
import de.be4.classicalb.core.parser.node.AIncludesMachineClause;
import de.be4.classicalb.core.parser.node.AIntegerExpression;
import de.be4.classicalb.core.parser.node.AInvariantModelClause;
import de.be4.classicalb.core.parser.node.ALambdaExpression;
import de.be4.classicalb.core.parser.node.ALetSubstitution;
import de.be4.classicalb.core.parser.node.ALocalOperationsMachineClause;
import de.be4.classicalb.core.parser.node.AMachineClauseParseUnit;
import de.be4.classicalb.core.parser.node.AMachineHeader;
import de.be4.classicalb.core.parser.node.AMachineReference;
import de.be4.classicalb.core.parser.node.AOpSubstitution;
import de.be4.classicalb.core.parser.node.AOpWithReturnSubstitution;
import de.be4.classicalb.core.parser.node.AOperation;
import de.be4.classicalb.core.parser.node.AOperationsMachineClause;
import de.be4.classicalb.core.parser.node.AOppatternParseUnit;
import de.be4.classicalb.core.parser.node.AParallelSubstitution;
import de.be4.classicalb.core.parser.node.APartitionPredicate;
import de.be4.classicalb.core.parser.node.APredicateDefinition;
import de.be4.classicalb.core.parser.node.APredicateParseUnit;
import de.be4.classicalb.core.parser.node.APrimedIdentifierExpression;
import de.be4.classicalb.core.parser.node.APromotesMachineClause;
import de.be4.classicalb.core.parser.node.AProverComprehensionSetExpression;
import de.be4.classicalb.core.parser.node.AQuantifiedIntersectionExpression;
import de.be4.classicalb.core.parser.node.AQuantifiedUnionExpression;
import de.be4.classicalb.core.parser.node.ARecExpression;
import de.be4.classicalb.core.parser.node.ARefinementMachineParseUnit;
import de.be4.classicalb.core.parser.node.ASeesMachineClause;
import de.be4.classicalb.core.parser.node.ASeesModelClause;
import de.be4.classicalb.core.parser.node.ASelectSubstitution;
import de.be4.classicalb.core.parser.node.ASequenceExtensionExpression;
import de.be4.classicalb.core.parser.node.ASequenceSubstitution;
import de.be4.classicalb.core.parser.node.ASetExtensionExpression;
import de.be4.classicalb.core.parser.node.ASetsContextClause;
import de.be4.classicalb.core.parser.node.ASetsMachineClause;
import de.be4.classicalb.core.parser.node.AStringExpression;
import de.be4.classicalb.core.parser.node.AStructExpression;
import de.be4.classicalb.core.parser.node.ASubstitutionDefinition;
import de.be4.classicalb.core.parser.node.ASubstitutionParseUnit;
import de.be4.classicalb.core.parser.node.ATheoremsContextClause;
import de.be4.classicalb.core.parser.node.ATheoremsModelClause;
import de.be4.classicalb.core.parser.node.ATrueExpression;
import de.be4.classicalb.core.parser.node.ATruePredicate;
import de.be4.classicalb.core.parser.node.AUniversalQuantificationPredicate;
import de.be4.classicalb.core.parser.node.AUsesMachineClause;
import de.be4.classicalb.core.parser.node.AValuesMachineClause;
import de.be4.classicalb.core.parser.node.AVarSubstitution;
import de.be4.classicalb.core.parser.node.AVariablesMachineClause;
import de.be4.classicalb.core.parser.node.AVariablesModelClause;
import de.be4.classicalb.core.parser.node.AWitness;
import de.be4.classicalb.core.parser.node.EOF;
import de.be4.classicalb.core.parser.node.Node;
import de.be4.classicalb.core.parser.node.PEventstatus;
import de.be4.classicalb.core.parser.node.Start;
import de.be4.classicalb.core.parser.node.TIdentifierLiteral;
import de.prob.prolog.output.IPrologTermOutput;

/**
 * This class defines the output of a B machine as a prolog term.
 * 
 * @author plagge
 */
public class ASTProlog extends DepthFirstAdapter {
	// If a node class ends with one of the following constants, remove that
	// suffix to make Prolog functors more readable
	private static final List<String> IGNORE_ENDS = new LinkedList<String>(
			Arrays.asList("expression", "predicate", "machine_clause",
					"substitution", "parse_unit", "model_clause",
					"context_clause", "eventstatus", "argpattern"));

	private static final Map<String, String> REWRITINGS = createRewritings();

	// the simpleFormats are mappings from (simple) class names to prolog
	// functor representing them
	private final Map<String, String> simpleFormats = new HashMap<String, String>();

	// to look up the identifier of each node
	private final PositionPrinter positionPrinter;

	// helper object to print the prolog terms
	private final IPrologTermOutput pout;

	public ASTProlog(final IPrologTermOutput pout,
			final PositionPrinter positionPrinter) {
		this.positionPrinter = positionPrinter;
		this.pout = pout;
		if (positionPrinter != null) {
			positionPrinter.setPrologTermOutput(pout);
		}
	}

	private static Map<String, String> createRewritings() {
		Map<String, String> rewritings = new HashMap<String, String>();
		rewritings.put("unequal", "not_equal");
		rewritings.put("universal_quantification", "forall");
		rewritings.put("existential_quantification", "exists");
		rewritings.put("unary", "unary_minus");
		rewritings.put("belong", "member");
		rewritings.put("not_belong", "not_member");
		rewritings.put("include", "subset");
		rewritings.put("not_include", "not_subset");
		rewritings.put("include_strictly", "subset_strict");
		rewritings.put("not_include_strictly", "not_subset_strict");
		rewritings.put("op_with_return", "operation_call");
		rewritings.put("op", "operation_call");
		rewritings.put("subtract", "minus");
		rewritings.put("prover_comprehension_set", "comprehension_set");
		return Collections.unmodifiableMap(rewritings);
	}

	private static String rewrite(final String atom) {
		String result = atom == null ? null : REWRITINGS.get(atom);
		return result == null ? atom : result;
	}

	@Override
	public void inStart(final Start node) {
		// intentionally left blank: don't write the start node.
	}

	@Override
	public void outStart(final Start node) {
		pout.flush();
	}

	/**
	 * If the node is not handled otherwise, we just open it (see
	 * {@link #open(Node)}), print the sub-nodes, and close it later in
	 * {@link #defaultOut(Node)}
	 */
	@Override
	public void defaultIn(final Node node) {
		open(node);
	}

	/**
	 * This is the counterpart to {@link #defaultIn(Node)}
	 */
	@Override
	public void defaultOut(final Node node) {
		close(node);
	}

	/**
	 * This prints the functor of a prolog term together with the opening
	 * parenthesis. The first argument of the term is the identifier of the
	 * syntax tree element.
	 * 
	 * @param node
	 *            the node of the syntax tree, never <code>null</code>. It is
	 *            assumed that <code>node</code> is an abstract syntax tree
	 *            element, which class name is A* .
	 */
	private void open(final Node node) {
		pout.openTerm(simpleFormat(node));
		printPosition(node);
	}

	private void printPosition(final Node node) {
		if (positionPrinter != null) {
			positionPrinter.printPosition(node);
		} else {
			pout.printAtom("none");
		}
	}

	/**
	 * The counterpart to {@link #open(Node)}, prints the closing parenthesis of
	 * the term.
	 * 
	 * @param node
	 */
	private void close(final Node node) {
		pout.closeTerm();
	}

	/**
	 * Print a list of syntax tree elements as a Prolog list (
	 * <code>[term1, ..., termN]</code>)
	 * 
	 * @param nodes
	 *            A list of nodes, never <code>null</code>. The list may be
	 *            empty. The list does not use generics, because subclasses of
	 *            <code>Node</code> are used, too.
	 */
	private void printAsList(@SuppressWarnings("rawtypes") final List nodes) {
		pout.openList();
		for (Object elem : nodes) {
			((Node) elem).apply(this);
		}
		pout.closeList();
	}

	/**
	 * This method combines {@link #open(Node)}, {@link #printAsList(List)} and
	 * {@link #close(Node)}.
	 * 
	 * @param node
	 *            Like in {@link #open(Node)}
	 * @param list
	 *            Like in {@link #printAsList(List)}
	 */
	private void printOCAsList(final Node node,
			@SuppressWarnings("rawtypes") final List list) {
		open(node);
		printAsList(list);
		close(node);
	}

	/**
	 * @param node
	 *            Never <code>null</code>, node is assumed to be a terminal
	 *            symbol that can be printed as a simple string
	 */
	@Override
	public void defaultCase(final Node node) {
		pout.printAtom(node.toString().trim());
	}

	@Override
	public void caseEOF(final EOF node) {
		// do nothing
	}

	/**
	 * 
	 * @param node
	 * @return
	 */
	private String simpleFormat(final Node node) {
		String className = node.getClass().getSimpleName();
		String formatted = simpleFormats.get(className);
		if (formatted == null) {
			if (className.startsWith("A")) {
				className = className.substring(1);
				formatted = formatCamel(className).substring(1);
				int length = formatted.length();
				for (String checkend : IGNORE_ENDS) {
					if (formatted.endsWith(checkend)) {
						formatted = formatted.substring(0,
								length - checkend.length() - 1);
						break;
					}
				}
			} else {
				formatted = className;
			}
			formatted = rewrite(formatted);
			simpleFormats.put(className, formatted);
		}
		return formatted;
	}

	/**
	 * 
	 * @param input
	 *            A string with an identifier in camel style (e.g.
	 *            ClassDoingSomeStuff), never <code>null</code>.
	 * @return The input string in lower case and seperated by _ (e.g.
	 *         class_doing_some_stuff).
	 */
	private String formatCamel(final String input) {
		StringWriter out = new StringWriter();
		char[] chars = input.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			char current = chars[i];
			if (Character.isUpperCase(current)) {
				out.append('_');
				out.append(Character.toLowerCase(current));
			} else {
				out.append(current);
			}
		}
		return out.toString();
	}

	private void printIdentifier(final LinkedList<TIdentifierLiteral> list) {
		pout.printAtom(Utils.getIdentifierAsString(list));
	}

	private void printNullSafeSubstitution(final Node subst) {
		if (subst == null) {
			pout.openTerm("skip");
			pout.printAtom("none");
			pout.closeTerm();
		} else {
			subst.apply(this);
		}
	}

	@Override
	public void caseAIdentifierExpression(final AIdentifierExpression node) {
		open(node);
		printIdentifier(node.getIdentifier());
		close(node);
	}

	@Override
	public void caseAPrimedIdentifierExpression(
			final APrimedIdentifierExpression node) {
		open(node);
		printIdentifier(node.getIdentifier());
		pout.printNumber(Long.parseLong((node.getGrade().getText())));
		close(node);
	}

	/***************************************************************************
	 * special cases with lists
	 */

	// Parse Units
	@Override
	public void caseAAbstractMachineParseUnit(
			final AAbstractMachineParseUnit node) {
		open(node);
		pout.printAtom(node.getType().getText());
		node.getHeader().apply(this);
		printAsList(node.getMachineClauses());
		close(node);
	}

	@Override
	public void caseAStringExpression(final AStringExpression node) {
		inAStringExpression(node);
		if (node.getContent() != null) {
			node.getContent().apply(this);
		} else {
			pout.printAtom("");
		}
		outAStringExpression(node);
	}

	@Override
	public void caseARefinementMachineParseUnit(
			final ARefinementMachineParseUnit node) {
		open(node);
		node.getHeader().apply(this);
		node.getRefMachine().apply(this);
		printAsList(node.getMachineClauses());
		close(node);
	}

	@Override
	public void caseAImplementationMachineParseUnit(
			final AImplementationMachineParseUnit node) {
		open(node);
		node.getHeader().apply(this);
		node.getRefMachine().apply(this);
		printAsList(node.getMachineClauses());
		close(node);
	}

	// machine header

	@Override
	public void caseAMachineHeader(final AMachineHeader node) {
		open(node);
		printIdentifier(node.getName());
		printAsList(node.getParameters());
		close(node);
	}

	// machine clauses

	@Override
	public void caseADefinitionsMachineClause(
			final ADefinitionsMachineClause node) {
		printOCAsList(node, node.getDefinitions());
	}

	@Override
	public void caseASeesMachineClause(final ASeesMachineClause node) {
		printOCAsList(node, node.getMachineNames());
	}

	@Override
	public void caseAPromotesMachineClause(final APromotesMachineClause node) {
		printOCAsList(node, node.getMachineNames());
	}

	@Override
	public void caseAUsesMachineClause(final AUsesMachineClause node) {
		printOCAsList(node, node.getMachineNames());
	}

	@Override
	public void caseAIncludesMachineClause(final AIncludesMachineClause node) {
		printOCAsList(node, node.getMachineReferences());
	}

	@Override
	public void caseAExtendsMachineClause(final AExtendsMachineClause node) {
		printOCAsList(node, node.getMachineReferences());
	}

	@Override
	public void caseAImportsMachineClause(final AImportsMachineClause node) {
		printOCAsList(node, node.getMachineReferences());
	}

	@Override
	public void caseASetsMachineClause(final ASetsMachineClause node) {
		printOCAsList(node, node.getSetDefinitions());
	}

	@Override
	public void caseAVariablesMachineClause(final AVariablesMachineClause node) {
		printOCAsList(node, node.getIdentifiers());
	}

	@Override
	public void caseAConcreteVariablesMachineClause(
			final AConcreteVariablesMachineClause node) {
		printOCAsList(node, node.getIdentifiers());
	}

	@Override
	public void caseAAbstractConstantsMachineClause(
			final AAbstractConstantsMachineClause node) {
		printOCAsList(node, node.getIdentifiers());
	}

	@Override
	public void caseAConstantsMachineClause(final AConstantsMachineClause node) {
		printOCAsList(node, node.getIdentifiers());
	}

	@Override
	public void caseAAssertionsMachineClause(final AAssertionsMachineClause node) {
		printOCAsList(node, node.getPredicates());
	}

	@Override
	public void caseAValuesMachineClause(final AValuesMachineClause node) {
		printOCAsList(node, node.getEntries());
	}

	@Override
	public void caseALocalOperationsMachineClause(
			final ALocalOperationsMachineClause node) {
		printOCAsList(node, node.getOperations());
	}

	@Override
	public void caseAOperationsMachineClause(final AOperationsMachineClause node) {
		printOCAsList(node, node.getOperations());
	}

	// machine reference

	@Override
	public void caseAMachineReference(final AMachineReference node) {
		open(node);
		printIdentifier(node.getMachineName());
		printAsList(node.getParameters());
		close(node);
	}

	// definition

	@Override
	public void caseAPredicateDefinition(final APredicateDefinition node) {
		open(node);
		node.getName().apply(this);
		printAsList(node.getParameters());
		node.getRhs().apply(this);
		close(node);
	}

	@Override
	public void caseASubstitutionDefinition(final ASubstitutionDefinition node) {
		open(node);
		node.getName().apply(this);
		printAsList(node.getParameters());
		node.getRhs().apply(this);
		close(node);
	}

	@Override
	public void caseAExpressionDefinition(final AExpressionDefinition node) {
		open(node);
		node.getName().apply(this);
		printAsList(node.getParameters());
		node.getRhs().apply(this);
		close(node);
	}

	// set

	@Override
	public void caseAEnumeratedSet(final AEnumeratedSet node) {
		open(node);
		printIdentifier(node.getIdentifier());
		printAsList(node.getElements());
		close(node);
	}

	// operation

	@Override
	public void caseAOperation(final AOperation node) {
		open(node);
		pout.openTerm("identifier");
		printPosition(node);
		printIdentifier(node.getOpName());
		pout.closeTerm();
		printAsList(node.getReturnValues());
		printAsList(node.getParameters());
		node.getOperationBody().apply(this);
		close(node);
	}

	// predicate

	@Override
	public void caseAUniversalQuantificationPredicate(
			final AUniversalQuantificationPredicate node) {
		open(node);
		printAsList(node.getIdentifiers());
		node.getImplication().apply(this);
		close(node);
	}

	@Override
	public void caseAExistentialQuantificationPredicate(
			final AExistentialQuantificationPredicate node) {
		open(node);
		printAsList(node.getIdentifiers());
		node.getPredicate().apply(this);
		close(node);
	}

	@Override
	public void caseADefinitionPredicate(final ADefinitionPredicate node) {
		open(node);
		node.getDefLiteral().apply(this);
		printAsList(node.getParameters());
		close(node);
	}

	// expression

	@Override
	public void caseAGeneralSumExpression(final AGeneralSumExpression node) {
		open(node);
		printAsList(node.getIdentifiers());
		node.getPredicates().apply(this);
		node.getExpression().apply(this);
		close(node);
	}

	@Override
	public void caseAGeneralProductExpression(
			final AGeneralProductExpression node) {
		open(node);
		printAsList(node.getIdentifiers());
		node.getPredicates().apply(this);
		node.getExpression().apply(this);
		close(node);
	}

	@Override
	public void caseACoupleExpression(final ACoupleExpression node) {
		printOCAsList(node, node.getList());
	}

	@Override
	public void caseAComprehensionSetExpression(
			final AComprehensionSetExpression node) {
		open(node);
		printAsList(node.getIdentifiers());
		node.getPredicates().apply(this);
		close(node);
	}

	@Override
	public void caseAProverComprehensionSetExpression(
			final AProverComprehensionSetExpression node) {
		open(node);
		printAsList(node.getIdentifiers());
		node.getPredicates().apply(this);
		close(node);
	}

	@Override
	public void caseAEventBComprehensionSetExpression(
			final AEventBComprehensionSetExpression node) {
		open(node);
		printAsList(node.getIdentifiers());
		node.getExpression().apply(this);
		node.getPredicates().apply(this);
		close(node);
	}

	@Override
	public void caseASetExtensionExpression(final ASetExtensionExpression node) {
		printOCAsList(node, node.getExpressions());
	}

	@Override
	public void caseAQuantifiedUnionExpression(
			final AQuantifiedUnionExpression node) {
		open(node);
		printAsList(node.getIdentifiers());
		node.getPredicates().apply(this);
		node.getExpression().apply(this);
		close(node);
	}

	@Override
	public void caseAQuantifiedIntersectionExpression(
			final AQuantifiedIntersectionExpression node) {
		open(node);
		printAsList(node.getIdentifiers());
		node.getPredicates().apply(this);
		node.getExpression().apply(this);
		close(node);
	}

	@Override
	public void caseALambdaExpression(final ALambdaExpression node) {
		open(node);
		printAsList(node.getIdentifiers());
		node.getPredicate().apply(this);
		node.getExpression().apply(this);
		close(node);
	}

	@Override
	public void caseASequenceExtensionExpression(
			final ASequenceExtensionExpression node) {
		printOCAsList(node, node.getExpression());
	}

	@Override
	public void caseAFunctionExpression(final AFunctionExpression node) {
		open(node);
		node.getIdentifier().apply(this);
		printAsList(node.getParameters());
		close(node);
	}

	@Override
	public void caseARecExpression(final ARecExpression node) {
		printOCAsList(node, node.getEntries());
	}

	@Override
	public void caseAStructExpression(final AStructExpression node) {
		printOCAsList(node, node.getEntries());
	}

	@Override
	public void caseAIntegerExpression(final AIntegerExpression node) {
		open(node);
		final String text = node.getLiteral().getText();
		if (text.length() < 17) {
			pout.printNumber(Long.parseLong(text));
		} else {
			pout.printNumber(new BigInteger(text));
		}
		close(node);
	}

	@Override
	public void caseADefinitionExpression(final ADefinitionExpression node) {
		open(node);
		node.getDefLiteral().apply(this);
		printAsList(node.getParameters());
		close(node);
	}

	// substitutions

	@Override
	public void caseAAssignSubstitution(final AAssignSubstitution node) {
		open(node);
		printAsList(node.getLhsExpression());
		printAsList(node.getRhsExpressions());
		close(node);
	}

	@Override
	public void caseAChoiceSubstitution(final AChoiceSubstitution node) {
		printOCAsList(node, node.getSubstitutions());
	}

	@Override
	public void caseAIfSubstitution(final AIfSubstitution node) {
		open(node);
		node.getCondition().apply(this);
		node.getThen().apply(this);
		printAsList(node.getElsifSubstitutions());
		printNullSafeSubstitution(node.getElse());
		close(node);
	}

	@Override
	public void caseASelectSubstitution(final ASelectSubstitution node) {
		open(node);
		node.getCondition().apply(this);
		node.getThen().apply(this);
		printAsList(node.getWhenSubstitutions());
		final Node elsenode = node.getElse();
		if (elsenode != null) {
			elsenode.apply(this);
		}
		close(node);
	}

	@Override
	public void caseACaseSubstitution(final ACaseSubstitution node) {
		open(node);
		node.getExpression().apply(this);
		printAsList(node.getEitherExpr());
		node.getEitherSubst().apply(this);
		printAsList(node.getOrSubstitutions());
		printNullSafeSubstitution(node.getElse());
		close(node);
	}

	@Override
	public void caseACaseOrSubstitution(final ACaseOrSubstitution node) {
		open(node);
		printAsList(node.getExpressions());
		node.getSubstitution().apply(this);
		close(node);
	}

	@Override
	public void caseAAnySubstitution(final AAnySubstitution node) {
		open(node);
		printAsList(node.getIdentifiers());
		node.getWhere().apply(this);
		node.getThen().apply(this);
		close(node);
	}

	@Override
	public void caseALetSubstitution(final ALetSubstitution node) {
		open(node);
		printAsList(node.getIdentifiers());
		node.getPredicate().apply(this);
		node.getSubstitution().apply(this);
		close(node);
	}

	@Override
	public void caseABecomesElementOfSubstitution(
			final ABecomesElementOfSubstitution node) {
		open(node);
		printAsList(node.getIdentifiers());
		node.getSet().apply(this);
		close(node);
	}

	@Override
	public void caseABecomesSuchSubstitution(final ABecomesSuchSubstitution node) {
		open(node);
		printAsList(node.getIdentifiers());
		node.getPredicate().apply(this);
		close(node);
	}

	@Override
	public void caseAVarSubstitution(final AVarSubstitution node) {
		open(node);
		printAsList(node.getIdentifiers());
		node.getSubstitution().apply(this);
		close(node);
	}

	@Override
	public void caseASequenceSubstitution(final ASequenceSubstitution node) {
		printOCAsList(node, node.getSubstitutions());
	}

	@Override
	public void caseAOpSubstitution(final AOpSubstitution node) {
		open(node);
		node.getName().apply(this);
		pout.emptyList();
		printAsList(node.getParameters());
		close(node);
	}

	@Override
	public void caseAOpWithReturnSubstitution(
			final AOpWithReturnSubstitution node) {
		open(node);
		pout.openTerm("identifier");
		printPosition(node);
		printIdentifier(node.getOperation());
		pout.closeTerm();
		printAsList(node.getResultIdentifiers());
		printAsList(node.getParameters());
		close(node);
	}

	@Override
	public void caseAParallelSubstitution(final AParallelSubstitution node) {
		printOCAsList(node, node.getSubstitutions());
	}

	@Override
	public void caseADefinitionSubstitution(final ADefinitionSubstitution node) {
		open(node);
		node.getDefLiteral().apply(this);
		printAsList(node.getParameters());
		close(node);
	}

	// true and false

	@Override
	public void caseATrueExpression(final ATrueExpression node) {
		pout.openTerm("boolean_true");
		printPosition(node);
		pout.closeTerm();
	}

	@Override
	public void caseAFalseExpression(final AFalseExpression node) {
		pout.openTerm("boolean_false");
		printPosition(node);
		pout.closeTerm();
	}

	@Override
	public void caseATruePredicate(final ATruePredicate node) {
		pout.openTerm("truth");
		printPosition(node);
		pout.closeTerm();
	}

	@Override
	public void caseAFalsePredicate(final AFalsePredicate node) {
		pout.openTerm("falsity");
		printPosition(node);
		pout.closeTerm();
	}

	@Override
	public void caseAPartitionPredicate(final APartitionPredicate node) {
		open(node);
		node.getSet().apply(this);
		printAsList(node.getElements());
		close(node);
	}

	// ignore some nodes

	@Override
	public void caseAExpressionParseUnit(final AExpressionParseUnit node) {
		node.getExpression().apply(this);
	}

	@Override
	public void caseAMachineClauseParseUnit(final AMachineClauseParseUnit node) {
		node.getMachineClause().apply(this);
	}

	@Override
	public void caseAPredicateParseUnit(final APredicateParseUnit node) {
		node.getPredicate().apply(this);
	}

	@Override
	public void caseASubstitutionParseUnit(final ASubstitutionParseUnit node) {
		node.getSubstitution().apply(this);
	}

	@Override
	public void caseAEventBModelParseUnit(final AEventBModelParseUnit node) {
		open(node);
		node.getName().apply(this);
		printAsList(node.getModelClauses());
		close(node);
	}

	@Override
	public void caseAVariablesModelClause(final AVariablesModelClause node) {
		printOCAsList(node, node.getIdentifiers());
	}

	@Override
	public void caseASeesModelClause(final ASeesModelClause node) {
		printOCAsList(node, node.getSees());
	}

	@Override
	public void caseAInvariantModelClause(final AInvariantModelClause node) {
		printOCAsList(node, node.getPredicates());
	}

	@Override
	public void caseATheoremsModelClause(final ATheoremsModelClause node) {
		printOCAsList(node, node.getPredicates());
	}

	@Override
	public void caseAEventsModelClause(final AEventsModelClause node) {
		printOCAsList(node, node.getEvent());
	}

	@Override
	public void caseAEvent(final AEvent node) {
		open(node);
		node.getEventName().apply(this);
		final PEventstatus status = node.getStatus();
		if (status != null) {
			status.apply(this);
		}
		printAsList(node.getRefines());
		printAsList(node.getVariables());
		printAsList(node.getGuards());
		printAsList(node.getTheorems());
		printAsList(node.getAssignments());
		printAsList(node.getWitness());
		close(node);
	}

	@Override
	public void caseAWitness(final AWitness node) {
		open(node);
		pout.openTerm("identifier");
		printPosition(node);
		pout.printAtom(node.getName().getText().trim());
		pout.closeTerm();
		node.getPredicate().apply(this);
		close(node);
	}

	@Override
	public void caseAEventBContextParseUnit(final AEventBContextParseUnit node) {
		open(node);
		node.getName().apply(this);
		printAsList(node.getContextClauses());
		close(node);
	}

	@Override
	public void caseAExtendsContextClause(final AExtendsContextClause node) {
		printOCAsList(node, node.getExtends());
	}

	@Override
	public void caseASetsContextClause(final ASetsContextClause node) {
		printOCAsList(node, node.getSet());
	}

	@Override
	public void caseAConstantsContextClause(final AConstantsContextClause node) {
		printOCAsList(node, node.getIdentifiers());
	}

	@Override
	public void caseAAxiomsContextClause(final AAxiomsContextClause node) {
		printOCAsList(node, node.getPredicates());
	}

	@Override
	public void caseATheoremsContextClause(final ATheoremsContextClause node) {
		printOCAsList(node, node.getPredicates());
	}

	@Override
	public void caseAOppatternParseUnit(final AOppatternParseUnit node) {
		open(node);
		printIdentifier(node.getName());
		printAsList(node.getParameters());
		close(node);
	}

}