package mobile.example.dbtest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;
import java.util.List;

public class EditActivity extends Activity {
    private ArrayAdapter<ContactDto> adapter;
    private ArrayList<ContactDto> list;
    EditText name;
    EditText phone;
    EditText cat;
    ContactDBHelper helper;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        list = new ArrayList<ContactDto>();

        name = (EditText)findViewById(R.id.new_name);
        phone = (EditText)findViewById(R.id.new_num);
        cat = (EditText)findViewById(R.id.new_cat);

        helper = new ContactDBHelper(this);

    }

    public void onClick(View v){
        SQLiteDatabase db = helper.getWritableDatabase();
        String new_name = name.getText().toString();
        String new_phone = phone.getText().toString();
        String new_cat = cat.getText().toString();

        Intent intent = getIntent();
        String n = intent.getExtras().getString("n");

       // Toast.makeText(this, n, Toast.LENGTH_SHORT).show();

        ContentValues row = new ContentValues();
        row.put("name", new_name);
        row.put("phone", new_phone);
        row.put("category", new_cat);

        String whereClause = "name=?";
        String[] whereArgs = new String[]{n};
        db.update(ContactDBHelper.TABLE_NAME, row, whereClause, whereArgs);

        adapter.notifyDataSetChanged();
        helper.close();
    }


}
