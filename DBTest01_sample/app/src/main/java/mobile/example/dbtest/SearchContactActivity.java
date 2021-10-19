package mobile.example.dbtest;

import android.app.Activity;
import android.app.AlertDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;

public class SearchContactActivity extends Activity {

	ContactDBHelper helper;
	EditText name;
	SQLiteDatabase db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_contact);

		name = (EditText)findViewById(R.id.etSearchName);
		helper = new ContactDBHelper(this);
		db = helper.getReadableDatabase();

	}

	
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btnSearchContact :
				helper = new ContactDBHelper(this);
				SQLiteDatabase db = helper.getReadableDatabase();

				String[] columns = {"_id", "name", "phone", "category"};
				String selection = "name=?";
				String[] selectArgs = new String[]{name.getText().toString()};

				Cursor cursor = db.query(ContactDBHelper.TABLE_NAME, columns, selection, selectArgs, null, null, null, null);


				//int count = cursor.getCount();

				String result = "";
				if(cursor!=null){
					while(cursor.moveToNext()){
						String name = cursor.getString(1);
						String phone = cursor.getString(2);
						String cat = cursor.getString(3);
						result = "name : " + name + "\n phone : " + phone + "\n category : " + cat;

					}
				}

				//Toast.makeText(this, result, Toast.LENGTH_SHORT).show();



				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle("사용자 정보")
						.setMessage(result)
						.setPositiveButton("확인", null)
						.show();

				cursor.close();
				break;
			case R.id.btnClose:
				break;
		}
	}

	
}
