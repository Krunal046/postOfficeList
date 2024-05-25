package com.ersiver.test_krunal.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ersiver.test_krunal.model.PostOfficeModel
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.ersiver.test_krunal.databinding.ItemAddressBinding
import com.ersiver.test_krunal.ui.MainActivity
import com.ersiver.test_krunal.ui.PostDetailsActivity

class AddressAdapter(val mainActivity: MainActivity) : ListAdapter<PostOfficeModel, AddressAdapter.AddressViewHolder>(AddressDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemAddressBinding.inflate(inflater, parent, false)
        return AddressViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            val item = getItem(position)
            val intent = Intent(mainActivity, PostDetailsActivity::class.java).apply {
                putExtra("name", item.Name)
                putExtra("description", item.Description)
                putExtra("pinCode", item.PINCode)
                putExtra("branchType", item.BranchType)
                putExtra("deliveryStatus", item.DeliveryStatus)
                putExtra("taluk", item.Taluk)
                putExtra("circle", item.Circle)
                putExtra("district", item.District)
                putExtra("division", item.Division)
                putExtra("region", item.Region)
                putExtra("state", item.State)
                putExtra("country", item.Country)
            }
            mainActivity.startActivity(intent)
        }

    }

    inner class AddressViewHolder(private val binding: ItemAddressBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(address: PostOfficeModel) {
            binding.nameTextView.text = address.Name
            val description = address.Description
            val district = address.District
            val state = address.State
            val pinCode = address.PINCode
            if (description == "" || description.isNullOrEmpty()) {
                binding.addressTextView.text = "${pinCode}, ${district}, ${state}"
            } else {
                binding.addressTextView.text = "${address.Description},${pinCode}, ${address.District}, ${address.State}"
            }
        }
    }

    private class AddressDiffCallback : DiffUtil.ItemCallback<PostOfficeModel>() {
        override fun areItemsTheSame(oldItem: PostOfficeModel, newItem: PostOfficeModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: PostOfficeModel, newItem: PostOfficeModel): Boolean {
            return oldItem == newItem
        }
    }
}
