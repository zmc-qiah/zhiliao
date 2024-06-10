package org.jxxy.debug.search.http.respone

import org.jxxy.debug.common.scheme.SchemeDetail
import org.jxxy.debug.common.scheme.SchemeSearch

class SearchRespone(
    var historyInfos: List<HistoryInfo?>?,
    var hotResourceInfos: HotResourceInfos?,
    var hotTopicInfos: List<HotTopicInfo?>?
) {
    class HistoryInfo(
        var history: String?
    )

    class HotResourceInfos(
        var noteInfos: List<NoteInfo?>?,
        var resourceInfos: List<ResourceInfo?>?
    ) {
        class NoteInfo(
            var photoUrl: String?
        )

        class ResourceInfo(
            var author: String?,
            var authorHead: String?,
            var content: String?,
            var createTime: String?,
            var ip: String?,
            var likes: Int?,
            var photo: Any?,
            var readHot: Int?,
            var scheme: SchemeDetail?,
            var title: String?
        ) {
            class Scheme(
                var resourceId: Int?,
                var route: String?,
                var type: Int?
            )
        }
    }

    class HotTopicInfo(
        var resourceReads: Int?,
        var resourceTitle: String?,
        var scheme: SchemeSearch?
    ) {
        class Scheme(
            var keyword: String?,
            var route: String?,
            var type: Int?
        )
    }
}