package io.github.drumber.kitsune.ui.search

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.algolia.instantsearch.core.connection.ConnectionHandler
import com.algolia.instantsearch.helper.android.list.autoScrollToStart
import com.algolia.instantsearch.helper.android.searchbox.SearchBoxViewAppCompat
import com.google.android.material.navigation.NavigationBarView
import io.github.drumber.kitsune.GlideApp
import io.github.drumber.kitsune.R
import io.github.drumber.kitsune.data.model.media.MediaAdapter
import io.github.drumber.kitsune.data.model.media.MediaSearchResult
import io.github.drumber.kitsune.data.model.media.toMedia
import io.github.drumber.kitsune.databinding.FragmentSearchBinding
import io.github.drumber.kitsune.databinding.LayoutResourceLoadingBinding
import io.github.drumber.kitsune.ui.adapter.OnItemClickListener
import io.github.drumber.kitsune.ui.adapter.paging.MediaSearchPagingAdapter
import io.github.drumber.kitsune.ui.base.BaseCollectionFragment
import io.github.drumber.kitsune.util.algolia.connectView
import io.github.drumber.kitsune.util.extensions.navigateSafe
import io.github.drumber.kitsune.util.initPaddingWindowInsetsListener
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : BaseCollectionFragment(R.layout.fragment_search),
    OnItemClickListener<MediaSearchResult>,
    NavigationBarView.OnItemReselectedListener {

    private val binding: FragmentSearchBinding by viewBinding()

    private val viewModel: SearchViewModel by viewModel()

    override val recyclerView: RecyclerView
        get() = binding.rvMedia

    override val resourceLoadingBinding: LayoutResourceLoadingBinding
        get() = binding.layoutLoading

    private val connectionHandler = ConnectionHandler()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            root.initPaddingWindowInsetsListener(
                left = true,
                top = true,
                right = true,
                bottom = false
            )
        }

        val adapter = MediaSearchPagingAdapter(GlideApp.with(this), this)
        setRecyclerViewAdapter(adapter)
        recyclerView.autoScrollToStart(adapter)

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.searchResultSource.collectLatest {
                adapter.submitData(it)
            }
        }

        observeSearchBox()
    }

    private fun observeSearchBox() {
        viewModel.searchBox.observe(viewLifecycleOwner) { searchBox ->
            val searchBoxView = SearchBoxViewAppCompat(binding.searchView)
            connectionHandler += searchBox.connectView(searchBoxView)
        }
    }

    override fun onItemClick(item: MediaSearchResult) {
        val mediaAdapter = MediaAdapter.fromMedia(item.toMedia())
        val action = SearchFragmentDirections.actionSearchFragmentToDetailsFragment(mediaAdapter)
        findNavController().navigateSafe(R.id.search_fragment, action)
    }

    override fun onNavigationItemReselected(item: MenuItem) {
        binding.appBarLayout.setExpanded(true)
        if (recyclerView.canScrollVertically(-1)) {
            super.onNavigationItemReselected(item)
        } else {
            binding.searchView.requestFocus()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        connectionHandler.clear()
    }

}
