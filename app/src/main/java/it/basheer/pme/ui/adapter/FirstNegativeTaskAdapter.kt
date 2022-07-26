package it.basheer.pme.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jude.easyrecyclerview.adapter.BaseViewHolder
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter
import it.basheer.pme.R
import it.basheer.pme.data.model.Task
import it.basheer.pme.databinding.LayoutFirstNegativeTaskItemBinding

class FirstNegativeTaskAdapter(private val mContext: Context) : RecyclerArrayAdapter<Task>(mContext) {

    override fun OnCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val binding =
            LayoutFirstNegativeTaskItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(mContext, binding)
    }

    class MyViewHolder(
        private val mContext: Context,
        private val binding: LayoutFirstNegativeTaskItemBinding
    ) : BaseViewHolder<Task>(binding.root) {

        @SuppressLint("LongLogTag", "SetTextI18n")
        override fun setData(data: Task) {
            binding.apply {
                taskItemTxtTaskName.text = data.name
                taskItemTxtTaskPoints.text = "${data.points} ${context.resources.getString(R.string.pt)}"

                if (data.count!! == 0) {
                    taskItemTxtTaskCount.visibility = View.GONE
                } else {
                    taskItemTxtTaskCount.visibility = View.VISIBLE
                    if (data.count == 1) {
                        taskItemTxtTaskCount.text = "${data.count} ${context.resources.getString(R.string.once)}"
                    } else {
                        taskItemTxtTaskCount.text = "${data.count} ${context.resources.getString(R.string.times)}"
                    }
                }

                if (data.duration!! == 0) {
                    taskItemTxtTaskDuration.visibility = View.GONE
                } else {
                    taskItemTxtTaskDuration.visibility = View.VISIBLE
                    taskItemTxtTaskDuration.text = "${data.duration} ${context.resources.getString(R.string.min)}"
                }
            }
        }

    }

}