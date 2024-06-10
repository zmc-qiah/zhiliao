package orgjxxy.debug.identity.http.service
import org.jxxy.debug.corekit.http.bean.BaseResp
import orgjxxy.debug.identity.bean.OptionData
import orgjxxy.debug.identity.http.response.IdentityAllGetResponse
import orgjxxy.debug.identity.http.response.IdentityGetResponse
import orgjxxy.debug.identity.http.response.OptionResponse
import orgjxxy.debug.identity.http.response.getByIdResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface IdentityService {
    //搜索初始页面
    @GET("identity/tab/get")
    suspend fun Identityget(): BaseResp<IdentityGetResponse?>
    @GET("identity/getById")
    suspend fun getById(@Query("identityId") identityId: Int): BaseResp<getByIdResponse?>

    @GET("identity/all/get")
    suspend fun IdentityAllGet(): BaseResp<IdentityAllGetResponse?>

    @POST("identity/post")
    suspend fun identityanswerpost(@Query("identityId") identityId: Int,@Body optionData: OptionData): BaseResp<OptionResponse>
}
