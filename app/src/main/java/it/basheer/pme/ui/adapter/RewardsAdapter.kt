package it.basheer.pme.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import com.jude.easyrecyclerview.adapter.BaseViewHolder
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter
import it.basheer.pme.R
import it.basheer.pme.base.BaseApp
import it.basheer.pme.data.model.ActiveTask
import it.basheer.pme.data.model.Reward
import it.basheer.pme.data.model.Task
import it.basheer.pme.databinding.LayoutFirstPositiveTaskItemBinding
import it.basheer.pme.databinding.LayoutFirstRewardItemBinding
import it.basheer.pme.databinding.LayoutRewardItemBinding

class RewardsAdapter(private val mContext: Context, private val onClickListener: (reward: Reward) -> Unit) : RecyclerArrayAdapter<Reward>(mContext) {

    override fun OnCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val binding =
            LayoutRewardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(mContext, binding, onClickListener)
    }

    class MyViewHolder(
        private val mContext: Context,
        private val binding: LayoutRewardItemBinding,
        private val onClickListener: (reward: Reward) -> Unit
    ) : BaseViewHolder<Reward>(binding.root) {

        @SuppressLint("LongLogTag", "SetTextI18n")
        override fun setData(data: Reward) {
            binding.apply {
                taskItemTxtTaskName.text = data.name
                taskItemTxtTaskPoints.text = "${data.points} ${mContext.resources.getString(R.string.pt)}"


                if (BaseApp.getInstance().getUser().value?.points!! >= data.points) {

                    when (mContext.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
                        Configuration.UI_MODE_NIGHT_YES -> {

                        }
                        else -> {
                            rewardItemLayout.background = mContext.resources.getDrawable(R.drawable.card_bordered_bg_tertiary95, mContext.theme)
                        }
                    }

                    rewardItemLayout.setOnClickListener {
                        onClickListener(data)
                    }
                } else {

                    when (mContext.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
                        Configuration.UI_MODE_NIGHT_YES -> {

                        }
                        else -> {
                            rewardItemLayout.background = mContext.resources.getDrawable(R.drawable.card_bordered_bg_neutral80, mContext.theme)
                        }
                    }

                    rewardItemLayout.setOnClickListener {
                        Toast.makeText(mContext, mContext.getString(R.string.not_enough_points), Toast.LENGTH_SHORT).show()
                    }
                }


            }
        }

    }

}