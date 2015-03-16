package org.javamoney.moneta.bp;

import org.javamoney.bp.convert.ExchangeRateProviderSupplier;

import org.javamoney.moneta.bp.internal.convert.ECBCurrentRateProvider;
import org.javamoney.moneta.bp.internal.convert.ECBHistoric90RateProvider;
import org.javamoney.moneta.bp.internal.convert.ECBHistoricRateProvider;
import org.javamoney.moneta.bp.internal.convert.IMFRateProvider;
import org.javamoney.moneta.bp.internal.convert.IdentityRateProvider;

/**
 * <p>
 * This enum contains all implementation of moneta. Using this enum will easier
 * to choose an available implementation.
 * </p>
 * <code>ExchangeRateProvider provider = MonetaryConversions.getExchangeRateProvider(ExchangeRateType.ECB);<code>
 *
 * @author otaviojava
 */
public enum ExchangeRateType implements ExchangeRateProviderSupplier {
    /**
     * Exchange rate to the European Central Bank. Uses the
     * {@link org.javamoney.moneta.bp.internal.convert.ECBCurrentRateProvider} implementation.
     */
    ECB("ECB", "Exchange rate to the European Central Bank."),
    /**
     * Exchange rate to the International Monetary Fond. Uses the
     * {@link IMFRateProvider} implementation.
     */
    IMF("IMF", "Exchange rate to the International Monetary Fond."),
    /**
     * Exchange rate to European Central Bank (last 90 days). Uses the
     * {@link ECBHistoric90RateProvider} implementation.
     */
    ECB_HIST90("ECB-HIST90",
            "Exchange rate to European Central Bank (last 90 days)."),
    /**
     * Uses the {@link ECBHistoricRateProvider} implementation.
     */
    ECB_HIST(
            "ECB-HIST",
            "Exchange rate to the European Central Bank that loads all data up to 1999 into its historic data cache."),
    /**
     * Uses the {@link IdentityRateProvider} implementation.
     */
    IDENTITY(
            "IDENT",
            "Exchange rate rate with factor one for identical base/term currencies");

    private String type;

    private String description;

    ExchangeRateType(String type, String description) {
        this.type = type;
        this.description = description;
    }

    @Override
    public String get() {
        return type;
    }

    @Override
    public String getDescription() {
        return description;
    }

}
