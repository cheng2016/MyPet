package com.cds.pet.data.source.local.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.cds.pet.data.entity.SMessage;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "SMESSAGE".
*/
public class SMessageDao extends AbstractDao<SMessage, Long> {

    public static final String TABLENAME = "SMESSAGE";

    /**
     * Properties of entity SMessage.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Token = new Property(1, String.class, "token", false, "TOKEN");
        public final static Property UserId = new Property(2, String.class, "userId", false, "USER_ID");
        public final static Property Uid = new Property(3, String.class, "uid", false, "UID");
        public final static Property MsgId = new Property(4, String.class, "msgId", false, "MSG_ID");
        public final static Property MsgType = new Property(5, String.class, "msgType", false, "MSG_TYPE");
        public final static Property Title = new Property(6, String.class, "title", false, "TITLE");
        public final static Property Content = new Property(7, String.class, "content", false, "CONTENT");
        public final static Property OrderId = new Property(8, String.class, "orderId", false, "ORDER_ID");
        public final static Property Tailtime = new Property(9, String.class, "tailtime", false, "TAILTIME");
    }


    public SMessageDao(DaoConfig config) {
        super(config);
    }
    
    public SMessageDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"SMESSAGE\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"TOKEN\" TEXT," + // 1: token
                "\"USER_ID\" TEXT," + // 2: userId
                "\"UID\" TEXT," + // 3: uid
                "\"MSG_ID\" TEXT," + // 4: msgId
                "\"MSG_TYPE\" TEXT," + // 5: msgType
                "\"TITLE\" TEXT," + // 6: title
                "\"CONTENT\" TEXT," + // 7: content
                "\"ORDER_ID\" TEXT," + // 8: orderId
                "\"TAILTIME\" TEXT);"); // 9: tailtime
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"SMESSAGE\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, SMessage entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String token = entity.getToken();
        if (token != null) {
            stmt.bindString(2, token);
        }
 
        String userId = entity.getUserId();
        if (userId != null) {
            stmt.bindString(3, userId);
        }
 
        String uid = entity.getUid();
        if (uid != null) {
            stmt.bindString(4, uid);
        }
 
        String msgId = entity.getMsgId();
        if (msgId != null) {
            stmt.bindString(5, msgId);
        }
 
        String msgType = entity.getMsgType();
        if (msgType != null) {
            stmt.bindString(6, msgType);
        }
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(7, title);
        }
 
        String content = entity.getContent();
        if (content != null) {
            stmt.bindString(8, content);
        }
 
        String orderId = entity.getOrderId();
        if (orderId != null) {
            stmt.bindString(9, orderId);
        }
 
        String tailtime = entity.getTailtime();
        if (tailtime != null) {
            stmt.bindString(10, tailtime);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, SMessage entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String token = entity.getToken();
        if (token != null) {
            stmt.bindString(2, token);
        }
 
        String userId = entity.getUserId();
        if (userId != null) {
            stmt.bindString(3, userId);
        }
 
        String uid = entity.getUid();
        if (uid != null) {
            stmt.bindString(4, uid);
        }
 
        String msgId = entity.getMsgId();
        if (msgId != null) {
            stmt.bindString(5, msgId);
        }
 
        String msgType = entity.getMsgType();
        if (msgType != null) {
            stmt.bindString(6, msgType);
        }
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(7, title);
        }
 
        String content = entity.getContent();
        if (content != null) {
            stmt.bindString(8, content);
        }
 
        String orderId = entity.getOrderId();
        if (orderId != null) {
            stmt.bindString(9, orderId);
        }
 
        String tailtime = entity.getTailtime();
        if (tailtime != null) {
            stmt.bindString(10, tailtime);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public SMessage readEntity(Cursor cursor, int offset) {
        SMessage entity = new SMessage( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // token
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // userId
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // uid
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // msgId
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // msgType
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // title
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // content
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // orderId
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9) // tailtime
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, SMessage entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setToken(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setUserId(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setUid(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setMsgId(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setMsgType(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setTitle(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setContent(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setOrderId(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setTailtime(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(SMessage entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(SMessage entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(SMessage entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}