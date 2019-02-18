package com.jyb.service.impl;

import java.util.Date;
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

import com.jyb.entity.Log;
import com.jyb.entity.UserInformation;
import com.jyb.repository.LogRepositroy;
import com.jyb.service.LogService;
import com.jyb.util.DateUtil;
import com.jyb.util.StringUtil;
@Service("logService")
@Transactional
public class LogServiceImpl implements LogService{

	@Autowired
	private LogRepositroy logRepositroy;
	
	@Override
	public List<Log> listPage(final Log log, Integer page, Integer pageSize, Direction direction, String... properties) {
		// TODO Auto-generated method stub
		Pageable pageable = new PageRequest(page-1, pageSize, direction, properties);
		Page<Log> pageLog = logRepositroy.findAll(new Specification<Log>() {
			@Override
			public Predicate toPredicate(Root<Log> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				Predicate predicate =  cb.conjunction();
				if(log!=null){
					if(log.getUserInformation()!=null && StringUtil.isNotEmpty(log.getUserInformation().getUserName())){
						predicate.getExpressions().add(cb.like(root.<UserInformation>get("userInformation").<String>get("userName"), "%"+log.getUserInformation().getUserName()+"%"));
					}
					if(log.getType()!=null){
						predicate.getExpressions().add(cb.equal(root.get("type"),log.getType()));
					}
					if(StringUtil.isNotEmpty(log.getStartTime()) && StringUtil.isNotEmpty(log.getEndTime())){
						try {
							predicate.getExpressions().add(cb.between(root.<Date>get("logCreationTime"),DateUtil.formatString(log.getStartTime(),"yyyy-MM-dd HH:mm:ss"),DateUtil.formatString(log.getEndTime(),"yyyy-MM-dd HH:mm:ss")));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				return predicate;
			}
		},pageable);
		return pageLog.getContent();
	}

	@Override
	public Long getCount(final Log log) {
		// TODO Auto-generated method stub
		Long count = logRepositroy.count(new Specification<Log>() {
			@Override
			public Predicate toPredicate(Root<Log> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				Predicate predicate =  cb.conjunction();
				if(log!=null){
					if(log.getUserInformation()!=null && StringUtil.isNotEmpty(log.getUserInformation().getUserName())){
						predicate.getExpressions().add(cb.like(root.<UserInformation>get("userInformation").<String>get("userName"), "%"+log.getUserInformation().getUserName()+"%"));
					}
					if(log.getType()!=null){
						predicate.getExpressions().add(cb.equal(root.get("type"),log.getType()));
					}
					if(StringUtil.isNotEmpty(log.getStartTime()) && StringUtil.isNotEmpty(log.getEndTime())){
						try {
							predicate.getExpressions().add(cb.between(root.<Date>get("logCreationTime"),DateUtil.formatString(log.getStartTime(),"yyyy-MM-dd HH:mm:ss"),DateUtil.formatString(log.getEndTime(),"yyyy-MM-dd HH:mm:ss")));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				return predicate;
			}
		});
		return count;
	}

	@Override
	public void save(Log log) {
		// TODO Auto-generated method stub
		logRepositroy.save(log);
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		logRepositroy.delete(id);
	}

}
