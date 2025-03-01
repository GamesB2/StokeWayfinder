package com.example.w028006g.regnlogin.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class EventDataSource {
	private String[] allColumns = { SqliteHelper.COLUMN_ID,
			SqliteHelper.COLUMN_EVENT_TYPE, SqliteHelper.COLUMN_EVENT_DATE,
			SqliteHelper.COLUMN_PLACE_NAME, };
	private Context context;
	protected SQLiteDatabase database;
	protected SqliteHelper dbHelper;

	public EventDataSource(Context context) {
		this.context = context;
		initSqliteHelper();
		open();
	}

	protected void initSqliteHelper() {
		dbHelper = new SqliteHelper(context);
	}

	protected void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		database.close();
		dbHelper.close();
	}

	public void create(String type, String date, String placeName)
	{
		ContentValues values = new ContentValues();
		values.put(SqliteHelper.COLUMN_EVENT_TYPE, type);
		values.put(SqliteHelper.COLUMN_EVENT_DATE, date);
		values.put(SqliteHelper.COLUMN_PLACE_NAME, placeName);
		database.insert(SqliteHelper.TABLE_EVENTS, null, values);
	}
}
