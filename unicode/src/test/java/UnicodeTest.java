import static org.junit.Assert.*;
import  org.junit.Test;
import de.prob.unicode.UnicodeTranslator;

public class UnicodeTest {

	@Test public void badAssTests() {
		assertEquals("nafor", UnicodeTranslator.toAscii("nafor"));
		assertEquals("x:   NAT", UnicodeTranslator.toAscii("x\u2208   NAT"));
		assertEquals("cur_floor := groundf",UnicodeTranslator.toAscii("cur_floor \u2254 groundf"));
		assertEquals("cur_floor < groundf",UnicodeTranslator.toAscii("cur_floor < groundf"));
		assertEquals("direction_up = FALSE",UnicodeTranslator.toAscii("direction_up = FALSE"));
		assertEquals("b : (groundf .. topf)",UnicodeTranslator.toAscii("b \u2208 (groundf \uu2025 topf)"));
		assertEquals("call_buttons := call_buttons \\/ {b}",UnicodeTranslator.toAscii("call_buttons \u2254 call_buttons \u222a {b}"));
		assertEquals("b /: call_buttons",UnicodeTranslator.toAscii("b \u2209 call_buttons"));
	}
}
