package it.basheer.pme.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.view.LayoutInflater
import android.view.ViewGroup
import com.jude.easyrecyclerview.adapter.BaseViewHolder
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter
import it.basheer.pme.R
import it.basheer.pme.data.model.ActiveTask
import it.basheer.pme.databinding.LayoutNegativeTaskItemBinding

class NegativeTaskAdapter(private val mContext: Context, private val onClickListener: (activeTask: ActiveTask) -> Unit) : RecyclerArrayAdapter<ActiveTask>(mContext) {

    override fun OnCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val binding =
            LayoutNegativeTaskItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(mContext, binding, onClickListener)
    }

    class MyViewHolder(
        private val mContext: Context,
        private val binding: LayoutNegativeTaskItemBinding,
        private val onClickListener: (activeTask: ActiveTask) -> Unit
    ) : BaseViewHolder<ActiveTask>(binding.root) {

        @SuppressLint("LongLogTag", "SetTextI18n")
        override fun setData(data: ActiveTask) {
            binding.apply {
                taskItemTxtTaskName.text = data.name
                taskItemTxtTaskPoints.text = "${data.points} ${context.resources.getString(R.string.pt)}"
                when (data.status) {
                    0 -> {
                        taskItemTxtTaskStatus.text = mContext.resources.getString(R.string.free)
                        when (context.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
                            Configuration.UI_MODE_NIGHT_YES -> {

                            }
                            else -> {
                                taskItemLayout.background = mContext.resources.getDrawable(R.drawable.card_bordered_bg_error99, mContext.theme)
                            }
                        }
                    }
                    else -> {
                        taskItemTxtTaskStatus.text = mContext.resources.getString(R.string.down)
                        when (context.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
                            Configuration.UI_MODE_NIGHT_YES -> {

                            }
                            else -> {
                                taskItemLayout.background = mContext.resources.getDrawable(R.drawable.card_bordered_bg_error70, mContext.theme)
                            }
                        }
                    }
                }

                if (data.duration!! != 0) {
                    taskItemTxtTaskDuration.text = "${data.duration} ${mContext.resources.getString(R.string.min)}"
                } else {
                    taskItemTxtTaskDuration.text = ""
                }

                taskItemLayout.setOnClickListener {
                    onClickListener(data)
                }
            }
        }

    }

}