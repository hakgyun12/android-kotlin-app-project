package org.techtown.notesapp.dao

import androidx.room.*
import org.techtown.notesapp.entities.Notes


@Dao
interface NoteDao {

    @Query("SELECT * FROM notes ORDER BY id DESC")
    suspend fun getAllNotes(): List<Notes>

    @Query("SELECT * FROM notes WHERE id =:id")
    suspend fun getSpecificNote(id: Int): Notes

    /**
     * onConflict = OnConfictStrategy.REPLACE의 의미는 Insert(삽입)할 때
     * PrimaryKey가 겹치는 것이 있으면 덮어 쓴다는 의미이다. 우리는 PrimaryKey가 id로 이루어져 있으므로
     * id가 겹치는 걸 의미한다.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotes(notes: Notes)

    /**
     * suspend란 비동기 실행을 위한 중단 지점의 의미이다.
     */
    @Delete
    suspend fun deleteNote(notes: Notes)

    @Query("DELETE FROM notes WHERE id =:id")
    suspend fun deleteSpecificNote(id:Int)

    @Update
    suspend fun updateNote(note:Notes)
}