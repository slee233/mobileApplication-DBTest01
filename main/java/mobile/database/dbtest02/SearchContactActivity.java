package mobile.database.dbtest02;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class SearchContactActivity extends AppCompatActivity {

	EditText etSearchName;

	ContactDBHelper helper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_contact);

		etSearchName = findViewById(R.id.etSearchName);

		helper = new ContactDBHelper(this);
	}
	
	
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.btnSearchContactSave:
			helper = new ContactDBHelper(this);
			SQLiteDatabase db = helper.getReadableDatabase();

			String[] columns = {"name", "phone", "category"};
			String selection = "name=?";
			String[] selectArgs = new String[]{etSearchName.getText().toString()};

			Cursor cursor = db.query(ContactDBHelper.TABLE_NAME, columns, selection, selectArgs, null, null, null);

			String result = "";
			if(cursor!=null) {
				while (cursor.moveToNext()) {
					String name = cursor.getString(cursor.getColumnIndex(ContactDBHelper.COL_NAME));
					String phone = cursor.getString(cursor.getColumnIndex(ContactDBHelper.COL_PHONE));
					String category = cursor.getString(cursor.getColumnIndex(ContactDBHelper.COL_CATEGORY));

					result += "name : " + name + "/n phone : " + phone + "\n category : " + category;
				}
			}

			AlertDialog.Builder builder = new AlertDialog.Builder(SearchContactActivity.this);
			builder.setTitle("사용자 정보");
			if(result != "")
					builder.setMessage(result);
			else
				builder.setMessage("사용자가 없습니다.");

			builder.setPositiveButton("확인", null)
					.show();

			cursor.close();
			break;
		case R.id.btnClose :
			finish();
			break;
		}
	}
	
	
	
}
