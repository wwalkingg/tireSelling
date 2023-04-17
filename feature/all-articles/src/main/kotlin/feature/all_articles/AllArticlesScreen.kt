package feature.all_articles

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.router.stack.push
import com.example.android.core.model.Article
import core.common.NavConfig
import core.common.navigation
import core.component_base.LoadUIState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllArticlesScreen(modifier: Modifier = Modifier, component: AllArticlesComponent) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
//    loadAllArticlesUIStateFlow
    var selectedTab by remember {
        mutableStateOf("全部")
    }

    val loadAllArticles by component.modelState.loadAllArticlesUIStateFlow.collectAsState()
    val tabs = if (loadAllArticles is LoadUIState.Loaded) {
        (loadAllArticles as LoadUIState.Loaded).data.map { it.key }
    } else listOf("全部")

    Scaffold(modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection), topBar = {
        TopBar(selectedTab, tabs = tabs, onTabChange = { selectedTab = it })
    }) { padding ->
        LazyColumn(
            Modifier.padding(padding)
        ) {
            when (loadAllArticles) {
                is LoadUIState.Error -> {}
                is LoadUIState.Loaded -> {
                    items(
                        items = (loadAllArticles as LoadUIState.Loaded<Map<String, List<Article>>>).data[selectedTab]!!,
                        key = { article: Article -> article.id }
                    ) { article ->
                        Text(
                            text = article.title,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    navigation.push(
                                        NavConfig.ArticleDetail(
                                            article.id,
                                            article.title
                                        )
                                    )
                                }
                                .padding(horizontal = 10.dp, vertical = 5.dp),
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }

                LoadUIState.Loading -> {}
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    selectedTab: String = "全部",
    tabs: List<String>,
    onTabChange: (tab: String) -> Unit
) {
    val selectedTabIndex = tabs.indexOf(selectedTab)
    Column {
        MediumTopAppBar(
            title = { Text(text = "全部技术文章") },
            navigationIcon = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = null)
                }
            },
        )
        val indicator = @Composable { tabPositions: List<TabPosition> ->
            FancyAnimatedIndicator(tabPositions = tabPositions, selectedTabIndex = selectedTabIndex)
        }
        ScrollableTabRow(modifier = Modifier,selectedTabIndex = selectedTabIndex, indicator = indicator, edgePadding = 10.dp) {
            tabs.forEach { s ->
                Tab(modifier = Modifier.padding(vertical = 10.dp), selected = selectedTab == s, onClick = { onTabChange(s) }) {
                    Text(
                        text = s,
                        modifier = Modifier
                    )
                }
            }
        }
    }
}

@Composable
fun FancyIndicator(color: Color, modifier: Modifier = Modifier) {
    // Draws a rounded rectangular with border around the Tab, with a 5.dp padding from the edges
    // Color is passed in as a parameter [color]
    Box(
        modifier
            .padding(5.dp)
            .fillMaxSize()
            .border(BorderStroke(2.dp, color), RoundedCornerShape(5.dp))
    )
}

@Composable
fun FancyAnimatedIndicator(tabPositions: List<TabPosition>, selectedTabIndex: Int) {
    val colors = listOf(
        MaterialTheme.colorScheme.primary,
        MaterialTheme.colorScheme.secondary,
        MaterialTheme.colorScheme.tertiary,
    )
    val transition = updateTransition(selectedTabIndex)
    val indicatorStart by transition.animateDp(
        transitionSpec = {
            // Handle directionality here, if we are moving to the right, we
            // want the right side of the indicator to move faster, if we are
            // moving to the left, we want the left side to move faster.
            if (initialState < targetState) {
                spring(dampingRatio = 1f, stiffness = 50f)
            } else {
                spring(dampingRatio = 1f, stiffness = 300f)
            }
        }
    ) {
        tabPositions[it].left
    }

    val indicatorEnd by transition.animateDp(
        transitionSpec = {
            // Handle directionality here, if we are moving to the right, we
            // want the right side of the indicator to move faster, if we are
            // moving to the left, we want the left side to move faster.
            if (initialState < targetState) {
                spring(dampingRatio = 1f, stiffness = 1000f)
            } else {
                spring(dampingRatio = 1f, stiffness = 50f)
            }
        }
    ) {
        tabPositions[it].right
    }

    val indicatorColor by transition.animateColor {
        colors[it % colors.size]
    }

    FancyIndicator(
        // Pass the current color to the indicator
        indicatorColor,
        modifier = Modifier
            // Fill up the entire TabRow, and place the indicator at the start
            .fillMaxSize()
            .wrapContentSize(align = Alignment.BottomStart)
            // Apply an offset from the start to correctly position the indicator around the tab
            .offset(x = indicatorStart)
            // Make the width of the indicator follow the animated width as we move between tabs
            .width(indicatorEnd - indicatorStart)
    )
}