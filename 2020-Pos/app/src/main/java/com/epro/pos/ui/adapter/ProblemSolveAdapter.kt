package com.epro.pos.ui.adapter

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.epro.pos.R
import com.epro.pos.mvp.model.bean.ProblemSolveBean
import com.epro.pos.mvp.model.bean.ProblemsBean
import com.mike.baselib.view.recyclerview.ViewHolder
import com.mike.baselib.view.recyclerview.adapter.CommonAdapter

class ProblemSolveAdapter(mContext: Context, list: ArrayList<ProblemsBean>, layoutId: Int = R.layout.problem_solve_list) : CommonAdapter<ProblemsBean>(mContext, list, layoutId) {

    override fun bindData(holder: ViewHolder, data: ProblemsBean, position: Int) {
        holder.setText(R.id.tvProTitle,data.title)
        var flag = false
        val imgProExpand =holder.getView<ImageView>(R.id.imgProExpand)
        val tvProblem =holder.getView<TextView>(R.id.tvProblem)
        holder.setOnItemClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                if (!flag){
                    tvProblem.visibility= View.VISIBLE
                    imgProExpand.setImageResource(R.mipmap.pull_arrow)
                    tvProblem.text = data.answer
                    flag = true
                }else{
                    imgProExpand.setImageResource(R.mipmap.icon_gray_arrow)
                    tvProblem.visibility= View.GONE
                    flag = false
                }
            }
        })

    }
}