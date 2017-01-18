package divyanshu.approximation;

import android.content.Context;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import org.mariuszgromada.math.mxparser.*;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextInputLayout til_function;
    TextInputLayout til_initial_x;
    static List<Double> roots = new ArrayList<>();
    static String function = "";
    static String initial_x = "";
    static String data = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        til_function = (TextInputLayout) findViewById(R.id.til_function);
        til_initial_x = (TextInputLayout) findViewById(R.id.til_initial_x);
        til_function.setErrorEnabled(true);
        til_initial_x.setErrorEnabled(true);

    }

    public void calculate(View view) {

        function = "(" + til_function.getEditText().getText().toString() + ")";
        initial_x = til_initial_x.getEditText().getText().toString();

        boolean flag = true;

        if(function.equals("()")){
            til_function.setError("Enter function of x");
            flag = false;
        }else
            til_function.setError(null);

        if(initial_x.equals("")) {
            til_initial_x.setError("Enter initial x");
            flag = false;
        }else
            til_initial_x.setError(null);

        if(flag){
            Argument x = new Argument("x = " + initial_x);
            Expression ex = new Expression("x - " + function + "/" + "der(" + function + ", x)" , x);
            boolean flag2 = true;

            if(!ex.checkSyntax()){
                til_function.setError("Wrong syntax");
                flag2 = false;
            }else
                til_function.setError(null);


            if(flag2) {
                roots.clear();

                data = "f(x) = " + function.substring(1, function.length()-1)
                        + "\nInitial x = " + initial_x;

                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                for(int i=0; i<=15; ++i)
                {
                    x.setArgumentValue(ex.calculate());
                    roots.add(x.getArgumentValue());
                }

                showAnswer();
            }
        }
    }

    private void showAnswer() {
        final double avg = (roots.get(10) + roots.get(11) + roots.get(12) + roots.get(13) + roots.get(14))/5;
        boolean error = false;

        for(int i=10;i<=14;++i ){
            if(Math.abs(avg - roots.get(i)) > 0.01)
                error = true;
        }

        final TextView tv_answer = (TextView)findViewById(R.id.tv_answer);
        final Animation in = new AlphaAnimation(0.0f, 1.0f);
        in.setDuration(250);
        final Animation out = new AlphaAnimation(1.0f, 0.0f);
        out.setDuration(250);

        if(error){
            tv_answer.startAnimation(out);
            out.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }
                @Override
                public void onAnimationEnd(Animation animation) {
                    tv_answer.setText("Answer oscillates too much");
                    tv_answer.startAnimation(in);
                }
                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
        }
        else{
            tv_answer.startAnimation(out);
            out.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    tv_answer.setText(roots.get(14) + "");
                    tv_answer.startAnimation(in);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            }
    }

    public void show_calculations(View view) {
        final BottomSheetDialogFragment myBottomSheet = MyBottomSheetDialogFragment.newInstance(roots, data);
        myBottomSheet.show(getSupportFragmentManager(), myBottomSheet.getTag());
    }
}