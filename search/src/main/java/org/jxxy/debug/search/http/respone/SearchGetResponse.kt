package org.jxxy.debug.search.http.respone

import org.jxxy.debug.common.scheme.Scheme
import org.jxxy.debug.common.scheme.SchemeDetail

class SearchGetResponse(
    var gridInfos: List<GridInfo?>?,
    var noteInfos: List<NoteInfo?>?,
    var questionInfo: QuestionInfo?,
    var wikiInfo: WikiInfo?
) {
    data class GridInfo(
           var author: String?,
           var authorHead: String?,
           var describe: String?,
           var createTime: String?,
           var ip: String?,
           var likes: Int?,
           var photo: String?,
           var readHot: Int?,
           var scheme: Scheme?,
           var title: String?
        )

        class NoteInfo(
            var photoUrl: String?
        )

        class QuestionInfo(
            var shortText: String?,
            var title: String?
        )

        class WikiInfo(
            var shortText: String?,
            var title: String?
        )
    }
