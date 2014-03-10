package com.ms.learn.db;

import android.content.ContentValues;
import android.database.Cursor;


public abstract class DatabaseBuilder<T> {
	
	/**
	 * 从Cursor中获取数据
	 * 
	 * @param c
	 * @return
	 */
	public abstract T build(Cursor c);
	
	/**
	 * 将数据放入到Cursor中
	 * 
	 * @param t
	 * @return
	 */
	public abstract ContentValues deconstruct(T t);

}
