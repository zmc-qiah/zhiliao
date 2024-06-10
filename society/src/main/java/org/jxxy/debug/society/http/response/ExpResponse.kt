package org.jxxy.debug.society.http.response

class ExpResponse(
        var activityShareInfo: List<ActivityShareInfo?>?,
        var background: String?,
        var date: String?,
        var userInfo: UserInfo?)
     {
        class ActivityShareInfo(
            var activityInfo: ActivityInfo?,
            var day: String?,
            var mouth: String?,
            var shareContent: String?,
            var sharePhoto: String?,
            var userInfo: UserInfo?,
            var year: String?
        ) {
            class ActivityInfo(
                var date: String?,
                var photo: String?,
                var scheme: Scheme?,
                var title: String?
            ) {
                class Scheme(
                    var route: String?,
                    var type: Int?,
                    var url: String?
                )
            }

            class UserInfo(
                var headPhoto: String?,
                var userName: String?
            )
        }

        class UserInfo(
            var headPhoto: String?,
            var userName: String?
        )
    }
