package org.chatline;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;

import org.chatline.domain.ChatterPeriod;
import org.junit.Test;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

public class DurationTest {

	LocalDateTime first = LocalDateTime.of(2014, 2, 14, 13, 31, 5);
	LocalDateTime next = LocalDateTime.of(2020, 1, 13, 14, 33, 10);

	@Test
	public void testDuration() {
		System.out.println("Duration");
		Duration duration = Duration.between(first, next);
		System.out.println(duration.toString());
		long days = duration.toDays();
		long daySecs =  days*24*60*60;
		System.out.println("days = " + days + ", secs=" + daySecs);
		System.out.println(duration.toHours());
		System.out.println(duration.toMinutes());
		long secs = duration.toMillis()/1000;
		System.out.println("total secs = " + secs + ", diff=" + (secs-daySecs));
		System.out.println("Period");
		Period period = Period.between(first.toLocalDate(), next.toLocalDate());
		System.out.println(period.toString());
		System.out.println(period.getYears());
		System.out.println(period.getMonths());
		System.out.println(period.getDays());
	}

	@Test
	public void testPeriod() {
		System.out.println("Period");
		Period period = Period.between(first.toLocalDate(), next.toLocalDate());
		System.out.println(period.toString());
		System.out.println(period.getYears());
		System.out.println(period.getMonths());
		System.out.println(period.getDays());
	}

	@Test
	public void testChatterPeriodSecond() {
		LocalDateTime first = LocalDateTime.of(2014, 1, 14, 13, 31, 5);
		LocalDateTime next = LocalDateTime.of(2014, 1, 14, 13, 31, 10);

		ChatterPeriod chatterPeriod = ChatterPeriod.between(first, next);
		assertThat(chatterPeriod.toSeconds(), equalTo(5l));
		
		LocalDateTime expectedNext = first.plusSeconds(5);
	}

	@Test
	public void testChatterPeriodMinutes() {
		LocalDateTime first = LocalDateTime.of(2014, 1, 14, 13, 31, 5);
		LocalDateTime next = LocalDateTime.of(2014, 1, 14, 13, 33, 10);

		ChatterPeriod chatterPeriod = ChatterPeriod.between(first, next);
		assertThat(chatterPeriod.toMinutes(), equalTo(2l));
		assertThat(chatterPeriod.toSeconds(), equalTo(5l));
	}

	@Test
	public void testChatterPeriodHours() {
		LocalDateTime first = LocalDateTime.of(2014, 1, 14, 13, 31, 5);
		LocalDateTime next = LocalDateTime.of(2014, 1, 14, 17, 33, 10);

		ChatterPeriod chatterPeriod = ChatterPeriod.between(first, next);
		assertThat(chatterPeriod.toHours(), equalTo(4l));
		assertThat(chatterPeriod.toMinutes(), equalTo(2l));
		assertThat(chatterPeriod.toSeconds(), equalTo(5l));
	}

	@Test
	public void testChatterPeriodDays() {
		LocalDateTime first = LocalDateTime.of(2014, 1, 14, 13, 31, 5);
		LocalDateTime next = LocalDateTime.of(2014, 1, 15, 17, 33, 10);

		ChatterPeriod chatterPeriod = ChatterPeriod.between(first, next);
		assertThat(chatterPeriod.toDays(), equalTo(1l));
		assertThat(chatterPeriod.toHours(), equalTo(4l));
		assertThat(chatterPeriod.toMinutes(), equalTo(2l));
		assertThat(chatterPeriod.toSeconds(), equalTo(5l));
	}

	@Test
	public void testChatterPeriodMonths() {
		LocalDateTime first = LocalDateTime.of(2014, 1, 14, 13, 31, 5);
		LocalDateTime next = LocalDateTime.of(2014, 3, 15, 17, 33, 10);

		ChatterPeriod chatterPeriod = ChatterPeriod.between(first, next);
		assertThat(chatterPeriod.toMonths(), equalTo(2l));
		assertThat(chatterPeriod.toDays(), equalTo(1l));
		assertThat(chatterPeriod.toHours(), equalTo(4l));
		assertThat(chatterPeriod.toMinutes(), equalTo(2l));
		assertThat(chatterPeriod.toSeconds(), equalTo(5l));
	}

	@Test
	public void testChatterPeriodYears() {
		LocalDateTime first = LocalDateTime.of(2014, 1, 14, 13, 31, 5);
		LocalDateTime next = LocalDateTime.of(2016, 3, 15, 17, 33, 10);

		ChatterPeriod chatterPeriod = ChatterPeriod.between(first, next);
		assertThat(chatterPeriod.toYears(), equalTo(2l));
		assertThat(chatterPeriod.toMonths(), equalTo(2l));
		assertThat(chatterPeriod.toDays(), equalTo(1l));
		assertThat(chatterPeriod.toHours(), equalTo(4l));
		assertThat(chatterPeriod.toMinutes(), equalTo(2l));
		assertThat(chatterPeriod.toSeconds(), equalTo(5l));
	}

	@Test
	public void testChatterPeriodNextDayBeforeFirst() {
		LocalDateTime first = LocalDateTime.of(2014, 1, 14, 13, 31, 5);
		LocalDateTime next = LocalDateTime.of(2016, 3, 13, 17, 33, 10);

		ChatterPeriod chatterPeriod = ChatterPeriod.between(first, next);
		assertThat(chatterPeriod.toYears(), equalTo(2l));
		assertThat(chatterPeriod.toMonths(), equalTo(1l));
		assertThat(chatterPeriod.toDays(), equalTo(28l));
		assertThat(chatterPeriod.toHours(), equalTo(4l));
		assertThat(chatterPeriod.toMinutes(), equalTo(2l));
		assertThat(chatterPeriod.toSeconds(), equalTo(5l));
	}

	@Test
	public void testChatterPeriodNextMonthBeforeFirst() {
		LocalDateTime first = LocalDateTime.of(2014, 2, 14, 13, 31, 5);
		LocalDateTime next = LocalDateTime.of(2016, 1, 13, 17, 33, 10);

		ChatterPeriod chatterPeriod = ChatterPeriod.between(first, next);
		assertThat(chatterPeriod.toYears(), equalTo(1l));
		assertThat(chatterPeriod.toMonths(), equalTo(10l));
		assertThat(chatterPeriod.toDays(), equalTo(30l));
		assertThat(chatterPeriod.toHours(), equalTo(4l));
		assertThat(chatterPeriod.toMinutes(), equalTo(2l));
		assertThat(chatterPeriod.toSeconds(), equalTo(5l));
	}

	@Test
	public void testChatterPeriodNextHourBeforeFirst() {
		LocalDateTime first = LocalDateTime.of(2014, 2, 14, 18, 31, 5);
		LocalDateTime next = LocalDateTime.of(2016, 1, 13, 17, 33, 10);

		ChatterPeriod chatterPeriod = ChatterPeriod.between(first, next);
		assertThat(chatterPeriod.toYears(), equalTo(1l));
		assertThat(chatterPeriod.toMonths(), equalTo(10l));
		assertThat(chatterPeriod.toDays(), equalTo(30l));
		assertThat(chatterPeriod.toHours(), equalTo(23l));
		assertThat(chatterPeriod.toMinutes(), equalTo(2l));
		assertThat(chatterPeriod.toSeconds(), equalTo(5l));
	}

}
