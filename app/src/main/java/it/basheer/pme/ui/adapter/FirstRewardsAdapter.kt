package it.basheer.pme.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.jude.easyrecyclerview.adapter.BaseViewHolder
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter
import it.basheer.pme.R
import it.basheer.pme.data.model.Reward
import it.basheer.pme.data.model.Task
import it.basheer.pme.databinding.LayoutFirstPositiveTaskItemBinding
import it.basheer.pme.databinding.LayoutFirstRewardItemBinding

class FirstRewardsAdapter(private val mContext: Context) : RecyclerArrayAdapter<Reward>(mContext) {

    override fun OnCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val binding =
            LayoutFirstRewardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(mContext, binding)
    }

    class MyViewHolder(
        private val mContext: Context,
        private val binding: LayoutFirstRewardItemBinding
    ) : BaseViewHolder<Reward>(binding.root) {

        @SuppressLint("LongLogTag", "SetTextI18n")
        override fun setData(data: Reward) {
            binding.apply {
                taskItemTxtTaskName.text = data.name
                taskItemTxtTaskPoints.text = "${data.points} ${mContext.resources.getString(R.string.pt)}"
            }
        }

    }

}