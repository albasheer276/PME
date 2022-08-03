package it.basheer.pme.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.view.*
import android.widget.PopupMenu
import android.widget.Toast
import com.jude.easyrecyclerview.adapter.BaseViewHolder
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter
import it.basheer.pme.R
import it.basheer.pme.base.BaseApp
import it.basheer.pme.data.model.Reward
import it.basheer.pme.data.model.RewardLog
import it.basheer.pme.data.model.TaskLog
import it.basheer.pme.data.model.User
import it.basheer.pme.databinding.LayoutMemberItemBinding
import it.basheer.pme.databinding.LayoutProgressItemBinding
import it.basheer.pme.databinding.LayoutRewardItemBinding
import it.basheer.pme.databinding.LayoutRewardLogItemBinding
import it.basheer.pme.util.getAlterDate

class RewardLogAdapter(private val mContext: Context) :
    RecyclerArrayAdapter<RewardLog>(mContext) {

    override fun OnCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val binding =
            LayoutRewardLogItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(mContext, binding)
    }

    class MyViewHolder(
        private val mContext: Context,
        private val binding: LayoutRewardLogItemBinding
    ) : BaseViewHolder<RewardLog>(binding.root) {

        @SuppressLint("LongLogTag", "SetTextI18n")
        override fun setData(data: RewardLog) {
            binding.apply {
                progressTxtDate.text = getAlterDate(mContext, data.date)
            }
        }
    }
}