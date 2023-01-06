package vng.training.w4.vertx;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.spi.VerticleFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("vng.training.w4.vertx")
public class Main {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();

        ApplicationContext context = new AnnotationConfigApplicationContext(Main.class);

        VerticleFactory verticleFactory = context.getBean(SpringVerticleFactory.class);

        vertx.registerVerticleFactory(verticleFactory);

        DeploymentOptions options = new DeploymentOptions().setInstances(4);
        vertx.deployVerticle(verticleFactory.prefix() + ":" + AddVerticle.class.getName(), options);
    }

}
