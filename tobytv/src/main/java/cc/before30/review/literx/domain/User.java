package cc.before30.review.literx.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * User: before30 
 * Date: 2016. 12. 21.
 * Time: 오후 1:03
 */
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class User {
	public static final User SKYLER = new User("swhite", "Skyler", "White");
	public static final User JESSE = new User("jpinkman", "Jesse", "Pinkman");
	public static final User WALTER = new User("wwhite", "Walter", "White");
	public static final User SAUL = new User("sgoodman", "Saul", "Goodman");

	private final String username;
	private final String firstname;
	private final String lastname;

}
