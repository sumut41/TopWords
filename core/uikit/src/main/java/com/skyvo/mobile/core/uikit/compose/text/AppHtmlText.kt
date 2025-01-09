package com.skyvo.mobile.core.uikit.compose.text

import android.annotation.SuppressLint
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.text.style.ForegroundColorSpan
import android.text.style.URLSpan
import android.text.util.Linkify
import android.view.View
import android.widget.TextView
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.res.ResourcesCompat
import androidx.core.text.HtmlCompat
import androidx.core.text.getSpans
import androidx.core.text.util.LinkifyCompat
import com.skyvo.mobile.core.uikit.theme.LocalAppColor
import com.skyvo.mobile.core.uikit.R
import java.util.regex.Pattern

@SuppressLint("RestrictedApi")
@Composable
fun AppHtmlText(
    modifier: Modifier = Modifier,
    htmlText: String,
    textColor: Color = LocalAppColor.current.colorTextMain,
    linkTextColor: Color = LocalAppColor.current.colorTextMain,
    textAlign: TextAlign? = null,
    maxLines: Int = Int.MAX_VALUE,
    fontSize: Float = 14f,
    isContainLink: Boolean = true
) {

    AndroidView(
        modifier = modifier.fillMaxWidth(),
        factory = { context ->
            TextView(context).apply {
                this.typeface = ResourcesCompat.getFont(context, R.font.inter_regular)
                this.setLinkTextColor(linkTextColor.hashCode())
                this.setTextColor(textColor.hashCode())
                this.autoLinkMask = Linkify.WEB_URLS
                this.textSize = fontSize
                this.maxLines = maxLines
                if (isContainLink) {
                    this@apply.linksClickable = true
                    this@apply.isClickable = true
                    this@apply.movementMethod = LinkMovementMethod.getInstance()
                } else {
                    this@apply.linksClickable = false
                    this@apply.isClickable = false
                }
                this.textAlignment = when (textAlign) {
                    TextAlign.Center -> {
                        View.TEXT_ALIGNMENT_CENTER
                    }

                    TextAlign.End -> {
                        View.TEXT_ALIGNMENT_TEXT_END
                    }

                    else -> {
                        View.TEXT_ALIGNMENT_TEXT_START
                    }
                }
                text = this.fromHtml(htmlText, linkTextColor)
            }
        },
        update = {
            it.text = it.fromHtml(htmlText, linkTextColor)
        }
    )
}

private fun TextView.fromHtml(htmlText: String, linkTextColor: Color): CharSequence {
    val htmlSequence = HtmlCompat.fromHtml(htmlText, HtmlCompat.FROM_HTML_MODE_LEGACY)
    val spannableText = SpannableStringBuilder(htmlSequence)

    LinkifyCompat.addLinks(spannableText, Linkify.WEB_URLS)
    spannableText.getSpans<URLSpan>(0, spannableText.length).forEach {
        spannableText.setSpan(
            URLSpan(it.url),
            spannableText.getSpanStart(it),
            spannableText.getSpanEnd(it),
            0
        )
        val spanColor =
            ForegroundColorSpan(linkTextColor.hashCode())
        spannableText.setSpan(
            spanColor,
            spannableText.getSpanStart(it),
            spannableText.getSpanEnd(it),
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        this.movementMethod = LinkMovementMethod.getInstance()
    }

    val phoneNumberPattern = Pattern.compile("[(]?[0-9]{1,4}[)]?[\\s0-9]{7,10}")
    LinkifyCompat.addLinks(this, phoneNumberPattern, "tel:")
    return spannableText
}