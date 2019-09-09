package com.stevechulsdev.roomtestproject.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.stevechulsdev.roomtestproject.entity.Person
import io.reactivex.Single

@Dao
interface PersonDao {

    @Query("SELECT * FROM person")
    fun getAllPerson(): List<Person>

    @Query("SELECT * FROM person")
    fun getAllPersonRx(): Single<List<Person>>

    @Query("DELETE FROM person")
    fun clearAll()

    // 해당 데이터를 추가합니다.
    @Insert(onConflict = OnConflictStrategy.REPLACE) // 이미 저장된 항목이 있을 경우, 덮어쓴다.
    fun insert(vararg persion: Person)

    // 해당 데이터를 업데이트 합니다.
    @Update
    fun update(vararg person: Person)

    // 해당 데이터를 삭제합니다.
    @Delete
    fun delete(vararg person: Person)
}