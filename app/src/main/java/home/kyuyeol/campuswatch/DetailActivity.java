package home.kyuyeol.campuswatch;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

/**
 * Created by yumin on 2017-12-18.
 */

public class DetailActivity extends AppCompatFont {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);

        Bundle bundle = getIntent().getExtras();
        TextView text = (TextView)findViewById(R.id.text);
        text.setText(String.valueOf(bundle.getInt("Index")));

    }
}
