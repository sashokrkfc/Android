package com.example.myapplicationregistration;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button read, clear, add;
    EditText etName;
    DBHelper dbHelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        add = (Button) findViewById(R.id.add);
        add.setOnClickListener(this);
        read = (Button) findViewById(R.id.read);
        read.setOnClickListener(this);
        clear = (Button) findViewById(R.id.clear);
        clear.setOnClickListener(this);

        etName = (EditText) findViewById(R.id.etName);
        etName.setOnClickListener(this);

        dbHelper = new DBHelper(this);
    }

    @Override
    public void onClick(View v) {

        String name = etName.getText().toString();
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        switch (v.getId()){
            case R.id.add:
                contentValues.put(DBHelper.KEY_NAME, name);
                sqLiteDatabase.insert(DBHelper.TABLE_CONTACTS, null, contentValues);
            break;

            case R.id.read:
                Cursor cursor = sqLiteDatabase.query(DBHelper.TABLE_CONTACTS,
                        null,null,
                        null,null,null,null);
                if(cursor.moveToFirst()){
                    int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
                    int nameIndex = cursor.getColumnIndex(DBHelper.KEY_NAME);
                    do {
                        Log.d("mLog", "ID = " + cursor.getInt(idIndex) +
                                  ", name = " + cursor.getString(nameIndex));

                    }while (cursor.moveToNext());
                }else
                    Log.d("mLog", "0 rows");

                cursor.close();
                break;
            case R.id.clear:
                sqLiteDatabase.delete(dbHelper.TABLE_CONTACTS, null,null);
                break;
        }
        dbHelper.close();

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
