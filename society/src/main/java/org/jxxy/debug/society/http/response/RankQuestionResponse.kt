package org.jxxy.debug.society.http.response

class RankQuestionResponse(
        var discussQuestionInfos: List<DiscussQuestionInfo?>?
    ) {
        class DiscussQuestionInfo(
            var authorHead: Any?,
            var authorName: Any?,
            var id: Int?,
            var questionPhoto:String?,
            var questionContent: String?,
            var questionLabel: String?,
            var questionTitle: String?,
            var question_hot: String?
        )
    }
