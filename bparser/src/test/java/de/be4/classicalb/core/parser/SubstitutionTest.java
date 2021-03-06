package de.be4.classicalb.core.parser;

import static org.junit.Assert.*;

import org.junit.Test;

import de.be4.classicalb.core.parser.analysis.Ast2String;
import de.be4.classicalb.core.parser.exceptions.BException;
import de.be4.classicalb.core.parser.exceptions.CheckException;
import de.be4.classicalb.core.parser.node.Start;

public class SubstitutionTest {

	@Test
	public void testParallelAssignWithComposedId() throws Exception {
		final String testMachine = "#SUBSTITUTION xx.yy, aa.bb := 5, 3";
		final String result = getTreeAsString(testMachine);

		assertEquals(
				"Start(ASubstitutionParseUnit(AAssignSubstitution([AIdentifierExpression([xx,yy]),AIdentifierExpression([aa,bb])],[AIntegerExpression(5),AIntegerExpression(3)])))",
				result);
	}

	@Test
	public void testParallelAssignWithNonIdentifier() {
		final String testMachine = "#SUBSTITUTION xx,yy,5  := 5, 3, zz";
		try {
			getTreeAsString(testMachine);
			fail("Expected exception");
		} catch (final BException e) {
			final CheckException cause = (CheckException) e.getCause();
			assertEquals(1, cause.getNodes().length);
			assertNotNull(cause.getNodes()[0]);
		}

	}

	@Test
	public void testPreconditionBool() throws Exception {
		final String testMachine = "#SUBSTITUTION PRE 1=1 THEN skip END";
		final String result = getTreeAsString(testMachine);

		assertEquals(
				"Start(ASubstitutionParseUnit(APreconditionSubstitution(AEqualPredicate(AIntegerExpression(1),AIntegerExpression(1)),ASkipSubstitution())))",
				result);
	}

	@Test
	public void testParallelList() throws Exception {
		final String testMachine = "#SUBSTITUTION skip || a:=b || x";
		final String result = getTreeAsString(testMachine);

		assertEquals(
			        "Start(ASubstitutionParseUnit(AParallelSubstitution([ASkipSubstitution(),AAssignSubstitution([AIdentifierExpression([a])],[AIdentifierExpression([b])]),AOpSubstitution(AIdentifierExpression([x]),[])])))",
				result);
	}

	@Test
	public void testSequenceList() throws Exception {
		final String testMachine = "#SUBSTITUTION skip ; x ; y";
		final String result = getTreeAsString(testMachine);

		assertEquals(
			        "Start(ASubstitutionParseUnit(ASequenceSubstitution([ASkipSubstitution(),AOpSubstitution(AIdentifierExpression([x]),[]),AOpSubstitution(AIdentifierExpression([y]),[])])))",
				result);
	}

	@Test
	public void testParallelAndSequence() throws Exception {
		final String testMachine = "#SUBSTITUTION skip || x ; y";
		final String result = getTreeAsString(testMachine);

		assertEquals(
			     "Start(ASubstitutionParseUnit(ASequenceSubstitution([AParallelSubstitution([ASkipSubstitution(),AOpSubstitution(AIdentifierExpression([x]),[])]),AOpSubstitution(AIdentifierExpression([y]),[])])))",
				result);
	}

	@Test
	public void testOperation1() throws Exception {
		final String testMachine = "#SUBSTITUTION op1;op2(x)";
		final String result = getTreeAsString(testMachine);

		assertEquals(
			     "Start(ASubstitutionParseUnit(ASequenceSubstitution([AOpSubstitution(AIdentifierExpression([op1]),[]),AOpSubstitution(AIdentifierExpression([op2]),[AIdentifierExpression([x])])])))",
				result);
	}

	@Test
	public void testOperation2() throws Exception {
		final String testMachine = "#SUBSTITUTION function(x)(y)";
		final String result = getTreeAsString(testMachine);

		assertEquals(
			        "Start(ASubstitutionParseUnit(AOpSubstitution(AFunctionExpression(AIdentifierExpression([function]),[AIdentifierExpression([x])]),[AIdentifierExpression([y])])))",
				result);
	}

	@Test
	public void testFunctionSubstitution() throws Exception {
		final String testMachine = "#SUBSTITUTION f(x) := y";
		final String result = getTreeAsString(testMachine);

		assertEquals(
				"Start(ASubstitutionParseUnit(AAssignSubstitution([AFunctionExpression(AIdentifierExpression([f]),[AIdentifierExpression([x])])],[AIdentifierExpression([y])])))",
				result);

	}

	@Test
	public void testMultiFunctionSubstitution() throws Exception {
		final String testMachine = "#SUBSTITUTION f(x),g(y),h := a,b,c";
		final String result = getTreeAsString(testMachine);

		assertEquals(
				"Start(ASubstitutionParseUnit(AAssignSubstitution([AFunctionExpression(AIdentifierExpression([f]),[AIdentifierExpression([x])]),AFunctionExpression(AIdentifierExpression([g]),[AIdentifierExpression([y])]),AIdentifierExpression([h])],[AIdentifierExpression([a]),AIdentifierExpression([b]),AIdentifierExpression([c])])))",
				result);

	}

	private String getTreeAsString(final String testMachine) throws BException {
		// System.out.println("Parsing \"" + testMachine + "\"");
		final BParser parser = new BParser("testcase");
		final Start startNode = parser.parse(testMachine, false);

		// startNode.apply(new ASTPrinter());
		final Ast2String ast2String = new Ast2String();
		startNode.apply(ast2String);
		final String string = ast2String.toString();
		// System.out.println(string);
		return string;
	}
}
