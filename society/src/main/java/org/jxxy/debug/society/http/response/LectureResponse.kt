package org.jxxy.debug.society.http.response

class LectureResponse(

        var lectureInfos: List<LectureInfo?>?)

     {
        class LectureInfo(
            var lectureNums: String?,
            var lecturePhoto: String?,
            var lecturePlace: String?,
            var lectureTitle: String?
        )
    }
