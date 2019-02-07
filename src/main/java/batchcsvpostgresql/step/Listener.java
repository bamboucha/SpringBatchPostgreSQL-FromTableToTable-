package batchcsvpostgresql.step;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import batchcsvpostgresql.dao.CustomerDao;
import batchcsvpostgresql.model.Customer;
import batchcsvpostgresql.model.CustomerDTO;;


public class Listener implements ItemReadListener<CustomerDTO>, JobExecutionListener{
	private static final Logger log = LoggerFactory.getLogger(Listener.class);

		@Override
		public void beforeRead() {
			System.out.println("ItemReadListener - beforeRead");
		}

		@Override
		public void afterRead(CustomerDTO item) {
			System.out.println("ItemReadListener - afterRead"+ item);
			
		}

		@Override
		public void onReadError(Exception ex) {
			ex.printStackTrace();
			
		}

		@Override
		public void beforeJob(JobExecution jobExecution) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void afterJob(JobExecution jobExecution) {
			// TODO Auto-generated method stub
			
		}
}
