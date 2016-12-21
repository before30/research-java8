package cc.before30.utils;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.Assert.*;

/**
 * User: before30 
 * Date: 2016. 12. 13.
 * Time: 오후 4:54
 */
public class Java8DateTimeUtilsNowTest {
	@Rule
	public Java8DateTimeUtilsNow java8DateTimeUtilsNow = Java8DateTimeUtilsNow.fixedWith(LocalDateTime.of(1979, 12, 31, 9, 30, 59));

	@Test
	public void testConstructor() {
		LocalDateTime now = Java8DateTimeUtils.now();
		LocalDate today = Java8DateTimeUtils.today();

		assertEquals("year", 1979, now.getYear());
		assertEquals("now", LocalDateTime.of(1979, 12, 31, 9, 30, 59), now);
		assertEquals("today", LocalDate.of(1979, 12, 31), today);
	}

	@Test
	public void testFixCurrentLocalDateTime() {
		java8DateTimeUtilsNow.fixCurrentLocalDateTime(LocalDateTime.of(2079, 12, 31, 9, 30, 59));
		LocalDateTime now = Java8DateTimeUtils.now();
		LocalDate today = Java8DateTimeUtils.today();

		assertEquals("year", 2079, now.getYear());
		assertEquals("now", LocalDateTime.of(2079, 12, 31, 9, 30, 59), now);
		assertEquals("today", LocalDate.of(2079, 12, 31), today);
	}
}