package matic.apps.maticgithubreposampleandroid.ui.trending.screenUtils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import matic.apps.maticgithubreposampleandroid.R
import matic.apps.maticgithubreposampleandroid.models.Repo
import matic.apps.maticgithubreposampleandroid.utils.networkingUtils.ApiResult

class RepositoriesAdapter : RecyclerView.Adapter<RepositoryHolder>() {
    var items: ArrayList<Repo> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.repo_holder_layout, parent, false)
        return RepositoryHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RepositoryHolder, position: Int) {
        holder.onBind(items[position])
    }

    fun addItems(result: List<Repo>) {
        items.addAll(result)
        notifyItemRangeInserted((items.size - result.size)-1, result.size - 1)
    }

}