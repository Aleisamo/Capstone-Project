package aleisamo.github.com.childadventure;

import android.view.View;

import java.util.List;

public interface OnClickListener {
    void onClick(View view, int position, List<?> list);

    int onLongClick(View view, int position, List<?> list);
}
