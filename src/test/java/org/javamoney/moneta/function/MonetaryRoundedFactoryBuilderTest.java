package org.javamoney.moneta.function;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import java.math.MathContext;
import java.math.RoundingMode;

import javax.money.MonetaryAmount;
import javax.money.MonetaryOperator;

import org.testng.annotations.Test;



public class MonetaryRoundedFactoryBuilderTest {


	@Test(expectedExceptions = NullPointerException.class)
	public void shouldReturnErrorWhenMonetaryOperatorIsNull() {
		MonetaryOperator roundingOperator = null;
		MonetaryRoundedFactory.of(roundingOperator);
		fail();
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void shouldReturnErrorWhenMathContextIsNull() {
		MathContext mathContext = null;
		MonetaryRoundedFactory.of(mathContext);
		fail();
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void shouldReturnErrorWhenRoundingModeIsNull() {
		RoundingMode roundingMode = null;
		MonetaryRoundedFactory.withRoundingMode(roundingMode);
		fail();
	}

	@Test
	public void shouldReturnTheSameMonetaryOperator() {
		MonetaryOperator monetaryOperator = new MonetaryOperator() {

			@Override
			public MonetaryAmount apply(MonetaryAmount amount) {
				return amount;
			}
		};
		MonetaryRoundedFactory factory = MonetaryRoundedFactory.of(monetaryOperator);
		assertNotNull(factory);
		assertEquals(monetaryOperator, factory.getRoundingOperator());

	}

	@Test
	public void shouldReturnTheSameMathContextOperator() {
		MonetaryRoundedFactory factory = MonetaryRoundedFactory.of(MathContext.DECIMAL32);
		assertNotNull(factory);
		assertNotNull(factory.getRoundingOperator());
	}

	@Test
	public void shouldReturnMathContextOperator() {
		int precision = 6;
		RoundingMode roundingMode = RoundingMode.HALF_EVEN;
		MonetaryRoundedFactory factory = MonetaryRoundedFactory
				.withRoundingMode(roundingMode).withPrecision(precision)
				.build();

		assertNotNull(factory);
		MonetaryOperator roundingOperator = factory.getRoundingOperator();
		assertNotNull(roundingOperator);
		assertTrue(PrecisionContextRoundedOperator.class.isInstance(roundingOperator));
		MathContext result = PrecisionContextRoundedOperator.class.cast(roundingOperator).getMathContext();

		assertEquals(precision, result.getPrecision());
		assertEquals(roundingMode, result.getRoundingMode());

	}

	@Test
	public void shouldReturnScaleRoundingOperator() {
		int scale = 6;
		RoundingMode roundingMode = RoundingMode.HALF_EVEN;
		MonetaryRoundedFactory factory = MonetaryRoundedFactory
				.withRoundingMode(roundingMode).withScale(scale)
				.build();

		assertNotNull(factory);
		MonetaryOperator roundingOperator = factory.getRoundingOperator();
		assertNotNull(roundingOperator);
		assertTrue(ScaleRoundedOperator.class.isInstance(roundingOperator));
		ScaleRoundedOperator result = ScaleRoundedOperator.class.cast(roundingOperator);

		assertEquals(scale, result.getScale());
		assertEquals(roundingMode, result.getRoundingMode());

	}

	@Test
	public void shouldReturnMathContextScaleOperator() {
		int precision = 6;
		int scale = 3;
		RoundingMode roundingMode = RoundingMode.HALF_EVEN;
		MonetaryRoundedFactory factory = MonetaryRoundedFactory
				.withRoundingMode(roundingMode).withPrecision(precision).withScale(scale)
				.build();

		assertNotNull(factory);
		MonetaryOperator roundingOperator = factory.getRoundingOperator();
		assertNotNull(roundingOperator);
		assertTrue(PrecisionScaleRoundedOperator.class.isInstance(roundingOperator));
		PrecisionScaleRoundedOperator result = PrecisionScaleRoundedOperator.class.cast(roundingOperator);

		assertEquals(precision, result.getMathContext().getPrecision());
		assertEquals(scale, result.getScale());
		assertEquals(roundingMode, result.getMathContext().getRoundingMode());

	}

	@Test
	public void shouldReturnScaleMathContextOperator() {
		int precision = 6;
		int scale = 3;
		RoundingMode roundingMode = RoundingMode.HALF_EVEN;
		MonetaryRoundedFactory factory = MonetaryRoundedFactory
				.withRoundingMode(roundingMode).withScale(scale).withPrecision(precision)
				.build();

		assertNotNull(factory);
		MonetaryOperator roundingOperator = factory.getRoundingOperator();
		assertNotNull(roundingOperator);
		assertTrue(PrecisionScaleRoundedOperator.class.isInstance(roundingOperator));
		PrecisionScaleRoundedOperator result = PrecisionScaleRoundedOperator.class.cast(roundingOperator);

		assertEquals(precision, result.getMathContext().getPrecision());
		assertEquals(scale, result.getScale());
		assertEquals(roundingMode, result.getMathContext().getRoundingMode());

	}

}