package it.basheer.pme.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.view.LayoutInflater
import android.view.ViewGroup
import com.jude.easyrecyclerview.adapter.BaseViewHolder
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter
import it.basheer.pme.R
import it.basheer.pme.data.model.ActiveTasks
import it.basheer.pme.databinding.LayoutPositiveTaskItemBinding

class PositiveTaskAdapter(private val mContext: Context) : RecyclerArrayAdapter<ActiveTasks>(mContext) {

    override fun OnCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val binding =
            LayoutPositiveTaskItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(mContext, binding)
    }

    class MyViewHolder(
        private val mContext: Context,
        private val binding: LayoutPositiveTaskItemBinding
    ) : BaseViewHolder<ActiveTasks>(binding.root) {

        @SuppressLint("LongLogTag", "SetTextI18n")
        override fun setData(data: ActiveTasks) {
            binding.apply {
                taskItemTxtTaskName.text = data.name
                taskItemTxtTaskPoints.text = "${data.points} ${context.resources.getString(R.string.pt)}"
                taskItemTxtTaskPeriod.text = data.period
                taskItemTxtTaskCount.text = "${data.completed} ${mContext.resources.getString(R.string.slash)} ${data.count}"

                when (data.status) {
                    0 -> {
                        taskItemTxtTaskStatus.text = mContext.resources.getString(R.string.todo)
                        when (context.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
                            Configuration.UI_MODE_NIGHT_YES -> {

                            }
                            else -> {
                                taskItemLayout.background = mContext.resources.getDrawable(R.drawable.card_bordered_bg_primary99, mContext.theme)
                            }
                        }
                    }
                    1 -> {
                        taskItemTxtTaskStatus.text = mContext.resources.getString(R.string.in_progress)
                        when (context.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
                            Configuration.UI_MODE_NIGHT_YES -> {

                            }
                            else -> {
                                taskItemLayout.background = mContext.resources.getDrawable(R.drawable.card_bordered_bg_primary90, mContext.theme)
                            }
                        }
                    }
                    else -> {
                        taskItemTxtTaskStatus.text = mContext.resources.getString(R.string.done)
                        when (context.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
                            Configuration.UI_MODE_NIGHT_YES -> {

                            }
                            else -> {
                                taskItemLayout.background = mContext.resources.getDrawable(R.drawable.card_bordered_bg_primary70, mContext.theme)
                            }
                        }
                    }
                }

                taskItemTxtTaskDuration.text = "${data.duration} ${mContext.resources.getString(R.string.min)}"
            }
        }

    }

}