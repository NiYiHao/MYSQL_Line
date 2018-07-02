package com.example.administrator.mysql_line;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ShowActivity extends AppCompatActivity {
    private TextView txt_name, txt_phone, txt_mail,txt_birth;
    public Bundle bData;
    public DBadapter dbAdapter;
    public int index;
    private String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_show );
        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );
        initView();
        bData = this.getIntent().getExtras();
        name = bData.getString("item_name");
        dbAdapter = new DBadapter(this);
        Cursor cursor = dbAdapter.queryByName(name);
        index = cursor.getInt(0);
        txt_name.setText(cursor.getString(1));
        txt_phone.setText(cursor.getString(2));
        txt_mail.setText(cursor.getString(3));
        txt_birth.setText(cursor.getString(4));
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("type","edit");
                intent.putExtra("item_name",name);
                intent.setClass(ShowActivity.this, EditActivity.class );
                startActivity(intent);
            }
        });
    }
    private void initView(){
        txt_name = findViewById(R.id.textName);
        txt_phone = findViewById(R.id.textPhome);
        txt_mail = findViewById(R.id.textMail);
        txt_birth = findViewById(R.id.textBrith);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.del,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.del_data:
                Boolean isDeleted = dbAdapter.deleteContacts(index);
                if(isDeleted)
                    Toast.makeText(ShowActivity.this,"已刪除!", Toast.LENGTH_LONG).show();
                Intent i = new Intent();
                i.setClass( this, MainActivity.class );
                startActivity( i );

                break;
        }
        return super.onOptionsItemSelected( item );
    }
}
