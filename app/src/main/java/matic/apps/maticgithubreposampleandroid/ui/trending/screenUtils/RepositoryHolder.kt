package matic.apps.maticgithubreposampleandroid.ui.trending.screenUtils

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import matic.apps.maticgithubreposampleandroid.R
import matic.apps.maticgithubreposampleandroid.models.Repo

class RepositoryHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var repoName: TextView = itemView.findViewById(R.id.repo_name)

    fun onBind(repo: Repo) {
        repoName.text = repo.fullName
    }
}
