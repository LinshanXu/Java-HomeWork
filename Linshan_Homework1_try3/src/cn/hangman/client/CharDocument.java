package cn.hangman.client;


import java.awt.Toolkit;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

//ֻ�����ı���¼����ĸ
class CharDocument extends PlainDocument {
	private static final long serialVersionUID = 1L;
	public void insertString(int offs, String str, AttributeSet a)
			throws BadLocationException {
		// ���ַ���Ϊ�գ�ֱ�ӷ��ء�
		if (str == null) {
			return;
		}
		int len = getLength();
		String s = getText(0, len);// �ı������е��ַ�
		try {
			s = s.substring(0, offs) + str + s.substring(offs, len);// �����е��ַ�������ַ�
			char[] c = s.toCharArray(); // 
			for(int i=0;i<c.length;i++)	{
				if (c[i]<'a'||c[i]>'z') 
						throw new Exception();
			}
		} catch (Exception e) {
			Toolkit.getDefaultToolkit().beep();// ������ʾ����
			return;
		}
		super.insertString(offs, str, a);// ���ַ���ӵ��ı���
	}
}
