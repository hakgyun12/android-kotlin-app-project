package org.techtown.recipeapp.entities

import androidx.room.*
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import org.techtown.recipeapp.entities.converter.CategoryListConverter


@Entity(tableName = "Category")
data class Category(

    @PrimaryKey(autoGenerate = true)
    var id:Int,

    @ColumnInfo(name = "categorieitems")
    @Expose
    @SerializedName("categories")
    @TypeConverters(CategoryListConverter::class)
    val categorieitems: List<CategoryItems>? = null
)