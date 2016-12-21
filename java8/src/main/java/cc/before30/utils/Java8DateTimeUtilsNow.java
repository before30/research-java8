package cc.before30.utils;

import org.junit.rules.ExternalResource;

import java.time.LocalDateTime;

/**
 * User: before30 
 * Date: 2016. 12. 13.
 * Time: 오후 3:23
 */
public class Java8DateTimeUtilsNow extends ExternalResource {

	private static final Java8DateTimeUtilsNow INSTANCE = new Java8DateTimeUtilsNow();

	private LocalDateTime fixedCurrentLocalDateTime;

	private Java8DateTimeUtilsNow() {

	}

	public static Java8DateTimeUtilsNow none() {
		INSTANCE.fixedCurrentLocalDateTime = null;
		return INSTANCE;
	}

	public static Java8DateTimeUtilsNow fixedWith(LocalDateTime localDateTime) {
		INSTANCE.fixedCurrentLocalDateTime = localDateTime;
		return INSTANCE;
	}

	public void fixCurrentLocalDateTime(LocalDateTime localDateTime) {
		Java8DateTimeUtils.fixCurrentLocalDateTime(localDateTime);
	}

	@Override protected void before() throws Throwable {
		if (fixedCurrentLocalDateTime != null) {
			Java8DateTimeUtils.fixCurrentLocalDateTime(fixedCurrentLocalDateTime);
		}
	}

	@Override protected void after() {
		Java8DateTimeUtils.unfixCurrentLocalDateTime();
	}
}
