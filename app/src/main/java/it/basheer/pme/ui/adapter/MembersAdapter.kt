package it.basheer.pme.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jude.easyrecyclerview.adapter.BaseViewHolder
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter
import it.basheer.pme.R
import it.basheer.pme.data.model.User
import it.basheer.pme.databinding.LayoutMemberItemBinding

class MembersAdapter(private val mContext: Context, private val onDelete: (user: User) -> Unit, private val onChoose: (user: User) -> Unit) :
    RecyclerArrayAdapter<User>(mContext) {

    override fun OnCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val binding =
            LayoutMemberItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(mContext, binding, onDelete, onChoose)
    }

    class MyViewHolder(
        private val mContext: Context,
        private val binding: LayoutMemberItemBinding,
        private val onDelete: (user: User) -> Unit,
        private val onChoose: (user: User) -> Unit
    ) : BaseViewHolder<User>(binding.root) {

        @SuppressLint("LongLogTag", "SetTextI18n")
        override fun setData(data: User) {
            binding.apply {
                memberItemTxtName.text = data.name
                if (!data.is_member) {
                    memberItemTxtName.text = "${memberItemTxtName.text} [${mContext.resources.getString(R.string.Mine)}]"
                }
                if (data.is_selected) {
                    memberItemTxtName.text = "${memberItemTxtName.text} [${mContext.resources.getString(R.string.default_user)}]"
                    memberItemBtnChoose.visibility = View.INVISIBLE
                } else {
                    memberItemBtnChoose.visibility = View.VISIBLE
                }

                memberItemTxtPoints.text = data.points.toString()

                memberItemBtnDelete.setOnClickListener {
                    onDelete(data)
                }

                memberItemBtnChoose.setOnClickListener {
                    onChoose(data)
                }

                if (!data.is_member || data.is_selected) {
                    memberItemBtnDelete.visibility = View.GONE
                } else {
                    memberItemBtnDelete.visibility = View.VISIBLE
                }
            }
        }
    }
}