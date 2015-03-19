package nomouse.lang;

/**
 * 字符处理帮助类
 * 
 * @author wuch
 * 
 */
public class StringUtils {

	public static final String SPACE = " ";

	public static final String EMPTY = "";

	public static final String LF = "\n";

	public static final String CR = "\r";

	// Empty checks
	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * Checks if a CharSequence is empty ("") or null.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.isEmpty(null)      = true
	 * StringUtils.isEmpty("")        = true
	 * StringUtils.isEmpty(" ")       = false
	 * StringUtils.isEmpty("bob")     = false
	 * StringUtils.isEmpty("  bob  ") = false
	 * </pre>
	 * 
	 * <p>
	 * NOTE: This method changed in Lang version 2.0. It no longer trims the
	 * CharSequence. That functionality is available in isBlank().
	 * </p>
	 * 
	 * @param cs
	 *            the CharSequence to check, may be null
	 * @return {@code true} if the CharSequence is empty or null
	 * @since 3.0 Changed signature from isEmpty(String) to
	 *        isEmpty(CharSequence)
	 */
	public static boolean isEmpty(final CharSequence cs) {
		return cs == null || cs.length() == 0;
	}

	/**
	 * <p>
	 * Checks if a CharSequence is not empty ("") and not null.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.isNotEmpty(null)      = false
	 * StringUtils.isNotEmpty("")        = false
	 * StringUtils.isNotEmpty(" ")       = true
	 * StringUtils.isNotEmpty("bob")     = true
	 * StringUtils.isNotEmpty("  bob  ") = true
	 * </pre>
	 * 
	 * @param cs
	 *            the CharSequence to check, may be null
	 * @return {@code true} if the CharSequence is not empty and not null
	 * @since 3.0 Changed signature from isNotEmpty(String) to
	 *        isNotEmpty(CharSequence)
	 */
	public static boolean isNotEmpty(final CharSequence cs) {
		return !StringUtils.isEmpty(cs);
	}

	/**
	 * <pre>
	 * StringUtils.compare(null,null)  = 0
	 * StringUtils.compare(null,"")    = -1
	 * StringUtils.compare("", "a")    =  -1
	 * StringUtils.compare("a","ab")   = -1
	 * StringUtils.compare("ac","abcd")  = 1
	 * StringUtils.compare("ac","ac ")  = -1
	 * </pre>
	 * 
	 * @param left
	 * @param right
	 * @return 0
	 */
	public static int compare(String left, String right) {
		// 默认相等
		int result = 0;
		if (left == null || right == null) {
			result = (left == null ? 0 : 1) - (right == null ? 0 : 1);
		} else if (left.length() == 0 || right.length() == 0) {
			result = (left.length() == 0 ? 0 : 1)
					- (right.length() == 0 ? 0 : 1);
		} else {
			int leftLength = left.length();
			int rightLength = right.length();

			// 默认长度长的大
			result = leftLength - rightLength;
			// 取较小的长度
			int lenght = leftLength > rightLength ? rightLength : leftLength;

			// 长度相同，比较每一位
			char[] leftChars = left.toCharArray();
			char[] rightChars = right.toCharArray();

			for (int i = 0; i < lenght; i++) {
				if (leftChars[i] - rightChars[i] == 0) {
				} else {
					result = leftChars[i] - rightChars[i];
					break;
				}
			}
		}

		return result;
	}

	public static boolean equals(String a, String b) {
		if (a == b)
			return true;
		int length;
		if (a != null && b != null && (length = a.length()) == b.length()) {
			if (a instanceof String && b instanceof String) {
				return a.equals(b);
			} else {
				for (int i = 0; i < length; i++) {
					if (a.charAt(i) != b.charAt(i))
						return false;
				}
				return true;
			}
		}
		return false;
	}

	/**
	 * <pre>
	 * StringUtils.substring(null, *, *)    = null
	 * StringUtils.substring("", * ,  *)    = "";
	 * StringUtils.substring("abc", 0, 2)   = "ab"
	 * StringUtils.substring("abc", 2, 0)   = ""
	 * StringUtils.substring("abc", 2, 4)   = "c"
	 * StringUtils.substring("abc", 4, 6)   = ""
	 * StringUtils.substring("abc", 2, 2)   = ""
	 * StringUtils.substring("abc", -2, -1) = "b"
	 * StringUtils.substring("abc", -4, 2)  = "ab"
	 * </pre>
	 * 
	 * @param str
	 *            the String to get the substring from, may be null
	 * @param start
	 *            the position to start from, negative means count back from the
	 *            end of the String by this many characters
	 * @param end
	 *            the position to end at (exclusive), negative means count back
	 *            from the end of the String by this many characters
	 * @return substring from start position to end position, {@code null} if
	 *         null String input
	 */
	public static String substring(final String str, int start, int end) {
		if (str == null) {
			return null;
		}

		// handle negatives
		if (end < 0) {
			end = str.length() + end; // remember end is negative
		}
		if (start < 0) {
			start = str.length() + start; // remember start is negative
		}

		// check length next
		if (end > str.length()) {
			end = str.length();
		}

		// if start is greater than end, return ""
		if (start > end) {
			return EMPTY;
		}

		if (start < 0) {
			start = 0;
		}
		if (end < 0) {
			end = 0;
		}

		return str.substring(start, end);
	}

	// Contains
	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * Checks if CharSequence contains a search character, handling {@code null}
	 * . This method uses {@link String#indexOf(int)} if possible.
	 * </p>
	 * 
	 * <p>
	 * A {@code null} or empty ("") CharSequence will return {@code false}.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.contains(null, *)    = false
	 * StringUtils.contains("", *)      = false
	 * StringUtils.contains("abc", 'a') = true
	 * StringUtils.contains("abc", 'z') = false
	 * </pre>
	 * 
	 * @param seq
	 *            the CharSequence to check, may be null
	 * @param searchChar
	 *            the character to find
	 * @return true if the CharSequence contains the search character, false if
	 *         not or {@code null} string input
	 * @since 2.0
	 * @since 3.0 Changed signature from contains(String, int) to
	 *        contains(CharSequence, int)
	 */
	public static boolean contains(final CharSequence seq, final int searchChar) {
		if (isEmpty(seq)) {
			return false;
		}
		return CharSequenceUtils.indexOf(seq, searchChar, 0) >= 0;
	}

	/**
	 * <p>
	 * Checks if CharSequence contains a search CharSequence, handling
	 * {@code null}. This method uses {@link String#indexOf(String)} if
	 * possible.
	 * </p>
	 * 
	 * <p>
	 * A {@code null} CharSequence will return {@code false}.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.contains(null, *)     = false
	 * StringUtils.contains(*, null)     = false
	 * StringUtils.contains("", "")      = true
	 * StringUtils.contains("abc", "")   = true
	 * StringUtils.contains("abc", "a")  = true
	 * StringUtils.contains("abc", "z")  = false
	 * </pre>
	 * 
	 * @param seq
	 *            the CharSequence to check, may be null
	 * @param searchSeq
	 *            the CharSequence to find, may be null
	 * @return true if the CharSequence contains the search CharSequence, false
	 *         if not or {@code null} string input
	 * @since 2.0
	 * @since 3.0 Changed signature from contains(String, String) to
	 *        contains(CharSequence, CharSequence)
	 */
	public static boolean contains(final CharSequence seq,
			final CharSequence searchSeq) {
		if (seq == null || searchSeq == null) {
			return false;
		}
		return CharSequenceUtils.indexOf(seq, searchSeq, 0) >= 0;
	}

	/**
	 * <p>
	 * Checks if CharSequence contains a search CharSequence irrespective of
	 * case, handling {@code null}. Case-insensitivity is defined as by
	 * {@link String#equalsIgnoreCase(String)}.
	 * 
	 * <p>
	 * A {@code null} CharSequence will return {@code false}.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.contains(null, *) = false
	 * StringUtils.contains(*, null) = false
	 * StringUtils.contains("", "") = true
	 * StringUtils.contains("abc", "") = true
	 * StringUtils.contains("abc", "a") = true
	 * StringUtils.contains("abc", "z") = false
	 * StringUtils.contains("abc", "A") = true
	 * StringUtils.contains("abc", "Z") = false
	 * </pre>
	 * 
	 * @param str
	 *            the CharSequence to check, may be null
	 * @param searchStr
	 *            the CharSequence to find, may be null
	 * @return true if the CharSequence contains the search CharSequence
	 *         irrespective of case or false if not or {@code null} string input
	 * @since 3.0 Changed signature from containsIgnoreCase(String, String) to
	 *        containsIgnoreCase(CharSequence, CharSequence)
	 */
	public static boolean containsIgnoreCase(final CharSequence str,
			final CharSequence searchStr) {
		if (str == null || searchStr == null) {
			return false;
		}
		final int len = searchStr.length();
		final int max = str.length() - len;
		for (int i = 0; i <= max; i++) {
			if (CharSequenceUtils
					.regionMatches(str, true, i, searchStr, 0, len)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Check whether the given CharSequence contains any whitespace characters.
	 * 
	 * @param seq
	 *            the CharSequence to check (may be {@code null})
	 * @return {@code true} if the CharSequence is not empty and contains at
	 *         least 1 whitespace character
	 * @see java.lang.Character#isWhitespace
	 * @since 3.0
	 */
	// From org.springframework.util.StringUtils, under Apache License 2.0
	public static boolean containsWhitespace(final CharSequence seq) {
		if (isEmpty(seq)) {
			return false;
		}
		final int strLen = seq.length();
		for (int i = 0; i < strLen; i++) {
			if (Character.isWhitespace(seq.charAt(i))) {
				return true;
			}
		}
		return false;
	}
}
