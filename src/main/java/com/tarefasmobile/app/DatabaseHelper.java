package com.tarefasmobile.app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHelper";
    private static final String TABLE_NAME = "tasks";

    public DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTable =
                "CREATE TABLE " + TABLE_NAME + "(" +
                    "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "task VARCHAR(30) NOT NULL," +
                    "completed BOOLEAN default 0" +
                ");";
        sqLiteDatabase.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean criarTarefa(String task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("task", task);

        Log.d(TAG, "Criando tarefa: " + task);

        long result = db.insert(TABLE_NAME, null, contentValues);

        // -1 means fail
        return result != -1;
    }
    public Cursor buscarTarefas() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        Log.i(TAG, "Buscando tarefas");
        return cursor;
    }
    public Cursor buscarTarefaPorId(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT id, task, completed FROM " + TABLE_NAME + " WHERE id = " + id;
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }
    public void atualizarNomeTarefa(int id, String novoNome) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "UPDATE " +TABLE_NAME + " SET task=" +novoNome + " WHERE id=" +id;
        db.execSQL(sql);
    }
    public void atualizarStatusTarefa(int id, boolean completed) {
        int status = completed ? 1 : 0;
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "UPDATE " +TABLE_NAME + " SET completed=" +status + " WHERE id=" +id;
        db.execSQL(sql);
    }
    public void excluirTarefa(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "DELETE FROM " +TABLE_NAME + " WHERE id=" +id;
        db.execSQL(sql);
    }

    public void limparTarefas() {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "DELETE FROM " +TABLE_NAME;
        db.execSQL(sql);
    }
}
