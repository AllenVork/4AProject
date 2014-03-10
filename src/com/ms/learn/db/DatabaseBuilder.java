package com.ms.learn.db;

import android.content.ContentValues;
import android.database.Cursor;


public abstract class DatabaseBuilder<T> {
	
	/**
	 * ��Cursor�л�ȡ����
	 * 
	 * @param c
	 * @return
	 */
	public abstract T build(Cursor c);
	
	/**
	 * �����ݷ��뵽Cursor��
	 * 
	 * @param t
	 * @return
	 */
	public abstract ContentValues deconstruct(T t);

}
