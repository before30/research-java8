package cc.before30;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@SpringBootApplication
@Slf4j
public class Application {

	@Value("${threadPool.db.init_size}")
	private int THREAD_POOL_DB_INIT_SIZE;

	@Value("${threadPool.db.max_size")
	private int THREAD_POOL_DB_MAX_SIZE;

	@Value("${threadPooldb.queue_size}")
	private int THREAD_POOL_DB_QUEUE_SIZE;

	@Bean(name = "dbThreadPoolExecutor")
	public TaskExecutor dbThreadPoolExecutor() {
		ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
		threadPoolTaskExecutor.setCorePoolSize(THREAD_POOL_DB_INIT_SIZE);
		threadPoolTaskExecutor.setMaxPoolSize(THREAD_POOL_DB_MAX_SIZE);
		threadPoolTaskExecutor.setQueueCapacity(THREAD_POOL_DB_QUEUE_SIZE);
		threadPoolTaskExecutor.initialize();

		return threadPoolTaskExecutor;
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
