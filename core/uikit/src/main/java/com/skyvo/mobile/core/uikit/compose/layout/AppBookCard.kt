package com.skyvo.mobile.core.uikit.compose.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest
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
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(size = AppDimension.default.dp6))
                .heightIn(max = AppDimension.default.booksCardItemHeight)
        ) {
            AsyncImage(
                modifier = Modifier.size(AppDimension.default.dp100),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(book.imageUrl)
                    .error(com.skyvo.mobile.core.uikit.R.drawable.ic_nav_book)
                    .build(),
                contentDescription = "Book Image",
                contentScale = ContentScale.FillHeight
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = AppDimension.default.dp12,
                    top = AppDimension.default.dp10
                )
                .weight(2f)
        ) {
            AppText(
                text = book.title.orEmpty(),
                style = AppTypography.default.bodyXXlargeSemiBold
            )

            AppText(
                modifier = Modifier.padding(
                    top = AppDimension.default.dp8
                ),
                text = book.genre.orEmpty(),
                style = AppTypography.default.body,
                color = LocalAppColor.current.colorTextSubtler,
            )
        }

        Box(
            modifier = Modifier
                .padding(
                    end = AppDimension.default.dp16,
                    top = AppDimension.default.dp8
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
}