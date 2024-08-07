package com.batch.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.DuplicateJobException;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class SimpleJobCongifuration extends DefaultBatchConfiguration{
	
	@Bean
	public Job simpleJob(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) throws DuplicateJobException {
		return new JobBuilder("simpleJob", jobRepository)
				.start(simpleStep1(jobRepository, platformTransactionManager))
				.build();
	}
	
	@Bean
	public Step simpleStep1(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
		return new StepBuilder("simpleStep1", jobRepository)
				.tasklet(testTasklet(), platformTransactionManager)
				.build();
	}
	
	@Bean
	public Tasklet testTasklet() {
		return ((contribution, chunkContext) -> {
			System.out.println(">>>>>> This is Step1 ");
			return RepeatStatus.FINISHED;
		});
	}
	
	
	

}
