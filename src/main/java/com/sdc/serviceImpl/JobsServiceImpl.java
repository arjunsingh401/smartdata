/**
 * 
 */
package com.sdc.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sdc.model.Jobs;
import com.sdc.repository.JobsRepository;
import com.sdc.service.JobsService;

/**
 * @author arjun
 *
 */
@Service
public class JobsServiceImpl implements JobsService {

	@Autowired
	JobsRepository jobsRepository;
	
	@Override
	public List<Jobs> getJobs(){
		List<Jobs> jobs = new ArrayList<Jobs>();
		try {
		 jobs = jobsRepository.getJobs();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return jobs;
	}

	@Override
	public int stopJob(String jobId) {
		try {
			return jobsRepository.stopJob(jobId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
}
