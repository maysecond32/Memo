package com.example.memo

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val editTitle = findViewById<EditText>(R.id.editTitle)
        val editContent = findViewById<EditText>(R.id.editContent)
        val imageSave = findViewById<ImageButton>(R.id.imageSave)
        val imageDelete = findViewById<ImageButton>(R.id.imageDelete)

        var memo = intent.getSerializableExtra("memo") as Memo

        editTitle.setText(memo.title)
        editContent.setText(memo.content)

        imageSave.setOnClickListener {
            //제목과 내용이 둘 다 있을 때 main으로 돌아가기
            if (editTitle.text.toString().isNotEmpty() && editContent.text.toString().isNotEmpty()){
                memo.title = editTitle.text.toString()
                memo.content = editContent.text.toString()
                
                val returnIntent = Intent()
                returnIntent.putExtra("returnMemo", memo)
                
                //결과값 설정
                setResult(Activity.RESULT_OK, returnIntent)
                
                //액티비티 종료
                finish()
            }
            else if (editTitle.text.toString().isNotEmpty()){
                Toast.makeText(this, "내용이 비어있습니다.", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this, "제목이 비어있습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        imageDelete.setOnClickListener {
            setResult(Activity.RESULT_CANCELED, Intent().putExtra("returnMemo", memo))
            finish()
        }
    }
}