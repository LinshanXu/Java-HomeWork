package cn.hangman.client;


import java.awt.Toolkit;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

//只允许文本框录入字母
class CharDocument extends PlainDocument {
	private static final long serialVersionUID = 1L;
	public void insertString(int offs, String str, AttributeSet a)
			throws BadLocationException {
		// 若字符串为空，直接返回。
		if (str == null) {
			return;
		}
		int len = getLength();
		String s = getText(0, len);// 文本框已有的字符
		try {
			s = s.substring(0, offs) + str + s.substring(offs, len);// 在已有的字符后添加字符
			char[] c = s.toCharArray(); // 
			for(int i=0;i<c.length;i++)	{
				if (c[i]<'a'||c[i]>'z') 
						throw new Exception();
			}
		} catch (Exception e) {
			Toolkit.getDefaultToolkit().beep();// 发出提示声音
			return;
		}
		super.insertString(offs, str, a);// 把字符添加到文本框
	}
}
