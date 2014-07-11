package br.quartzcluster;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.ScheduleBuilder;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

/**
 *
 * @author fabio.zoroastro
 */
public class AppQuartz {

    private static AppQuartz instance;
    private Scheduler scheduler;

    private AppQuartz() throws SchedulerException {
	scheduler = StdSchedulerFactory.getDefaultScheduler();

    }

    public static AppQuartz get() throws SchedulerException {
	if (instance == null) {
	    instance = new AppQuartz();
	}
	return instance;
    }

    public void init(boolean start) throws SchedulerException {
	createJobsAndTriggers();
	System.out.println("\nIniciando Quartz");
	if (start) {
	    start();
	}
    }

    public void start() throws SchedulerException {
	if (scheduler.isStarted()) {
	    throw new RuntimeException("JAH INICIADO");
	}
	scheduler.start();

    }

    public void stop(boolean waitForJobsToComplete) throws SchedulerException {
	scheduler.shutdown(waitForJobsToComplete);
    }

    /**
     * <p>
     * Passos:
     * </p>
     * <ul>
     * <li>1. Cria o JobDetail(mapeamento para execução da tarefa)</li>
     * <li>2. Cria o Scheduler(agendamento)</li>
     * <li>3. Cria o Trigger que será disparado imediatamente(quando for
     * iniciada)</li>
     * <li>4. Verifica a existência da trigger</li>
     * <li>4.1. Realiza o agendamento da trigger e job</li>
     *
     * </ul>
     *
     * @throws SchedulerException
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void createJobsAndTriggers() throws SchedulerException {

	String schedId = scheduler.getSchedulerInstanceId();

	System.out.printf("\nConfigurando a instância: %s", schedId);

	JobDetail job = JobBuilder.newJob(AppJob.class)
		.withIdentity("minhaJob", "fabio").build();

	ScheduleBuilder scheduleBuilder = SimpleScheduleBuilder
		.repeatSecondlyForever(10);

	Trigger trigger = TriggerBuilder.newTrigger()
		.withIdentity("minhaTrigger").withSchedule(scheduleBuilder)
		.startNow().build();

	// Muito importante. As triggers não podem sobrepor as existentes para
	// que o trabalho de cluster funcione corretamente
	if (!scheduler.checkExists(trigger.getKey())) {
	    scheduler.scheduleJob(job, trigger);
	}
    }

}
