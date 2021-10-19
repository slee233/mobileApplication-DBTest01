package mobile.database.dbtest02;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class UpdateActivity extends AppCompatActivity {
    private ArrayAdapter<ContactDto> adapter;
    EditText etName;
    EditText etPhone;
    EditText etCategory;

    ContactDBHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        etCategory = findViewById(R.id.etCategory);

        helper = new ContactDBHelper(this);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String phone = intent.getStringExtra("phone");
        String category = intent.getStringExtra("category");

        etName.setText(name);
        etPhone.setText(phone);
        etCategory.setText(category);
    }

    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnUpdateContact:
                SQLiteDatabase db = helper.getWritableDatabase();

                String newName = etName.getText().toString();
                String newPhone = etPhone.getText().toString();
                String newCategory = etCategory.getText().toString();

                ContentValues row = new ContentValues();
                row.put("name", newName);
                row.put("phone", newPhone);
                row.put("category", newCategory);

                long id = getIntent().getLongExtra("id", 0);
                String whereClause = "_id=?";
                String[] whereArgs = new String[]{String.valueOf(id)};
                db.update(ContactDBHelper.TABLE_NAME, row, whereClause, whereArgs);

                adapter.notifyDataSetChanged();
                helper.close();
                break;

            case R.id.btnUpdateContactClose:
                finish();
                break;
        }
    }


}
