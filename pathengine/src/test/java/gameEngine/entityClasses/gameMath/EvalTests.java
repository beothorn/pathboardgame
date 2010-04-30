package gameEngine.entityClasses.gameMath;

import gameEngine.gameMath.Eval;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EvalTests {

	@Before
	public void setPrecision(){
		Eval.setPrecision(0.01);
	}

	@Test
	public void testComparations(){
		Assert.assertTrue(Eval.equals(5.001, 5.009));
		Assert.assertTrue(Eval.greaterOrEqualsTo(5.001, 5.009));
		Assert.assertFalse(Eval.greaterThan(5.001, 5.009));
		Assert.assertTrue(Eval.lessOrEqualsTo(5.001, 5.009));
		Assert.assertFalse(Eval.lessThan(5.001, 5.009));

		Assert.assertFalse(Eval.equals(5.01, 5.09));
		Assert.assertFalse(Eval.greaterOrEqualsTo(5.01, 5.09));
		Assert.assertFalse(Eval.greaterThan(5.01, 5.09));
		Assert.assertTrue(Eval.lessOrEqualsTo(5.01, 5.09));
		Assert.assertTrue(Eval.lessThan(5.01, 5.09));

		final double newPrecision = 0.1;

		Assert.assertTrue(Eval.equals(5.01, 5.09,newPrecision));
		Assert.assertTrue(Eval.greaterOrEqualsTo(5.01, 5.09,newPrecision));
		Assert.assertFalse(Eval.greaterThan(5.01, 5.09,newPrecision));
		Assert.assertTrue(Eval.lessOrEqualsTo(5.01, 5.09,newPrecision));
		Assert.assertFalse(Eval.lessThan(5.01, 5.09,newPrecision));

		Assert.assertFalse(Eval.equals(5.1, 5.9,newPrecision));
		Assert.assertFalse(Eval.greaterOrEqualsTo(5.1, 5.9,newPrecision));
		Assert.assertFalse(Eval.greaterThan(5.1, 5.9,newPrecision));
		Assert.assertTrue(Eval.lessOrEqualsTo(5.1, 5.9,newPrecision));
		Assert.assertTrue(Eval.lessThan(5.1, 5.9,newPrecision));

	}


}
