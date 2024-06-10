package org.jxxy.debug.society.http.response

class DiscussResponse(
    var discussInfos: List<DiscussInfo?>?
) {
        class DiscussInfo(
            var answerContent: String?,
            var answerHead: String?,
            var answerName: String?,
            var questionLabel: String?,
            var questionTitle: String?,
            var id:Int?
        )
    }
