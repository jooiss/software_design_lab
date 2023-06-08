package org.techtown.mydatarecycler
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

val DATABASE_NAME = "MyDB"
val TABLE_NAME = "Users"
val COL_NAME = "name"
val COL_AGE = "age"
val COL_ID = "id"

class DataBaseHandler(var context: Context) :SQLiteOpenHelper(context, DATABASE_NAME,null,1){

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE $TABLE_NAME ($COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,$COL_NAME VARCHAR(50),$COL_AGE INTEGER)"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

    fun insertData(user:User){
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(COL_NAME,user.name)
        cv.put(COL_AGE,user.age)

        val result = db.insert(TABLE_NAME,null,cv)

        if(result == (-1).toLong())
            Toast.makeText(context,"Failed",Toast.LENGTH_LONG).show()
        else
            Toast.makeText(context,"Success",Toast.LENGTH_LONG).show()
    }

    @SuppressLint("Range")
    fun readData():MutableList<User>{
        val list :MutableList<User> = ArrayList()
        val db = this.readableDatabase
        val query = "Select * from $TABLE_NAME"
        val result:Cursor = db.rawQuery(query,null)

        if(result.moveToFirst()){
            do {
                val user = User()
                user.id = result.getInt(result.getColumnIndex(COL_ID))
                user.name = result.getString(result.getColumnIndex(COL_NAME))
                user.age = result.getInt(result.getColumnIndex(COL_AGE))
                list.add(user)
            }while (result.moveToNext())
        }else
            Toast.makeText(context,"There is no data.",Toast.LENGTH_LONG).show()

        result.close()
        db.close()
        return list
    }

    fun deleteData(){
        val db = this.writableDatabase

        db.delete(TABLE_NAME, null,null)
        db.close()
    }

}