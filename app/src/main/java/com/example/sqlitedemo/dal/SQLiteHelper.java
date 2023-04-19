package com.example.sqlitedemo.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.sqlitedemo.model.Item;

import java.util.ArrayList;
import java.util.List;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "ChiTieu.db";
    private static int DATABASE_VERSION = 1;

    public SQLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE items(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title TEXT, category TEXT, price TEXT, date TEXT)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    //getAll order by date descending
    public List<Item> getAll() {
        List<Item> list = new ArrayList<>();
        SQLiteDatabase st = getReadableDatabase();
        String order = "date DESC";
        Cursor rs = st.query("items", null, null, null, null, null, order);
        while (rs != null && rs.moveToNext()) {
            int id = rs.getInt(0);
            String title = rs.getString(1);
            String c = rs.getString(2);
            String p = rs.getString(3);
            String d = rs.getString(4);
            list.add(new Item(id, title, c, p, d));
        }
        return list;
    }

    //add item
    public long addItem(Item i) {
        ContentValues values = new ContentValues();
        values.put("title", i.getTitle());
        values.put("category", i.getCategory());
        values.put("price", i.getPrice());
        values.put("date", i.getDate());
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.insert("items", null, values);
    }

    //lay cac item theo date
    public List<Item> getByDate(String date) {
        List<Item> list = new ArrayList<>();
        String whereClause = "date like ?";
        String[] whereArgs = {date};
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor rs = sqLiteDatabase.query("items",
                null, whereClause, whereArgs,
                null, null, null);
        while ((rs != null) && (rs.moveToNext())) {
            int id = rs.getInt(0);
            String title = rs.getString(1);
            String category = rs.getString(2);
            String price = rs.getString(3);
            list.add(new Item(id, title, category, price, date));
        }
        return list;
    }

    //update
    public int updateItem(Item i) {
        ContentValues values = new ContentValues();
        values.put("title", i.getTitle());
        values.put("category", i.getCategory());
        values.put("price", i.getPrice());
        values.put("date", i.getDate());
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String whereClause = "id = ?";
        String[] whereArgs = {Integer.toString(i.getId())};
        return sqLiteDatabase.update("items",
                values, whereClause, whereArgs);
    }

    //delete
    public int deleteItem(int id) {
        String whereClause = "id = ?";
        String[] whereArgs = {Integer.toString(id)};
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.delete("items",
                whereClause, whereArgs);
    }

    public List<Item> searchByTitle(String key) {
        List<Item> list = new ArrayList<>();
        String where = "title like ?";
        String[] args = {"%" + key + "%"};
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.query("items", null, where,
                args, null, null, null);
        while (rs != null && rs.moveToNext()) {
            list.add(new Item(rs.getInt(0), rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
        }
        return list;
    }

    public List<Item> searchByCategory(String category) {
        List<Item> list = new ArrayList<>();
        String where = "category like ?";
        String[] args = {category};
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.query("items", null, where,
                args, null, null, null);
        while (rs != null && rs.moveToNext()) {
            list.add(new Item(rs.getInt(0), rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
        }
        return list;
    }

    public List<Item> searchByDateFromTo(String from, String to) {
        List<Item> list = new ArrayList<>();
        String where = "date BETWEEN ? AND ?";
        String[] args = {from.trim(), to.trim()};
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.query("items", null, where,
                args, null, null, null);
        while (rs != null && rs.moveToNext()) {
            list.add(new Item(rs.getInt(0), rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
        }
        return list;
    }

}
