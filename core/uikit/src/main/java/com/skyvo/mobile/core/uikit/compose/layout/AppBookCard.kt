package com.skyvo.mobile.core.uikit.compose.layout

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.skyvo.mobile.core.uikit.R
import com.skyvo.mobile.core.uikit.compose.icon.AppIcon
import com.skyvo.mobile.core.uikit.compose.text.AppText
import com.skyvo.mobile.core.uikit.compose.widget.Book
import com.skyvo.mobile.core.uikit.theme.AppDimension
import com.skyvo.mobile.core.uikit.theme.AppTypography
import com.skyvo.mobile.core.uikit.theme.LocalAppColor
import com.skyvo.mobile.core.uikit.util.GetLevelIcon

@Composable
fun AppBookCard(
    book: Book,
    navigateBookDetail: (Book) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = LocalAppColor.current.background,
                shape = RoundedCornerShape(AppDimension.default.dp10)
            )
            .border(
                width = 1.dp,
                color = LocalAppColor.current.colorBorder,
                shape = RoundedCornerShape(AppDimension.default.dp10)
            )
            .clip(RoundedCornerShape(AppDimension.default.dp10))
            .clickable {
                navigateBookDetail(book)
            }
    ) {
        Box(
            modifier = Modifier
                .height(AppDimension.default.bookImageHeight)
                .clip(RoundedCornerShape(AppDimension.default.dp6))
        ) {
            AsyncImage(
                modifier = Modifier.fillMaxWidth()
                    .height(AppDimension.default.bookImageHeight),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(book.imageUrl)
                    .error(R.drawable.img_placeholder)
                    .build(),
                contentDescription = "Book Image",
                contentScale = ContentScale.Crop
            )

            if (book.isNew == true) {
                Box(
                    modifier = Modifier
                        .padding(all = AppDimension.default.dp8)
                        .size(AppDimension.default.dp32)
                        .align(Alignment.TopEnd)
                        .background(
                            color = LocalAppColor.current.primary,
                            shape = CircleShape
                        )
                ) {
                    AppIcon(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(AppDimension.default.dp20),
                        imageVector = ImageVector.vectorResource(R.drawable.ic_premium),
                        contentDescription = "Clock Icon",
                        tint = LocalAppColor.current.colorTextMain
                    )
                }
            }
        }

        AppText(
            modifier = Modifier
                .padding(
                    top = AppDimension.default.dp8,
                    start = AppDimension.default.dp16,
                    end = AppDimension.default.dp16
                ),
            text = book.title.orEmpty(),
            style = AppTypography.default.bodyBold,
            overflow = TextOverflow.Ellipsis,
            maxLines = 2
        )

        AppText(
            modifier = Modifier.padding(
                top = AppDimension.default.dp4,
                start = AppDimension.default.dp16,
                end = AppDimension.default.dp16
            ),
            text = book.genre.orEmpty(),
            style = AppTypography.default.body,
            color = LocalAppColor.current.colorTextSubtler,
            maxLines = 1
        )

        book.min?.let {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = AppDimension.default.dp16),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row (
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AppIcon(
                        modifier = Modifier
                            .padding(end = AppDimension.default.dp6)
                            .size(AppDimension.default.dp12),
                        imageVector = ImageVector.vectorResource(R.drawable.ic_clock),
                        tint = LocalAppColor.current.colorTextSubtler,
                        contentDescription = "Clock Icon",
                    )
                    AppText(
                        text = it,
                        style = AppTypography.default.bodySmall,
                        color = LocalAppColor.current.colorTextSubtler
                    )
                }
                book.level?.let {
                    Row (
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            modifier = Modifier
                                .padding(end = AppDimension.default.dp6)
                                .size(AppDimension.default.dp12),
                            imageVector = ImageVector.vectorResource(
                                GetLevelIcon(it)
                            ),
                            contentDescription = it,
                        )

                        AppText(
                            text = it,
                            style = AppTypography.default.bodySmall,
                            color = LocalAppColor.current.primary
                        )
                    }
                }
            }
        }
    }
}