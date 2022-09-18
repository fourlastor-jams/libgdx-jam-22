package io.github.fourlastor.game.di;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import javax.inject.Scope;

@Scope
@Retention(RUNTIME)
public @interface ScreenScoped {}
