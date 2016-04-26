package nomouse.util;


/**
 * 字符处理帮助类
 *
 * @author nomouse
 */
public class StringUtils {

    public static final String UTF8  = "UTF-8";

    public static final String SPACE = " ";

    public static final String EMPTY = "";

    public static final String LF    = "\n";

    public static final String CR    = "\r";

    // Empty checks
    // -----------------------------------------------------------------------

    /**
     * <p>
     * 检查是否是空字符串("")或者null.
     * </p>
     * <p/>
     * 
     * <pre>
     * StringUtils.isEmpty(null)      = true
     * StringUtils.isEmpty("")        = true
     * StringUtils.isEmpty(" ")       = false
     * StringUtils.isEmpty("bob")     = false
     * StringUtils.isEmpty("  bob  ") = false
     * </pre>
     * <p/>
     *
     * @param cs the CharSequence to check, may be null
     * @return {@code true} if the CharSequence is empty or null
     * @since 3.0 Changed signature from isEmpty(String) to isEmpty(CharSequence)
     */
    public static boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    /**
     * <p>
     * 检查是否不是空字符串("")或者null.
     * </p>
     * <p/>
     * 
     * <pre>
     * StringUtils.isNotEmpty(null)      = false
     * StringUtils.isNotEmpty("")        = false
     * StringUtils.isNotEmpty(" ")       = true
     * StringUtils.isNotEmpty("bob")     = true
     * StringUtils.isNotEmpty("  bob  ") = true
     * </pre>
     *
     * @param cs the CharSequence to check, may be null
     * @return {@code true} if the CharSequence is not empty and not null
     * @since 3.0 Changed signature from isNotEmpty(String) to isNotEmpty(CharSequence)
     */
    public static boolean isNotEmpty(final CharSequence cs) {
        return !StringUtils.isEmpty(cs);
    }

    /**
     * <p>
     * 检查是否是全空格、空字符串 ("") 或者null.
     * </p>
     *
     * <pre>
     * StringUtils.isBlank(null)      = true
     * StringUtils.isBlank("")        = true
     * StringUtils.isBlank(" ")       = true
     * StringUtils.isBlank("bob")     = false
     * StringUtils.isBlank("  bob  ") = false
     * </pre>
     *
     * @param str the String to check, may be null
     * @return <code>true</code> if the String is null, empty or whitespace
     * @since 2.0
     */
    public static boolean isBlank(final CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(cs.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>
     * 比较两个string
     * </p>
     * 
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
     * @return 0:相等;负值:left小;正值:left大
     */
    public static int compare(String left, String right) {
        // 默认相等
        int result = 0;
        if (left == null || right == null) {
            result = (left == null ? 0 : 1) - (right == null ? 0 : 1);
        } else if (left.length() == 0 || right.length() == 0) {
            result = (left.length() == 0 ? 0 : 1) - (right.length() == 0 ? 0 : 1);
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
                    // 相等
                } else {
                    result = leftChars[i] - rightChars[i];
                    break;
                }
            }
        }

        return result;
    }

    /**
     * <p>
     * 判断两个string是否值相等
     * </p>
     */
    public static boolean equals(String a, String b) {
        if (a == b) return true;
        int length;
        if (a != null && b != null && (length = a.length()) == b.length()) {
            if (a instanceof String && b instanceof String) {
                return a.equals(b);
            } else {
                for (int i = 0; i < length; i++) {
                    if (a.charAt(i) != b.charAt(i)) return false;
                }
                return true;
            }
        }
        return false;
    }

    /**
     * <p>
     * 截取string
     * </p>
     * 
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
     * @param str the String to get the substring from, may be null
     * @param start the position to start from, negative means count back from the end of the String by this many
     * characters
     * @param end the position to end at (exclusive), negative means count back from the end of the String by this many
     * characters
     * @return substring from start position to end position, {@code null} if null String input
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
     * 检查一个string是否包含一个char
     * </p>
     * <p/>
     * 
     * <pre>
     * StringUtils.contains(null, *)    = false
     * StringUtils.contains("", *)      = false
     * StringUtils.contains("abc", 'a') = true
     * StringUtils.contains("abc", 'z') = false
     * </pre>
     *
     * @param seq 源字符，可null
     * @param searchSeq 搜索字符，可null
     * @return true 包含
     */
    public static boolean contains(final CharSequence seq, final int searchChar) {
        if (isEmpty(seq)) {
            return false;
        }
        return indexOf(seq, searchChar, 0) >= 0;
    }

    /**
     * <p>
     * 检查一个string是否包含一个string
     * </p>
     * <p/>
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
     * @param seq 源字符，可null
     * @param searchSeq 搜索字符，可null
     * @return true 包含
     */
    public static boolean contains(final CharSequence seq, final CharSequence searchSeq) {
        if (seq == null || searchSeq == null) {
            return false;
        }
        return seq.toString().indexOf(searchSeq.toString(), 0) >= 0;
    }

    /**
     * 检查一个string中是否包含空白符
     *
     * @param seq 源字符，可null
     * @return {@code true} 包含，null肯定不包含
     * @see Character#isWhitespace
     */
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

    static int indexOf(final CharSequence cs, final int searchChar, int start) {
        if (cs instanceof String) {
            return ((String) cs).indexOf(searchChar, start);
        } else {
            final int sz = cs.length();
            if (start < 0) {
                start = 0;
            }
            for (int i = start; i < sz; i++) {
                if (cs.charAt(i) == searchChar) {
                    return i;
                }
            }
            return -1;
        }
    }

    static boolean regionMatches(final CharSequence cs, final boolean ignoreCase, final int thisStart,
                                 final CharSequence substring, final int start, final int length) {
        if (cs instanceof String && substring instanceof String) {
            return ((String) cs).regionMatches(ignoreCase, thisStart, (String) substring, start, length);
        } else {
            int index1 = thisStart;
            int index2 = start;
            int tmpLen = length;

            while (tmpLen-- > 0) {
                char c1 = cs.charAt(index1++);
                char c2 = substring.charAt(index2++);

                if (c1 == c2) {
                    continue;
                }

                if (!ignoreCase) {
                    return false;
                }

                // The same check as in String.regionMatches():
                if (Character.toUpperCase(c1) != Character.toUpperCase(c2)
                    && Character.toLowerCase(c1) != Character.toLowerCase(c2)) {
                    return false;
                }
            }

            return true;
        }
    }

}
