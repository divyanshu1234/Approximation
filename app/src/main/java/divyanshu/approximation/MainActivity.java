package divyanshu.approximation;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.mariuszgromada.math.mxparser.*;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextInputLayout til_function;
    TextInputLayout til_initial_x;
    ListView lv_roots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        til_function = (TextInputLayout) findViewById(R.id.til_function);
        til_initial_x = (TextInputLayout) findViewById(R.id.til_initial_x);
        lv_roots = (ListView) findViewById(R.id.lv_roots);

    }

    public void calculate(View view) {


        String expression = "(" + til_function.getEditText().getText().toString() + ")";
        String initial_x = til_initial_x.getEditText().getText().toString();
        boolean flag = true;

        if(expression.equals("()")){
            til_function.setError("Enter function");
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
            Expression ex = new Expression("x - " + expression + "/" + "der(" + expression + ", x)" , x);
            boolean flag2 = true;

            if(!ex.checkSyntax()){
                til_function.setError("Wrong syntax");
                flag2 = false;
            }else
                til_function.setError(null);

            try {
                Double.parseDouble(initial_x);
                til_initial_x.setError(null);
            }catch (Exception e){
                til_initial_x.setError("Wrong syntax");
                flag2 = false;
            }


            if(flag2) {
                ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE))
                        .hideSoftInputFromInputMethod(view.getWindowToken(),0);

                List<String> roots = new ArrayList<>();
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, roots);
                lv_roots.setAdapter(arrayAdapter);

                for(int i=0; i<=15; ++i)
                {
                    x.setArgumentValue(ex.calculate());
                    roots.add(x.getArgumentValue() + "");
                    arrayAdapter.notifyDataSetChanged();
                }
            }
        }
    }
}
