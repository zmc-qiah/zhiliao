package org.jxxy.debug.home.Fragment.http.response
import com.google.gson.*
import com.google.gson.internal.Streams
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import org.jxxy.debug.corekit.gson.SubTypeAdapterWrapper
import java.util.regex.Pattern

/**
 * 异构类型解析器
 */
class MyCommonTypeAdapter<T>(
    val gson: Gson,
    val adapterFactory: TypeAdapterFactory,
    private val fieldName: String
) : TypeAdapter<T>() {
    private val TAG = "MyCommonTypeAdapter"
    private val classMap: MutableMap<String, TypeAdapter<T>> = HashMap()
    private val fieldMap: MutableMap<String, TypeAdapter<T>> = HashMap()
    inline fun <reified R : T> addSubTypeAdapter(
        fieldValue: Int
    ) {
        val cls = R::class.java
        val typeAdapter = gson.getDelegateAdapter(adapterFactory, TypeToken.get(cls))
        addSubTypeAdapter(fieldValue.toString(), cls, typeAdapter)
    }

    inline fun <reified R : T> addSubTypeAdapter(
        fieldValue: String
    ) {
        val cls = R::class.java
        val typeAdapter = gson.getDelegateAdapter(adapterFactory, TypeToken.get(cls))
        addSubTypeAdapter(fieldValue, cls, typeAdapter)
    }

    fun <R : T> addSubTypeAdapter(
        fieldValue: String,
        cls: Class<R>,
        typeAdapter: TypeAdapter<R>
    ) {
        val adapter: TypeAdapter<T> = SubTypeAdapterWrapper(cls, typeAdapter)
        classMap[cls.name] = adapter
        fieldMap[fieldValue] = adapter
    }

    override fun write(out: JsonWriter, value: T?) {
        if (value == null) {
            out.nullValue()
            return
        }
        value.let {
            getTypeAdapterByClass(it::class.java.name).write(out, value)
        }
    }

    override fun read(`in`: JsonReader): T? {
        if (`in`.peek() == JsonToken.NULL) {
            `in`.nextNull()
            return null
        }
        val obj = Streams.parse(`in`).asJsonObject
        val fieldElement = obj.get(fieldName)
        if (fieldElement == null || fieldElement.isJsonNull) {
            throw JsonSyntaxException("${obj.toString()}Field $fieldName is null or not found")
        }
        val string = fieldElement.toString()
        val pattern = Pattern.compile("\"type\":\\s*(\\d+)")
        val matcher = pattern.matcher(string)
        var res :String?= null
        if (matcher.find()) {
            res = matcher.group(1)
        }
        return getTypeAdapterByField(res?:"").fromJsonTree(obj)
    }

    private fun getTypeAdapterByClass(className: String): TypeAdapter<T> {
        return classMap[className] ?: throw JsonParseException("Unknown class : $className")
    }

    private fun getTypeAdapterByField(fieldValue: String): TypeAdapter<T> {
        return fieldMap[fieldValue] ?: throw JsonParseException("Unknown fieldValue : $fieldValue")
    }
}