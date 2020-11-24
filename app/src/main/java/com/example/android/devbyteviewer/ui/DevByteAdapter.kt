package com.example.android.devbyteviewer.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.devbyteviewer.databinding.DevbyteItemBinding
import com.example.android.devbyteviewer.domain.Video

/**
 * RecyclerView Adapter for setting up data binding on the items in the list.
 */
class DevByteAdapter(private val callback: VideoClick) :
        ListAdapter<Video, DevByteAdapter.DevByteViewHolder>(VideoClickDiffCallback()) {

    /**
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DevByteViewHolder.from(parent)

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
     * position.
     */
    override fun onBindViewHolder(holder: DevByteViewHolder, position: Int) {
        val videoItem = getItem(position)
        holder.bind(videoItem, callback)
    }

    class DevByteViewHolder private constructor(private val viewDataBinding: DevbyteItemBinding) :
            RecyclerView.ViewHolder(viewDataBinding.root) {
        fun bind(item: Video, videoCallback: VideoClick) {
            viewDataBinding.video = item
            viewDataBinding.videoCallback = videoCallback
            viewDataBinding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): DevByteViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = DevbyteItemBinding.inflate(
                        layoutInflater, parent, false)
                return DevByteViewHolder(binding)
            }
        }
    }

}

/**
 * Click listener for Videos. By giving the block a name it helps a reader understand what it does.
 *
 */
class VideoClick(val block: (Video) -> Unit) {
    /**
     * Called when a video is clicked
     *
     * @param video the video that was clicked
     */
    fun onClick(video: Video) = block(video)
}


class VideoClickDiffCallback : DiffUtil.ItemCallback<Video>() {
    override fun areItemsTheSame(oldItem: Video, newItem: Video) = oldItem.url == newItem.url

    override fun areContentsTheSame(oldItem: Video, newItem: Video) = oldItem == newItem
}
