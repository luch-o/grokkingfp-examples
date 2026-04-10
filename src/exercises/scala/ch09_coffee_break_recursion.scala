import cats.effect.IO
import cats.implicits._
import cats.effect.unsafe.implicits.global

import ch08_SchedulingMeetings.retry
import ch09_CurrencyExchange.exchangeRatesTableApiCall


object model {
    opaque type Currency = String
    object Currency {
        def apply(value: String): Currency = value
        extension(currency: Currency) def name: String = currency
    }
}

import model._


def exchangeTable(from: Currency): IO[Map[Currency, BigDecimal]] = {
    IO.delay(exchangeRatesTableApiCall(from.name)).map(table =>
        table.map(kv =>
            kv match {
                case (currencyName, rate) => (Currency(currencyName), rate)
            }
        )
    )
}

def extractSingleCurrencyRate(currencyToExtract: Currency)
    (table: Map[Currency, BigDecimal]): Option[BigDecimal] = {
    table.get(currencyToExtract)
}

def currencyRate(from: Currency, to: Currency): IO[BigDecimal] = {
    for {
        rateTable  <- retry(exchangeTable(from), 10)
        rate       <- extractSingleCurrencyRate(to)(rateTable) match {
            case Some(rate) => IO.pure(rate)
            case None       => currencyRate(from, to)
        } 
    } yield rate
}