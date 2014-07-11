package br.quartzcluster;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.quartz.SchedulerException;

/**
 * <h2>AppContextListener</h2>
 * <p>
 * Inicia/para as triggers do quartz
 * </p>
 *
 * @author fabio.zoroastro
 */
public class AppContextListener implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent event) {
	try {
	    AppQuartz.get().stop(true);
	} catch (SchedulerException e) {
	    e.printStackTrace();
	}

    }

    @Override
    public void contextInitialized(ServletContextEvent event) {
	try {

	    AppQuartz.get().init(true);
	} catch (SchedulerException e) {

	    e.printStackTrace();
	}

    }

}
