package org.chatline.domain;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.function.LongConsumer;
import java.util.function.LongFunction;
import java.util.function.LongPredicate;

public final class ChatterPeriod {
	private Optional<Long> seconds = Optional.empty();
	private Optional<Long> minutes = Optional.empty();
	private Optional<Long> hours = Optional.empty();
	private Optional<Long> days = Optional.empty();
	private Optional<Long> months = Optional.empty();
	private Optional<Long> years = Optional.empty();

	public static ChatterPeriod between(LocalDateTime first, LocalDateTime next) {
		ChatterPeriod chatterPeriod = new ChatterPeriod();

		Period period = Period.between(first.toLocalDate(), next.toLocalDate());
		chatterPeriod.years = addToOptionalField(period.getYears());
		chatterPeriod.months = addToOptionalField(period.getMonths());
		chatterPeriod.days = addToOptionalField(period.getDays());

		Duration duration = Duration.between(first, next);
		
		long totalDaysInPeriod = duration.toDays();
		long totalSecondsInPeriodDays =  totalDaysInPeriod*24*60*60;
		long totalSecsInPeriod = duration.toMillis()/1000;
				
		chatterPeriod.seconds = addToOptionalField(totalSecsInPeriod - totalSecondsInPeriodDays);
		// re-evaluate the duration
		duration = Duration.ofSeconds(chatterPeriod.seconds.get());

		chatterPeriod.calculateFields(duration.toHours(), value -> value > 0, 
				value -> value*ChronoUnit.HOURS.getDuration().getSeconds(), value -> chatterPeriod.hours = addToOptionalField(value));

		// re-evaluate the duration
		duration = Duration.ofSeconds(chatterPeriod.seconds.get());
		
		chatterPeriod.calculateFields(duration.toMinutes(), value -> value > 0, 
				value -> value*ChronoUnit.MINUTES.getDuration().getSeconds(), value -> chatterPeriod.minutes = addToOptionalField(value));

		return chatterPeriod;
	}
	
	public long toSeconds() {
		return seconds.orElse(0l);
	}


	public long toMinutes() {
		return minutes.orElse(0l);
	}

	public long toHours() {
		return hours.orElse(0l);
	}

	public long toDays() {
		return days.orElse(0l);
	}

	public long toMonths() {
		return months.orElse(0l);
	}

	public long toYears() {
		return years.orElse(0l);
	}

	private void calculateFields(long field, LongPredicate predicate, LongFunction<Long> function, LongConsumer consumer) {
		if (predicate.test(field) && seconds.isPresent()) {
			seconds = Optional.of(seconds.get() - function.apply(field));
			consumer.accept(field);
		}
	}

	private static Optional<Long> addToOptionalField(long value) {
		if (value > 0) {
			return Optional.of(value);
		}
		return Optional.empty();
	}
}
