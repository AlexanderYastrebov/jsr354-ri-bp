package org.javamoney.moneta.format;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Objects;

import javax.money.MonetaryAmount;
import javax.money.format.AmountFormatContext;
import javax.money.format.AmountFormatContextBuilder;
import javax.money.format.MonetaryParseException;

import org.javamoney.moneta.function.MonetaryAmountProducer;

/**
 * The default implementation that uses the {@link DecimalFormat} as formatter.
 * @author Otavio Santana
 * @since 1.0.1
 */
class DefaultMonetaryAmountFormatSymbols extends MonetaryAmountFormatSymbols {

	static final String STYLE = "MonetaryAmountFormatSymbols";

	private static final AmountFormatContext CONTEXT = AmountFormatContextBuilder.of(STYLE).build();

	private final MonetaryAmountSymbols symbols;

	private final DecimalFormat decimalFormat;

	private final MonetaryAmountProducer producer;

	private final MonetaryAmountNumericInformation numericInformation;

	DefaultMonetaryAmountFormatSymbols(MonetaryAmountSymbols symbols, MonetaryAmountProducer producer) {
		this.symbols = symbols;
		this.producer = producer;
		this.decimalFormat = (DecimalFormat) DecimalFormat.getCurrencyInstance();
		decimalFormat.setDecimalFormatSymbols(symbols.getFormatSymbol());
		numericInformation = new MonetaryAmountNumericInformation(decimalFormat);
	}

	DefaultMonetaryAmountFormatSymbols(String pattern, MonetaryAmountSymbols symbols, MonetaryAmountProducer producer) {
		this.symbols = symbols;
		this.producer = producer;
		this.decimalFormat = new DecimalFormat(pattern, symbols.getFormatSymbol());
		numericInformation = new MonetaryAmountNumericInformation(decimalFormat);
	}

	@Override
	public AmountFormatContext getContext() {
		return CONTEXT;
	}

	@Override
	public void print(Appendable appendable, MonetaryAmount amount)
			throws IOException {

		Objects.requireNonNull(appendable);
		appendable.append(queryFrom(amount));
	}

	@Override
	public MonetaryAmount parse(CharSequence text)
			throws MonetaryParseException {
		Objects.requireNonNull(text);
		try {
			Number number = decimalFormat.parse(text.toString());
			return producer.create(symbols.getCurrency(), number);
		}catch (Exception exception) {
			throw new MonetaryParseException(exception.getMessage(), text, 0);
		}
	}

	@Override
	public String queryFrom(MonetaryAmount amount) {
		if(amount == null) {
			return "null";
		}
		return decimalFormat.format((amount.getNumber().numberValue(
						BigDecimal.class)));
	}

	@Override
	public MonetaryAmountSymbols getAmountSymbols() {
		return symbols;
	}

	@Override
	public MonetaryAmountNumericInformation getNumericInformation() {
		return numericInformation;
	}

	@Override
	public int hashCode() {
		return Objects.hash(symbols, numericInformation);
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == this) {
			return true;
		}
		if (DefaultMonetaryAmountFormatSymbols.class.isInstance(obj)) {
			DefaultMonetaryAmountFormatSymbols other = DefaultMonetaryAmountFormatSymbols.class.cast(obj);
			return Objects.equals(other.symbols, symbols) && Objects.equals(other.numericInformation, numericInformation);
		}
		return false;
	}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(DefaultMonetaryAmountFormatSymbols.class.getName()).append('{')
		.append(" numericInformation: ").append(numericInformation).append(',')
		.append(" symbols: ").append(symbols).append('}');
		return sb.toString();
	}

	@Override
	public String format(MonetaryAmount amount) {
		StringBuilder sb = new StringBuilder();
		try {
			print(sb, amount);
		} catch (IOException e) {
			throw new IllegalStateException("Formatting error.", e);
		}
		return sb.toString();
	}

}