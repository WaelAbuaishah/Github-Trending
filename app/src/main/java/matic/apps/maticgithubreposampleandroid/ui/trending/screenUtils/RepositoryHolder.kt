package matic.apps.maticgithubreposampleandroid.ui.trending.screenUtils

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import matic.apps.maticgithubreposampleandroid.R
import matic.apps.maticgithubreposampleandroid.models.Repo
import matic.apps.maticgithubreposampleandroid.utils.generalUtils.NumberUtils

class RepositoryHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var repoName: TextView = itemView.findViewById(R.id.repo_name)
    private var repoDescription: TextView = itemView.findViewById(R.id.repo_description)
    private var repoOwnerName: TextView = itemView.findViewById(R.id.repo_owner_name)
    private var ownerImage: ImageView = itemView.findViewById(R.id.owner_image)
    private var numberOfStars: TextView = itemView.findViewById(R.id.number_of_stars)

    fun onBind(repo: Repo) {
        repoName.text = repo.fullName
        repoDescription.text = repo.description
        repoOwnerName.text = repo.owner.login
        numberOfStars.text = NumberUtils.formatNumberChars(repo.stars.toLong())

        Glide.with(ownerImage).load(repo.owner.avatarUrl).into(ownerImage)

    }
}
