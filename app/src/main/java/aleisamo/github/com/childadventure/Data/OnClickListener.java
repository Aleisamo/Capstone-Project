package aleisamo.github.com.childadventure.Data;

import android.view.View;

import java.util.List;

public interface OnClickListener {
    void onClick(View view, int position, List<?> list);

    String[] onLongClick(View view, int position, List<?> list);
}
