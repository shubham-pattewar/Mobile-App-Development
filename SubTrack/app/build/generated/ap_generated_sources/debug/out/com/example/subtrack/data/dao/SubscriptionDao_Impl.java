package com.example.subtrack.data.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.subtrack.data.model.Subscription;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class SubscriptionDao_Impl implements SubscriptionDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Subscription> __insertionAdapterOfSubscription;

  private final EntityDeletionOrUpdateAdapter<Subscription> __deletionAdapterOfSubscription;

  private final EntityDeletionOrUpdateAdapter<Subscription> __updateAdapterOfSubscription;

  public SubscriptionDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfSubscription = new EntityInsertionAdapter<Subscription>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `subscriptions` (`id`,`name`,`category`,`cost`,`currency`,`billingCycle`,`customCycleDays`,`startDate`,`nextRenewalDate`,`colorTag`,`notes`,`isActive`,`reminderDaysBefore`,`paymentMethod`,`isTrial`,`trialEndDate`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final Subscription entity) {
        statement.bindLong(1, entity.id);
        if (entity.name == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.name);
        }
        if (entity.category == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.category);
        }
        statement.bindDouble(4, entity.cost);
        if (entity.currency == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.currency);
        }
        if (entity.billingCycle == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.billingCycle);
        }
        statement.bindLong(7, entity.customCycleDays);
        statement.bindLong(8, entity.startDate);
        statement.bindLong(9, entity.nextRenewalDate);
        if (entity.colorTag == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.colorTag);
        }
        if (entity.notes == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.notes);
        }
        final int _tmp = entity.isActive ? 1 : 0;
        statement.bindLong(12, _tmp);
        statement.bindLong(13, entity.reminderDaysBefore);
        if (entity.paymentMethod == null) {
          statement.bindNull(14);
        } else {
          statement.bindString(14, entity.paymentMethod);
        }
        final int _tmp_1 = entity.isTrial ? 1 : 0;
        statement.bindLong(15, _tmp_1);
        statement.bindLong(16, entity.trialEndDate);
      }
    };
    this.__deletionAdapterOfSubscription = new EntityDeletionOrUpdateAdapter<Subscription>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `subscriptions` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final Subscription entity) {
        statement.bindLong(1, entity.id);
      }
    };
    this.__updateAdapterOfSubscription = new EntityDeletionOrUpdateAdapter<Subscription>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `subscriptions` SET `id` = ?,`name` = ?,`category` = ?,`cost` = ?,`currency` = ?,`billingCycle` = ?,`customCycleDays` = ?,`startDate` = ?,`nextRenewalDate` = ?,`colorTag` = ?,`notes` = ?,`isActive` = ?,`reminderDaysBefore` = ?,`paymentMethod` = ?,`isTrial` = ?,`trialEndDate` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final Subscription entity) {
        statement.bindLong(1, entity.id);
        if (entity.name == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.name);
        }
        if (entity.category == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.category);
        }
        statement.bindDouble(4, entity.cost);
        if (entity.currency == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.currency);
        }
        if (entity.billingCycle == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.billingCycle);
        }
        statement.bindLong(7, entity.customCycleDays);
        statement.bindLong(8, entity.startDate);
        statement.bindLong(9, entity.nextRenewalDate);
        if (entity.colorTag == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.colorTag);
        }
        if (entity.notes == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.notes);
        }
        final int _tmp = entity.isActive ? 1 : 0;
        statement.bindLong(12, _tmp);
        statement.bindLong(13, entity.reminderDaysBefore);
        if (entity.paymentMethod == null) {
          statement.bindNull(14);
        } else {
          statement.bindString(14, entity.paymentMethod);
        }
        final int _tmp_1 = entity.isTrial ? 1 : 0;
        statement.bindLong(15, _tmp_1);
        statement.bindLong(16, entity.trialEndDate);
        statement.bindLong(17, entity.id);
      }
    };
  }

  @Override
  public void insert(final Subscription subscription) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfSubscription.insert(subscription);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final Subscription subscription) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfSubscription.handle(subscription);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final Subscription subscription) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfSubscription.handle(subscription);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public LiveData<List<Subscription>> getAllSubscriptions() {
    final String _sql = "SELECT * FROM subscriptions ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"subscriptions"}, false, new Callable<List<Subscription>>() {
      @Override
      @Nullable
      public List<Subscription> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfCost = CursorUtil.getColumnIndexOrThrow(_cursor, "cost");
          final int _cursorIndexOfCurrency = CursorUtil.getColumnIndexOrThrow(_cursor, "currency");
          final int _cursorIndexOfBillingCycle = CursorUtil.getColumnIndexOrThrow(_cursor, "billingCycle");
          final int _cursorIndexOfCustomCycleDays = CursorUtil.getColumnIndexOrThrow(_cursor, "customCycleDays");
          final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "startDate");
          final int _cursorIndexOfNextRenewalDate = CursorUtil.getColumnIndexOrThrow(_cursor, "nextRenewalDate");
          final int _cursorIndexOfColorTag = CursorUtil.getColumnIndexOrThrow(_cursor, "colorTag");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final int _cursorIndexOfReminderDaysBefore = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderDaysBefore");
          final int _cursorIndexOfPaymentMethod = CursorUtil.getColumnIndexOrThrow(_cursor, "paymentMethod");
          final int _cursorIndexOfIsTrial = CursorUtil.getColumnIndexOrThrow(_cursor, "isTrial");
          final int _cursorIndexOfTrialEndDate = CursorUtil.getColumnIndexOrThrow(_cursor, "trialEndDate");
          final List<Subscription> _result = new ArrayList<Subscription>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Subscription _item;
            _item = new Subscription();
            _item.id = _cursor.getInt(_cursorIndexOfId);
            if (_cursor.isNull(_cursorIndexOfName)) {
              _item.name = null;
            } else {
              _item.name = _cursor.getString(_cursorIndexOfName);
            }
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _item.category = null;
            } else {
              _item.category = _cursor.getString(_cursorIndexOfCategory);
            }
            _item.cost = _cursor.getDouble(_cursorIndexOfCost);
            if (_cursor.isNull(_cursorIndexOfCurrency)) {
              _item.currency = null;
            } else {
              _item.currency = _cursor.getString(_cursorIndexOfCurrency);
            }
            if (_cursor.isNull(_cursorIndexOfBillingCycle)) {
              _item.billingCycle = null;
            } else {
              _item.billingCycle = _cursor.getString(_cursorIndexOfBillingCycle);
            }
            _item.customCycleDays = _cursor.getInt(_cursorIndexOfCustomCycleDays);
            _item.startDate = _cursor.getLong(_cursorIndexOfStartDate);
            _item.nextRenewalDate = _cursor.getLong(_cursorIndexOfNextRenewalDate);
            if (_cursor.isNull(_cursorIndexOfColorTag)) {
              _item.colorTag = null;
            } else {
              _item.colorTag = _cursor.getString(_cursorIndexOfColorTag);
            }
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _item.notes = null;
            } else {
              _item.notes = _cursor.getString(_cursorIndexOfNotes);
            }
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsActive);
            _item.isActive = _tmp != 0;
            _item.reminderDaysBefore = _cursor.getInt(_cursorIndexOfReminderDaysBefore);
            if (_cursor.isNull(_cursorIndexOfPaymentMethod)) {
              _item.paymentMethod = null;
            } else {
              _item.paymentMethod = _cursor.getString(_cursorIndexOfPaymentMethod);
            }
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsTrial);
            _item.isTrial = _tmp_1 != 0;
            _item.trialEndDate = _cursor.getLong(_cursorIndexOfTrialEndDate);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<List<Subscription>> getActiveSubscriptions() {
    final String _sql = "SELECT * FROM subscriptions WHERE isActive = 1 ORDER BY nextRenewalDate ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"subscriptions"}, false, new Callable<List<Subscription>>() {
      @Override
      @Nullable
      public List<Subscription> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfCost = CursorUtil.getColumnIndexOrThrow(_cursor, "cost");
          final int _cursorIndexOfCurrency = CursorUtil.getColumnIndexOrThrow(_cursor, "currency");
          final int _cursorIndexOfBillingCycle = CursorUtil.getColumnIndexOrThrow(_cursor, "billingCycle");
          final int _cursorIndexOfCustomCycleDays = CursorUtil.getColumnIndexOrThrow(_cursor, "customCycleDays");
          final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "startDate");
          final int _cursorIndexOfNextRenewalDate = CursorUtil.getColumnIndexOrThrow(_cursor, "nextRenewalDate");
          final int _cursorIndexOfColorTag = CursorUtil.getColumnIndexOrThrow(_cursor, "colorTag");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final int _cursorIndexOfReminderDaysBefore = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderDaysBefore");
          final int _cursorIndexOfPaymentMethod = CursorUtil.getColumnIndexOrThrow(_cursor, "paymentMethod");
          final int _cursorIndexOfIsTrial = CursorUtil.getColumnIndexOrThrow(_cursor, "isTrial");
          final int _cursorIndexOfTrialEndDate = CursorUtil.getColumnIndexOrThrow(_cursor, "trialEndDate");
          final List<Subscription> _result = new ArrayList<Subscription>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Subscription _item;
            _item = new Subscription();
            _item.id = _cursor.getInt(_cursorIndexOfId);
            if (_cursor.isNull(_cursorIndexOfName)) {
              _item.name = null;
            } else {
              _item.name = _cursor.getString(_cursorIndexOfName);
            }
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _item.category = null;
            } else {
              _item.category = _cursor.getString(_cursorIndexOfCategory);
            }
            _item.cost = _cursor.getDouble(_cursorIndexOfCost);
            if (_cursor.isNull(_cursorIndexOfCurrency)) {
              _item.currency = null;
            } else {
              _item.currency = _cursor.getString(_cursorIndexOfCurrency);
            }
            if (_cursor.isNull(_cursorIndexOfBillingCycle)) {
              _item.billingCycle = null;
            } else {
              _item.billingCycle = _cursor.getString(_cursorIndexOfBillingCycle);
            }
            _item.customCycleDays = _cursor.getInt(_cursorIndexOfCustomCycleDays);
            _item.startDate = _cursor.getLong(_cursorIndexOfStartDate);
            _item.nextRenewalDate = _cursor.getLong(_cursorIndexOfNextRenewalDate);
            if (_cursor.isNull(_cursorIndexOfColorTag)) {
              _item.colorTag = null;
            } else {
              _item.colorTag = _cursor.getString(_cursorIndexOfColorTag);
            }
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _item.notes = null;
            } else {
              _item.notes = _cursor.getString(_cursorIndexOfNotes);
            }
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsActive);
            _item.isActive = _tmp != 0;
            _item.reminderDaysBefore = _cursor.getInt(_cursorIndexOfReminderDaysBefore);
            if (_cursor.isNull(_cursorIndexOfPaymentMethod)) {
              _item.paymentMethod = null;
            } else {
              _item.paymentMethod = _cursor.getString(_cursorIndexOfPaymentMethod);
            }
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsTrial);
            _item.isTrial = _tmp_1 != 0;
            _item.trialEndDate = _cursor.getLong(_cursorIndexOfTrialEndDate);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public List<Subscription> getActiveSubscriptionsSync() {
    final String _sql = "SELECT * FROM subscriptions WHERE isActive = 1 ORDER BY nextRenewalDate ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
      final int _cursorIndexOfCost = CursorUtil.getColumnIndexOrThrow(_cursor, "cost");
      final int _cursorIndexOfCurrency = CursorUtil.getColumnIndexOrThrow(_cursor, "currency");
      final int _cursorIndexOfBillingCycle = CursorUtil.getColumnIndexOrThrow(_cursor, "billingCycle");
      final int _cursorIndexOfCustomCycleDays = CursorUtil.getColumnIndexOrThrow(_cursor, "customCycleDays");
      final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "startDate");
      final int _cursorIndexOfNextRenewalDate = CursorUtil.getColumnIndexOrThrow(_cursor, "nextRenewalDate");
      final int _cursorIndexOfColorTag = CursorUtil.getColumnIndexOrThrow(_cursor, "colorTag");
      final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
      final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
      final int _cursorIndexOfReminderDaysBefore = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderDaysBefore");
      final int _cursorIndexOfPaymentMethod = CursorUtil.getColumnIndexOrThrow(_cursor, "paymentMethod");
      final int _cursorIndexOfIsTrial = CursorUtil.getColumnIndexOrThrow(_cursor, "isTrial");
      final int _cursorIndexOfTrialEndDate = CursorUtil.getColumnIndexOrThrow(_cursor, "trialEndDate");
      final List<Subscription> _result = new ArrayList<Subscription>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final Subscription _item;
        _item = new Subscription();
        _item.id = _cursor.getInt(_cursorIndexOfId);
        if (_cursor.isNull(_cursorIndexOfName)) {
          _item.name = null;
        } else {
          _item.name = _cursor.getString(_cursorIndexOfName);
        }
        if (_cursor.isNull(_cursorIndexOfCategory)) {
          _item.category = null;
        } else {
          _item.category = _cursor.getString(_cursorIndexOfCategory);
        }
        _item.cost = _cursor.getDouble(_cursorIndexOfCost);
        if (_cursor.isNull(_cursorIndexOfCurrency)) {
          _item.currency = null;
        } else {
          _item.currency = _cursor.getString(_cursorIndexOfCurrency);
        }
        if (_cursor.isNull(_cursorIndexOfBillingCycle)) {
          _item.billingCycle = null;
        } else {
          _item.billingCycle = _cursor.getString(_cursorIndexOfBillingCycle);
        }
        _item.customCycleDays = _cursor.getInt(_cursorIndexOfCustomCycleDays);
        _item.startDate = _cursor.getLong(_cursorIndexOfStartDate);
        _item.nextRenewalDate = _cursor.getLong(_cursorIndexOfNextRenewalDate);
        if (_cursor.isNull(_cursorIndexOfColorTag)) {
          _item.colorTag = null;
        } else {
          _item.colorTag = _cursor.getString(_cursorIndexOfColorTag);
        }
        if (_cursor.isNull(_cursorIndexOfNotes)) {
          _item.notes = null;
        } else {
          _item.notes = _cursor.getString(_cursorIndexOfNotes);
        }
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfIsActive);
        _item.isActive = _tmp != 0;
        _item.reminderDaysBefore = _cursor.getInt(_cursorIndexOfReminderDaysBefore);
        if (_cursor.isNull(_cursorIndexOfPaymentMethod)) {
          _item.paymentMethod = null;
        } else {
          _item.paymentMethod = _cursor.getString(_cursorIndexOfPaymentMethod);
        }
        final int _tmp_1;
        _tmp_1 = _cursor.getInt(_cursorIndexOfIsTrial);
        _item.isTrial = _tmp_1 != 0;
        _item.trialEndDate = _cursor.getLong(_cursorIndexOfTrialEndDate);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<Subscription> getSubscriptionById(final int id) {
    final String _sql = "SELECT * FROM subscriptions WHERE id = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    return __db.getInvalidationTracker().createLiveData(new String[] {"subscriptions"}, false, new Callable<Subscription>() {
      @Override
      @Nullable
      public Subscription call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfCost = CursorUtil.getColumnIndexOrThrow(_cursor, "cost");
          final int _cursorIndexOfCurrency = CursorUtil.getColumnIndexOrThrow(_cursor, "currency");
          final int _cursorIndexOfBillingCycle = CursorUtil.getColumnIndexOrThrow(_cursor, "billingCycle");
          final int _cursorIndexOfCustomCycleDays = CursorUtil.getColumnIndexOrThrow(_cursor, "customCycleDays");
          final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "startDate");
          final int _cursorIndexOfNextRenewalDate = CursorUtil.getColumnIndexOrThrow(_cursor, "nextRenewalDate");
          final int _cursorIndexOfColorTag = CursorUtil.getColumnIndexOrThrow(_cursor, "colorTag");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final int _cursorIndexOfReminderDaysBefore = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderDaysBefore");
          final int _cursorIndexOfPaymentMethod = CursorUtil.getColumnIndexOrThrow(_cursor, "paymentMethod");
          final int _cursorIndexOfIsTrial = CursorUtil.getColumnIndexOrThrow(_cursor, "isTrial");
          final int _cursorIndexOfTrialEndDate = CursorUtil.getColumnIndexOrThrow(_cursor, "trialEndDate");
          final Subscription _result;
          if (_cursor.moveToFirst()) {
            _result = new Subscription();
            _result.id = _cursor.getInt(_cursorIndexOfId);
            if (_cursor.isNull(_cursorIndexOfName)) {
              _result.name = null;
            } else {
              _result.name = _cursor.getString(_cursorIndexOfName);
            }
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _result.category = null;
            } else {
              _result.category = _cursor.getString(_cursorIndexOfCategory);
            }
            _result.cost = _cursor.getDouble(_cursorIndexOfCost);
            if (_cursor.isNull(_cursorIndexOfCurrency)) {
              _result.currency = null;
            } else {
              _result.currency = _cursor.getString(_cursorIndexOfCurrency);
            }
            if (_cursor.isNull(_cursorIndexOfBillingCycle)) {
              _result.billingCycle = null;
            } else {
              _result.billingCycle = _cursor.getString(_cursorIndexOfBillingCycle);
            }
            _result.customCycleDays = _cursor.getInt(_cursorIndexOfCustomCycleDays);
            _result.startDate = _cursor.getLong(_cursorIndexOfStartDate);
            _result.nextRenewalDate = _cursor.getLong(_cursorIndexOfNextRenewalDate);
            if (_cursor.isNull(_cursorIndexOfColorTag)) {
              _result.colorTag = null;
            } else {
              _result.colorTag = _cursor.getString(_cursorIndexOfColorTag);
            }
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _result.notes = null;
            } else {
              _result.notes = _cursor.getString(_cursorIndexOfNotes);
            }
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsActive);
            _result.isActive = _tmp != 0;
            _result.reminderDaysBefore = _cursor.getInt(_cursorIndexOfReminderDaysBefore);
            if (_cursor.isNull(_cursorIndexOfPaymentMethod)) {
              _result.paymentMethod = null;
            } else {
              _result.paymentMethod = _cursor.getString(_cursorIndexOfPaymentMethod);
            }
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsTrial);
            _result.isTrial = _tmp_1 != 0;
            _result.trialEndDate = _cursor.getLong(_cursorIndexOfTrialEndDate);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
