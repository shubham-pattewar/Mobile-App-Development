package com.example.subtrack.data.database;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.example.subtrack.data.dao.SubscriptionDao;
import com.example.subtrack.data.dao.SubscriptionDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile SubscriptionDao _subscriptionDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(3) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `subscriptions` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT, `category` TEXT, `cost` REAL NOT NULL, `currency` TEXT, `billingCycle` TEXT, `customCycleDays` INTEGER NOT NULL, `startDate` INTEGER NOT NULL, `nextRenewalDate` INTEGER NOT NULL, `colorTag` TEXT, `notes` TEXT, `isActive` INTEGER NOT NULL, `reminderDaysBefore` INTEGER NOT NULL, `paymentMethod` TEXT, `isTrial` INTEGER NOT NULL, `trialEndDate` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '5ad4b1be60d819e5d7c4e23a808348fd')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `subscriptions`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsSubscriptions = new HashMap<String, TableInfo.Column>(16);
        _columnsSubscriptions.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSubscriptions.put("name", new TableInfo.Column("name", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSubscriptions.put("category", new TableInfo.Column("category", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSubscriptions.put("cost", new TableInfo.Column("cost", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSubscriptions.put("currency", new TableInfo.Column("currency", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSubscriptions.put("billingCycle", new TableInfo.Column("billingCycle", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSubscriptions.put("customCycleDays", new TableInfo.Column("customCycleDays", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSubscriptions.put("startDate", new TableInfo.Column("startDate", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSubscriptions.put("nextRenewalDate", new TableInfo.Column("nextRenewalDate", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSubscriptions.put("colorTag", new TableInfo.Column("colorTag", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSubscriptions.put("notes", new TableInfo.Column("notes", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSubscriptions.put("isActive", new TableInfo.Column("isActive", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSubscriptions.put("reminderDaysBefore", new TableInfo.Column("reminderDaysBefore", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSubscriptions.put("paymentMethod", new TableInfo.Column("paymentMethod", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSubscriptions.put("isTrial", new TableInfo.Column("isTrial", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSubscriptions.put("trialEndDate", new TableInfo.Column("trialEndDate", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysSubscriptions = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesSubscriptions = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoSubscriptions = new TableInfo("subscriptions", _columnsSubscriptions, _foreignKeysSubscriptions, _indicesSubscriptions);
        final TableInfo _existingSubscriptions = TableInfo.read(db, "subscriptions");
        if (!_infoSubscriptions.equals(_existingSubscriptions)) {
          return new RoomOpenHelper.ValidationResult(false, "subscriptions(com.example.subtrack.data.model.Subscription).\n"
                  + " Expected:\n" + _infoSubscriptions + "\n"
                  + " Found:\n" + _existingSubscriptions);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "5ad4b1be60d819e5d7c4e23a808348fd", "30f37fbc512f4bf67da6bb20b87e68bb");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "subscriptions");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `subscriptions`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(SubscriptionDao.class, SubscriptionDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public SubscriptionDao subscriptionDao() {
    if (_subscriptionDao != null) {
      return _subscriptionDao;
    } else {
      synchronized(this) {
        if(_subscriptionDao == null) {
          _subscriptionDao = new SubscriptionDao_Impl(this);
        }
        return _subscriptionDao;
      }
    }
  }
}
