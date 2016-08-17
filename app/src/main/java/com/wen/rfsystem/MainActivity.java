package com.wen.rfsystem;
/**
* 訂位系統專題  博文
 *
* */
import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class MainActivity extends AppCompatActivity{

    ArrayList<String> disp = new ArrayList<>();
    ListView lv;
    ArrayAdapter<String> adapter;
    TextView textDate;
    private DatePickerDialog datePickerDialog;
    List<reserve> mylist;

    public MainActivity() {
        super();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GregorianCalendar calendar = new GregorianCalendar();
         textDate = (TextView) findViewById(R.id.datetext);

        Calendar mCal = Calendar.getInstance();
        String dateformat = "yyyy/MM/dd";
        SimpleDateFormat df = new SimpleDateFormat(dateformat);
        String today = df.format(mCal.getTime());
        textDate.setText(today);
        // 實作DatePickerDialog的onDateSet方法，設定日期後將所設定的日期show在textDate上
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            //將設定的日期顯示出來
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
        textDate.setText(year + "/" + (monthOfYear+1) + "/" + dayOfMonth);
            }
        },calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
         calendar.get(Calendar.DAY_OF_MONTH));



        // listview
        SFsysDAO dao = new SFsysDAOImp(MainActivity.this);
        mylist = dao.getAllreserve();
        for (reserve s : mylist)
        {
            disp.add(dao.checkcus(s._id).tel);
        }

        adapter = new ArrayAdapter<String>( MainActivity.this,
                                            android.R.layout.simple_list_item_1,
                                            disp);

        lv = (ListView) findViewById(R.id.listView);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent it = new Intent(MainActivity.this, resDetail.class);
                it.putExtra("pos", mylist.get(position)._id);
                startActivity(it);
            }

        });
    }



    @Override
    protected void onResume() {
        super.onResume();
        SFsysDAO dao = new SFsysDAOImp(MainActivity.this);
        List<reserve> mylist = dao.getAllreserve();
        disp.clear();
        for (reserve s :mylist)
        {
            disp.add(dao.checkcus(s._id).tel);
        }
        adapter.notifyDataSetChanged();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add)
        {
            Intent it = new Intent(MainActivity.this, add.class);
            startActivity(it);
        }
        return super.onOptionsItemSelected(item);
    }


    public void setDate(View v) {
        datePickerDialog.show();
    }
    public void goRes(View v){
        Intent it = new Intent(MainActivity.this, resDetail.class);
        startActivity(it);
    }

    public void goCus(View v){
        Intent it = new Intent(MainActivity.this, cuslistActive.class);
        startActivity(it);
    }

    public void goSet(View v){
        Intent it = new Intent(MainActivity.this, setting.class);
        startActivity(it);
    }
    public void goAdd(View v){
        Intent it = new Intent(MainActivity.this, add.class);
        startActivity(it);
    }


}




