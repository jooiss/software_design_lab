package org.techtown.mydatarecycler

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val db = DataBaseHandler(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        insertButton.setOnClickListener {
            insert()
        }
        readButton.setOnClickListener {
            read()
        }

        deleteButton.setOnClickListener{
            db.deleteData()
            resultText.text=""
        }
    }

    private fun insert(){
        if(nameText.text.toString().isNotEmpty() && ageText.text.toString().isNotEmpty()){
            val user = User(nameText.text.toString(),ageText.text.toString().toInt())
            db.insertData(user)
        }else{
            Toast.makeText(this,"Please Fill All Data's",Toast.LENGTH_LONG).show()
        }
    }

    private fun read(){
        val data : MutableList<User> = db.readData()
        if(data.isNotEmpty())
            resultText.append("Read\n")
        for(i in 0 until data.size){
            resultText.append("ID : ${data[i].id} NAME : ${data[i].name} AGE : ${data[i].age}\n")
        }
    }

}