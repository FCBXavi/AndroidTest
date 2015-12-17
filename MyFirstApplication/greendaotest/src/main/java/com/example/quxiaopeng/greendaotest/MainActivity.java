package com.example.quxiaopeng.greendaotest;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.text.DateFormat;
import java.util.Date;

import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;
import me.test.greendao.DaoMaster;
import me.test.greendao.DaoSession;
import me.test.greendao.note;
import me.test.greendao.noteDao;

public class MainActivity extends Activity implements OnClickListener, AdapterView.OnItemClickListener {

    Button addBtn;
    Button searchBtn;
    EditText editText;
    ListView listView;

    private SQLiteDatabase database;
    private Cursor cursor;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    public static final String TAG = "DaoExample";

    SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        //官方推荐将获取DaoMaster对象的方法放到Application层,这样将避免多次创建生成Session
        setupDatabase();
        //获取NoteDao对象
        getNoteDao();

        String textColumn = noteDao.Properties.Text.columnName;
        String orderBy = textColumn + " COLLATE LOCALIZED ASC";
        cursor = database.query(getNoteDao().getTablename(), getNoteDao().getAllColumns(), null, null, null, null, orderBy);
        String[] from = {textColumn, noteDao.Properties.Comment.columnName};
        int[] to = {android.R.id.text1, android.R.id.text2};

        adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, cursor, from, to);
        listView.setAdapter(adapter);

        addBtn.setOnClickListener(this);
        searchBtn.setOnClickListener(this);
        listView.setOnItemClickListener(this);
    }

    private void initView() {
        addBtn = (Button) findViewById(R.id.btn_add);
        searchBtn = (Button) findViewById(R.id.btn_search);
        editText = (EditText) findViewById(R.id.et_input);
        listView = (ListView) findViewById(R.id.listview);
    }

    private void setupDatabase() {
        //通过DaoMaster内部类DevOpenHelper,你可以得到一个便利的SQLiteOpenHelper对象.
        //可能你已经注意到了,你并不需要去编写[CREATE TABLE]这样的SQL语句,因为greenDAO已经帮你做了
        //注意:默认的DaoMaster.DevOpenHelper会在数据库升级时,删除所有的表,意味着这将导致数据的丢失
        //所以,在正式的项目中,你还应该做一层封装,来实现数据库的安全升级.
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "notes-db", null);
        database = helper.getWritableDatabase();
        //注意:该数据库连接属于DaoMaster,所以多个Session指的是相同的数据库连接.
        daoMaster = new DaoMaster(database);
        daoSession = daoMaster.newSession();
    }

    private noteDao getNoteDao() {
        return daoSession.getNoteDao();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                addNote();
                break;
            case R.id.btn_search:
                search();
                break;
            default:
                break;
        }
    }

    private void addNote() {
        String noteText = editText.getText().toString();
        editText.setText("");

        final DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
        String comment = "Added on" + dateFormat.format(new Date());

        //插入操作,简单到只要你创建一个java对象
        note note = new note(null, noteText, comment, new Date());
        getNoteDao().insert(note);
        Log.d(TAG, "Insert new note, ID: " + note.getId());
        cursor.requery();
//        adapter.notifyDataSetChanged();
    }

    private void search() {
        //Query类代表了一个可以被重复执行的查询
        Query query = getNoteDao().queryBuilder().where(noteDao.Properties.Text.eq("Test1")).orderAsc(noteDao.Properties.Date).build();
        //查询结果以List返回
//        List notes = query.list();
        //在QueryBuilder类中内置两个Flag用于方便输出执行的SQL语句与传递参数的值
        QueryBuilder.LOG_SQL = true;
        QueryBuilder.LOG_VALUES = true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        getNoteDao().deleteByKey(id);
        Log.d(TAG, "Delete note, ID: " + id);
        cursor.requery();
//        adapter.notifyDataSetChanged();
    }
}
