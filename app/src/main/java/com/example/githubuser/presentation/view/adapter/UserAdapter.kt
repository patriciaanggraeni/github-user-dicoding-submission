package com.example.githubuser.presentation.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

class UserAdapter<T: Any, VB: ViewBinding>(
    private val binding: (inflater: LayoutInflater, parent: ViewGroup, attachToParent: Boolean) -> VB,
    private val itemClick: (T) -> Unit,
    private val bindViewHolder: (binding: VB, item: T, position: Int) -> Unit,
): RecyclerView.Adapter<UserAdapter<T, VB>.ViewHolder>() {

    private val userList: MutableList<T> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewBinding = binding.invoke(inflater, parent, false)
        return ViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = userList[position]
        holder.bind(user)
    }

    override fun getItemCount() = userList.size

    fun loadUsers(users: List<T>) {
        userList.clear()
        userList.addAll(users)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: VB): RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = layoutPosition
                if (position != RecyclerView.NO_POSITION) {
                    itemClick(userList[position])
                }
            }
        }

        fun bind(item: T) {
            bindViewHolder(binding, item, layoutPosition)
        }
    }
}