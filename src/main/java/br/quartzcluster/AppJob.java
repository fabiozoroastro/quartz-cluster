package br.quartzcluster;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author fabio.zoroastro
 */
public class AppJob implements Job {

    /*
     * (non-Javadoc)
     *
     * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
     */
    @Override
    public void execute(JobExecutionContext ctx) throws JobExecutionException {

	System.out.printf("\nExecutado às: %tc", new Date());
    }
}
