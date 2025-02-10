package com.skyvo.mobile.core.base.util

import androidx.fragment.app.Fragment
import com.google.android.play.core.review.ReviewManagerFactory

object InAppReviewManager {

    fun showFeedbackDialog(fragment: Fragment) {
        val reviewManager = ReviewManagerFactory.create(fragment.requireActivity())
        reviewManager.requestReviewFlow().addOnCompleteListener {

            if (it.isSuccessful) {
                val fakeFlow = reviewManager.launchReviewFlow(fragment.requireActivity(), it.result)

                fakeFlow.addOnCompleteListener {
                    println("Review Fake Review Dialog Gösterildi.")
                }
                reviewManager.launchReviewFlow(fragment.requireActivity(), it.result)
            } else {
                println("InAppReviewManager Error requesting review flow  ${it.exception}")
            }
        }.addOnFailureListener {
            println("Review Flow Başlatılamadı: ${it.message}")
        }
    }
    // internal teste açtığımızda buradaki Feedback kısmını test et !!
}