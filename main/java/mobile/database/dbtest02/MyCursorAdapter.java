package mobile.database.dbtest02;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class MyCursorAdapter extends CursorAdapter {
    LayoutInflater inflater;
    int layout;

    public MyCursorAdapter(Context context, int layout, Cursor c) {
        super(context, c, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        this.inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        this.layout = layout;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = inflater.inflate(layout, parent, false);
        ViewHolder holder = new ViewHolder();   //채우기 기능은 없음(생성만)
        view.setTag(holder);
        return view;
        //return inflater.inflate(layout, parent, false); 도 가능
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder = (ViewHolder) view.getTag();

        if(holder.tvContactName == null){   //맨 처음 들어왔을 경우
            holder.tvContactName = view.findViewById(R.id.tvContactName);
            holder.tvContactPhone = view.findViewById(R.id.tvContactPhone);
        }

//        TextView tvContactName = view.findViewById(R.id.tvContactName);
//        TextView tvContactPhone = view.findViewById(R.id.tvContactPhone);

//        tvContactName.setText(cursor.getString(cursor.getColumnIndex(ContactDBHelper.COL_NAME)));
//        tvContactPhone.setText(cursor.getString(cursor.getColumnIndex(ContactDBHelper.COL_PHONE)));
        holder.tvContactName.setText(cursor.getString(cursor.getColumnIndex(ContactDBHelper.COL_NAME)));
        holder.tvContactPhone.setText(cursor.getString(cursor.getColumnIndex(ContactDBHelper.COL_PHONE)));
    }

    static class ViewHolder {
        public ViewHolder(){    //초기 기본값
            tvContactName = null;
            tvContactPhone = null;
        }
        TextView tvContactName;
        TextView tvContactPhone;
    }
}
