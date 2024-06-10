package org.jxxy.debug.theme.viewHolder

import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.view.View
import org.jxxy.debug.common.util.MoreViewAlpha0to1
import org.jxxy.debug.common.util.MoreViewAlpha1to0
import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder2
import org.jxxy.debug.theme.bean.AIOcr
import org.jxxy.debug.theme.databinding.ItemThemeOcrBinding

class AiOcrViewHolder(view: ItemThemeOcrBinding) :
    MultipleViewHolder2<ItemThemeOcrBinding, AIOcr>(view) {
    var count = 0

    init {

    }

    override fun setHolder(entity: AIOcr) {
//        Glide.with(view.bgView).load(entity.bg).into(view.bgView)
//        view.bgView.load(entity.bg)
//        view.ocrTv.text = entity.ocr
//        view.rePhotoTv.text = entity.rePhoto

        val spannableStringOcr = SpannableString(entity.ocr)
        spannableStringOcr.setSpan(
            RelativeSizeSpan(2f),
            0,
            3,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannableStringOcr.setSpan(
            ForegroundColorSpan(org.jxxy.debug.corekit.util.ResourceUtil.getColor(org.jxxy.debug.corekit.R.color.color_yellow)),
            0,
            3,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        view.ocrTv.text = spannableStringOcr
        val spannableStringPhoto = SpannableString(entity.rePhoto)
        spannableStringPhoto.setSpan(
            RelativeSizeSpan(2f),
            0,
            4,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannableStringPhoto.setSpan(
            ForegroundColorSpan(Color.parseColor("#DDBAAC")),
            0,
            4,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        view.rePhotoTv.text = spannableStringPhoto
        val spannableString = SpannableString("你使用知了识别4709行文字")
        spannableString.setSpan(RelativeSizeSpan(1.5f), 7, 11, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(
            ForegroundColorSpan(org.jxxy.debug.corekit.util.ResourceUtil.getColor(org.jxxy.debug.corekit.R.color.color_yellow)),
            7,
            11,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        view.ocrCntTv.text = spannableString
        val spannableString2 = SpannableString("你使用知了识别81张图片")
        spannableString2.setSpan(RelativeSizeSpan(1.5f), 7, 9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString2.setSpan(
            ForegroundColorSpan(Color.parseColor("#DDBAAC")),
            7,
            9,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        view.repCntTv.text = spannableString2

    }

    override fun setHolder(entity: AIOcr, payload: Any) {
        if (payload as? Boolean ?: false) {
            view.run {
                val list = listOf<List<View>>(
                    listOf(aTv),
                    listOf(ocrCntTv),
                    listOf(repCntTv),
                    listOf(ocrTv, rePhotoTv)
                )
                MoreViewAlpha0to1(list, 300)
            }
        } else {
            view.run {
                val list = listOf<List<View>>(
                    listOf(aTv),
                    listOf(ocrCntTv),
                    listOf(repCntTv),
                    listOf(ocrTv, rePhotoTv)
                )
                MoreViewAlpha1to0(list.reversed(), 10)
            }
        }
    }
}