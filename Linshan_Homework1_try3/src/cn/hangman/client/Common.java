package cn.hangman.client;

/**
 * ͨ�÷�����
 * @version 1.0
 */
public class Common {
	/**
	 * �ж�һ���ַ����Ƿ���������ɵ�
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
