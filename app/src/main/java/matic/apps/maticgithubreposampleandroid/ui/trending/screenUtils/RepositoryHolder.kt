package matic.apps.maticgithubreposampleandroid.ui.trending.screenUtils

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import matic.apps.maticgithubreposampleandroid.R
import matic.apps.maticgithubreposampleandroid.models.Repo

class RepositoryHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var repoName: TextView = itemView.findViewById(R.id.repo_name)
    private var repoDescription: TextView = itemView.findViewById(R.id.repo_description)
    private var repoOwnerName: TextView = itemView.findViewById(R.id.repo_owner_name)
    private var ownerImage: ImageView = itemView.findViewById(R.id.owner_image)

    fun onBind(repo: Repo) {
        repoName.text = repo.fullName
        repoDescription.text = repo.description
        repoOwnerName.text = repo.owner.login

        Glide.with(ownerImage).load(repo.owner.avatarUrl).into(ownerImage)

    }
}
