package cc.before30.utils;

import java.time.*;
import java.util.Date;

/**
 * User: before30 
 * Date: 2016. 12. 13.
 * Time: 오후 3:07
 */
public class Java8DateTimeUtils {
	private Java8DateTimeUtils() {
		// make utilities
	}

	private static volatile Clock clock = Clock.systemDefaultZone();

	static void fixCurrentLocalDateTime(LocalDateTime currentLocalDateTime) {
		clock = Clock.fixed(ZonedDateTime.of(currentLocalDateTime,
			ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());
	}

	static void unfixCurrentLocalDateTime() {
		clock = Clock.systemDefaultZone();
	}

	public static LocalDateTime now() {
		return LocalDateTime.now(clock);
	}

	public static Instant nowAsInstant() {
		return Instant.now(clock);
	}

	public static Date nowAsDate() {
		return asUtilDate(now());
	}

	public static LocalDate today() {
		return LocalDate.now(clock);
	}

	public static Date todayAsDate() {
		return asUtilDate(today());
	}

	public static LocalDate asLocalDate(java.util.Date date) {
		return asLocalDate(date, ZoneId.systemDefault());
	}

	public static LocalDate asLocalDate(java.util.Date date, ZoneId zone) {
		if (date == null) {
			return null;
		}

		if (date instanceof java.sql.Date) {
			return ((java.sql.Date) date).toLocalDate();
		} else {
			return Instant.ofEpochMilli(date.getTime()).atZone(zone).toLocalDate();
		}
	}

	public static LocalDateTime asLocalDateTime(java.util.Date date) {
		return asLocalDateTime(date, ZoneId.systemDefault());
	}

	public static LocalDateTime asLocalDateTime(java.util.Date date, ZoneId zone) {
		if (date == null) {
			return null;
		}

		if (date instanceof java.sql.Timestamp) {
			return ((java.sql.Timestamp) date).toLocalDateTime();
		} else {
			return Instant.ofEpochMilli(date.getTime()).atZone(zone).toLocalDateTime();
		}
	}

	public static java.util.Date asUtilDate(Object date) {
		return asUtilDate(date, ZoneId.systemDefault());
	}

	public static java.util.Date asUtilDate(Object date, ZoneId zone) {
		if (date == null) {
			return null;
		}


		if (date instanceof java.sql.Date || date instanceof java.sql.Timestamp) {
			return new java.util.Date(((java.util.Date) date).getTime());
		}
		if (date instanceof java.util.Date) {
			return (java.util.Date) date;
		}
		if (date instanceof LocalDate) {
			return Date.from(((LocalDate) date).atStartOfDay(zone).toInstant());
		}
		if (date instanceof LocalDateTime) {
			return Date.from(((LocalDateTime) date).atZone(zone).toInstant());
		}
		if (date instanceof ZonedDateTime) {
			return Date.from(((ZonedDateTime) date).toInstant());
		}
		if (date instanceof Instant) {
			return Date.from((Instant) date);
		}

		throw new UnsupportedOperationException("Don't know hot to convert " + date.getClass().getName() + " to java.util.Date");
	}

	public static Instant asInstantWithUtilDate(Date date) {
		if (date == null) {
			return null;
		} else {
			return Instant.ofEpochMilli(date.getTime());
		}
	}

	public static Instant asInstant(LocalDateTime dateTime) {
		return asInstant(dateTime, systemDefaultZoneOffset());
	}

	public static Instant asInstant(LocalDateTime dateTime, ZoneOffset zoneOffset) {
		if (dateTime == null) {
			return null;
		}
		if (zoneOffset == null) {
			zoneOffset = systemDefaultZoneOffset();
		}

		return dateTime.toInstant(zoneOffset);
	}

	public static ZonedDateTime asZoneDateTime(Date date) {
		return asZonedDateTime(date, ZoneId.systemDefault());
	}

	public static ZonedDateTime asZonedDateTime(Date date, ZoneId zone) {
		if (date == null) {
			return null;
		} else {
			return asInstantWithUtilDate(date).atZone(zone);
		}
	}

	public static ZoneOffset systemDefaultZoneOffset() {
		return ZonedDateTime.now(clock).getOffset();
	}

	private static final int MAX_HOUR = 23;
	private static final int MAX_MIN = 59;
	private static final int MAX_SECOND = 59;

	public static LocalDateTime withMaximumValue(LocalDate localDate) {
		if (localDate == null) {
			return null;
		}

		return localDate.atStartOfDay().plusHours(MAX_HOUR).plusMinutes(MAX_MIN).plusSeconds(MAX_SECOND);
	}
}
