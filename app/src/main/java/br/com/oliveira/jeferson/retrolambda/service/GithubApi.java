package br.com.oliveira.jeferson.retrolambda.service;

import br.com.oliveira.jeferson.retrolambda.model.User;
import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by jeferson on 28/07/16.
 */
public interface GithubApi {

    @GET("/users/{user}")
    Observable<User> user(@Path("user") String user);

}
