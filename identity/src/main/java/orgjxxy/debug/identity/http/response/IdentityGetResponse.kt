package orgjxxy.debug.identity.http.response

class IdentityGetResponse(
        var first: List<First?>?
    ) {
        class First(
            var id: Int?,
            var list: List<Optionanswer>?,
            var tabName: String?
        ) {
            class Optionanswer(
                var id: Int?,
                var tabName: String?
            )
        }
    }
