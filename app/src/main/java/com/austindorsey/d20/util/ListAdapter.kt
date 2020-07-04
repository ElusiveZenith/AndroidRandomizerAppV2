package com.austindorsey.d20.util

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.austindorsey.d20.R


class ListAdapter : RecyclerView.Adapter<ListAdapter.ViewHolder>() {
    var data = mutableListOf<String>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.custom_dice_string_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[holder.adapterPosition]
        holder.textField.text = item
        //Listener to update stored die side when changed
        holder.textField.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                data[holder.adapterPosition] = s.toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        holder.deleteButton.setOnClickListener {
            removeItem(holder.adapterPosition)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textField: TextView = itemView.findViewById(R.id.list_item_text)
        val deleteButton: ImageButton = itemView.findViewById(R.id.list_item_delete_button)
    }

    fun addItem() {
        data.add("")
        notifyItemInserted(data.size)
    }

    private fun removeItem(position: Int) {
        data.removeAt(position)
        notifyItemRemoved(position)
    }
}