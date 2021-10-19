package mobile.database.dbtest02;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class InsertContactActivity extends AppCompatActivity {

	EditText etName;
	EditText etPhone;
	EditText etCategory;

	ContactDBHelper helper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_insert_contact);

		etName = findViewById(R.id.newName);
		etPhone = findViewById(R.id.newPhone);
		etCategory = findViewById(R.id.newCat);

		helper = new ContactDBHelper(this);
	}
	
	
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.btnAddNewContact:
			String name = etName.getText().toString();
			String phone = etPhone.getText().toString();
			String category = etCategory.getText().toString();

			SQLiteDatabase db = helper.getWritableDatabase();

			ContentValues row = new ContentValues();
			row.put(ContactDBHelper.COL_NAME, name);
			row.put(ContactDBHelper.COL_PHONE, phone);
			row.put(ContactDBHelper.COL_CATEGORY, category);

			db.insert(ContactDBHelper.TABLE_NAME, null, row);
			finish();
			helper.close();
			break;
		case R.id.btnAddNewContactClose:
			finish();
			break;
		}
	}
	
	
	
	
	
}
