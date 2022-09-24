package io.github.fourlastor.game.route;

import dagger.Module;
import dagger.Provides;

@Module
public class RouterModule {

    private final Router router;

    public RouterModule(Router router) {
        this.router = router;
    }

    @Provides
    public Router router() {
        return router;
    }
}
