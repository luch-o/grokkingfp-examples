object model {
    opaque type Currency = String
    object Currency {
        def apply(value: String): Currency = value
        extension(currency: Currency) def name: String = currency
    }
}

import model._


def extractSingleCurrencyRate(currencyToExtract: Currency)
    (table: Map[Currency, BigDecimal]): Option[BigDecimal] = {
    table.get(currencyToExtract)
}

val usdExchageTables = List(
    Map(Currency("EUR") -> BigDecimal(0.88)),
    Map(Currency("EUR") -> BigDecimal(0.89), Currency("JPY") -> BigDecimal(114.62)),
    Map(Currency("JPY") -> BigDecimal(114))
)

val eurRates = usdExchageTables.map(extractSingleCurrencyRate(Currency("EUR")))
val jpyRates = usdExchageTables.map(extractSingleCurrencyRate(Currency("JPY")))
val btcRates = usdExchageTables.map(extractSingleCurrencyRate(Currency("BTC")))