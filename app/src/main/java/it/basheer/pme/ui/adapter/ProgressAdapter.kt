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
import it.basheer.pme.data.model.TaskLog
import it.basheer.pme.data.model.User
import it.basheer.pme.databinding.LayoutMemberItemBinding
import it.basheer.pme.databinding.LayoutProgressItemBinding
import it.basheer.pme.databinding.LayoutRewardItemBinding
import it.basheer.pme.util.getAlterDate

class ProgressAdapter(private val mContext: Context, private val onDelete: (taskLog: TaskLog) -> Unit) :
    RecyclerArrayAdapter<TaskLog>(mContext) {

    override fun OnCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val binding =
            LayoutProgressItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(mContext, binding, onDelete)
    }

    class MyViewHolder(
        private val mContext: Context,
        private val binding: LayoutProgressItemBinding,
        private val onDelete: (taskLog: TaskLog) -> Unit
    ) : BaseViewHolder<TaskLog>(binding.root) {

        @SuppressLint("LongLogTag", "SetTextI18n")
        override fun setData(data: TaskLog) {
            binding.apply {
                progressTxtDate.text = getAlterDate(mContext, data.date)

                root.setOnLongClickListener {
                    val popupMenu = PopupMenu(mContext, binding.root)
                    popupMenu.menuInflater.inflate(R.menu.progress_menu, popupMenu.menu)
                    popupMenu.setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.deleteItem -> {
                                onDelete(data)
                            }
                        }
                        true
                    }
                    popupMenu.show()
                    return@setOnLongClickListener false
                }
            }
        }
    }
}