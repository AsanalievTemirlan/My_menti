package com.example.hw_03_m7.ui.medicines

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mymenti.data.local.model.MentiModel
import com.example.mymenti.databinding.ItemMentiBinding
import com.example.mymenti.ui.fragment.Menti.OnClick


class MentiAdapter(
    private val onLongClick: OnClick,
    private val onClick: OnClick
) : androidx.recyclerview.widget.ListAdapter<MentiModel, MentiAdapter.ViewHolder>(
    DiffCallback()
) {

    class ViewHolder(private val binding: ItemMentiBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: MentiModel) = with(binding) {
            tvGroup.text = "Группа: ${model.group}"
            tvName.setText("Имя: ${model.name}")
            tvMonth.setText("Месяц: ${model.month}")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemMentiBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnLongClickListener {
            onLongClick.onLongClick(getItem(position))
            true
        }
        holder.itemView.setOnClickListener {
            onClick.onClick(getItem(position))
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<MentiModel>() {
        override fun areItemsTheSame(oldItem: MentiModel, newItem: MentiModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MentiModel, newItem: MentiModel): Boolean {
            return oldItem == newItem
        }
    }
}