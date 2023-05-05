package hello.container;

import jakarta.servlet.ServletContainerInitializer;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;

import java.util.Set;

public class myContainerInitV1 implements ServletContainerInitializer {
    @Override
    public void onStartup(Set<Class<?>> c, ServletContext ctx) throws ServletException {
        System.out.println("myContainerInitV1.onStartup");
        System.out.println("myContainerInitV1 c = " + c);
        System.out.println("myContainerInitV1 ctx = " + ctx);
    }
}
