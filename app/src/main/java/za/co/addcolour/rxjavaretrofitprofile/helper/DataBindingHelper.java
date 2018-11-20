package za.co.addcolour.rxjavaretrofitprofile.helper;

import android.databinding.BindingAdapter;
import android.widget.TextView;

import java.util.Random;

import za.co.addcolour.rxjavaretrofitprofile.R;
import za.co.addcolour.rxjavaretrofitprofile.RoundedLetterView;

public class DataBindingHelper {

    @BindingAdapter({"textView"})
    public static void setTextView(TextView textView, String value) {
        textView.setText(value);
    }

    @BindingAdapter({"textViewDate"})
    public static void setTextViewDate(TextView textView, long timestamp) {
        textView.setText(DateHelper.dateFormat(timestamp));
    }

    @BindingAdapter({"roundedLetterView"})
    public static void setRoundedLetter(RoundedLetterView letterView, String username) {

        Random random = new Random();
        int[] mArrayColor = letterView.getContext().getResources().getIntArray(R.array.android_colors);

        letterView.setBackgroundColor(mArrayColor[random.nextInt(mArrayColor.length)]);
        letterView.setTitleText(String.valueOf(username.charAt(0)));
        letterView.setTitleSize(45);
    }
}