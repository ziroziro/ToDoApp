package com.ziro.todoapp.adapter

import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.ListFragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.ziro.todoapp.R
import com.ziro.todoapp.data.Priority
import com.ziro.todoapp.data.ToDoData
import com.ziro.todoapp.fragment.ListFragmentDirections
import kotlinx.android.synthetic.main.row_layout.view.*

class ListAdapter : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    var dataList = emptyList<ToDoData>()

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            MyViewHolder(
                    LayoutInflater.from(parent.context)
                            .inflate(R.layout.row_layout, parent, false)
            )

    override fun getItemCount() = dataList.size

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.itemView.tv_title.text = dataList[position].title
            holder.itemView.tv_description.text = dataList[position].description
            holder.itemView.row_background.setOnClickListener{
                val action = ListFragmentDirections.actionListFragmentToUpdateFragment(dataList[position])
                holder.itemView.findNavController().navigate(action)
            }

            val priority = dataList[position].priority
            when (priority) {
                Priority.HIGH -> holder.itemView.priority_indicator.setCardBackgroundColor(
                        ContextCompat.getColor(holder.itemView.context, R.color.red)
                )

                Priority.MEDIUM -> holder.itemView.priority_indicator.setCardBackgroundColor(
                        ContextCompat.getColor(holder.itemView.context, R.color.yellow)
                )

                Priority.LOW -> holder.itemView.priority_indicator.setCardBackgroundColor(
                        ContextCompat.getColor(holder.itemView.context, R.color.green)
                )
            }
        }

        fun setData(toDoData: List<ToDoData>) {
            this.dataList = toDoData
            notifyDataSetChanged()
        }

}