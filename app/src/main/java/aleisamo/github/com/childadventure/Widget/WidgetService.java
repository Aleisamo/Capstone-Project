package aleisamo.github.com.childadventure.Widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class WidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return (new ChildAdventureViewFactory(this.getApplicationContext()));
    }
}
