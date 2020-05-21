package com.epro.pos.ui.fragment.settings;

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.epro.pos.R
import com.epro.pos.listener.FragmentChanageEvent
import com.epro.pos.listener.ProblemClickEvent
import com.epro.pos.mvp.contract.ProblemSolveContract
import com.epro.pos.mvp.model.bean.ProblemsBean
import com.epro.pos.mvp.presenter.ProblemSolvePresenter
import com.epro.pos.ui.adapter.ProblemSolveAdapter
import com.mike.baselib.fragment.BaseTitleBarCustomFragment
import kotlinx.android.synthetic.main.activity_problem_solve.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class ProblemSolveFragment : BaseTitleBarCustomFragment<ProblemSolveContract.View, ProblemSolvePresenter>(), ProblemSolveContract.View, View.OnClickListener {
    override fun onClick(v: View?) {
       when(v){
           getLeftView()->{
               var msg = FragmentChanageEvent()
               msg.postion = 13
               msg.level = 2
               msg.type = 4 //问题反馈 2级界面中第5个界面
               EventBus.getDefault().postSticky(msg)
           }
       }
    }

    override fun lazyLoad() {

    }

    companion object {
        const val TAG = "ProblemSolve"
        fun newInstance(str: String): ProblemSolveFragment {
            val args = Bundle()
            args.putString(TAG, str)
            val fragment = ProblemSolveFragment()
            fragment.arguments = args
            return fragment
        }

        fun newInstance(): ProblemSolveFragment {
            return newInstance("")
        }
    }

    override fun getPresenter(): ProblemSolvePresenter {
        return ProblemSolvePresenter()
    }

    override fun onProblemSolveSuccess() {
    }


    override fun layoutContentId(): Int {
        return R.layout.activity_problem_solve
    }

    override fun initData() {

    }

    override fun initView() {
        super.initView()
        initProblemData()
        setHaveBackView(true)
        getLeftTitleView().text = getString(R.string.system_settings)
        getTitleView().text =getString(R.string.problem_solve_title)
        val linearLayoutManager = LinearLayoutManager(activity!!)
        rcProblemDetail.layoutManager = linearLayoutManager
        rcProblemDetail.adapter = ProblemSolveAdapter(activity!!,getData())
    }

    private fun getData(): ArrayList<ProblemsBean> {
        when (position) {
            0 -> { return topQuestion}
            1 -> { return checkIn }
            2 -> { return accountRelated }
            3 -> { return accountPay }
            4 -> { return distributionRelated }
            5 -> { return invoiceRelated }
            6 -> { return afterSales }
            7 -> { return other }
        }
        return ArrayList()
    }

    override fun initListener() {
        getLeftView().setOnClickListener(this)
    }

    var topQuestion = ArrayList<ProblemsBean>()
    var checkIn = ArrayList<ProblemsBean>()
    var accountRelated = ArrayList<ProblemsBean>()
    var accountPay = ArrayList<ProblemsBean>()
    var distributionRelated = ArrayList<ProblemsBean>()
    var invoiceRelated = ArrayList<ProblemsBean>()
    var afterSales = ArrayList<ProblemsBean>()
    var other = ArrayList<ProblemsBean>()
    private fun initProblemData() {
        topQuestion.clear()
        checkIn.clear()
        accountRelated.clear()
        accountPay.clear()
        distributionRelated.clear()
        invoiceRelated.clear()
        afterSales.clear()
        other.clear()
        //热门问题
        var problem1 = ProblemsBean(getString(R.string.top_questions_title_1),getString(R.string.top_questions_answer_1))
        var problem2 = ProblemsBean(getString(R.string.top_questions_title_2),getString(R.string.top_questions_answer_2))
        var problem3 = ProblemsBean(getString(R.string.top_questions_title_3),getString(R.string.top_questions_answer_3))
        topQuestion.add(problem1)
        topQuestion.add(problem2)
        topQuestion.add(problem3)
        //入住流程
        var checkIn1 = ProblemsBean(getString(R.string.check_in_conditions_title_1),getString(R.string.check_in_conditions_answer_1))
        var checkIn2 = ProblemsBean(getString(R.string.check_in_conditions_title_2),getString(R.string.check_in_conditions_answer_2))
        var checkIn3 = ProblemsBean(getString(R.string.check_in_conditions_title_3),getString(R.string.check_in_conditions_answer_3))
        checkIn.add(checkIn1)
        checkIn.add(checkIn2)
        checkIn.add(checkIn3)
        //账户相关
        var accountRelated1 = ProblemsBean(getString(R.string.account_related_title_1),getString(R.string.account_related_answer_1))
        var accountRelated2 = ProblemsBean(getString(R.string.account_related_title_2),getString(R.string.account_related_answer_2))
        var accountRelated3 = ProblemsBean(getString(R.string.account_related_title_3),getString(R.string.account_related_answer_3))
        accountRelated.add(accountRelated1)
        accountRelated.add(accountRelated2)
        accountRelated.add(accountRelated3)
        //支付相关
        var accountPay1 = ProblemsBean(getString(R.string.account_pay_title_1),getString(R.string.account_pay_answer_1))
        var accountPay2 = ProblemsBean(getString(R.string.account_pay_title_2),getString(R.string.account_pay_answer_2))
        var accountPay3 = ProblemsBean(getString(R.string.account_pay_title_3),getString(R.string.account_pay_answer_3))
        accountPay.add(accountPay1)
        accountPay.add(accountPay2)
        accountPay.add(accountPay3)
        //配送相关
        var distributionRelated1 = ProblemsBean(getString(R.string.distribution_related_title_1),getString(R.string.distribution_related_answer_1))
        var distributionRelated2 = ProblemsBean(getString(R.string.distribution_related_title_2),getString(R.string.distribution_related_answer_2))
        var distributionRelated3 = ProblemsBean(getString(R.string.distribution_related_title_3),getString(R.string.distribution_related_answer_3))
        distributionRelated.add(distributionRelated1)
        distributionRelated.add(distributionRelated2)
        distributionRelated.add(distributionRelated3)
        //发票问题
        var invoiceRelated1 = ProblemsBean(getString(R.string.invoice_related_title_1),getString(R.string.invoice_related_answer_1))
        var invoiceRelated2 = ProblemsBean(getString(R.string.invoice_related_title_2),getString(R.string.invoice_related_answer_2))
        invoiceRelated.add(invoiceRelated1)
        invoiceRelated.add(invoiceRelated2)
        //售后服务
        var afterSales1 = ProblemsBean(getString(R.string.after_sales_related_title_1),getString(R.string.after_sales_related_answer_1))
        var afterSales2 = ProblemsBean(getString(R.string.after_sales_related_title_2),getString(R.string.after_sales_related_answer_2))
        afterSales.add(afterSales1)
        afterSales.add(afterSales2)
        //其他
        var other1 = ProblemsBean(getString(R.string.other_title_1),getString(R.string.other_answer_1))
        var other2 = ProblemsBean(getString(R.string.other_title_2),getString(R.string.other_answer_2))
        other.add(other1)
        other.add(other2)

    }

    var position:Int=-1000
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun onClickProblem(event:ProblemClickEvent){
       logTools.t("YB").d("position : "+event.position)
       position = event.position
    }
}


