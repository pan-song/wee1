package com.bawei.util_dierzhou_lianxi2.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.bawei.util_dierzhou_lianxi2.bean.MyBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "MY_BEAN".
*/
public class MyBeanDao extends AbstractDao<MyBean, Void> {

    public static final String TABLENAME = "MY_BEAN";

    /**
     * Properties of entity MyBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property JsonStr = new Property(0, String.class, "jsonStr", false, "JSON_STR");
    }


    public MyBeanDao(DaoConfig config) {
        super(config);
    }
    
    public MyBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"MY_BEAN\" (" + //
                "\"JSON_STR\" TEXT);"); // 0: jsonStr
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"MY_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, MyBean entity) {
        stmt.clearBindings();
 
        String jsonStr = entity.getJsonStr();
        if (jsonStr != null) {
            stmt.bindString(1, jsonStr);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, MyBean entity) {
        stmt.clearBindings();
 
        String jsonStr = entity.getJsonStr();
        if (jsonStr != null) {
            stmt.bindString(1, jsonStr);
        }
    }

    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    @Override
    public MyBean readEntity(Cursor cursor, int offset) {
        MyBean entity = new MyBean( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0) // jsonStr
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, MyBean entity, int offset) {
        entity.setJsonStr(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
     }
    
    @Override
    protected final Void updateKeyAfterInsert(MyBean entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    @Override
    public Void getKey(MyBean entity) {
        return null;
    }

    @Override
    public boolean hasKey(MyBean entity) {
        // TODO
        return false;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
