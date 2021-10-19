package mobile.database.dbtest02;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AllContactsActivity extends AppCompatActivity {
	
	ListView lvContacts = null;
	ContactDBHelper helper;
	Cursor cursor;
	SimpleCursorAdapter adapter;
	//MyCursorAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_all_contacts);
		lvContacts = (ListView)findViewById(R.id.lvContacts);

		helper = new ContactDBHelper(this);

//		 SimpleCursorAdapter 객체 생성
        adapter = new SimpleCursorAdapter (this, android.R.layout.simple_list_item_2, null,
											new String[] {ContactDBHelper.COL_NAME, ContactDBHelper.COL_PHONE}, new int[] {android.R.id.text1, android.R.id.text2},
											CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

		//MycursorAdapter 객체 생성
//		adapter = new MyCursorAdapter(this, R.layout.listview_layout, null);

		lvContacts.setAdapter(adapter);

//		리스트 뷰 클릭 처리
        lvContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
				Intent intent = new Intent(getApplicationContext(), UpdateActivity.class);

				intent.putExtra("name", cursor.getString(cursor.getColumnIndex(ContactDBHelper.COL_NAME)));
				intent.putExtra("phone", cursor.getString(cursor.getColumnIndex(ContactDBHelper.COL_PHONE)));
				intent.putExtra("category", cursor.getString(cursor.getColumnIndex(ContactDBHelper.COL_CATEGORY)));
				intent.putExtra("id", id);
				startActivity(intent);
            }
        });

//		리스트 뷰 롱클릭 처리
		lvContacts.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, final long id) {
				AlertDialog.Builder builder = new AlertDialog.Builder(AllContactsActivity.this);
				builder.setMessage("삭제하시겠습니까?")
						.setPositiveButton("확인", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								SQLiteDatabase db = helper.getWritableDatabase();

								String whereClause = "_id=?";
								String[] whereArgs = new String[]{String.valueOf(id)};

								db.delete(ContactDBHelper.TABLE_NAME, whereClause, whereArgs);

								adapter.notifyDataSetChanged();
								finish();	//수정해야함
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
//        DB에서 데이터를 읽어와 Adapter에 설정
        SQLiteDatabase db = helper.getReadableDatabase();
        cursor = db.rawQuery("select * from " + ContactDBHelper.TABLE_NAME, null);

        adapter.changeCursor(cursor);
		adapter.notifyDataSetChanged();
        helper.close();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
//        cursor 사용 종료
		if (cursor != null) cursor.close();
	}

}




