package it.basheer.pme.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.jude.easyrecyclerview.adapter.BaseViewHolder
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter
import it.basheer.pme.R
import it.basheer.pme.data.model.Task
import it.basheer.pme.databinding.LayoutFirstPositiveTaskItemBinding

class FirstPositiveTaskAdapter(private val mContext: Context) : RecyclerArrayAdapter<Task>(mContext) {

    override fun OnCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val binding =
            LayoutFirstPositiveTaskItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(mContext, binding)
    }

    class MyViewHolder(
        private val mContext: Context,
        private val binding: LayoutFirstPositiveTaskItemBinding
    ) : BaseViewHolder<Task>(binding.root) {

        @SuppressLint("LongLogTag", "SetTextI18n")
        override fun setData(data: Task) {
            binding.apply {
                taskItemTxtTaskName.text = data.name
                taskItemTxtTaskPoints.text = "${data.points} ${context.resources.getString(R.string.pt)}"
                taskItemTxtTaskPeriod.text = data.period
                if (data.count == 1) {
                    taskItemTxtTaskCount.text = "${data.count} ${context.resources.getString(R.string.once)}"
                } else {
                    taskItemTxtTaskCount.text = "${data.count} ${context.resources.getString(R.string.times)}"
                }

                taskItemTxtTaskDuration.text = "${data.duration} ${context.resources.getString(R.string.min)}"
            }
        }

    }

}