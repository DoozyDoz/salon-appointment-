package com.example.loginthird.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.loginthird.R
import com.example.loginthird.models.UISession


class SessionListAdapter(private val sessions: List<UISession>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val PENDING_SESSION_VIEW_TYPE = 0
        const val COMPLETED_SESSION_VIEW_TYPE = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            PENDING_SESSION_VIEW_TYPE -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_appointment_pending, parent, false)
                PendingSessionViewHolder(view)
            }
            COMPLETED_SESSION_VIEW_TYPE -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_appointment_done, parent, false)
                CompletedSessionViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type: $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val session = sessions[position]

        when (holder.itemViewType) {
            PENDING_SESSION_VIEW_TYPE -> {
                val pendingHolder = holder as PendingSessionViewHolder
                pendingHolder.titleTextView.text = session.title
                pendingHolder.completeButton.setOnClickListener {
                    // Do something when complete button is clicked
                }
            }
            COMPLETED_SESSION_VIEW_TYPE -> {
                val completedHolder = holder as CompletedSessionViewHolder
                completedHolder.titleTextView.text = session.title
            }
        }
    }

    override fun getItemCount() = sessions.size

    override fun getItemViewType(position: Int): Int {
        return if (sessions[position].completed) COMPLETED_SESSION_VIEW_TYPE else PENDING_SESSION_VIEW_TYPE
    }

    class PendingSessionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.session_title_pending)
        val completeButton: Button = itemView.findViewById(R.id.session_complete_button)
    }

    class CompletedSessionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.session_title_completed)
    }
}
