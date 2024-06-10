package org.jxxy.debug.theme.fragment

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Process
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider

import com.suke.widget.SwitchButton
import com.yarolegovich.discretescrollview.DiscreteScrollView
import com.yarolegovich.discretescrollview.transform.ScaleTransformer
import navigation
import org.jxxy.debug.common.scheme.Scheme
import org.jxxy.debug.common.scheme.SchemeAI
import org.jxxy.debug.corekit.common.BaseApplication
import org.jxxy.debug.corekit.common.BaseFragment
import org.jxxy.debug.corekit.util.ResourceUtil
import org.jxxy.debug.corekit.util.toast
import org.jxxy.debug.theme.R

import org.jxxy.debug.theme.adapter.ItemClickListener
import org.jxxy.debug.theme.adapter.ThemeDsvAdapter
import org.jxxy.debug.theme.adapter.ThemeDsvViewHolder
import org.jxxy.debug.theme.adapter.ThemeRvAdapter
import org.jxxy.debug.theme.bean.*
import org.jxxy.debug.theme.bean.AIBean
import org.jxxy.debug.theme.bean.AIOcr
import org.jxxy.debug.theme.bean.AIPose
import org.jxxy.debug.theme.bean.AiAdv
import org.jxxy.debug.theme.bean.AiAdvBean
import org.jxxy.debug.theme.bean.AiEmo
import org.jxxy.debug.theme.bean.AiEmoBean
import org.jxxy.debug.theme.bean.ThemeAIPaint
import org.jxxy.debug.theme.bean.ThemeAIPaintBean
import org.jxxy.debug.theme.bean.ThemeNavigation
import org.jxxy.debug.theme.bean.ThemeNavigationBean
import org.jxxy.debug.theme.databinding.FragemetThemeBinding
import org.jxxy.debug.theme.floatball.service.SuspendWindowService
import org.jxxy.debug.theme.floatball.utils.Utils
import org.jxxy.debug.theme.floatball.utils.ViewModleMain
import org.jxxy.debug.theme.floatball.utils.ViewModleMain.stateModel
import org.jxxy.debug.theme.http.viewModel.ThemeViewModel


class ThemeFragment : BaseFragment<FragemetThemeBinding>() {

    override fun bindLayout(): FragemetThemeBinding = FragemetThemeBinding.inflate(layoutInflater)
    var flag = false
    private val themeRv by lazy { find.themeRv }
    private val viewModel by lazy { ViewModelProvider(this).get(ThemeViewModel::class.java) }
    private val dsv by lazy { find.dsv }
    private lateinit var cards: List<AIBean>
    private lateinit var cardAdapter: ThemeDsvAdapter
    private val themeRvAdapter: ThemeRvAdapter by lazy { ThemeRvAdapter() }
    private var themeCurrent: Int = 0
    override fun initView() {
        val switchButton = find.switchButton
        BaseApplication.context().startService(Intent(BaseApplication.context(), SuspendWindowService::class.java))
        stateModel.postValue(ViewModleMain.isShowSuspendWindow.value ?: false)
        find.switchText.text = "关闭悬浮球"
        switchButton.setOnCheckedChangeListener(SwitchButton.OnCheckedChangeListener { view, isChecked ->
            if (isChecked) {
                find.switchText.text = "开启悬浮球"
            } else {
                find.switchText.text = "关闭悬浮球"
            }
            if (flag) {
                flag = false
                return@OnCheckedChangeListener
            }
            if (Utils.commonROMPermissionCheck(requireActivity())) {
                if (isRecordAudioPermissionGranted()) {
                    ViewModleMain.isShowSuspendWindow.postValue(isChecked)
                } else {
                    "请开启录音权限".toast()
                    requestRecordAudioPermission()
                }
            } else {
                Toast.makeText(requireActivity(), "请开启悬浮窗权限", Toast.LENGTH_SHORT).show()
                requestDataLauncher.launch(Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION).apply {
                    data = Uri.parse("package:${requireActivity().packageName}")
                })
            }
        })
        themeRv.adapter = themeRvAdapter
        themeRv.isEnabled = false

        dsv.setSlideOnFling(false)
        cards = getCards()
        themeRvAdapter.add(cards)
        cardAdapter = ThemeDsvAdapter(itemClickListener)
        dsv.adapter = cardAdapter
        dsv.setSlideOnFling(true)
        dsv.setAdapter(cardAdapter)
        cardAdapter.add(cards)
        dsv.addOnItemChangedListener(itemChangedListener)
        dsv.scrollToPosition(0)
        dsv.setItemTransitionTimeMillis(100)
        dsv.setItemTransformer(
            ScaleTransformer.Builder()
                .setMinScale(0.6f)
                .build()
        )
        dsv.addScrollStateChangeListener(scrollListener)
//        find.bgView.setmViewPager(dsv, cards.size)
        find.newBgView.setIndex(SchemeAI.EMO)
//        find.bgView.onPageChangeListener = scrollListener
    }
    private fun getCards(): List<AIBean> {
        return listOf(
            AiKnowledge(
                listOf(
                    "AI绘画的运用场景",
                    "如何使用AI绘画",
                    "手机助手的作用",
                    "AI绘画能带来哪些便利",
                    "chatGpt的妙用",
                    "AI能给我们带来什么",
                    "我们应该如何与时俱进",
                    "人脸识别的安全性",
                    "情绪识别如何与我们的生活挂钩"
                ),
                ResourceUtil.getString(R.string.theme_kn_name),
                "545",
                SchemeAI(SchemeAI.KNOWLEDGE)
            ),
            AIOcr(
                ResourceUtil.getString(R.string.theme_ocr_name),
"https://bucket-lsp.oss-cn-hangzhou.aliyuncs.com/2023/09/21/f1780a32-67b9-47ad-9ef8-6541938a636a.png",                "OCR 系统的特点是集硬件和软件于一体，旨在通过扫描物理文档的文本，将文档中的字符转换为代码，再将代码用于数据处理。OCR 系统通过三个步骤实现其功能：\n" +
                        "图像预处理\n" +
                        "首先，硬件（通常是光学扫描仪）将文件的物理形式处理成图像。生成的图像被转换成黑白版本，然后分析亮区域（背景）和暗区域（字符）。OCR 系统还可以进一步将图像分类为单独元素，如表格、文本或图像。\n" +
                        "智能字符识别\n" +
                        "AI 通过分析图像的黑暗区域来识别字母和数字。通常，AI 会使用以下其中一种方法来一次锁定一个字符、单词或文本块：\n" +
                        "模式识别：利用多种多样的文本、文本格式和笔迹来训练 AI 算法。AI 算法将在图像上扫描到的字符与已学习过的字符之间进行比较，以识别并匹配字符；\n" +
                        "特征提取：为了识别新的字符，AI 算法将应用有关特定字符特征的规则。特征包括字符角度、交叉或水平线和曲线的数量。\n" +
                        "机器在识别出字符后，再将字符转换成可用于进一步操作的 ASCII 码。\n" +
                        "后处理\n" +
                        "最后，AI 将纠正结果文件中的错误。例如，根据文档中的特定词汇来训练 AI，确保输出的内容没有超出词典的范围，来保证文档的质量。",
                "图像识别的原理和应用：从基础知识到实际案例\n" +
                        "图像识别是一种利用计算机对图像进行处理、分析和理解，以识别各种不同模式的目标和对象的技术。图像识别是人工智能和计算机视觉的一个重要分支，它在各个领域都有广泛的应用，如遥感、通讯、军事、公安、医学、机器人等。\n" +
                        "图像识别的基本过程可以分为以下几个步骤：\n" +
                        "信息获取：通过传感器，将光或声音等信息转化为电信号，形成数字图像或波形。\n" +
                        "预处理：对原始数据进行一些操作，如二值化、平滑、变换、增强、恢复、滤波等，以提高图像质量和减少噪声。\n",
                ResourceUtil.getString(R.string.url_note_top_right),
                SchemeAI(SchemeAI.OCR)
            ),
            ThemeNavigation(
                listOf(
                    ThemeNavigationBean(
                        "ai思维导图",
                        "https://shutu.cn/",
                        "https://feizhuke.com/wp-content/uploads/2023/06/img_649335d33856e.png",
                        "上传文档，AI一键总结归纳为导图；输入想法，AI一键生成思维导图；海量导图模板与素材的应用、文件跨平台云同步、多人同时在线协作，助力学习、工作的效率提升。"
                    ),
                    ThemeNavigationBean(
                        "ChatGpt",
                        "https://chat.openai.com/",
                        "https://bucket-lsp.oss-cn-hangzhou.aliyuncs.com/2023/09/21/4233003f-19a5-4f0f-96eb-dc2e2f7ac9be.png",
                        "ChatGPT,由OpenAl开发的一" +
                                "款基于人工智能技术的对话机" +
                                "器人,使用自然语言处理和机" +
                                "器学习算法,可以理解和生成" +
                                "类似人类语言的对话。"
                    ),
                    ThemeNavigationBean(
                        "OpenDream",
                        "https://www.grammarly.com/",
                        "https://bucket-lsp.oss-cn-hangzhou.aliyuncs.com/2023/09/21/ca4771ed-3073-4dbe-81a4-1c29bfaa2c2b.png",
                        "OpenDream，人工智能艺术生成器,"
                    ),
                    ThemeNavigationBean(
                        "揽睿星舟",
                        "https://www.lanrui-ai.com/",
                        "https://feizhuke.com/wp-content/uploads/2023/08/d2c71-www.lanrui-ai.com.png",
                        "sd一键云部署搭建stable diffusion的ai服务平台网站"
                    ),

                    ),
                ResourceUtil.getString(R.string.theme_na_name),
                ResourceUtil.getString(R.string.url_note_top_right),
                SchemeAI(SchemeAI.NAVIGATION)),
            AiAdv(
                listOf(
                    AiAdvBean(
                        "这个世界是充满了奇异景观的，你可能会遇到飘浮的岩石岛屿、飞舞的幻影生物，甚至是漂浮在半空中的古老遗迹。但这个神秘世界也隐藏着未知的危险和未解之谜。你，一位强大的召唤师，以掌握着古老的魔法艺术，已经准备好踏上这个充满魔法和挑战的旅程。你的冒险，充满了魔法的奥秘和未知的危险，正式展开。",
                        "https://bucket-lsp.oss-cn-hangzhou.aliyuncs.com/2023/09/14/f4196e90-a6ee-479c-8a94-760bac7837f3.png"
                    ),
                    AiAdvBean(
                        "这片森林不仅充满了生命，还隐藏着未知的危险。远处传来野兽的嘶吼，密林深处似乎有神秘的生物在观察着你。你，一名精通自然和魔法的德鲁伊，决定进入这个充满生机和挑战的神秘森林，以探索它的奥秘和守护大自然的平衡。你的冒险，充满了自然之力和未知的危险，已经正式展开",
                        "https://bucket-lsp.oss-cn-hangzhou.aliyuncs.com/2023/09/14/f23c367b-5aab-4ca8-9d6d-c1b7f6405d25.jpeg"                   ),
                    AiAdvBean(
                        "这个世界是充满了奇异景观的，你可能会遇到飘浮的岩石岛屿、飞舞的幻影生物，甚至是漂浮在半空中的古老遗迹。但这个神秘世界也隐藏着未知的危险和未解之谜。你，一位强大的召唤师，以掌握着古老的魔法艺术，已经准备好踏上这个充满魔法和挑战的旅程。你的冒险，充满了魔法的奥秘和未知的危险，正式展开。",
                        "https://bucket-lsp.oss-cn-hangzhou.aliyuncs.com/2023/09/14/0cc8792f-b444-4a53-9f45-772988051e07.jpeg"
                    ),
                    AiAdvBean(
                        "在这座神秘的魔法学院中，魔法的秘密似乎在空气中流淌，每一本古老的法术书都承载着无限的可能性。学院的大厅高耸而神秘，蜡烛的烛光投下变幻莫测的影子。你的脚步回响在大理石地板上，仿佛与过去的巨匠们的智慧交织在一起。但在这个充满机会的学院中，也潜藏着危险，来自古老法术和不可知之力的威胁。你，一名强大的法师，正站在这个神秘魔法学院的门槛上，准备探寻其中的奥秘和挑战。你的冒险，充满魔法和危险，已经开始。",
                        "https://bucket-lsp.oss-cn-hangzhou.aliyuncs.com/2023/09/14/1c57391b-4ad7-4c59-9e33-5b85d0e9deba.jpeg"
                    )
                ),
                ResourceUtil.getString(R.string.theme_adv_name),
                ResourceUtil.getString(R.string.url_note_top_right),
                SchemeAI(SchemeAI.ADV)
            ),
            ThemeAIPaint(
                listOf(
                    ThemeAIPaintBean(
                        "[deformed | disfigured], poorly drawn, [bad : wrong] anatomy, [extra | missing | floating | disconnected] limb, (mutated hands and fingers), blurry",
                        "https://bucket-lsp.oss-cn-hangzhou.aliyuncs.com/2023/09/13/48984829-e3d1-44cf-b9bc-97c20ac36203.jpeg"
                    ),
                    ThemeAIPaintBean(
                        "thin, fine fractal glossy vivid colored ink sketch shiny contours outlines of a perfect physique female silhouette Negative prompt: (worst quality, low quality, normal quality, lowres, low details, oversaturated, undersaturated, overexposed, underexposed, grayscale, bw, bad photo, bad photography, bad art:1.4),",
                        "https://bucket-lsp.oss-cn-hangzhou.aliyuncs.com/2023/09/13/8db28676-76be-4468-96ed-bf6f936b9008.jpeg"
                    ),
                    ThemeAIPaintBean(
                        "(1 girl:1.5) Kindergarten,double bun,gray gradient hair,air bangs,double braids,(empty eyes:1.7),latex,jitome,border,Altostratus,(Sky:1.2),(Colorful Cloud:1.8) Landscape,Outdoor,Solo,Long Hair,Trees,Flowers,Skirt,Sitting,Shoes,(Grass: 2) (Dew:1.1),Buildings,spectators",
                        "https://bucket-lsp.oss-cn-hangzhou.aliyuncs.com/2023/09/13/fd5b1638-9209-47a9-a56e-396a67057668.jpeg"
                    ),
                    ThemeAIPaintBean(
                        "[deformed | disfigured], poorly drawn, [bad : wrong] anatomy, [extra | missing | floating | disconnected] limb, (mutated hands and fingers), blurry",
                        "https://bucket-lsp.oss-cn-hangzhou.aliyuncs.com/2023/09/16/5a5728c6-6210-41b3-b27e-4c5d946a0522.jpeg"
                    ),
                    ThemeAIPaintBean(
                        "[deformed | disfigured], poorly drawn, [bad : wrong] anatomy, [extra | missing | floating | disconnected] limb, (mutated hands and fingers), blurry",
                        "https://bucket-lsp.oss-cn-hangzhou.aliyuncs.com/2023/09/16/2f488183-ae9a-49b6-a081-6f738ac923bb.jpeg"                    ),
                    ThemeAIPaintBean(
                        "thin, fine fractal glossy vivid colored ink sketch shiny contours outlines of a perfect physique female silhouette Negative prompt: (worst quality, low quality, normal quality, lowres, low details, oversaturated, undersaturated, overexposed, underexposed, grayscale, bw, bad photo, bad photography, bad art:1.4),",
                        "https://bucket-lsp.oss-cn-hangzhou.aliyuncs.com/2023/09/16/5c237dff-b0e2-4d98-aebe-e0934365ea5d.jpeg"                    ),
                ),
                ResourceUtil.getString(R.string.theme_paint_name), ResourceUtil.getString(R.string.url_note_top_right),
                SchemeAI(SchemeAI.PAINT)
            ),

            AIPose(ResourceUtil.getString(R.string.theme_pose_name),ResourceUtil.getString(R.string.url_note_top_right),
                SchemeAI(SchemeAI.POSE)),
            AiEmo(
                listOf(
                    AiEmoBean(
                        "2023-9-08",
                        "我做了很多事但又感觉什么都没做成，忙忙碌碌不知所谓，感觉好迷茫啊。",
                        "https://bucket-lsp.oss-cn-hangzhou.aliyuncs.com/2023/09/15/ae8e2e6c-7f3e-4bd7-a208-1346bf2c4119.png",
                        "2")
                    ,
                    AiEmoBean(
                        "2023-9-13",
                        "今天我骑自行车双手离开把手回头装逼，然后一头撞在电线杆上，痛得我想死！",
                        "https://bucket-lsp.oss-cn-hangzhou.aliyuncs.com/2023/09/22/47550763-9b17-41ad-bd86-e95f82bacf5f.jpg",
"2"                    ),
                    AiEmoBean(
                        "2023-9-10",
                        "我在走路的时候路边突然来了一辆车，差点撞上我，还好我的朋友一把拉住了我让我往后退，感激她一辈子！",
                        "https://bucket-lsp.oss-cn-hangzhou.aliyuncs.com/2023/09/15/3cfd241a-273e-4d02-ae22-06a061a59b07.png",
"3"                    ),
                    AiEmoBean(
                        "2023-9-14",
                        "今天下公交车的时候，公交车门把我鞋子夹掉了",
                        "https://bucket-lsp.oss-cn-hangzhou.aliyuncs.com/2023/09/15/975578b8-c480-4a29-94b0-bbe2bce8560f.jpg",
                        "1"
                    )
                ),
                ResourceUtil.getString(R.string.theme_emo_name),
                ResourceUtil.getString(R.string.url_note_top_right),
                SchemeAI(SchemeAI.EMO)
            ),
        )
    }
    override fun subscribeUi() {
        stateModel.observe(this) {
            if (flag1) {
                flag1 = false
                flag = true
                find.switchButton.isChecked = false
            }
            if (it != null) {
                if (find.switchButton.isChecked != it) {
                    flag = true
                    find.switchButton.isChecked = it
                }
            }
        }
        find.root.transitionToEnd()
    }

    private val itemChangedListener = object :
        DiscreteScrollView.OnItemChangedListener<ThemeDsvViewHolder> {
        override fun onCurrentItemChanged(
            viewHolder: ThemeDsvViewHolder?,
            adapterPosition: Int
        ) {
            find.newBgView.setIndex(getANum(adapterPosition))
            viewHolder?.showText()
        }
    }
    private val scrollListener = object :
        DiscreteScrollView.ScrollStateChangeListener<ThemeDsvViewHolder> {
        override fun onScrollStart(
            currentItemHolder: ThemeDsvViewHolder,
            adapterPosition: Int
        ) {
            currentItemHolder.hideText()
            themeRvAdapter.notifyItemChanged(adapterPosition, false)
        }

        override fun onScrollEnd(
            currentItemHolder: ThemeDsvViewHolder,
            adapterPosition: Int
        ) {
            find.newBgView.setIndex(getANum(adapterPosition))
            currentItemHolder.hideText()
            themeRvAdapter.notifyItemChanged(adapterPosition, true)
        }

        override fun onScroll(
            scrollPosition: Float,
            currentPosition: Int,
            newPosition: Int,
            currentHolder: ThemeDsvViewHolder?,
            newCurrent: ThemeDsvViewHolder?
        ) {
            var scrollPosition = Math.abs(scrollPosition)
            Log.d("scrollPosition", "onScroll: $scrollPosition$currentPosition$newPosition")
            if (scrollPosition < 0.5) {
                if (themeCurrent != currentPosition) {
                    themeRv.scrollToPosition(currentPosition)
                    themeCurrent = currentPosition
                }
                themeRv.scaleX = 1 - (scrollPosition) * 2
                themeRv.scaleY = 1 - (scrollPosition) * 2
            } else {
                if (themeCurrent != newPosition) {
                    themeRv.scrollToPosition(newPosition)
                    themeCurrent = newPosition
                }
                themeRv.scaleX = ((scrollPosition - 0.5) * 2).toFloat()
                themeRv.scaleY = ((scrollPosition - 0.5) * 2).toFloat()
            }
            find.newBgView.onScroll(
                scrollPosition, getANum(currentPosition), getANum(newPosition)
            )
        }
    }

    fun getANum(int: Int): Int {
        return (cards.get(int).scheme as SchemeAI).aiType
    }
    private val itemClickListener: ItemClickListener by lazy {
        object : ItemClickListener {
            override fun onClick(position: Int, scheme: Scheme?) {
                Log.d("TAG", "onClick: $position $themeCurrent")
                if (position != themeCurrent) dsv.smoothScrollToPosition(position)
                else scheme?.navigation(requireContext())
            }
        }
    }
    private val requestDataLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            Log.d("requestDataLauncher", "${find.switchButton.isChecked}: ")
            flag1 = true
            stateModel.postValue(!find.switchButton.isChecked)
        }
    /** 检查录音权限是否有授权 */
    private fun isRecordAudioPermissionGranted(): Boolean {
        return context?.checkPermission(
            Manifest.permission.RECORD_AUDIO,
            Process.myPid(),
            Process.myUid()
        ) == PackageManager.PERMISSION_GRANTED
    }
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {

            } else {
                // 权限未授予
                // 可以显示一个消息或提示用户打开权限设置界面
                "录音权限未开启"
            }
            flag1 = true
            stateModel.postValue(!find.switchButton.isChecked)
        }
    private fun requestRecordAudioPermission() {
        val permission = Manifest.permission.RECORD_AUDIO
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                permission
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // 如果没有录音权限，请求权限
            requestPermissionLauncher.launch(permission)
        } else {
            // 权限已经授予，执行需要录音权限的操作
        }
    }
    var flag1 = false
    override fun onResume() {
        super.onResume()
        stateModel.postValue(ViewModleMain.isShowSuspendWindow.value)
//        EmoDialog().show(parentFragmentManager)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
