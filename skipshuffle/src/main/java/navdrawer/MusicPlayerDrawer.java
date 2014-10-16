package navdrawer;

import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.dontbelievethebyte.skipshuffle.activities.BaseActivity;

public class MusicPlayerDrawer {

    private ListView drawerList;

    public MusicPlayerDrawer(BaseActivity baseActivity, int drawerId)
    {
        drawerList = (ListView) baseActivity.findViewById(drawerId);
    }

    public void setClickListener(ListView.OnItemClickListener navDrawerClickListener)
    {
        drawerList.setOnItemClickListener(navDrawerClickListener);
    }

    public void setTouchListener(View.OnTouchListener touchListener)
    {
        drawerList.setOnTouchListener(touchListener);
    }

    public void setAdapter(BaseAdapter baseAdapter)
    {
        drawerList.setAdapter(baseAdapter);
    }
}
