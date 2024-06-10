package org.jxxy.debug.society.http.response

class Playresponse(
        var lectureInfos: List<LectureResponse.LectureInfo?>? )
{
        class LectureInfo(
            var lectureNums: String?,
            var lecturePhoto: String?,
            var lecturePlace: String?,
            var lectureTitle: String?
        )
    }
