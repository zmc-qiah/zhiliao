package org.jxxy.debug.society.http.response

import org.jxxy.debug.common.scheme.SchemeDetail
import org.jxxy.debug.common.scheme.SchemeH5

class MovableResponse(
        var carousel: List<Carousel?>?,
        var photoUrl: List<String?>?,
        var posters: String?
    ) {
        class Carousel(
            var photo: String?,
            var scheme: SchemeH5?,
            var title: String?
        ) {
            class Scheme(
                var route: String?,
                var type: Int?,
                var url: String?
            )
        }
    }
