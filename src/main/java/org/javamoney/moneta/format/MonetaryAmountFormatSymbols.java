package org.javamoney.moneta.format;

import java.text.DecimalFormat;
import java.util.Objects;

import javax.money.MonetaryAmount;
import javax.money.format.MonetaryAmountFormat;

import org.javamoney.moneta.function.MonetaryAmountProducer;
import org.javamoney.moneta.function.MoneyProducer;
/**
 *The {@link MonetaryAmountFormat} that uses the {@link MonetaryAmountSymbols} to format {@link MonetaryAmount}.
 * @author Otavio Santana
 * @see {@link MonetaryAmountSymbols}
 * @see {@link MonetaryAmountFormat}
 * @see {@link MonetaryAmountNumericInformation}
 * @since 1.0.1
 */
public abstract class MonetaryAmountFormatSymbols implements MonetaryAmountFormat {

	/**
	 * Gets the {@link MonetaryAmountSymbols} used in this {@link MonetaryAmountFormatSymbols}
	 * @return symbols
	 */
	abstract MonetaryAmountSymbols getAmountSymbols();
	/**
	 * Gets the {@link MonetaryAmountNumericInformation} used in this {@link MonetaryAmountFormatSymbols}
	 * @return numeric information
	 */
	abstract MonetaryAmountNumericInformation getNumericInformation();

	/**
	 * Creates {@link MonetaryAmountSymbols} using the symbols and producer
	 * @param symbols
	 * @param producer
	 * @return the {@link MonetaryAmountSymbols}
	 * @see {@link MonetaryAmountSymbols}
	 * @see {@link MonetaryAmountFormatSymbols}
	 * @see {@link MonetaryAmountProducer}
	 */
	public static MonetaryAmountFormatSymbols of(MonetaryAmountSymbols symbols, MonetaryAmountProducer producer) {
		return new DefaultMonetaryAmountFormatSymbols(Objects.requireNonNull(symbols), Objects.requireNonNull(producer));
	}
	/**
	 * Creates {@link MonetaryAmountSymbols} using the pattern and symbol.
	 * @param pattern
	 * @param symbols
	 * @param producer
	 * @return the {@link MonetaryAmountSymbols}
	 * @see {@link MonetaryAmountSymbols}
	 * @see {@link MonetaryAmountFormatSymbols}
	 * @see {@link MonetaryAmountProducer}
	 * @see {@link DecimalFormat}
	 */
	public static MonetaryAmountFormatSymbols of(String pattern, MonetaryAmountSymbols symbols, MonetaryAmountProducer producer) {
		return new DefaultMonetaryAmountFormatSymbols(
				Objects.requireNonNull(pattern),
				Objects.requireNonNull(symbols),
				Objects.requireNonNull(producer));
	}
	/**
	 * Creates a default {@link MonetaryAmountSymbols}.
	 * @return the {@link MonetaryAmountSymbols}
	 * @see {@link MonetaryAmountSymbols}
	 * @see {@link MonetaryAmountFormatSymbols}
	 * @see {@link MonetaryAmountProducer}
	 * @see {@link DecimalFormat}
	 */
	public static MonetaryAmountFormatSymbols getDefafult() {
		return new DefaultMonetaryAmountFormatSymbols(new MonetaryAmountSymbols(), new MoneyProducer());
	}

}
