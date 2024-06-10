package org.jxxy.debug.member.adapter
import android.database.DataSetObserver
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import org.jxxy.debug.member.bean.PointDetailGroup
import org.jxxy.debug.member.databinding.ItemPointDetailGroupBinding
import org.jxxy.debug.member.databinding.ItemPointDetailsChildBinding

class PointDetailAdapter(val list: List<PointDetailGroup>) : BaseExpandableListAdapter() {
    override fun registerDataSetObserver(observer: DataSetObserver?) {
    }

    override fun unregisterDataSetObserver(observer: DataSetObserver?) {
    }

    override fun getGroupCount(): Int = list.size

    override fun getChildrenCount(groupPosition: Int): Int = list.get(groupPosition).list.size

    override fun getGroup(groupPosition: Int): Any = list[groupPosition]

    override fun getChild(groupPosition: Int, childPosition: Int): Any = list[groupPosition].list.get(childPosition)

    override fun getGroupId(groupPosition: Int): Long = groupPosition.toLong()

    override fun getChildId(groupPosition: Int, childPosition: Int): Long = groupPosition.toLong() + childPosition.toLong()

    override fun hasStableIds(): Boolean = false

    override fun getGroupView(
        groupPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        val view: View
        val binding: ItemPointDetailGroupBinding
        if (convertView == null) {
            binding = ItemPointDetailGroupBinding.inflate(LayoutInflater.from(parent?.context), parent, false)
            view = binding.root
            view.setTag(binding)
        } else {
            view = convertView
            binding = view.tag as ItemPointDetailGroupBinding
        }
        val pointDetailGroup = list.get(groupPosition)
        binding.pointDetailsDate.text = pointDetailGroup.createTime!!.split(" ")[0]
        binding.pointDetailsAdd.text = pointDetailGroup.todayGetSum.toString()
        return view
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        val view: View
        val binding: ItemPointDetailsChildBinding
        if (convertView == null) {
            binding = ItemPointDetailsChildBinding.inflate(LayoutInflater.from(parent?.context), parent, false)
            view = binding.root
            view.setTag(binding)
        } else {
            view = convertView
            binding = view.tag as ItemPointDetailsChildBinding
        }
        val pointDetailChild = list.get(groupPosition).list.get(childPosition)
        binding.pointDetailsDescribe.text = pointDetailChild.name
        binding.pointDetailsAdd.text = pointDetailChild.nums.toString()
        return view
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean = true

    override fun areAllItemsEnabled(): Boolean = false

    override fun isEmpty(): Boolean = false

    override fun onGroupExpanded(groupPosition: Int) {
    }

    override fun onGroupCollapsed(groupPosition: Int) {
    }

    override fun getCombinedChildId(groupId: Long, childId: Long): Long = 0

    override fun getCombinedGroupId(groupId: Long): Long = 0
}
