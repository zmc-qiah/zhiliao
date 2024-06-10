package org.jxxy.debug.test.fragment.adapter

import android.content.Intent
import android.graphics.Color
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.jxxy.debug.corekit.recyclerview.MultipleTypeAdapter
import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder
import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder2
import org.jxxy.debug.corekit.util.*
import org.jxxy.debug.test.R
import org.jxxy.debug.test.databinding.*
import org.jxxy.debug.test.fragment.activity.AnswerQuestionFinishActivity
import org.jxxy.debug.test.fragment.bean.Question
import org.jxxy.debug.test.fragment.bean.QuestionPart
import org.jxxy.debug.test.fragment.fragment.QuestionFragment
import org.jxxy.debug.test.fragment.viewModel.QuestionViewModel
import java.lang.StringBuilder

class QuestionRvAdapter(
    val questions: ArrayList<Question>,
    val type: Int,
    val index: Int,
    val fragmentManager: FragmentManager,
    val viewModel: QuestionViewModel,
    val choose: String? = null
) : MultipleTypeAdapter() {
    val options: ArrayList<ItemQuestionOptionBinding> = ArrayList()
    val imageOptions: ArrayList<ItemQuestionImageOptionBinding> = ArrayList()
    val nowChoose: ArrayList<Int> = ArrayList()
    val trueAnswer: ArrayList<Int> = ArrayList()
    var trueFillAnswer: String = ""
    val questionPartList = ArrayList<QuestionPart>()
    var showType: Int = -1
    lateinit var putView: ItemQuestionPutBinding
    lateinit var finishView: ItemQuestionFinishBinding
    lateinit var fillPart: ItemQuestionContentFllBinding

    companion object {
        const val QUESTION_CONTENT = 1
        const val QUESTION_OPTION = 2
        const val QUESTION_PUT = 3
        const val QUESTION_FINISH = 4
        const val QUESTION_IMAGE = 5
        const val QUESTION_FILL = 6
        const val QUESTION_IMAGE_OPTION = 7
        const val QUESTION_BOSS = 8

        const val SINGLE_SELECTION = 1
        const val MULTIPLE_SELECTION = 2
        const val JUDGMENT_QUESTION = 3
        const val FILL_QUESTION = 4

        const val TAKE = 1
        const val RECITE = 2
    }

    init {
        if(viewModel.answerType == AnswerRvAdapter.SPECIAL) {
            viewModel.changeBossWay(0)
        }
        questions[index].run {
            if (type != FILL_QUESTION) {
                questionPartList.add(QuestionPart(1, questionText))
            } else {
                questionPartList.add(QuestionPart(6, questionText))
            }
            if (questionShow == 2) {
                questionPartList.add(QuestionPart(5, questionPhoto!!))
            }
            if (questionShow == 3) {
                questionPartList.add(QuestionPart(7, optionA, 0))
                questionPartList.add(QuestionPart(7, optionB, 1))
                optionC?.let {
                    questionPartList.add(QuestionPart(7, it, 2))
                }
                optionD?.let {
                    questionPartList.add(QuestionPart(7, it, 3))
                }
            } else {
                if (type != FILL_QUESTION) {
                    questionPartList.add(QuestionPart(2, optionA, 0))
                    questionPartList.add(QuestionPart(2, optionB, 1))
                    optionC?.let {
                        questionPartList.add(QuestionPart(2, it, 2))
                    }
                    optionD?.let {
                        questionPartList.add(QuestionPart(2, it, 3))
                    }
                }
            }
            questionPartList.add(QuestionPart(3, ""))
            questionPartList.add(
                QuestionPart(
                    4, if (parse != "" && parse != null) {
                        parse
                    } else {
                        "暂无解析"
                    }
                )
            )
            questionPartList.add(QuestionPart(1,"",6))
            questionPartList.add(QuestionPart(1,"",6))
            questionPartList.add(QuestionPart(1,"",6))
//            for (i in 0 until result.size) {
//                trueAnswer.add(result[i])
//            }
            if (type != FILL_QUESTION) {
                for (option in answer.toCharArray()) {
                    trueAnswer.add(option.toInt() - 'A'.toInt())
                }
            } else {
                trueFillAnswer = answer
            }
            showType = questionShow
        }
        add(questionPartList)

    }

    override fun createViewHolder(
        viewType: Int,
        inflater: LayoutInflater,
        parent: ViewGroup
    ): RecyclerView.ViewHolder? = when (viewType) {
        QUESTION_CONTENT -> QuestionContentViewHolder(
            ItemQuestionContentBinding.inflate(
                inflater,
                parent,
                false
            )
        )
        QUESTION_OPTION -> QuestionOptionViewHolder(
            ItemQuestionOptionBinding.inflate(
                inflater,
                parent,
                false
            )
        )
        QUESTION_PUT -> QuestionPutViewHolder(
            ItemQuestionPutBinding.inflate(
                inflater,
                parent,
                false
            )
        )
        QUESTION_FINISH -> QuestionFinishViewHolder(
            ItemQuestionFinishBinding.inflate(inflater, parent, false)
        )

        QUESTION_IMAGE -> QuestionImageViewHolder(
            ItemQuestionImageBinding.inflate(inflater, parent, false)
        )
        QUESTION_FILL -> {
            QuestionFillViewHolder(
                ItemQuestionContentFllBinding.inflate(
                    inflater,
                    parent,
                    false
                )
            )
        }
        QUESTION_IMAGE_OPTION -> {
            QuestionImageOptionViewHolder(
                ItemQuestionImageOptionBinding.inflate(
                    inflater,
                    parent,
                    false
                )
            )
        }
        else -> null
    }

    inner class QuestionContentViewHolder(view: ItemQuestionContentBinding) :
        MultipleViewHolder2<ItemQuestionContentBinding, QuestionPart>(view) {
        override fun setHolder(entity: QuestionPart) {
            if(entity.opstion == 6){
                view.root.hide()
            }else{
                view.root.show()
            }
            view.questionTypeTv.text = when (type) {
                SINGLE_SELECTION -> "单选题"
                MULTIPLE_SELECTION -> "多选题"
                JUDGMENT_QUESTION -> "判断题"
                FILL_QUESTION -> "填空题"
                else -> "暂无"
            }
//            view.questionWayTv.text = when (type) {
//                SINGLE_SELECTION -> "选出一个正确选项"
//                MULTIPLE_SELECTION -> "选择至少一个正确的选项"
//                JUDGMENT_QUESTION -> "判断下列题目正误"
//                FILL_QUESTION -> "将正确答案填入空缺处"
//                else -> "暂无"
//            }
            view.questionContentTv.text = entity.content
        }
    }

    inner class QuestionOptionViewHolder(val view: ItemQuestionOptionBinding) :
        MultipleViewHolder<QuestionPart>(view.root) {
        var id = -1
        override fun setHolder(entity: QuestionPart) {
            id = entity.opstion
            view.optionBtn.text = (id + 'A'.toInt()).toChar().toString()
            if (!(view in options)) {
                options.add(view)
            }
            when (type) {
                SINGLE_SELECTION -> {
                    view.root.singleClick {
                        if (nowChoose.size == 0) {
                            nowChoose.add(id)
                            view.optionBtn.setBackgroundResource(R.drawable.button_option_work)
                            view.optionBtn.setTextColor(Color.parseColor("#FFFFFF"))
                        }
                        if (nowChoose[0] != id) {
                            view.optionBtn.setBackgroundResource(R.drawable.button_option_work)
                            view.optionBtn.setTextColor(Color.parseColor("#FFFFFF"))
                            options[nowChoose[0]].optionBtn.setBackgroundResource(R.drawable.button_option_normal)
                            options[nowChoose[0]].optionBtn.setTextColor(Color.parseColor("#0a0a0a"))
                            nowChoose[0] = id
                        }
//                        putView.root.show()
                        var isRight = -1
                        if (nowChoose[0] == trueAnswer[0]) {
                            if(viewModel.answerType == AnswerRvAdapter.SPECIAL) {
                                viewModel.changeBossWay(1)
                                viewModel.trueMedia.start()
                            }
                            if (showType != 3) {
                                options[nowChoose[0]].optionBtn.setBackgroundResource(R.drawable.button_option_true)
                                options[nowChoose[0]].optionBtn.setTextColor(Color.parseColor("#FFFFFF"))
                                options[nowChoose[0]].optionBtn.text = "√"
                                options[nowChoose[0]].optionTv.setTextColor(Color.parseColor("#399DFF"))

                            } else {
                                imageOptions[nowChoose[0]].optionTv.setBackgroundResource(R.drawable.button_option_true)
                                imageOptions[nowChoose[0]].optionTv.setTextColor(Color.parseColor("#FFFFFF"))
                                imageOptions[nowChoose[0]].optionTv.text = "√"
                            }
                            viewModel.rightCount.run {
                                val count = value ?: 0
                                value = count + 1
                            }
                            finishView.trueOptionTv.text
                            isRight = 1
                            if (viewModel.answerType != AnswerRvAdapter.COLLECTION && viewModel.answerType != AnswerRvAdapter.WRONG)
                                viewModel.itemState[index] = AnswerSheetRvAdapter.ANSWER_SHEET_TRUE
                            if (viewModel.answerType == AnswerRvAdapter.WRONG || viewModel.answerType == AnswerRvAdapter.COLLECTION) {
                                viewModel.postAnswer(
                                    when (viewModel.answerType) {
                                        AnswerRvAdapter.WRONG -> 1
                                        AnswerRvAdapter.COLLECTION -> 2
                                        else -> 0
                                    },
                                    questions[index].id
                                )
                            }
                        } else {
                            if(viewModel.answerType == AnswerRvAdapter.SPECIAL){
                                viewModel.errorMedia.start()
                                viewModel.changeBossWay(2)
                            }
                            if (showType != 3) {
                                options[nowChoose[0]].optionBtn.setBackgroundResource(R.drawable.button_option_wrong)
                                options[nowChoose[0]].optionBtn.setTextColor(Color.parseColor("#FFFFFF"))
                                options[nowChoose[0]].optionBtn.text = "X"
                                options[nowChoose[0]].optionTv.setTextColor(Color.parseColor("#FC656A"))
                                options[trueAnswer[0]].optionBtn.setBackgroundResource(R.drawable.button_option_true)
                                options[trueAnswer[0]].optionBtn.setTextColor(Color.parseColor("#FFFFFF"))
                                options[trueAnswer[0]].optionBtn.text = "√"
                                options[trueAnswer[0]].optionTv.setTextColor(Color.parseColor("#399DFF"))
                            } else {
                                imageOptions[nowChoose[0]].optionTv.setBackgroundResource(R.drawable.button_option_wrong)
                                imageOptions[nowChoose[0]].optionTv.setTextColor(Color.parseColor("#FFFFFF"))
                                imageOptions[nowChoose[0]].optionTv.text = "√"
                                imageOptions[trueAnswer[0]].optionTv.setBackgroundResource(R.drawable.button_option_true)
                                imageOptions[trueAnswer[0]].optionTv.setTextColor(Color.parseColor("#FFFFFF"))
                                imageOptions[trueAnswer[0]].optionTv.text = "X"
                            }
                            viewModel.wrongCount.run {
                                val count = value ?: 0
                                value = count + 1
                            }
                            viewModel.addMistake(questions[viewModel.index.value!!].id)
                            isRight = 2
                            if (viewModel.answerType != AnswerRvAdapter.COLLECTION && viewModel.answerType != AnswerRvAdapter.WRONG)
                                viewModel.itemState[index] = AnswerSheetRvAdapter.ANSWER_SHEET_WRONG
                        }
//                        it.hide()
                        val chooseString = "您选择 " + (nowChoose[0] + 'A'.toInt()).toChar()
                        val string =
                            SpannableString(chooseString)
                        viewModel.chooseMap[viewModel.index.value!!] = chooseString
                        string.setSpan(
                            when (isRight) {
                                1 -> ForegroundColorSpan(Color.parseColor("#1EABFA"))
                                2 -> ForegroundColorSpan(Color.parseColor("#FC656A"))
                                else -> ""
                            },
                            4,
                            string.length,
                            SpannableString.SPAN_INCLUSIVE_EXCLUSIVE
                        )
                        finishView.chooseOptionTv.text = string
                        finishView.root.show()
                        when (viewModel.answerType) {
                            AnswerRvAdapter.WRONG, AnswerRvAdapter.COLLECTION -> finishView.nextQuestionBtn.hide()
                            else -> {}
                        }
                        if (showType != 3) {
                            for (i in 0 until options.size) {
                                options[i].root.isEnabled = false
                            }
                        } else {
                            for (i in 0 until imageOptions.size) {
                                imageOptions[i].root.isEnabled = false
                            }
                        }
                    }
                }
                MULTIPLE_SELECTION -> {
                    view.root.singleClick {
                        if (!(id in nowChoose)) {
                            view.optionBtn.setBackgroundResource(R.drawable.button_multiple_option_work)
                            view.optionBtn.setTextColor(Color.parseColor("#ffffff"))
                            nowChoose.add(id)
                        } else {
                            view.optionBtn.setBackgroundResource(R.drawable.button_multiple_option_normal)
                            view.optionBtn.setTextColor(Color.parseColor("#0a0a0a"))
                            nowChoose.remove(id)
                        }
                        putView.root.show()
                    }
                    view.optionBtn.setBackgroundResource(R.drawable.button_multiple_option_normal)
                }
                JUDGMENT_QUESTION -> {
                    view.root.singleClick {
                        if (nowChoose.size == 0) {
                            nowChoose.add(id)
                            view.optionBtn.setBackgroundResource(R.drawable.button_option_work)
                            view.optionBtn.setTextColor(Color.parseColor("#FFFFFF"))
                        }
                        if (nowChoose[0] != id) {
                            view.optionBtn.setBackgroundResource(R.drawable.button_option_work)
                            view.optionBtn.setTextColor(Color.parseColor("#ffffff"))
                            options[nowChoose[0]].optionBtn.setBackgroundResource(R.drawable.button_option_normal)
                            options[nowChoose[0]].optionBtn.setTextColor(Color.parseColor("#0a0a0a"))
                            nowChoose[0] = id
                        }
//                        putView.root.show()
                        var isRight = -1
                        if (nowChoose[0] == trueAnswer[0]) {
                            if(viewModel.answerType == AnswerRvAdapter.SPECIAL) {
                                viewModel.changeBossWay(1)
                                viewModel.trueMedia.start()
                            }
                            options[nowChoose[0]].optionBtn.setBackgroundResource(R.drawable.button_option_true)
                            options[nowChoose[0]].optionBtn.setTextColor(Color.parseColor("#ffffff"))
                            options[nowChoose[0]].optionBtn.text = "√"
                            options[nowChoose[0]].optionTv.setTextColor(Color.parseColor("#1EABFA"))
                            viewModel.rightCount.run {
                                val count = value ?: 0
                                value = count + 1
                            }
                            isRight = 1
                            if (viewModel.answerType != AnswerRvAdapter.COLLECTION && viewModel.answerType != AnswerRvAdapter.WRONG)
                                viewModel.itemState[index] = AnswerSheetRvAdapter.ANSWER_SHEET_TRUE
                            if (viewModel.answerType == AnswerRvAdapter.WRONG || viewModel.answerType == AnswerRvAdapter.COLLECTION) {
                                viewModel.postAnswer(
                                    when (viewModel.answerType) {
                                        AnswerRvAdapter.WRONG -> 1
                                        AnswerRvAdapter.COLLECTION -> 2
                                        else -> 0
                                    },
                                    questions[index].id
                                )
                            }
                        } else {
                            if(viewModel.answerType == AnswerRvAdapter.SPECIAL){
                                viewModel.errorMedia.start()
                                viewModel.changeBossWay(2)
                            }
                            options[nowChoose[0]].optionBtn.setBackgroundResource(R.drawable.button_option_wrong)
                            options[nowChoose[0]].optionBtn.setTextColor(Color.parseColor("#ffffff"))
                            options[nowChoose[0]].optionBtn.text = "X"
                            options[nowChoose[0]].optionTv.setTextColor(Color.parseColor("#FC656A"))
                            options[trueAnswer[0]].optionBtn.setBackgroundResource(R.drawable.button_option_true)
                            options[trueAnswer[0]].optionBtn.setTextColor(Color.parseColor("#FFFFFF"))
                            options[trueAnswer[0]].optionBtn.text = "√"
                            options[trueAnswer[0]].optionTv.setTextColor(Color.parseColor("#1EABFA"))
                            viewModel.wrongCount.run {
                                val count = value ?: 0
                                value = count + 1
                            }
                            viewModel.addMistake(questions[viewModel.index.value!!].id)
                            isRight = 2
                            if (viewModel.answerType != AnswerRvAdapter.COLLECTION && viewModel.answerType != AnswerRvAdapter.WRONG)
                                viewModel.itemState[index] = AnswerSheetRvAdapter.ANSWER_SHEET_WRONG
                        }
//                        it.hide()
                        val chooseString = "您选择 " + when (nowChoose[0]) {
                            0 -> "A"
                            1 -> "B"
                            else -> ""
                        }
                        val string =
                            SpannableString(
                                chooseString
                            )
                        viewModel.chooseMap[viewModel.index.value!!] = chooseString
                        string.setSpan(
                            when (isRight) {
                                1 -> ForegroundColorSpan(Color.parseColor("#1EABFA"))
                                2 -> ForegroundColorSpan(Color.parseColor("#FC656A"))
                                else -> ""
                            },
                            4,
                            string.length,
                            SpannableString.SPAN_INCLUSIVE_EXCLUSIVE
                        )
                        finishView.chooseOptionTv.text = string
                        finishView.root.show()
                        when (viewModel.answerType) {
                            AnswerRvAdapter.WRONG, AnswerRvAdapter.COLLECTION -> finishView.nextQuestionBtn.hide()
                            else -> {}
                        }
                        for (i in 0 until options.size) {
                            options[i].root.isEnabled = false
                        }
                    }
                }
            }

            if (type == JUDGMENT_QUESTION) {
                view.optionTv.text = entity.content
            } else {
                view.optionTv.text = entity.content.substring(3)
            }
        }
    }

    inner class QuestionPutViewHolder(view: ItemQuestionPutBinding) :
        MultipleViewHolder2<ItemQuestionPutBinding, QuestionPart>(view) {
        override fun setHolder(entity: QuestionPart) {
            putView = view
            if (type != FILL_QUESTION) {
                view.root.hide()
            }
            when (type) {
                SINGLE_SELECTION -> {
                    var isRight = -1
                    view.putBtn.singleClick {
                        if (nowChoose[0] == trueAnswer[0]) {
                            if(viewModel.answerType == AnswerRvAdapter.SPECIAL) {
                                viewModel.changeBossWay(1)
                            }
                            if (showType != 3) {
                                options[nowChoose[0]].optionBtn.setBackgroundResource(R.drawable.button_option_true)
                                options[nowChoose[0]].optionBtn.setTextColor(Color.parseColor("#FFFFFF"))
                                options[nowChoose[0]].optionBtn.text = "√"
                                options[nowChoose[0]].optionTv.setTextColor(Color.parseColor("#399DFF"))

                            } else {
                                imageOptions[nowChoose[0]].optionTv.setBackgroundResource(R.drawable.button_option_true)
                                imageOptions[nowChoose[0]].optionTv.setTextColor(Color.parseColor("#FFFFFF"))
                                imageOptions[nowChoose[0]].optionTv.text = "√"
                            }
                            viewModel.rightCount.run {
                                val count = value ?: 0
                                value = count + 1
                            }
                            finishView.trueOptionTv.text
                            isRight = 1
                            if (viewModel.answerType != AnswerRvAdapter.COLLECTION && viewModel.answerType != AnswerRvAdapter.WRONG)
                                viewModel.itemState[index] = AnswerSheetRvAdapter.ANSWER_SHEET_TRUE
                            if (viewModel.answerType == AnswerRvAdapter.WRONG || viewModel.answerType == AnswerRvAdapter.COLLECTION) {
                                viewModel.postAnswer(
                                    when (viewModel.answerType) {
                                        AnswerRvAdapter.WRONG -> 1
                                        AnswerRvAdapter.COLLECTION -> 2
                                        else -> 0
                                    },
                                    questions[index].id
                                )
                            }
                        } else {
                            if(viewModel.answerType == AnswerRvAdapter.SPECIAL){
                                viewModel.errorMedia.start()
                                viewModel.changeBossWay(2)
                            }
                            if (showType != 3) {
                                options[nowChoose[0]].optionBtn.setBackgroundResource(R.drawable.button_option_wrong)
                                options[nowChoose[0]].optionBtn.setTextColor(Color.parseColor("#FFFFFF"))
                                options[nowChoose[0]].optionBtn.text = "X"
                                options[nowChoose[0]].optionTv.setTextColor(Color.parseColor("#FC656A"))
                                options[trueAnswer[0]].optionBtn.setBackgroundResource(R.drawable.button_option_true)
                                options[trueAnswer[0]].optionBtn.setTextColor(Color.parseColor("#FFFFFF"))
                                options[trueAnswer[0]].optionBtn.text = "√"
                                options[trueAnswer[0]].optionTv.setTextColor(Color.parseColor("#399DFF"))
                            } else {
                                imageOptions[nowChoose[0]].optionTv.setBackgroundResource(R.drawable.button_option_wrong)
                                imageOptions[nowChoose[0]].optionTv.setTextColor(Color.parseColor("#FFFFFF"))
                                imageOptions[nowChoose[0]].optionTv.text = "√"
                                imageOptions[trueAnswer[0]].optionTv.setBackgroundResource(R.drawable.button_option_true)
                                imageOptions[trueAnswer[0]].optionTv.setTextColor(Color.parseColor("#FFFFFF"))
                                imageOptions[trueAnswer[0]].optionTv.text = "X"
                            }
                            viewModel.wrongCount.run {
                                val count = value ?: 0
                                value = count + 1
                            }
                            viewModel.addMistake(questions[viewModel.index.value!!].id)
                            isRight = 2
                            if (viewModel.answerType != AnswerRvAdapter.COLLECTION && viewModel.answerType != AnswerRvAdapter.WRONG)
                                viewModel.itemState[index] = AnswerSheetRvAdapter.ANSWER_SHEET_WRONG
                        }
                        it.hide()
                        val chooseString = "您选择 " + (nowChoose[0] + 'A'.toInt()).toChar()
                        val string =
                            SpannableString(chooseString)
                        viewModel.chooseMap[viewModel.index.value!!] = chooseString
                        string.setSpan(
                            when (isRight) {
                                1 -> ForegroundColorSpan(Color.parseColor("#1EABFA"))
                                2 -> ForegroundColorSpan(Color.parseColor("#FC656A"))
                                else -> ""
                            },
                            4,
                            string.length,
                            SpannableString.SPAN_INCLUSIVE_EXCLUSIVE
                        )
                        finishView.chooseOptionTv.text = string
                        finishView.root.show()
                        when (viewModel.answerType) {
                            AnswerRvAdapter.WRONG, AnswerRvAdapter.COLLECTION -> finishView.nextQuestionBtn.hide()
                            else -> {}
                        }
                        if (showType != 3) {
                            for (i in 0 until options.size) {
                                options[i].root.isEnabled = false
                            }
                        } else {
                            for (i in 0 until imageOptions.size) {
                                imageOptions[i].root.isEnabled = false
                            }
                        }
                    }
                }
                MULTIPLE_SELECTION -> {
                    view.putBtn.singleClick {
                        var isRight = -1
                        if (nowChoose.size != 0) {
                            var flag1 = true
                            var flag2 = true
                            for (answer in trueAnswer) {
                                if (!(answer in nowChoose)) {
                                    flag1 = false
                                    if (showType != 3) {
                                        options[answer].optionBtn.setBackgroundResource(R.drawable.button_multiple_option_miss)
                                        options[answer].optionBtn.setTextColor(Color.parseColor("#FFFFFF"))
                                        options[answer].optionTv.setTextColor(Color.parseColor("#1EABFA"))
                                    } else {
                                        imageOptions[answer].optionTv.setBackgroundResource(R.drawable.button_multiple_option_miss)
                                        imageOptions[answer].optionTv.setTextColor(
                                            Color.parseColor(
                                                "#FFFFFF"
                                            )
                                        )
                                    }
                                } else {
                                    if (showType != 3) {
                                        options[answer].optionBtn.setBackgroundResource(R.drawable.button_option_true)
                                        options[answer].optionBtn.setTextColor(Color.parseColor("#ffffff"))
                                        options[answer].optionTv.setTextColor(Color.parseColor("#1EABFA"))
                                        options[answer].optionBtn.text = "√"
                                    } else {
                                        imageOptions[answer].optionTv.setBackgroundResource(R.drawable.button_option_true)
                                        imageOptions[answer].optionTv.setTextColor(
                                            Color.parseColor(
                                                "#FFFFFF"
                                            )
                                        )
                                        imageOptions[answer].optionTv.text = "√"
                                    }
                                }
                            }
                            for (choose in nowChoose) {
                                if (!(choose in trueAnswer)) {
                                    flag2 = false
                                }
                            }
                            if (flag1 && flag2) {
                                if(viewModel.answerType == AnswerRvAdapter.SPECIAL) {
                                    viewModel.changeBossWay(1)
                                    viewModel.trueMedia.start()
                                }
                                viewModel.rightCount.run {
                                    val count = value ?: 0
                                    value = count + 1
                                }
                                isRight = 1
                                if (viewModel.answerType != AnswerRvAdapter.COLLECTION && viewModel.answerType != AnswerRvAdapter.WRONG)
                                    viewModel.itemState[index] =
                                        AnswerSheetRvAdapter.ANSWER_SHEET_TRUE
                                if (viewModel.answerType == AnswerRvAdapter.WRONG || viewModel.answerType == AnswerRvAdapter.COLLECTION) {
                                    viewModel.postAnswer(
                                        when (viewModel.answerType) {
                                            AnswerRvAdapter.WRONG -> 1
                                            AnswerRvAdapter.COLLECTION -> 2
                                            else -> 0
                                        },
                                        questions[index].id
                                    )
                                }
                            } else {
                                if(viewModel.answerType == AnswerRvAdapter.SPECIAL){
                                    viewModel.errorMedia.start()
                                    viewModel.changeBossWay(2)
                                }
                                viewModel.wrongCount.run {
                                    val count = value ?: 0
                                    value = count + 1
                                }
                                for (choose in nowChoose) {
                                    if (!(choose in trueAnswer)) {
                                        if (showType != 3) {
                                            options[choose].optionBtn.setBackgroundResource(R.drawable.button_option_wrong)
                                            options[choose].optionBtn.setTextColor(
                                                Color.parseColor(
                                                    "#ffffff"
                                                )
                                            )
                                            options[choose].optionTv.setTextColor(Color.parseColor("#FC656A"))
                                            options[choose].optionBtn.text = "X"
                                        } else {
                                            imageOptions[choose].optionTv.setBackgroundResource(R.drawable.button_option_wrong)
                                            imageOptions[choose].optionTv.setTextColor(
                                                Color.parseColor(
                                                    "#FFFFFF"
                                                )
                                            )
                                        }
                                    }
                                }
                                viewModel.addMistake(questions[viewModel.index.value!!].id)
                                isRight = 2
                                if (viewModel.answerType != AnswerRvAdapter.COLLECTION && viewModel.answerType != AnswerRvAdapter.WRONG)
                                    viewModel.itemState[index] =
                                        AnswerSheetRvAdapter.ANSWER_SHEET_WRONG
                            }
                            it.hide()
                            val chooseString = StringBuilder().apply {
                                append("您选择 ")
                                nowChoose.sort()
                                for (choose in nowChoose) {
                                    append((choose + 'A'.toInt()).toChar())
                                    if (!(choose == nowChoose[nowChoose.size - 1]))
                                        append("、")
                                }
                            }.toString()
                            val string =
                                SpannableString(chooseString)
                            viewModel.chooseMap[viewModel.index.value!!] = chooseString
                            string.setSpan(
                                when (isRight) {
                                    1 -> ForegroundColorSpan(Color.parseColor("#1EABFA"))
                                    2 -> ForegroundColorSpan(Color.parseColor("#FC656A"))
                                    else -> ""
                                },
                                4,
                                string.length,
                                SpannableString.SPAN_INCLUSIVE_EXCLUSIVE
                            )
                            finishView.chooseOptionTv.text = string
                            finishView.root.show()
                            when (viewModel.answerType) {
                                AnswerRvAdapter.WRONG, AnswerRvAdapter.COLLECTION -> finishView.nextQuestionBtn.hide()
                                else -> {}
                            }
                            if (showType != 3) {
                                for (button in options) {
                                    button.root.isEnabled = false
                                }
                            } else {
                                for (view in imageOptions) {
                                    view.root.isEnabled = false
                                }
                            }
                        } else {
                            Toast.makeText(context, "您还未选择", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                JUDGMENT_QUESTION -> {
                    var isRight = -1
                    view.putBtn.singleClick {
                        if (nowChoose[0] == trueAnswer[0]) {
                            if(viewModel.answerType == AnswerRvAdapter.SPECIAL) {
                                viewModel.changeBossWay(1)
                            }
                            options[nowChoose[0]].optionBtn.setBackgroundResource(R.drawable.button_option_true)
                            options[nowChoose[0]].optionBtn.setTextColor(Color.parseColor("#ffffff"))
                            options[nowChoose[0]].optionBtn.text = "√"
                            options[nowChoose[0]].optionTv.setTextColor(Color.parseColor("#1EABFA"))
                            viewModel.rightCount.run {
                                val count = value ?: 0
                                value = count + 1
                            }
                            isRight = 1
                            if (viewModel.answerType != AnswerRvAdapter.COLLECTION && viewModel.answerType != AnswerRvAdapter.WRONG)
                                viewModel.itemState[index] = AnswerSheetRvAdapter.ANSWER_SHEET_TRUE
                            if (viewModel.answerType == AnswerRvAdapter.WRONG || viewModel.answerType == AnswerRvAdapter.COLLECTION) {
                                viewModel.postAnswer(
                                    when (viewModel.answerType) {
                                        AnswerRvAdapter.WRONG -> 1
                                        AnswerRvAdapter.COLLECTION -> 2
                                        else -> 0
                                    },
                                    questions[index].id
                                )
                            }
                        } else {
                            options[nowChoose[0]].optionBtn.setBackgroundResource(R.drawable.button_option_wrong)
                            options[nowChoose[0]].optionBtn.setTextColor(Color.parseColor("#ffffff"))
                            options[nowChoose[0]].optionBtn.text = "X"
                            options[nowChoose[0]].optionTv.setTextColor(Color.parseColor("#FC656A"))
                            options[trueAnswer[0]].optionBtn.setBackgroundResource(R.drawable.button_option_true)
                            options[trueAnswer[0]].optionBtn.setTextColor(Color.parseColor("#FFFFFF"))
                            options[trueAnswer[0]].optionBtn.text = "√"
                            options[trueAnswer[0]].optionTv.setTextColor(Color.parseColor("#1EABFA"))
                            viewModel.wrongCount.run {
                                val count = value ?: 0
                                value = count + 1
                            }
                            viewModel.addMistake(questions[viewModel.index.value!!].id)
                            isRight = 2
                            if (viewModel.answerType != AnswerRvAdapter.COLLECTION && viewModel.answerType != AnswerRvAdapter.WRONG)
                                viewModel.itemState[index] = AnswerSheetRvAdapter.ANSWER_SHEET_WRONG
                        }
                        it.hide()
                        val chooseString = "您选择 " + when (nowChoose[0]) {
                            0 -> "A"
                            1 -> "B"
                            else -> ""
                        }
                        val string =
                            SpannableString(
                                chooseString
                            )
                        viewModel.chooseMap[viewModel.index.value!!] = chooseString
                        string.setSpan(
                            when (isRight) {
                                1 -> ForegroundColorSpan(Color.parseColor("#1EABFA"))
                                2 -> ForegroundColorSpan(Color.parseColor("#FC656A"))
                                else -> ""
                            },
                            4,
                            string.length,
                            SpannableString.SPAN_INCLUSIVE_EXCLUSIVE
                        )
                        finishView.chooseOptionTv.text = string
                        finishView.root.show()
                        when (viewModel.answerType) {
                            AnswerRvAdapter.WRONG, AnswerRvAdapter.COLLECTION -> finishView.nextQuestionBtn.hide()
                            else -> {}
                        }
                        for (i in 0 until options.size) {
                            options[i].root.isEnabled = false
                        }
                    }
                }
                FILL_QUESTION -> {
                    view.putBtn.singleClick {
                        var nowAnswer : String = ""
                        for (text in fillPart.fillText.getFillTexts()) {
                            Log.d("TEXT","-${text}-")
                            if(!(text.equals("") || text.equals("\t") || text.equals("        "))) {
                                nowAnswer += text
                            }
//                            if (text != fillPart.fillText.getFillTexts()[fillPart.fillText.getFillTexts().size - 1]) {
//                                nowAnswer += " "
//                            }
                        }
                        nowAnswer.replace(" ","")
                        nowAnswer.replace("\t","")
                        Log.d("NowAnswer", "-${nowAnswer}-")
                        var isRight = -1
                        if (nowAnswer.isEmpty()) {
                            Toast.makeText(context, "您还未输入", Toast.LENGTH_SHORT).show()
                        } else {
                            if (nowAnswer.equals(trueFillAnswer)) {
                                if(viewModel.answerType == AnswerRvAdapter.SPECIAL) {
                                    viewModel.changeBossWay(1)
                                    viewModel.trueMedia.start()
                                }
//                                fillPart.fillText.setTextColor(Color.parseColor("#00CC65"))
                                viewModel.rightCount.run {
                                    val count = value ?: 0
                                    value = count + 1
                                }
                                isRight = 1
                                if (viewModel.answerType != AnswerRvAdapter.COLLECTION && viewModel.answerType != AnswerRvAdapter.WRONG)
                                    viewModel.itemState[index] =
                                        AnswerSheetRvAdapter.ANSWER_SHEET_TRUE
                                if (viewModel.answerType == AnswerRvAdapter.WRONG || viewModel.answerType == AnswerRvAdapter.COLLECTION) {
                                    viewModel.postAnswer(
                                        when (viewModel.answerType) {
                                            AnswerRvAdapter.WRONG -> 1
                                            AnswerRvAdapter.COLLECTION -> 2
                                            else -> 0
                                        },
                                        questions[index].id
                                    )
                                }
                            } else {
                                if(viewModel.answerType == AnswerRvAdapter.SPECIAL){
                                    viewModel.errorMedia.start()
                                    viewModel.changeBossWay(2)
                                }
//                                fillPart.fillEt.setTextColor(Color.parseColor("#FF4B4A"))
                                viewModel.wrongCount.run {
                                    val count = value ?: 0
                                    value = count + 1
                                }
                                isRight = 2
                                viewModel.addMistake(questions[viewModel.index.value!!].id)
                                if (viewModel.answerType != AnswerRvAdapter.COLLECTION && viewModel.answerType != AnswerRvAdapter.WRONG)
                                    viewModel.itemState[index] =
                                        AnswerSheetRvAdapter.ANSWER_SHEET_WRONG
                            }
                            fillPart.fillText.isEnabled = false
                            it.hide()
                            finishView.root.show()
                            when (viewModel.answerType) {
                                AnswerRvAdapter.WRONG, AnswerRvAdapter.COLLECTION -> finishView.nextQuestionBtn.hide()
                                else -> {}
                            }
                            val string = SpannableString("正确答案 ${trueFillAnswer}")
                            string.setSpan(
                                ForegroundColorSpan(Color.parseColor("#1EABFA")),
                                5,
                                string.length,
                                SpannableString.SPAN_INCLUSIVE_EXCLUSIVE
                            )
                            val stringWrite = SpannableString("您的答案 ${nowAnswer}")
                            viewModel.chooseMap[viewModel.index.value!!] = nowAnswer
                            stringWrite.setSpan(
                                when (isRight) {
                                    1 -> ForegroundColorSpan(Color.parseColor("#1EABFA"))
                                    2 -> ForegroundColorSpan(Color.parseColor("#FC656A"))
                                    else -> null
                                },
                                5,
                                stringWrite.length,
                                SpannableString.SPAN_INCLUSIVE_EXCLUSIVE
                            )
                            finishView.chooseOptionTv.text = stringWrite
                            finishView.trueOptionTv.text = string
                            fillPart.fillText.isEnabled = false
                        }
                    }
                }
            }
        }
    }

    inner class QuestionFinishViewHolder(view: ItemQuestionFinishBinding) :
        MultipleViewHolder2<ItemQuestionFinishBinding, QuestionPart>(view) {
        override fun setHolder(entity: QuestionPart) {
            finishView = view
            view.root.hide()
            val string = SpannableString(
                if (type != JUDGMENT_QUESTION) {
                    "正确答案 " + StringBuilder().apply {
                        for (i in trueAnswer) {
                            append((i + 'A'.toInt()).toChar())
                            if (i != trueAnswer[trueAnswer.size - 1])
                                append("、")
                        }
                    }.toString()
                } else {
                    "正确答案 " + when (trueAnswer[0]) {
                        0 -> "A"
                        1 -> "B"
                        else -> ""
                    }
                }
            )
            string.setSpan(
                ForegroundColorSpan(Color.parseColor("#1EABFA")),
                5,
                string.length,
                SpannableString.SPAN_INCLUSIVE_EXCLUSIVE
            )
            view.trueOptionTv.text = string
            view.nextQuestionBtn.setOnClickListener {
                if(viewModel.answerType == AnswerRvAdapter.SPECIAL){
//                    viewModel.errorMedia.stop()
//                    viewModel.trueMedia.stop()
                }
                fragmentManager.beginTransaction().run {
                    if (index < questions.size - 1) {
                        replace(
                            R.id.questionFl,
                            QuestionFragment(
                                questions,
                                index + 1,
                                viewModel,
                                viewModel.chooseMap[index + 1]
                            )
                        )
                        viewModel.index.value = index + 1
                    } else {
                        if ((viewModel.rightCount.value!! + viewModel.wrongCount.value!!) != viewModel.sum) {
                            Toast.makeText(context, "您还没做完", Toast.LENGTH_SHORT).show()
                        } else {

                            if (viewModel.answerType == AnswerRvAdapter.SPECIAL && index >= viewModel.sum - 1) {
                                viewModel.endTime = System.currentTimeMillis()
                                val time = viewModel.endTime - viewModel.startTime
                                val intent =
                                    Intent(context, AnswerQuestionFinishActivity::class.java)
                                intent.putExtra("time", time)
                                intent.putExtra("right", viewModel.rightCount.value)
                                intent.putExtra("wrong", viewModel.wrongCount.value)
                                intent.putExtra("type", AnswerRvAdapter.SPECIAL)
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                viewModel.postSpecialScore(
                                    when (viewModel.specialType) {
                                        "基础概念" -> 1
                                        "应用技术" -> 2
                                        "教育发展" -> 3
                                        "伦理道德" -> 4
                                        else -> 0
                                    },
                                    (time / 1000).toInt(),
                                    ((viewModel.rightCount.value!! * 10.0 / (viewModel.rightCount.value!! + viewModel.wrongCount.value!!)).toInt())
                                )
                                context.startActivity(intent)
                                viewModel.way.invoke()
                            }
                            if (viewModel.answerType == AnswerRvAdapter.SPECIAL && viewModel.isGetQuestion[index / 10] == false) {
                                viewModel.getSpecialInner(viewModel.specialType, index / 10, 10)
                                viewModel.isGetQuestion[index / 10] = true
                                viewModel.index.value = index + 1
                            }
                            if (viewModel.wrongCount.value == 0 && viewModel.answerType == AnswerRvAdapter.ANSWER) {
                                viewModel.addAnswer()
                            }
                            if (viewModel.answerType == AnswerRvAdapter.ANSWER) {
                                viewModel.endTime = System.currentTimeMillis()
                                val time = viewModel.endTime - viewModel.startTime
                                val intent =
                                    Intent(context, AnswerQuestionFinishActivity::class.java)
                                intent.putExtra("time", time)
                                intent.putExtra("right", viewModel.rightCount.value)
                                intent.putExtra("wrong", viewModel.wrongCount.value)
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                context.startActivity(intent)
                                viewModel.way.invoke()
                            }
                        }
                    }
                    commit()
                }
            }
            view.analysisTv.text = entity.content
            if (choose != null) {
                if (type != FILL_QUESTION) {
                    val chooseAnswer = choose.substring(4).split("、")
                    for (choose in chooseAnswer) {
                        nowChoose.add(choose.toCharArray()[0].toInt() - 'A'.toInt())
                    }
                }
                when (type) {
                    SINGLE_SELECTION -> {
                        var isRight = -1
                        if (nowChoose[0] == trueAnswer[0]) {
                            if (showType != 3) {
                                options[nowChoose[0]].optionBtn.setBackgroundResource(R.drawable.button_option_true)
                                options[nowChoose[0]].optionBtn.setTextColor(Color.parseColor("#FFFFFF"))
                                options[nowChoose[0]].optionBtn.text = "√"
                                options[nowChoose[0]].optionTv.setTextColor(Color.parseColor("#399DFF"))
                            } else {
                                imageOptions[nowChoose[0]].optionTv.setBackgroundResource(R.drawable.button_option_true)
                                imageOptions[nowChoose[0]].optionTv.setTextColor(Color.parseColor("#FFFFFF"))
                                imageOptions[nowChoose[0]].optionTv.text = "√"
                            }
                            finishView.trueOptionTv.text
                            isRight = 1
                        } else {
                            if (showType != 3) {
                                options[nowChoose[0]].optionBtn.setBackgroundResource(R.drawable.button_option_wrong)
                                options[nowChoose[0]].optionBtn.setTextColor(Color.parseColor("#FFFFFF"))
                                options[nowChoose[0]].optionBtn.text = "X"
                                options[nowChoose[0]].optionTv.setTextColor(Color.parseColor("#FC656A"))
                                options[trueAnswer[0]].optionBtn.setBackgroundResource(R.drawable.button_option_true)
                                options[trueAnswer[0]].optionBtn.setTextColor(Color.parseColor("#FFFFFF"))
                                options[trueAnswer[0]].optionBtn.text = "√"
                                options[trueAnswer[0]].optionTv.setTextColor(Color.parseColor("#399DFF"))
                            } else {
                                imageOptions[nowChoose[0]].optionTv.setBackgroundResource(R.drawable.button_option_wrong)
                                imageOptions[nowChoose[0]].optionTv.setTextColor(Color.parseColor("#FFFFFF"))
                                imageOptions[nowChoose[0]].optionTv.text = "X"
                                imageOptions[trueAnswer[0]].optionTv.setBackgroundResource(R.drawable.button_option_true)
                                imageOptions[trueAnswer[0]].optionTv.setTextColor(Color.parseColor("#FFFFFF"))
                                imageOptions[trueAnswer[0]].optionTv.text = "√"
                            }
                            isRight = 2
                        }
                        putView.putBtn.hide()
                        val chooseString = "您选择 " + (nowChoose[0] + 'A'.toInt()).toChar()
                        val string =
                            SpannableString(chooseString)
                        viewModel.chooseMap[viewModel.index.value!!] = chooseString
                        string.setSpan(
                            when (isRight) {
                                1 -> ForegroundColorSpan(Color.parseColor("#1EABFA"))
                                2 -> ForegroundColorSpan(Color.parseColor("#FC656A"))
                                else -> ""
                            },
                            4,
                            string.length,
                            SpannableString.SPAN_INCLUSIVE_EXCLUSIVE
                        )
                        finishView.chooseOptionTv.text = string
                        finishView.root.show()
                        when (viewModel.answerType) {
                            AnswerRvAdapter.WRONG, AnswerRvAdapter.COLLECTION -> finishView.nextQuestionBtn.hide()
                            else -> {}
                        }
                        if (showType != 3) {
                            for (i in 0 until options.size) {
                                options[i].root.isEnabled = false
                            }
                        } else {
                            for (i in 0 until imageOptions.size) {
                                imageOptions[i].root.isEnabled = false
                            }
                        }
                    }
                    MULTIPLE_SELECTION -> {
                        var isRight = -1
                        if (nowChoose.size != 0) {
                            var flag1 = true
                            var flag2 = true
                            for (answer in trueAnswer) {
                                if (!(answer in nowChoose)) {
                                    flag1 = false
                                    if (showType != 3) {
                                        options[answer].optionBtn.setBackgroundResource(R.drawable.button_multiple_option_miss)
                                        options[answer].optionBtn.setTextColor(Color.parseColor("#FFFFFF"))
                                        options[answer].optionTv.setTextColor(Color.parseColor("#1EABFA"))
                                    } else {
                                        imageOptions[answer].optionTv.setBackgroundResource(R.drawable.button_multiple_option_miss)
                                        imageOptions[answer].optionTv.setTextColor(
                                            Color.parseColor(
                                                "#FFFFFF"
                                            )
                                        )
                                    }
                                } else {
                                    if (showType != 3) {
                                        options[answer].optionBtn.setBackgroundResource(R.drawable.button_option_true)
                                        options[answer].optionBtn.setTextColor(Color.parseColor("#ffffff"))
                                        options[answer].optionTv.setTextColor(Color.parseColor("#1EABFA"))
                                        options[answer].optionBtn.text = "√"
                                    } else {
                                        imageOptions[answer].optionTv.setBackgroundResource(R.drawable.button_option_true)
                                        imageOptions[answer].optionTv.setTextColor(
                                            Color.parseColor(
                                                "#FFFFFF"
                                            )
                                        )
                                        imageOptions[answer].optionTv.text = "√"
                                    }
                                }
                            }
                            for (choose in nowChoose) {
                                if (!(choose in trueAnswer)) {
                                    flag2 = false
                                }
                            }
                            if (flag1 && flag2) {
                                isRight = 1
                            } else {
                                for (choose in nowChoose) {
                                    if (!(choose in trueAnswer)) {
                                        if (showType != 3) {
                                            options[choose].optionBtn.setBackgroundResource(R.drawable.button_option_wrong)
                                            options[choose].optionBtn.setTextColor(
                                                Color.parseColor(
                                                    "#ffffff"
                                                )
                                            )
                                            options[choose].optionTv.setTextColor(Color.parseColor("#FC656A"))
                                            options[choose].optionBtn.text = "X"
                                        } else {
                                            imageOptions[choose].optionTv.setBackgroundResource(R.drawable.button_option_wrong)
                                            imageOptions[choose].optionTv.setTextColor(
                                                Color.parseColor(
                                                    "#FFFFFF"
                                                )
                                            )
                                        }
                                    }
                                }
                                isRight = 2
                            }
                            putView.putBtn.hide()
                            val chooseString = StringBuilder().apply {
                                append("您选择 ")
                                nowChoose.sort()
                                for (choose in nowChoose) {
                                    append((choose + 'A'.toInt()).toChar())
                                    if (!(choose == nowChoose[nowChoose.size - 1]))
                                        append("、")
                                }
                            }.toString()
                            val string =
                                SpannableString(chooseString)
                            viewModel.chooseMap[viewModel.index.value!!] = chooseString
                            string.setSpan(
                                when (isRight) {
                                    1 -> ForegroundColorSpan(Color.parseColor("#1EABFA"))
                                    2 -> ForegroundColorSpan(Color.parseColor("#FC656A"))
                                    else -> ""
                                },
                                4,
                                string.length,
                                SpannableString.SPAN_INCLUSIVE_EXCLUSIVE
                            )
                            finishView.chooseOptionTv.text = string
                            finishView.root.show()
                            when (viewModel.answerType) {
                                AnswerRvAdapter.WRONG, AnswerRvAdapter.COLLECTION -> finishView.nextQuestionBtn.hide()
                                else -> {}
                            }
                            if (showType != 3) {
                                for (button in options) {
                                    button.root.isEnabled = false
                                }
                            } else {
                                for (view in imageOptions) {
                                    view.root.isEnabled = false
                                }
                            }
                        }
                    }
                    JUDGMENT_QUESTION -> {
                        var isRight = -1
                        if (nowChoose[0] == trueAnswer[0]) {
                            options[nowChoose[0]].optionBtn.setBackgroundResource(R.drawable.button_option_true)
                            options[nowChoose[0]].optionBtn.setTextColor(Color.parseColor("#ffffff"))
                            options[nowChoose[0]].optionBtn.text = "√"
                            options[nowChoose[0]].optionTv.setTextColor(Color.parseColor("#1EABFA"))
                            isRight = 1
                        } else {
                            options[nowChoose[0]].optionBtn.setBackgroundResource(R.drawable.button_option_wrong)
                            options[nowChoose[0]].optionBtn.setTextColor(Color.parseColor("#ffffff"))
                            options[nowChoose[0]].optionBtn.text = "X"
                            options[nowChoose[0]].optionTv.setTextColor(Color.parseColor("#FC656A"))
                            options[trueAnswer[0]].optionBtn.setBackgroundResource(R.drawable.button_option_true)
                            options[trueAnswer[0]].optionBtn.setTextColor(Color.parseColor("#FFFFFF"))
                            options[trueAnswer[0]].optionBtn.text = "√"
                            options[trueAnswer[0]].optionTv.setTextColor(Color.parseColor("#1EABFA"))
                            isRight = 2
                        }
                        putView.putBtn.hide()
                        val chooseString = "您选择 " + when (nowChoose[0]) {
                            0 -> "A"
                            1 -> "B"
                            else -> ""
                        }
                        val string =
                            SpannableString(
                                chooseString
                            )
                        viewModel.chooseMap[viewModel.index.value!!] = chooseString
                        string.setSpan(
                            when (isRight) {
                                1 -> ForegroundColorSpan(Color.parseColor("#1EABFA"))
                                2 -> ForegroundColorSpan(Color.parseColor("#FC656A"))
                                else -> ""
                            },
                            4,
                            string.length,
                            SpannableString.SPAN_INCLUSIVE_EXCLUSIVE
                        )
                        finishView.chooseOptionTv.text = string
                        finishView.root.show()
                        when (viewModel.answerType) {
                            AnswerRvAdapter.WRONG, AnswerRvAdapter.COLLECTION -> finishView.nextQuestionBtn.hide()
                            else -> {}
                        }
                        for (i in 0 until options.size) {
                            options[i].root.isEnabled = false
                        }
                    }
                    FILL_QUESTION -> {
                        var nowAnswer = choose
//                        for (text in fillPart.fillText.getFillTexts()) {
//                            nowAnswer += text
//                            if(text != fillPart.fillText.getFillTexts()[fillPart.fillText.getFillTexts().size - 1]) {
//                                nowAnswer += " "
//                            }
//                        }
                        var isRight = -1
                        if (nowAnswer.equals(trueFillAnswer)) {
//                                fillPart.fillText.setTextColor(Color.parseColor("#00CC65"))
                            isRight = 1
                        } else {
//                                fillPart.fillEt.setTextColor(Color.parseColor("#FF4B4A"))
                            isRight = 2
                        }
                        fillPart.fillText.isEnabled = false
                        putView.putBtn.hide()
                        finishView.root.show()
                        when (viewModel.answerType) {
                            AnswerRvAdapter.WRONG, AnswerRvAdapter.COLLECTION -> finishView.nextQuestionBtn.hide()
                            else -> {}
                        }
                        val string = SpannableString("正确答案 ${trueFillAnswer}")
                        string.setSpan(
                            ForegroundColorSpan(Color.parseColor("#1EABFA")),
                            5,
                            string.length,
                            SpannableString.SPAN_INCLUSIVE_EXCLUSIVE
                        )
                        val stringWrite = SpannableString("您的答案 ${nowAnswer}")
                        viewModel.chooseMap[viewModel.index.value!!] = nowAnswer
                        stringWrite.setSpan(
                            when (isRight) {
                                1 -> ForegroundColorSpan(Color.parseColor("#1EABFA"))
                                2 -> ForegroundColorSpan(Color.parseColor("#FC656A"))
                                else -> null
                            },
                            5,
                            stringWrite.length,
                            SpannableString.SPAN_INCLUSIVE_EXCLUSIVE
                        )
                        finishView.chooseOptionTv.text = stringWrite
                        finishView.trueOptionTv.text = string
                        fillPart.fillText.isEnabled = false
                    }
                }
            }
        }
    }

    inner class QuestionImageViewHolder(view: ItemQuestionImageBinding) :
        MultipleViewHolder2<ItemQuestionImageBinding, QuestionPart>(view) {
        override fun setHolder(entity: QuestionPart) {
            view.questionContentIv.load(entity.content)
        }
    }

    inner class QuestionFillViewHolder(view: ItemQuestionContentFllBinding) :
        MultipleViewHolder2<ItemQuestionContentFllBinding, QuestionPart>(view) {
        override fun setHolder(entity: QuestionPart) {
            view.questionTypeTv.text = when (type) {
                SINGLE_SELECTION -> "单选"
                MULTIPLE_SELECTION -> "多选"
                JUDGMENT_QUESTION -> "判断"
                FILL_QUESTION -> "填空"
                else -> "暂无"
            }
//            view.questionWayTv.text = when (type) {
//                SINGLE_SELECTION -> "选出一个正确选项"
//                MULTIPLE_SELECTION -> "选择至少一个正确的选项"
//                JUDGMENT_QUESTION -> "判断下列题目正误"
//                FILL_QUESTION -> "将正确答案填入空缺处"
//                else -> "暂无"
//            }
            fillPart = view
            view.fillText.setText(entity.content)
            view.fillText.singleClick {
                putView.root.show()
            }
        }
    }

    inner class QuestionImageOptionViewHolder(view: ItemQuestionImageOptionBinding) :
        MultipleViewHolder2<ItemQuestionImageOptionBinding, QuestionPart>(view) {
        var id = -1
        override fun setHolder(entity: QuestionPart) {

            id = entity.opstion
            if (!(view in imageOptions)) {
                imageOptions.add(view)
            }
            when (type) {
                SINGLE_SELECTION -> {
                    view.root.singleClick {
                        if (nowChoose.size == 0) {
                            nowChoose.add(id)
                            view.optionTv.setBackgroundResource(R.drawable.button_option_work)
                            view.optionTv.setTextColor(Color.parseColor("#FFFFFF"))
                        }
                        if (nowChoose[0] != id) {
                            view.optionTv.setBackgroundResource(R.drawable.button_option_work)
                            view.optionTv.setTextColor(Color.parseColor("#ffffff"))
                            imageOptions[nowChoose[0]].optionTv.setBackgroundResource(R.drawable.button_option_normal)
                            imageOptions[nowChoose[0]].optionTv.setTextColor(Color.parseColor("#0a0a0a"))
                            nowChoose[0] = id
                        }
//                        putView.root.show()
                        var isRight = -1
                        if (nowChoose[0] == trueAnswer[0]) {
                            if(viewModel.answerType == AnswerRvAdapter.SPECIAL) {
                                viewModel.changeBossWay(1)
                                viewModel.trueMedia.start()
                            }
                            if (showType != 3) {
                                options[nowChoose[0]].optionBtn.setBackgroundResource(R.drawable.button_option_true)
                                options[nowChoose[0]].optionBtn.setTextColor(Color.parseColor("#FFFFFF"))
                                options[nowChoose[0]].optionBtn.text = "√"
                                options[nowChoose[0]].optionTv.setTextColor(Color.parseColor("#399DFF"))

                            } else {
                                imageOptions[nowChoose[0]].optionTv.setBackgroundResource(R.drawable.button_option_true)
                                imageOptions[nowChoose[0]].optionTv.setTextColor(Color.parseColor("#FFFFFF"))
                                imageOptions[nowChoose[0]].optionTv.text = "√"
                            }
                            viewModel.rightCount.run {
                                val count = value ?: 0
                                value = count + 1
                            }
                            finishView.trueOptionTv.text
                            isRight = 1
                            if (viewModel.answerType != AnswerRvAdapter.COLLECTION && viewModel.answerType != AnswerRvAdapter.WRONG)
                                viewModel.itemState[index] = AnswerSheetRvAdapter.ANSWER_SHEET_TRUE
                            if (viewModel.answerType == AnswerRvAdapter.WRONG || viewModel.answerType == AnswerRvAdapter.COLLECTION) {
                                viewModel.postAnswer(
                                    when (viewModel.answerType) {
                                        AnswerRvAdapter.WRONG -> 1
                                        AnswerRvAdapter.COLLECTION -> 2
                                        else -> 0
                                    },
                                    questions[index].id
                                )
                            }
                        } else {
                            if(viewModel.answerType == AnswerRvAdapter.SPECIAL){
                                viewModel.errorMedia.start()
                                viewModel.changeBossWay(2)
                            }
                            if (showType != 3) {
                                options[nowChoose[0]].optionBtn.setBackgroundResource(R.drawable.button_option_wrong)
                                options[nowChoose[0]].optionBtn.setTextColor(Color.parseColor("#FFFFFF"))
                                options[nowChoose[0]].optionBtn.text = "X"
                                options[nowChoose[0]].optionTv.setTextColor(Color.parseColor("#FC656A"))
                                options[trueAnswer[0]].optionBtn.setBackgroundResource(R.drawable.button_option_true)
                                options[trueAnswer[0]].optionBtn.setTextColor(Color.parseColor("#FFFFFF"))
                                options[trueAnswer[0]].optionBtn.text = "√"
                                options[trueAnswer[0]].optionTv.setTextColor(Color.parseColor("#399DFF"))
                            } else {
                                imageOptions[nowChoose[0]].optionTv.setBackgroundResource(R.drawable.button_option_wrong)
                                imageOptions[nowChoose[0]].optionTv.setTextColor(Color.parseColor("#FFFFFF"))
                                imageOptions[nowChoose[0]].optionTv.text = "X"
                                imageOptions[trueAnswer[0]].optionTv.setBackgroundResource(R.drawable.button_option_true)
                                imageOptions[trueAnswer[0]].optionTv.setTextColor(Color.parseColor("#FFFFFF"))
                                imageOptions[trueAnswer[0]].optionTv.text = "√"
                            }
                            viewModel.wrongCount.run {
                                val count = value ?: 0
                                value = count + 1
                            }
                            viewModel.addMistake(questions[viewModel.index.value!!].id)
                            isRight = 2
                            if (viewModel.answerType != AnswerRvAdapter.COLLECTION && viewModel.answerType != AnswerRvAdapter.WRONG)
                                viewModel.itemState[index] = AnswerSheetRvAdapter.ANSWER_SHEET_WRONG
                        }
//                        it.hide()
                        val chooseString = "您选择 " + (nowChoose[0] + 'A'.toInt()).toChar()
                        val string =
                            SpannableString(chooseString)
                        viewModel.chooseMap[viewModel.index.value!!] = chooseString
                        string.setSpan(
                            when (isRight) {
                                1 -> ForegroundColorSpan(Color.parseColor("#1EABFA"))
                                2 -> ForegroundColorSpan(Color.parseColor("#FC656A"))
                                else -> ""
                            },
                            4,
                            string.length,
                            SpannableString.SPAN_INCLUSIVE_EXCLUSIVE
                        )
                        finishView.chooseOptionTv.text = string
                        finishView.root.show()
                        when (viewModel.answerType) {
                            AnswerRvAdapter.WRONG, AnswerRvAdapter.COLLECTION -> finishView.nextQuestionBtn.hide()
                            else -> {}
                        }
                        if (showType != 3) {
                            for (i in 0 until options.size) {
                                options[i].root.isEnabled = false
                            }
                        } else {
                            for (i in 0 until imageOptions.size) {
                                imageOptions[i].root.isEnabled = false
                            }
                        }
                    }
                }
                MULTIPLE_SELECTION -> {
                    view.root.singleClick {
                        if (!(id in nowChoose)) {
                            view.optionTv.setBackgroundResource(R.drawable.button_option_work)
                            view.optionTv.setTextColor(Color.parseColor("#FFFFFF"))
                            nowChoose.add(id)
                        } else {
                            view.optionTv.setBackgroundResource(R.drawable.button_option_normal)
                            view.optionTv.setTextColor(Color.parseColor("#0A0A0A"))
                            nowChoose.remove(id)
                        }
                        putView.root.show()
                    }
                }
            }
            view.optionTv.text = (id + 'A'.toInt()).toChar().toString()
            Glide.with(view.optionIv).load(entity.content).into(view.optionIv)
            view.optionIv.scaleType = ImageView.ScaleType.FIT_XY
//            view.optionIv.load(entity.content)
        }
    }

//    inner class QuestionBossViewHolder(view: ItemQuestionBossBinding) :
//        MultipleViewHolder2<ItemQuestionBossBinding, QuestionPart>(view) {
//        override fun setHolder(entity: QuestionPart) {
//            bossView = view
//            view.questionBossIv.load("http://mms1.baidu.com/it/u=821404267,3659966372&fm=253&app=138&f=PNG?w=499&h=356")
//        }
//    }

}
