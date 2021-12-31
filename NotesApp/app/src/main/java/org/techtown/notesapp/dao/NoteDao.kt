package org.techtown.notesapp.dao

import androidx.room.*
import org.techtown.notesapp.entities.Notes


@Dao
interface NoteDao {

    @get:Query("SELECT * FROM notes ORDER BY id DESC")
    val allNotes: List<Notes>

    /**
     * onConflict = OnConfictStrategy.REPLACE의 의미는 Insert(삽입)할 때
     * PrimaryKey가 겹치는 것이 있으면 덮어 쓴다는 의미이다. 우리는 PrimaryKey가 id로 이루어져 있으므로
     * id가 겹치는 걸 의미한다.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun ins(notes: Notes)

    @Delete
    fun deleteNote(notes: Notes)
}