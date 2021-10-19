package mobile.example.dbtest;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import android.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;

public class AllContactsActivity extends Activity {

	private ListView lvContacts = null;

	private ArrayAdapter<ContactDto> adapter;
	private ContactDBHelper helper;
	private ArrayList<ContactDto> contactList;
	private SQLiteDatabase db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_all_contacts);

		helper = new ContactDBHelper(this);
		contactList = new ArrayList<ContactDto>();
		db = helper.getWritableDatabase();

		lvContacts = (ListView) findViewById(R.id.lvContacts);
		adapter = new ArrayAdapter<ContactDto>(this, android.R.layout.simple_list_item_1, contactList);

		lvContacts.setAdapter(adapter);

		lvContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, final int position, final long id) {
				Intent intent = new Intent(getApplicationContext(), EditActivity.class);
				intent.putExtra("n", contactList.get(position).getName());
				startActivity(intent);
			}
		});

		lvContacts.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {//
				AlertDialog.Builder builder = new AlertDialog.Builder(AllContactsActivity.this);
				builder.setTitle("삭제하시겠습니까?")
				.setPositiveButton("확인", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						SQLiteDatabase db = helper.getWritableDatabase();

						String whereClause = "name=?";
						String[] whereArgs = new String[]{contactList.get(position).getName()};

						db.delete(ContactDBHelper.TABLE_NAME, whereClause, whereArgs);


						adapter.notifyDataSetChanged();
					}
				})
				.setNegativeButton("취소", null)
				.show();
				return true;
			}
		});
	}


	@Override
	protected void onResume() {
		super.onResume();

		SQLiteDatabase db = helper.getReadableDatabase();
		//Cursor cursor = db.rawQuery("select * from " + ContactDBHelper.TABLE_NAME, null);
		Cursor cursor = db.query(ContactDBHelper.TABLE_NAME, null, null, null, null, null, null, null);

		contactList.clear();

		while (cursor.moveToNext()) {
			ContactDto dto = new ContactDto();
			dto.setId(cursor.getInt(cursor.getColumnIndex("_id")));
			dto.setName(cursor.getString(cursor.getColumnIndex(ContactDBHelper.COL_NAME)));
			dto.setPhone(cursor.getString(cursor.getColumnIndex(ContactDBHelper.COL_PHONE)));
			dto.setCategory(cursor.getString(cursor.getColumnIndex(ContactDBHelper.COL_CAT)));
			contactList.add(dto);
		}

		adapter.notifyDataSetChanged();

		cursor.close();
		helper.close();
	}



}




