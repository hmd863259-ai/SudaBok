package com.sudabook.app;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    private LinearLayout feed;
    private int likes = 0;
    private EditText postInput;

    private int dp(int value) {
        return (int) (value * getResources().getDisplayMetrics().density + 0.5f);
    }

    private TextView text(String value, int size, int color, boolean bold) {
        TextView v = new TextView(this);
        v.setText(value);
        v.setTextSize(size);
        v.setTextColor(color);
        if (bold) v.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        v.setPadding(dp(4), dp(5), dp(4), dp(5));
        return v;
    }

    private Button button(String label) {
        Button b = new Button(this);
        b.setText(label);
        b.setAllCaps(false);
        return b;
    }

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.rgb(24, 119, 242));
        getWindow().setNavigationBarColor(Color.BLACK);

        LinearLayout page = new LinearLayout(this);
        page.setOrientation(LinearLayout.VERTICAL);
        page.setBackgroundColor(Color.rgb(242, 244, 247));

        TextView header = text("SudaBook 🇸🇩", 25, Color.WHITE, true);
        header.setBackgroundColor(Color.rgb(24, 119, 242));
        header.setGravity(Gravity.CENTER_VERTICAL);
        header.setPadding(dp(16), dp(14), dp(16), dp(14));
        page.addView(header);

        ScrollView scroll = new ScrollView(this);
        feed = new LinearLayout(this);
        feed.setOrientation(LinearLayout.VERTICAL);
        feed.setPadding(dp(10), dp(10), dp(10), dp(10));

        LinearLayout composer = new LinearLayout(this);
        composer.setOrientation(LinearLayout.VERTICAL);
        composer.setPadding(dp(12), dp(10), dp(12), dp(10));
        composer.setBackgroundColor(Color.WHITE);

        composer.addView(text("شنو الحاصل؟ شارك أصحابك", 17, Color.DKGRAY, true));
        postInput = new EditText(this);
        postInput.setHint("اكتب منشورك هنا...");
        postInput.setMinLines(2);
        postInput.setGravity(Gravity.TOP | Gravity.RIGHT);
        composer.addView(postInput);

        Button publish = button("نشر");
        publish.setOnClickListener(v -> {
            String content = postInput.getText().toString().trim();
            if (content.isEmpty()) {
                Toast.makeText(this, "اكتب شيئاً أولاً", Toast.LENGTH_SHORT).show();
                return;
            }
            addPost("أنت", content);
            postInput.setText("");
            Toast.makeText(this, "تمت إضافة المنشور للتجربة على هذا الجهاز", Toast.LENGTH_SHORT).show();
        });
        composer.addView(publish);
        feed.addView(composer);

        addPost("فريق SudaBook", "أهلاً بيكم في سودابوك — مساحة تواصل تجمع السودانيين 🇸🇩");
        addPost("تجربة التطبيق", "هذه نسخة أولية. المنشورات حالياً تجريبية ومحلية وليست محفوظة في قاعدة بيانات سحابية.");

        scroll.addView(feed);
        page.addView(scroll, new LinearLayout.LayoutParams(-1, 0, 1));

        TextView nav = text("الرئيسية     الأصدقاء     الإشعارات     حسابي", 14, Color.WHITE, true);
        nav.setGravity(Gravity.CENTER);
        nav.setBackgroundColor(Color.rgb(24, 119, 242));
        nav.setPadding(dp(6), dp(12), dp(6), dp(12));
        page.addView(nav);

        setContentView(page);
    }

    private void addPost(String author, String content) {
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setBackgroundColor(Color.WHITE);
        card.setPadding(dp(12), dp(8), dp(12), dp(8));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-1, -2);
        params.setMargins(0, dp(8), 0, dp(2));

        card.addView(text(author, 16, Color.rgb(24, 119, 242), true));
        card.addView(text(content, 16, Color.DKGRAY, false));
        TextView count = text("👍 0 إعجاب     💬 تعليق", 13, Color.GRAY, false);
        card.addView(count);
        Button like = button("👍 أعجبني");
        like.setOnClickListener(v -> {
            likes++;
            count.setText("👍 " + likes + " إعجاب     💬 تعليق");
        });
        card.addView(like);
        feed.addView(card, params);
    }
}
