package matic.apps.maticgithubreposampleandroid.ui.trending

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import kotlinx.android.synthetic.main.view_trending_layout.*
import matic.apps.maticgithubreposampleandroid.R
import matic.apps.maticgithubreposampleandroid.models.Repo
import matic.apps.maticgithubreposampleandroid.ui.common.base.BaseView
import matic.apps.maticgithubreposampleandroid.ui.trending.screenUtils.RepositoriesAdapter
import matic.apps.maticgithubreposampleandroid.utils.networkingUtils.ApiResult
import android.view.Gravity
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar



/**
 * Shows the Trending screen.
 */
class TrendingView : BaseView() {

    lateinit var trendingViewModel: TrendingViewModel
    var reposAdapter: RepositoriesAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.view_trending_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setupViewModel ()
        initRecyclerViewListeners()
    }

    private fun setupViewModel() {
        // init the viewModel
        trendingViewModel =  ViewModelProviders
            .of(this)
            .get(TrendingViewModel::class.java)

        // observe on the result
        trendingViewModel.apiResult.observe(this, Observer {
            if (it.error != null) {
                setSnackBar(constraintLayout,it.error!!)
            } else {
                onListUpdated (it)
            }
        })

        // get the first list of items
        trendingViewModel.getGithubRepository(1)
    }

    private fun onListUpdated(newList: ApiResult<List<Repo>>?) {
        println("TrendingView.onListUpdated")

        onRecyclerViewData(newList)
    }

    private fun onRecyclerViewData(newList: ApiResult<List<Repo>>?, isClear: Boolean = false) {
        if (newList == null) {
            return
        }


        if (reposAdapter == null) {
            // to create the adapter
            reposAdapter = RepositoriesAdapter()
        } else {
            // to update the data (clear or add new data)
        }

        if (isClear) {
            // clear the old data
            reposAdapter!!.items = (newList!!.result as ArrayList<Repo>?)!!
        } else {
            reposAdapter!!.addItems (newList!!.result!!)
        }

        if (trending_recycler_view.adapter == null) {
            trending_recycler_view.iAdapter = reposAdapter
        }

    }

    private fun initRecyclerViewListeners() {
        trending_recycler_view!!.layoutManager = LinearLayoutManager(this@TrendingView.context , RecyclerView.VERTICAL , false)
        (trending_recycler_view!!.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false

        trending_recycler_view.setOnLoadMoreListener {
            println("TrendingView.initRecyclerViewListeners")
            trendingViewModel.getGithubRepository(isNextRequired = true)
        }

        trending_recycler_view.setOnRefreshListener {
            println("TrendingView.initRecyclerViewListeners")
        }
    }


    private fun setSnackBar(root: View, snackTitle: String) {
        val snackBar = Snackbar.make(root, snackTitle, Snackbar.LENGTH_SHORT)
        snackBar.show()
        val view = snackBar.view
        val message = view.findViewById(R.id.snackbar_text) as TextView
        message.gravity = Gravity.CENTER_HORIZONTAL
    }

}
