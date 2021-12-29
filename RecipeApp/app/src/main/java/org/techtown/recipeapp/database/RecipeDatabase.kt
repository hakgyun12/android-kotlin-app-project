package org.techtown.recipeapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.codingwithme.recipeapp.entities.Recipes
import org.techtown.recipeapp.entities.Category
import org.techtown.recipeapp.entities.CategoryItems
import org.techtown.recipeapp.entities.Meal
import org.techtown.recipeapp.entities.converter.MealListConverter
import org.techtown.recipeapp.dao.RecipeDao
import org.techtown.recipeapp.entities.MealsItems
import org.techtown.recipeapp.entities.converter.CategoryListConverter


@Database(entities = [Recipes::class,CategoryItems::class,Category::class,Meal::class,MealsItems::class],version = 1,exportSchema = false)
@TypeConverters(CategoryListConverter::class,MealListConverter::class)
abstract class RecipeDatabase: RoomDatabase() {

    companion object{

        var recipesDatabase:RecipeDatabase? = null

        @Synchronized
        fun getDatabase(context: Context): RecipeDatabase{
            if (recipesDatabase == null){
                recipesDatabase = Room.databaseBuilder(
                    context,
                    RecipeDatabase::class.java,
                    "recipe.db"
                ).build()
            }
            return recipesDatabase!!
        }
    }

    abstract fun recipeDao():RecipeDao
}