package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import drillwondocs.magicstudios.com.drilldowndocs.R;

import model.ProductDocuments;

/**
 * Created by jinbody on 2/3/2018.
 */

public class DocsAdapter extends BaseExpandableListAdapter {

    private static final class ViewHolder {
        TextView pdfTV;
        TextView migxidTV;
        TextView titleTV;
        TextView imageTV;
    }

    private final List<ProductDocuments> parentList;
    private final LayoutInflater inflater;

    private Context mContext;
    private int currentGroupPosition;

    public DocsAdapter(Context context, List parentList, ExpandableListView parentView) {
        this.inflater = LayoutInflater.from(context);
        this.parentList = parentList;
        mContext = context;
    }

    @Override
    public int getGroupCount() {
        return parentList.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return parentList.get(currentGroupPosition).children.size();
    }

    @Override
    public Object getGroup(int i) {
        return parentList.get(i);
    }

    @Override
    public Object getChild(int i, int childPosition) {
        return parentList.get(i).documentationList.get(childPosition);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parentView) {
        convertView = inflater.inflate(R.layout.parent_list_item, parentView, false);
        ((TextView) convertView.findViewById(R.id.parentTV)).setText(parentList.get(groupPosition).type);

        currentGroupPosition = groupPosition;
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parentView) {
        final ViewHolder holder;
        View view = convertView;
        if (view == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_items, parentView, false);
            holder.titleTV = view.findViewById(R.id.titleTV);
            holder.pdfTV = view.findViewById(R.id.pdfTV);
            holder.imageTV =  view.findViewById(R.id.imageTV);
            holder.migxidTV = view.findViewById(R.id.migxTV);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        ArrayList<ProductDocuments.ChildItem> items = parentList.get(groupPosition).children;
        holder.titleTV.setText("Title: " + items.get(childPosition).title);

        holder.migxidTV.setText("MIGX_ID: " + items.get(childPosition).migxID);
        holder.imageTV.setText("Image: " + items.get(childPosition).image);
        holder.pdfTV.setOnClickListener((View.OnClickListener) mContext);
        String pdfLink = items.get(childPosition).pdf;
        holder.pdfTV.setTag(pdfLink);
        holder.pdfTV.setText("Click to Open PDF");

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}
