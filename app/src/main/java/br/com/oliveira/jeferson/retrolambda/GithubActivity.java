package br.com.oliveira.jeferson.retrolambda;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.jakewharton.rxbinding.widget.TextViewTextChangeEvent;
import com.trello.rxlifecycle.RxLifecycle;
import com.trello.rxlifecycle.components.RxActivity;

import java.util.concurrent.TimeUnit;

import br.com.oliveira.jeferson.retrolambda.model.User;
import br.com.oliveira.jeferson.retrolambda.service.GithubApi;
import retrofit.RestAdapter;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class GithubActivity extends RxActivity {

    private final String LOG_TAG = "GithubActivity";
    private EditText edUser;
    private TextView result;
    private ImageButton btFind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_github);

        edUser = (EditText) findViewById(R.id.edUser);
        result = (TextView) findViewById(R.id.result);
        btFind = (ImageButton) findViewById(R.id.btFind);

        GithubApi mApi = createGithubApi();

        btFind.setOnClickListener(v -> mApi.user(edUser.getText().toString())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<User>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.d(LOG_TAG, "onError"+e.getMessage());
                            result.setText(e.getMessage());
                        }

                        @Override
                        public void onNext(User user) {
                            result.setText(user.name+" tem "+user.followers+" seguidores");
                        }
                    }));

    }

    private GithubApi createGithubApi(){

        RestAdapter.Builder builder = new RestAdapter.Builder().setEndpoint("https://api.github.com/");
        //builder.setRequestInterceptor(request -> request.addHeader("Authorization", ""));
        return builder.build().create(GithubApi.class);

    }

}
