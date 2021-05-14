package com.example.memo

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    val helper = SqliteHelper(this, "memo", 1)
    val adapter = RecyclerAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fun makeDummyData(): MutableList<Memo>{
            val data: MutableList<Memo> = mutableListOf()

            for (no in 1..10){
                val title = "이것은 ${no}번째 제목"
                val content = "이것은 ${no}번째 내용"
                val date = System.currentTimeMillis()
                var memo = Memo(no, title, content, date)
                data.add(memo)
            }
            return data
        }

        adapter.helper = helper

        val recyclerMemo = findViewById<RecyclerView>(R.id.recyclerMemo)
        val buttonAdd = findViewById<Button>(R.id.buttonAdd)

        adapter.listData.addAll(helper.selectMemo())

        recyclerMemo.adapter = adapter
        recyclerMemo.layoutManager = LinearLayoutManager(this)

        buttonAdd.setOnClickListener {
            //Toast.makeText(this, "Add button", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, DetailActivity::class.java)

            var memo = Memo("", "", System.currentTimeMillis())
            intent.putExtra("memo", memo)
            startActivityForResult(intent,99)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val memo = data?.getSerializableExtra("returnMemo") as Memo

        if(resultCode == Activity.RESULT_OK){
            when(requestCode){
                99 -> {
                    memo.datetime = System.currentTimeMillis()
                    helper.insertMemo(memo)

                    adapter.listData.clear()
                    adapter.listData.addAll(helper.selectMemo())
                    adapter.notifyDataSetChanged()
                }
            }
        }

        else if(resultCode == Activity.RESULT_CANCELED){
            helper.deleteMemo(memo)

            adapter.listData.clear()
            adapter.listData.addAll(helper.selectMemo())
            adapter.notifyDataSetChanged()
        }
    }
}