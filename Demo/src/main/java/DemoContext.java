import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class DemoContext implements ApplicationContextAware {


    private ApplicationContext applicationContext;

    private final ConcurrentHashMap<String, DemoHandler> demoMap = new ConcurrentHashMap<>();


    @PostConstruct
    public void register() {
        Map<String, DemoHandler> handlerMap = applicationContext.getBeansOfType(DemoHandler.class);
        handlerMap.values().forEach(demoHandler -> demoMap.put(demoHandler.getType(), demoHandler));
    }


    public String getHandler(String type){
        DemoHandler demoHandler = demoMap.get(type);
        return demoHandler.getType();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
