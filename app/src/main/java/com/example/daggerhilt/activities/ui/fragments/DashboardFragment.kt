package com.example.daggerhilt.activities.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.daggerhilt.R
import com.example.daggerhilt.databinding.FragmentDashboardBinding

interface OnItemClickListener {
    fun onItemClicked(position: Int)
}

class DashboardFragment : Fragment() {
    lateinit var dashboardBinding: FragmentDashboardBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardBinding = FragmentDashboardBinding.inflate(inflater, container, false)

        return dashboardBinding.root

    }


    override fun onAttach(context: Context) {
        super.onAttach(context)

    }

    override fun onDetach() {
        super.onDetach()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val list = listOf(
            DashboardRVModal("Global Market", R.drawable.global_market),
            DashboardRVModal("Global Market", R.drawable.global_market),
            DashboardRVModal("Global Market", R.drawable.global_market)
        )

        val layoutManager = GridLayoutManager(activity, 2)
        dashboardBinding.dashboardRV.layoutManager = layoutManager

        val courseRVAdapter = CourseRVAdapter(list, object : OnItemClickListener {
            override fun onItemClicked(position: Int) {
                if (position == 0) {
                    findNavController().navigate(R.id.action_dashboardFragment_to_globalMarketFragment)
                }
            }
        })
        dashboardBinding.dashboardRV.adapter = courseRVAdapter
    }

}


data class DashboardRVModal(
    var courseName: String,
    var courseImg: Int
)

class CourseRVAdapter(
    private val courseList: List<DashboardRVModal>,
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<CourseRVAdapter.CourseViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CourseRVAdapter.CourseViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.dashboard_rv_item,
            parent, false
        )
        return CourseViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CourseRVAdapter.CourseViewHolder, position: Int) {
        holder.courseNameTV.text = courseList[position].courseName
        holder.courseIV.setImageResource(courseList[position].courseImg)
        holder.courseIV.setOnClickListener {
            onItemClickListener.onItemClicked(position)
        }
    }

    override fun getItemCount(): Int {
        return courseList.size
    }

    class CourseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val courseNameTV: TextView = itemView.findViewById(R.id.idTVCourse)
        val courseIV: ImageView = itemView.findViewById(R.id.idIVCourse)

    }
}

