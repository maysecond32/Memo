package com.example.memo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat

class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.Holder>() {
    var helper: SqliteHelper? = null
    var listData = mutableListOf<Memo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val memo = listData[position]
        holder.setMemo(memo)
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var mMemo: Memo? = null
        val textTitle = itemView.findViewById<TextView>(R.id.textTitle)
        val textContent = itemView.findViewById<TextView>(R.id.textContent)
        val textDatetime = itemView.findViewById<TextView>(R.id.textDatetime)

        init {
            val buttonDelete = itemView.findViewById<Button>(R.id.buttonDelete)
            buttonDelete.setOnClickListener {
                helper?.deleteMemo(mMemo!!) //DB에서 삭제
                listData.remove(mMemo)  //listData에서 삭제
                notifyDataSetChanged()  //어댑터 갱신
            }
        }

        fun setMemo(memo: Memo){
            textTitle.text = memo.title
            textContent.text = memo.content
            val sdf = SimpleDateFormat("yyyy/MM/dd hh:mm")
            textDatetime.text = "${sdf.format(memo.datetime)}"

            this.mMemo = memo

            itemView.setOnClickListener {
                Toast.makeText(itemView?.context, "클릭된 아이템 = ${textTitle.text.toString()}", Toast.LENGTH_SHORT).show()
            }
        }
    }

}