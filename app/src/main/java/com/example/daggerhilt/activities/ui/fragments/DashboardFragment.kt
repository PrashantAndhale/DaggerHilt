package com.example.daggerhilt.activities.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.daggerhilt.R
import com.example.daggerhilt.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {
    lateinit var dashboardBinding: FragmentDashboardBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardBinding = FragmentDashboardBinding.inflate(inflater, container, false)
        return dashboardBinding.root

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

        val courseRVAdapter = CourseRVAdapter(list, requireActivity())
        dashboardBinding.dashboardRV.adapter = courseRVAdapter
    }

}

data class DashboardRVModal(
    var courseName: String,
    var courseImg: Int
)

class CourseRVAdapter(

    private val courseList: List<DashboardRVModal>,
    private val context: Context
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
        holder.courseNameTV.text = courseList.get(position).courseName
        holder.courseIV.setImageResource(courseList.get(position).courseImg)
    }

    override fun getItemCount(): Int {
        return courseList.size
    }

    class CourseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val courseNameTV: TextView = itemView.findViewById(R.id.idTVCourse)
        val courseIV: ImageView = itemView.findViewById(R.id.idIVCourse)
    }
}

