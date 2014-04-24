package gs.ui.tests.cosmo.pages;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Created by Itsik on 24-Apr-14.
 */
public class TestBeansContext implements ApplicationContextAware {


    private static TestBeansContext instance = null;
    private static ApplicationContext applicationContext;


    public static TestBeansContext get() {
        if (instance == null) {
            instance = applicationContext.getBean(TestBeansContext.class);
        }
        return instance;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public BlueprintPage getBlueprintPage() {
        return applicationContext.getBean(BlueprintPage.class);
    }
}