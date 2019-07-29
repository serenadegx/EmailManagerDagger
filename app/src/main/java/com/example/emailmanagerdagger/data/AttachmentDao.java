package com.example.emailmanagerdagger.data;

import java.util.List;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "ATTACHMENT".
*/
public class AttachmentDao extends AbstractDao<Attachment, Long> {

    public static final String TABLENAME = "ATTACHMENT";

    /**
     * Properties of entity Attachment.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property AttachmentId = new Property(1, long.class, "attachmentId", false, "ATTACHMENT_ID");
        public final static Property FileName = new Property(2, String.class, "fileName", false, "FILE_NAME");
        public final static Property Path = new Property(3, String.class, "path", false, "PATH");
        public final static Property Size = new Property(4, String.class, "size", false, "SIZE");
        public final static Property Total = new Property(5, long.class, "total", false, "TOTAL");
        public final static Property IsDownload = new Property(6, boolean.class, "isDownload", false, "IS_DOWNLOAD");
    }

    private Query<Attachment> email_AttachmentsQuery;

    public AttachmentDao(DaoConfig config) {
        super(config);
    }
    
    public AttachmentDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"ATTACHMENT\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"ATTACHMENT_ID\" INTEGER NOT NULL ," + // 1: attachmentId
                "\"FILE_NAME\" TEXT," + // 2: fileName
                "\"PATH\" TEXT," + // 3: path
                "\"SIZE\" TEXT," + // 4: size
                "\"TOTAL\" INTEGER NOT NULL ," + // 5: total
                "\"IS_DOWNLOAD\" INTEGER NOT NULL );"); // 6: isDownload
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"ATTACHMENT\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Attachment entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getAttachmentId());
 
        String fileName = entity.getFileName();
        if (fileName != null) {
            stmt.bindString(3, fileName);
        }
 
        String path = entity.getPath();
        if (path != null) {
            stmt.bindString(4, path);
        }
 
        String size = entity.getSize();
        if (size != null) {
            stmt.bindString(5, size);
        }
        stmt.bindLong(6, entity.getTotal());
        stmt.bindLong(7, entity.getIsDownload() ? 1L: 0L);
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Attachment entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getAttachmentId());
 
        String fileName = entity.getFileName();
        if (fileName != null) {
            stmt.bindString(3, fileName);
        }
 
        String path = entity.getPath();
        if (path != null) {
            stmt.bindString(4, path);
        }
 
        String size = entity.getSize();
        if (size != null) {
            stmt.bindString(5, size);
        }
        stmt.bindLong(6, entity.getTotal());
        stmt.bindLong(7, entity.getIsDownload() ? 1L: 0L);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public Attachment readEntity(Cursor cursor, int offset) {
        Attachment entity = new Attachment( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getLong(offset + 1), // attachmentId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // fileName
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // path
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // size
            cursor.getLong(offset + 5), // total
            cursor.getShort(offset + 6) != 0 // isDownload
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Attachment entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setAttachmentId(cursor.getLong(offset + 1));
        entity.setFileName(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setPath(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setSize(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setTotal(cursor.getLong(offset + 5));
        entity.setIsDownload(cursor.getShort(offset + 6) != 0);
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Attachment entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Attachment entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Attachment entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
    /** Internal query to resolve the "attachments" to-many relationship of Email. */
    public List<Attachment> _queryEmail_Attachments(long attachmentId) {
        synchronized (this) {
            if (email_AttachmentsQuery == null) {
                QueryBuilder<Attachment> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.AttachmentId.eq(null));
                email_AttachmentsQuery = queryBuilder.build();
            }
        }
        Query<Attachment> query = email_AttachmentsQuery.forCurrentThread();
        query.setParameter(0, attachmentId);
        return query.list();
    }

}