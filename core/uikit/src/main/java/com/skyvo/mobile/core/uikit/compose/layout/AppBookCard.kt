package com.skyvo.mobile.core.uikit.compose.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.skyvo.mobile.core.uikit.R
import com.skyvo.mobile.core.uikit.compose.icon.AppIcon
import com.skyvo.mobile.core.uikit.compose.text.AppText
import com.skyvo.mobile.core.uikit.compose.widget.Book
import com.skyvo.mobile.core.uikit.theme.AppDimension
import com.skyvo.mobile.core.uikit.theme.AppTypography
import com.skyvo.mobile.core.uikit.theme.LocalAppColor

@Composable
fun AppBookCard(
    book: Book,
    levelColor: Color = LocalAppColor.current.colorSurfaceBase,
    navigateBookDetail: (Book) -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = AppDimension.default.booksCardItemHeight)
            .padding(
                top = AppDimension.default.dp8,
                start = AppDimension.default.dp8,
                end = AppDimension.default.dp8,
                bottom = AppDimension.default.dp4
            )
            .shadow(
                AppDimension.default.dp8,
                shape = RoundedCornerShape(size = AppDimension.default.dp6),
                clip = false
            )
            .background(
                color = LocalAppColor.current.colorBackgroundSelected,
                shape = RoundedCornerShape(size = AppDimension.default.dp6)
            )
            .clickable {
                navigateBookDetail(book)
            }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .size(
                        width = AppDimension.default.dp100,
                        height = AppDimension.default.booksCardItemHeight
                    )
                    .padding(
                        end = AppDimension.default.dp8
                    )
                    .clip(RoundedCornerShape(size = AppDimension.default.dp6))
                    .heightIn(max = AppDimension.default.booksCardItemHeight)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(book.imageUrl)
                        .error(R.drawable.ic_nav_book)
                        .build(),
                    contentDescription = "Book Image",
                    contentScale = ContentScale.FillHeight
                )
            }

            Column(
                modifier = Modifier.padding(top = AppDimension.default.dp8)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top,
                ) {
                    AppText(
                        modifier = Modifier.weight(1f),
                        text = book.title.orEmpty(),
                        style = AppTypography.default.bodyBold,
                        overflow = TextOverflow.Ellipsis
                    )
                    AppSpacer()
                    Box(
                        modifier = Modifier
                            .padding(
                                end = AppDimension.default.dp8
                            )
                            .size(AppDimension.default.dp40)
                            .shadow(AppDimension.default.dp4, shape = CircleShape, clip = false)
                            .clip(RoundedCornerShape(size = AppDimension.default.dp40))
                            .background(
                                color = levelColor,
                                shape = RoundedCornerShape(size = AppDimension.default.dp40)
                            )
                    ) {
                        AppText(
                            modifier = Modifier.align(Alignment.Center),
                            text = book.level.orEmpty(),
                            style = AppTypography.default.bodyBold,
                            color = LocalAppColor.current.colorBooksLevel
                        )
                    }
                }

                AppText(
                    modifier = Modifier.padding(
                        top = AppDimension.default.dp8
                    ),
                    text = book.genre.orEmpty(),
                    style = AppTypography.default.body,
                    color = LocalAppColor.current.colorTextSubtler,
                )

                book.min?.let {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                top = AppDimension.default.dp16
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AppIcon(
                                modifier = Modifier
                                    .padding(end = AppDimension.default.dp6)
                                    .size(AppDimension.default.dp20),
                                imageVector = ImageVector.vectorResource(R.drawable.ic_clock),
                                contentDescription = "Clock Icon",
                            )
                            AppText(
                                text = it,
                                style = AppTypography.default.body
                            )
                        }

                        AppSpacer()

                        if (book.isNew == true) {
                            AppIcon(
                                modifier = Modifier
                                    .padding(end = AppDimension.default.dp8)
                                    .size(AppDimension.default.dp24),
                                imageVector = ImageVector.vectorResource(R.drawable.ic_premium),
                                contentDescription = "Clock Icon",
                                tint = LocalAppColor.current.primary
                            )
                        }
                    }
                }
            }
        }
    }
}