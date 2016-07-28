package br.com.oliveira.jeferson.retrolambda;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.jakewharton.rxbinding.widget.TextViewTextChangeEvent;
import com.trello.rxlifecycle.RxLifecycle;
import com.trello.rxlifecycle.components.RxActivity;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

public class MainActivity extends RxActivity {

    private EditText find;
    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        find = (EditText) findViewById(R.id.find);
        result = (TextView) findViewById(R.id.result);

        find.setOnClickListener(v->{

        });

        final Observable<TextViewTextChangeEvent> textChangeObservable = RxTextView.textChangeEvents(find);

        textChangeObservable.compose(RxLifecycle.bindActivity(lifecycle()))
                        .debounce(400, TimeUnit.MILLISECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(getSearchObserver());

    }

    private Observer<TextViewTextChangeEvent> getSearchObserver(){
        return new Observer<TextViewTextChangeEvent>(){

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(TextViewTextChangeEvent textViewTextChangeEvent) {
                result.setText("Searching for "+textViewTextChangeEvent.text().toString());
            }
        };
    }

}
