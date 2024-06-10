package orgjxxy.debug.identity.http.response

class IdentityAllGetResponse(
        var identityInfos: List<IdentityInfo?>?
    ) {
        class IdentityInfo(
            var identityId: Int?,
            var identityName: String?,
            var identityUrl: String?
        )
}
