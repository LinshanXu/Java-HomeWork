package cn.hangman.client;

/**
 * 通用方法类
 * @version 1.0
 */
public class Common {
	/**
	 * 判断一个字符串是否是数字组成的
	 * @param s
	 * @return true for s is one number ,else not a number
	 */
	public static boolean isnumber(String s) {

		try {
			if (s == null)
				return false;
			int result = Integer.parseInt(s);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
