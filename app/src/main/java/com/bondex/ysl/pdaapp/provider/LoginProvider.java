package com.bondex.ysl.pdaapp.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import com.bondex.ysl.pdaapp.bean.loginebean.LoginBean;
import com.bondex.ysl.pdaapp.util.CommonUtil;
import com.bondex.ysl.pdaapp.util.Constant;

public class LoginProvider extends ContentProvider {

    private static final int BEANS = 1;
    private static final int BEAN = 2;
    private static final String TABLE_NAME = "login_bean";
    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
    private static final String AUTHORITY = "com.bondex.ysl.pdaapp.login";
    private static final String CONTENT_URI_STRING = "content://" + AUTHORITY + "/" + TABLE_NAME;
    public static final Uri CONTENT_URI = Uri.parse(CONTENT_URI_STRING);
    private LoginSqlite sqlite;



    static {
        URI_MATCHER.addURI(AUTHORITY, TABLE_NAME, BEANS);
        URI_MATCHER.addURI(AUTHORITY, TABLE_NAME + "/#", BEAN);
    }

    @Override
    public boolean onCreate() {

        sqlite = new LoginSqlite(getContext(),Constant.SQLITE_NAME,null,Constant.SQLITE_VERSION);

        return false;
    }

    @Override
    public Cursor query( Uri uri,String[] projection,String selection, String[] selectionArgs,  String sortOrder) {

        SQLiteDatabase db = sqlite.getReadableDatabase();

        switch (URI_MATCHER.match(uri)) {
            case BEANS:

                if (sortOrder == null) {
                    return db.query(TABLE_NAME, projection, selection, selectionArgs, null, null, null);
                }
                return db.query(TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);

            case BEAN:

                long id = ContentUris.parseId(uri);
                String where = LoginBean.USER_ID + " = " + id;
                if (selection != null && !"".equals(selection)) {
                    where = selection + "  and " + where;
                }
                return db.query(TABLE_NAME, projection, where, selectionArgs, null, null, sortOrder);
            default:
                throw new IllegalArgumentException("Unkonwn Uri: " + uri.toString());
        }
    }

    @Override
    public String getType(Uri uri) {

        switch (URI_MATCHER.match(uri)) {
            case BEANS:
                return "vnd.android.cursor.dir/" + TABLE_NAME;

            case BEAN:
                return "vnd.android.cursor.item/" + TABLE_NAME;

            default:
                throw new IllegalArgumentException("Unkwon Uri:" + uri.toString());
        }
    }

    @Override
    public Uri insert(Uri uri,ContentValues values) {

        SQLiteDatabase db = sqlite.getWritableDatabase();
        switch (URI_MATCHER.match(uri)){

            case BEANS:
                long rowId = db.insert(TABLE_NAME,LoginBean.USER_ID,values);
                return ContentUris.withAppendedId(uri,rowId);
            default:
                throw new IllegalArgumentException("Unkwon Uri:" + uri.toString());

        }


    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        SQLiteDatabase db  = sqlite.getWritableDatabase();
        int count = 0;

        switch (URI_MATCHER.match(uri)){

            case BEANS:

                count = db.delete(TABLE_NAME,selection,selectionArgs);
                return count;
            case BEAN:

                long id = ContentUris.parseId(uri);
                String where = LoginBean.USER_ID + " = "+id;
                count = db.delete(TABLE_NAME,selection,selectionArgs);
                return count;
            default:
                throw new IllegalArgumentException("Unkown Uri:" + uri.toString());
        }

    }

    @Override
    public int update(Uri uri,  ContentValues values, String selection,String[] selectionArgs) {

        SQLiteDatabase db = sqlite.getWritableDatabase();
        int count = 0;
        switch (URI_MATCHER.match(uri)){

            case BEANS:

                count = db.update(TABLE_NAME,values,selection,selectionArgs);
                return  count;
            case BEAN:

                long id = ContentUris.parseId(uri);
                String where = LoginBean.USER_ID + " = "+id;
                if(CommonUtil.isNotEmpty(selection)){
                    where = selection+" and "+where;
                }
                count = db.update(TABLE_NAME,values,where,selectionArgs);
                return count;
            default:
                throw new IllegalArgumentException("Unkowon Uri: " + uri.toString());
        }

    }



    private static final String ADD_PWD = "ALTER TABLE "+TABLE_NAME+" add column  "+LoginBean.PASSWORD+" VARCHAR(20)";
    private static final String ADD_ISLOGINED = "ALTER TABLE "+TABLE_NAME+" add column  "+LoginBean.ISLOGINED+" VARCHAR(10)";
    private static final String ADD_WARHOUSE = "ALTER TABLE "+TABLE_NAME+" add column  "+LoginBean.WAREHOUSES+" TEXT";

    private class LoginSqlite extends SQLiteOpenHelper{

        public LoginSqlite(Context context,  String name,SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);


        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            String sql = "create table "+TABLE_NAME+"(" + LoginBean.USER_ID + " VARCHAR(50)," + LoginBean.USER_NAME + " VARCHAR(50)," + LoginBean.SALT + " VARCHAR(50),"
                    + LoginBean.USER_LEVEL + " VARCHAR(10));";
            db.execSQL(sql);
            db.execSQL(ADD_PWD);
            db.execSQL(ADD_ISLOGINED);
            db.execSQL(ADD_WARHOUSE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            if(oldVersion == 1 && newVersion == 2){
                db.execSQL(ADD_PWD);
            }else if(oldVersion == 2 && newVersion == 3){
                db.execSQL(ADD_ISLOGINED);
                db.execSQL(ADD_WARHOUSE);
            }


        }
    }
}
