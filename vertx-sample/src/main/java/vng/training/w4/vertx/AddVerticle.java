package vng.training.w4.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

@Component
@Scope(SCOPE_PROTOTYPE)
public class AddVerticle extends AbstractVerticle {
    private static final Logger LOG = LoggerFactory.getLogger(AddVerticle.class);

    @Override
    public void start(Promise<Void> startPromise) {
        HttpServer server = vertx.createHttpServer().requestHandler(request -> {
            int param1, param2;
            try {
                param1 = Integer.parseInt(request.getParam("param1"));
                param2 = Integer.parseInt(request.getParam("param2"));
            } catch (NumberFormatException e) {
                request.response().setStatusCode(400).end("Invalid parameters");
                return;
            }
            request.response().end(Integer.toString(param1 + param2));
        }).listen(8080, ar -> {
            if (ar.succeeded()) {
                LOG.info("AddVerticle started: @" + this.hashCode());
                startPromise.complete();
            } else {
                startPromise.fail(ar.cause());
            }
        });
    }
}
