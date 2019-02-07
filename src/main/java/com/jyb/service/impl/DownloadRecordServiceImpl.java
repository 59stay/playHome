package com.jyb.service.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.jyb.entity.DownloadRecord;
import com.jyb.repository.DownloadRecordRepositroy;
import com.jyb.service.DownloadRecordService;
import com.jyb.util.StringUtil;
@Service("downloadRecordService")
@Transactional
public class DownloadRecordServiceImpl implements DownloadRecordService {

	@Autowired
	private DownloadRecordRepositroy downloadRecordRepositroy; 
	
	@Override
	public List<DownloadRecord> listPage(DownloadRecord downloadRecord, Integer page, Integer pageSize,
			Direction direction, String... properties) {
		Pageable pageable = new PageRequest(page-1, pageSize, direction, properties);
		Page<DownloadRecord> pageDownloadRecord = downloadRecordRepositroy.findAll(new Specification<DownloadRecord>() {
			@Override
			public Predicate toPredicate(Root<DownloadRecord> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				Predicate predicate =  cb.conjunction();
				if(downloadRecord!=null){
					if(downloadRecord.getUserInformation()!=null && downloadRecord.getUserInformation().getId()!=null){
						predicate.getExpressions().add(cb.equal(root.get("userInformation").get("id"),downloadRecord.getUserInformation().getId()));
					}
					if(downloadRecord.getUserInformation()!=null && StringUtil.isNotEmpty(downloadRecord.getUserInformation().getUserName())){
						predicate.getExpressions().add(cb.like(root.get("userInformation").get("userName"), "%"+downloadRecord.getUserInformation().getUserName()+"%"));
					}
					if(StringUtil.isNotEmpty(downloadRecord.getResourceName())){
						predicate.getExpressions().add(cb.like(root.get("resourceName"), "%"+downloadRecord.getResourceName().trim()+"%"));
					}
			     	if(downloadRecord.getLargeCategory()!=null   ){
						predicate.getExpressions().add(cb.equal(root.get("largeCategory"),downloadRecord.getLargeCategory()));
					}	
				}
				return predicate;
			}
		},pageable);
		return pageDownloadRecord.getContent();
	}

	@Override
	public Long getCount(DownloadRecord downloadRecord) {
		// TODO Auto-generated method stub
		Long count = downloadRecordRepositroy.count(new Specification<DownloadRecord>() {
			@Override
			public Predicate toPredicate(Root<DownloadRecord> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				Predicate predicate =  cb.conjunction();
				if(downloadRecord!=null){
					if(downloadRecord.getUserInformation()!=null && downloadRecord.getUserInformation().getId()!=null){
						predicate.getExpressions().add(cb.equal(root.get("userInformation").get("id"),downloadRecord.getUserInformation().getId()));
					}
					if(downloadRecord.getUserInformation()!=null && StringUtil.isNotEmpty(downloadRecord.getUserInformation().getUserName())){
						predicate.getExpressions().add(cb.like(root.get("userInformation").get("userName"), "%"+downloadRecord.getUserInformation().getUserName()+"%"));
					}
					if(StringUtil.isNotEmpty(downloadRecord.getResourceName())){
						predicate.getExpressions().add(cb.like(root.get("resourceName"), "%"+downloadRecord.getResourceName().trim()+"%"));
					}
			     	if(downloadRecord.getLargeCategory()!=null   ){
						predicate.getExpressions().add(cb.equal(root.get("largeCategory"),downloadRecord.getLargeCategory()));
					}	
				}
				return predicate;
			}
		});
		return count;
	}

	@Override
	public void save(DownloadRecord downloadRecord) {
		// TODO Auto-generated method stub
		downloadRecordRepositroy.save(downloadRecord);
	}

	@Override
	public DownloadRecord getId(Integer id) {
		// TODO Auto-generated method stub
		return downloadRecordRepositroy.findOne(id);
	}
	
	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		downloadRecordRepositroy.delete(id);
	}

	@Override
	public Integer getDownloadTime(Integer userId, Integer resourceId,String largeCategory) {
		// TODO Auto-generated method stub
		return downloadRecordRepositroy.getDownloadTime(userId,resourceId,largeCategory);
	}

	@Override
	public void deleteDownloadRecord(Integer id,String largeCategory) {
		// TODO Auto-generated method stub
		downloadRecordRepositroy.deleteDownloadRecord(id,largeCategory); 
	}

}
