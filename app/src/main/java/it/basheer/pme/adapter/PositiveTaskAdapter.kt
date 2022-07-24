package it.basheer.pme.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.jude.easyrecyclerview.adapter.BaseViewHolder
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter
import it.basheer.pme.data.model.Task
import it.basheer.pme.databinding.LayoutTaskItemBinding

class PositiveTaskAdapter(private val mContext: Context): RecyclerArrayAdapter<Task>(mContext) {

    override fun OnCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val binding =
            LayoutTaskItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(mContext, binding)
    }

    class MyViewHolder(
        private val mContext: Context,
        private val binding: LayoutTaskItemBinding
    ) : BaseViewHolder<Task>(binding.root) {

        @SuppressLint("LongLogTag", "SetTextI18n")
        override fun setData(data: Task) {
            binding.apply {

            }
        }

    }

}