package app.healthcare.heartratehistory;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import app.healthcare.R;
import com.gc.materialdesign.treeview.model.TreeNode;

@SuppressLint("InflateParams")
public class HistoryHeartRateItem extends TreeNode.BaseNodeViewHolder<HistoryHeartRateItem.IconTreeItem>{
	ImageView historyChildItemImage;
	TextView historyChildItemDate;
	TextView historyChildItemFeel;
	TextView historyChildItemRate;
	public HistoryHeartRateItem(Context context) {
		super(context);
		
	}

	public static class IconTreeItem {
        public int icon;
        public String text;

        public IconTreeItem(int icon, String text) {
            this.icon = icon;
            this.text = text;
        }
    }

	@Override
	public View createNodeView(TreeNode node, IconTreeItem value) {
		final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.item_history_heartrate, null, false);
        historyChildItemImage = (ImageView) view.findViewById(R.id.history_child_item_type_image);
        historyChildItemDate = (TextView)view.findViewById(R.id.history_child_item_date);
        historyChildItemFeel = (TextView)view.findViewById(R.id.history_child_item_type_feel);
        historyChildItemRate = (TextView)view.findViewById(R.id.history_child_item_rate);

        return view;
	}

	public ImageView getHistoryChildItemImage() {
		return historyChildItemImage;
	}

	public void setHistoryChildItemImage(ImageView historyChildItemImage) {
		this.historyChildItemImage = historyChildItemImage;
	}

	public TextView getHistoryChildItemDate() {
		return historyChildItemDate;
	}

	public void setHistoryChildItemDate(TextView historyChildItemDate) {
		this.historyChildItemDate = historyChildItemDate;
	}

	public TextView getHistoryChildItemFeel() {
		return historyChildItemFeel;
	}

	public void setHistoryChildItemFeel(TextView historyChildItemFeel) {
		this.historyChildItemFeel = historyChildItemFeel;
	}

	public TextView getHistoryChildItemRate() {
		return historyChildItemRate;
	}

	public void setHistoryChildItemRate(TextView historyChildItemRate) {
		this.historyChildItemRate = historyChildItemRate;
	}
}
