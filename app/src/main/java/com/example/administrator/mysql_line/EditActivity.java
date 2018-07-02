package com.example.administrator.mysql_line;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edt_name, edt_phone, edt_mail, edt_birth;
    Button btn_ok, btn_back;
    TextView txtTitle;
    public String new_name, new_phone, new_mail, new_birth;
    public Bundle bData;
    public DBadapter dbAdapter;
    public int index;
    private int mYear, mMonth, mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_edit );
        initView();
        bData = this.getIntent().getExtras();
        dbAdapter = new DBadapter( this );

        if (bData.getString( "type" ).equals( "edit" )) {
            txtTitle.setText( "編輯聯絡人" );
            Cursor cursor = dbAdapter.queryByName( bData.getString( "item_name" ) );
            index = cursor.getInt( 0 );
            edt_name.setText( cursor.getString( 1 ) );
            edt_phone.setText( cursor.getString( 2 ) );
            edt_mail.setText( cursor.getString( 3 ) );
            edt_birth.setText( cursor.getString( 4 ) );
        }
        //按鈕行為設定
        btn_ok.setOnClickListener( this );
        btn_back.setOnClickListener( this );
    }

    private void initView() {
        txtTitle = findViewById( R.id.txtTitle );
        edt_name = findViewById( R.id.editName );
        edt_phone = findViewById( R.id.editPhome );
        edt_mail = findViewById( R.id.editMail );
        edt_birth = findViewById( R.id.editBrith );
        edt_name.setOnClickListener( this );
        edt_phone.setOnClickListener( this );
        edt_mail.setOnClickListener( this );
        edt_birth.setOnClickListener( this );
        btn_ok = findViewById( R.id.btn_ok );
        btn_back = findViewById( R.id.btn_back );
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.editName:
                if (bData.getString( "type" ).equals( "add" )) edt_name.setText( "" );
                break;
            case R.id.editPhome:
                if (bData.getString( "type" ).equals( "add" )) edt_phone.setText( "" );
                break;
            case R.id.editMail:
                if (bData.getString( "type" ).equals( "add" )) edt_mail.setText( "" );
                break;
            case R.id.editBrith:
                if (bData.getString( "type" ).equals( "add" )) edt_birth.setText( "" );
                final Calendar c = Calendar.getInstance();
                mYear = c.get( Calendar.YEAR );
                mMonth = c.get( Calendar.MONTH );
                mDay = c.get( Calendar.DAY_OF_MONTH );

                DatePickerDialog datePickerDialog = new DatePickerDialog( this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                edt_birth.setText( year + "-" + (monthOfYear + 1) + "-" + dayOfMonth );
                            }
                        }, mYear, mMonth, mDay );
                datePickerDialog.show();

                break;
            case R.id.btn_ok:
                new_name = edt_name.getText().toString();
                new_phone = edt_phone.getText().toString();
                new_mail = edt_mail.getText().toString();
                new_birth = edt_birth.getText().toString();
                Log.i( "new_name=", new_name );
                Log.i( "new_phone=", new_phone );
                Log.i( "new_mail=", new_mail );
                dbAdapter = new DBadapter( EditActivity.this );
                if (bData.getString( "type" ).equals( "add" )) {
                    try {
                        dbAdapter.createContacts( new_name, new_phone, new_mail, new_birth );
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        //回到列表
                        Intent i = new Intent( this, MainActivity.class );
                        startActivity( i );
                    }

                } else {
                    try {
                        dbAdapter.updateContacts( index, new_name, new_phone, new_mail, new_birth );
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        Intent i = new Intent( this, ShowActivity.class );
                        i.putExtra( "item_name", edt_name.getText().toString() );
                        startActivity( i );
                    }

                }
                break;
            case R.id.btn_back:
                if (bData.getString( "type" ).equals( "add" )) {
                    Intent i = new Intent( this, MainActivity.class );
                    startActivity( i );
                } else {
                    Intent i = new Intent( this, ShowActivity.class );
                    i.putExtra( "item_name", edt_name.getText().toString() );
                    startActivity( i );
                }
                break;
        }
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater menuInflater = getMenuInflater();
//        menuInflater.inflate(R.menu.add,menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()){
//
//            case R.id.del_data:
//                Boolean isDeleted = dbAdapter.deleteContacts(index);
//                if(isDeleted)
//                    Toast.makeText(EditActivity.this,"已刪除!", Toast.LENGTH_LONG).show();
//                break;
//
//
//        }
//        return super.onOptionsItemSelected(item);    }
}
