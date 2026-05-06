package com.example.foodsafe.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ScanHistoryDao {
    @Query("SELECT * FROM scan_history ORDER BY scanDate DESC")
    List<ScanHistory> getAllHistory();

    @Query("SELECT * FROM scan_history WHERE isSaved = 1 ORDER BY scanDate DESC")
    List<ScanHistory> getSavedProducts();

    @Insert
    void insert(ScanHistory scanHistory);

    @Update
    void update(ScanHistory scanHistory);

    @Delete
    void delete(ScanHistory scanHistory);

    @Query("SELECT * FROM scan_history WHERE barcode = :barcode LIMIT 1")
    ScanHistory getByBarcode(String barcode);
}
