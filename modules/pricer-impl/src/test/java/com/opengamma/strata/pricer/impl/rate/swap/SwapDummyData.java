/**
 * Copyright (C) 2014 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.strata.pricer.impl.rate.swap;

import static com.opengamma.strata.basics.date.BusinessDayConventions.MODIFIED_FOLLOWING;
import static com.opengamma.strata.basics.date.HolidayCalendars.GBLO;
import static com.opengamma.strata.basics.index.IborIndices.GBP_LIBOR_3M;
import static com.opengamma.strata.collect.TestHelper.date;

import com.opengamma.strata.basics.PayReceive;
import com.opengamma.strata.basics.currency.Currency;
import com.opengamma.strata.basics.currency.CurrencyAmount;
import com.opengamma.strata.basics.date.BusinessDayAdjustment;
import com.opengamma.strata.basics.date.DayCounts;
import com.opengamma.strata.basics.date.DaysAdjustment;
import com.opengamma.strata.basics.index.FxIndices;
import com.opengamma.strata.basics.schedule.Frequency;
import com.opengamma.strata.basics.schedule.PeriodicSchedule;
import com.opengamma.strata.collect.id.StandardId;
import com.opengamma.strata.finance.TradeInfo;
import com.opengamma.strata.finance.rate.FixedRateObservation;
import com.opengamma.strata.finance.rate.IborRateObservation;
import com.opengamma.strata.finance.rate.swap.ExpandedSwapLeg;
import com.opengamma.strata.finance.rate.swap.FixedRateCalculation;
import com.opengamma.strata.finance.rate.swap.FxResetNotionalExchange;
import com.opengamma.strata.finance.rate.swap.IborRateCalculation;
import com.opengamma.strata.finance.rate.swap.NotionalExchange;
import com.opengamma.strata.finance.rate.swap.NotionalSchedule;
import com.opengamma.strata.finance.rate.swap.PaymentSchedule;
import com.opengamma.strata.finance.rate.swap.RateAccrualPeriod;
import com.opengamma.strata.finance.rate.swap.RateCalculationSwapLeg;
import com.opengamma.strata.finance.rate.swap.RatePaymentPeriod;
import com.opengamma.strata.finance.rate.swap.Swap;
import com.opengamma.strata.finance.rate.swap.SwapTrade;

/**
 * Basic dummy objects used when the data within is not important.
 */
public final class SwapDummyData {

  /**
   * The notional.
   */
  public static final double NOTIONAL = 1_000_000d;
  /**
   * NotionalExchange.
   */
  public static final NotionalExchange NOTIONAL_EXCHANGE = NotionalExchange.builder()
      .paymentDate(date(2014, 7, 1))
      .paymentAmount(CurrencyAmount.of(Currency.GBP, NOTIONAL))
      .build();
  /**
   * NotionalExchange.
   */
  public static final FxResetNotionalExchange FX_RESET_NOTIONAL_EXCHANGE = FxResetNotionalExchange.builder()
      .paymentDate(date(2014, 7, 1))
      .referenceCurrency(Currency.USD)
      .notional(NOTIONAL)
      .index(FxIndices.WM_GBP_USD)
      .fixingDate(date(2014, 7, 1))
      .build();

  /**
   * IborRateObservation.
   */
  public static final IborRateObservation IBOR_RATE_OBSERVATION = IborRateObservation.of(GBP_LIBOR_3M, date(2014, 6, 30));
  /**
   * RateAccuralPeriod (ibor).
   */
  public static final RateAccrualPeriod IBOR_RATE_ACCRUAL_PERIOD = RateAccrualPeriod.builder()
      .startDate(date(2014, 7, 2))
      .endDate(date(2014, 10, 2))
      .rateObservation(IBOR_RATE_OBSERVATION)
      .yearFraction(0.25d)
      .build();
  /**
   * RatePaymentPeriod (ibor).
   */
  public static final RatePaymentPeriod IBOR_RATE_PAYMENT_PERIOD = RatePaymentPeriod.builder()
      .paymentDate(date(2014, 10, 4))
      .accrualPeriods(IBOR_RATE_ACCRUAL_PERIOD)
      .currency(Currency.GBP)
      .notional(NOTIONAL)
      .build();
  /**
   * ExpandedSwapLeg (ibor).
   */
  public static final ExpandedSwapLeg IBOR_EXPANDED_SWAP_LEG = ExpandedSwapLeg.builder()
      .paymentPeriods(IBOR_RATE_PAYMENT_PERIOD)
      .paymentEvents(NOTIONAL_EXCHANGE)
      .build();
  /**
   * RateCalculationSwapLeg (ibor).
   */
  public static final RateCalculationSwapLeg IBOR_RATECALC_SWAP_LEG = RateCalculationSwapLeg.builder()
      .payReceive(PayReceive.RECEIVE)
      .accrualSchedule(PeriodicSchedule.builder()
          .startDate(date(2014, 7, 2))
          .endDate(date(2014, 10, 2))
          .frequency(Frequency.P3M)
          .businessDayAdjustment(BusinessDayAdjustment.of(MODIFIED_FOLLOWING, GBLO))
          .build())
      .paymentSchedule(PaymentSchedule.builder()
          .paymentFrequency(Frequency.P3M)
          .paymentOffset(DaysAdjustment.ofBusinessDays(2, GBLO))
          .build())
      .notionalSchedule(NotionalSchedule.of(Currency.GBP, NOTIONAL))
      .calculation(IborRateCalculation.of(GBP_LIBOR_3M))
      .build();

  /**
   * FixedRateObservation.
   */
  public static final FixedRateObservation FIXED_RATE_OBSERVATION = FixedRateObservation.of(0.0123d);
  /**
   * RateAccuralPeriod (fixed).
   */
  public static final RateAccrualPeriod FIXED_RATE_ACCRUAL_PERIOD = RateAccrualPeriod.builder()
      .startDate(date(2014, 7, 2))
      .endDate(date(2014, 10, 2))
      .rateObservation(FIXED_RATE_OBSERVATION)
      .yearFraction(0.25d)
      .build();
  /**
   * RatePaymentPeriod (fixed).
   */
  public static final RatePaymentPeriod FIXED_RATE_PAYMENT_PERIOD = RatePaymentPeriod.builder()
      .paymentDate(date(2014, 10, 4))
      .accrualPeriods(FIXED_RATE_ACCRUAL_PERIOD)
      .currency(Currency.GBP)
      .notional(NOTIONAL)
      .build();
  /**
   * ExpandedSwapLeg (fixed).
   */
  public static final ExpandedSwapLeg FIXED_EXPANDED_SWAP_LEG = ExpandedSwapLeg.builder()
      .paymentPeriods(FIXED_RATE_PAYMENT_PERIOD)
      .paymentEvents(NOTIONAL_EXCHANGE)
      .build();
  /**
   * RatePaymentPeriod (fixed).
   */
  public static final RatePaymentPeriod FIXED_RATE_PAYMENT_PERIOD_USD = RatePaymentPeriod.builder()
      .paymentDate(date(2014, 10, 4))
      .accrualPeriods(FIXED_RATE_ACCRUAL_PERIOD)
      .currency(Currency.USD)
      .notional(NOTIONAL)
      .build();
  /**
   * ExpandedSwapLeg (fixed).
   */
  public static final ExpandedSwapLeg FIXED_EXPANDED_SWAP_LEG_USD = ExpandedSwapLeg.builder()
      .paymentPeriods(FIXED_RATE_PAYMENT_PERIOD_USD)
      .build();
  /**
   * RateCalculationSwapLeg (fixed).
   */
  public static final RateCalculationSwapLeg FIXED_RATECALC_SWAP_LEG = RateCalculationSwapLeg.builder()
      .payReceive(PayReceive.RECEIVE)
      .accrualSchedule(PeriodicSchedule.builder()
          .startDate(date(2014, 7, 2))
          .endDate(date(2014, 10, 2))
          .frequency(Frequency.P3M)
          .businessDayAdjustment(BusinessDayAdjustment.of(MODIFIED_FOLLOWING, GBLO))
          .build())
      .paymentSchedule(PaymentSchedule.builder()
          .paymentFrequency(Frequency.P3M)
          .paymentOffset(DaysAdjustment.ofBusinessDays(2, GBLO))
          .build())
      .notionalSchedule(NotionalSchedule.of(Currency.GBP, NOTIONAL))
      .calculation(FixedRateCalculation.of(0.0123d, DayCounts.ACT_365F))
      .build();

  /**
   * Swap.
   */
  public static final Swap SWAP = Swap.builder()
      .legs(IBOR_EXPANDED_SWAP_LEG, FIXED_EXPANDED_SWAP_LEG)
      .build();
  /**
   * Swap.
   */
  public static final Swap SWAP_CROSS_CURRENCY = Swap.builder()
      .legs(IBOR_EXPANDED_SWAP_LEG, FIXED_EXPANDED_SWAP_LEG_USD)
      .build();

  /**
   * Swap trade.
   */
  public static final SwapTrade SWAP_TRADE = SwapTrade.builder()
      .standardId(StandardId.of("OG-Trade", "1"))
      .tradeInfo(TradeInfo.builder().tradeDate(date(2014, 6, 30)).build())
      .product(SWAP)
      .build();

  /**
   * Restricted constructor.
   */
  private SwapDummyData() {
  }

}
