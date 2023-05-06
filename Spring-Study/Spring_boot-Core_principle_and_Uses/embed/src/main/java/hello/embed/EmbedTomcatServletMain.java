package hello.embed;

import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;

public class EmbedTomcatServletMain {
    public static void main(String[] args) {
        System.out.println("EmbedTomcatServletMain.main");

        // 톰캣 설정하기
        Tomcat tomcat = new Tomcat();
        Connector connector = new Connector();
        connector.setPort(8080);
        tomcat.setConnector(connector);

        // 서블릿 등록해주기
        tomcat.addContext("", "/");
    }
}
