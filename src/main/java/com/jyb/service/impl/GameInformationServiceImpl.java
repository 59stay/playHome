package com.jyb.service.impl;

import java.util.ArrayList;
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
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.jyb.entity.GameInformation;
import com.jyb.repository.GameInformationRepository;
import com.jyb.service.GameInformationService;
import com.jyb.util.StringUtil;


@Service("gameInformationService")
@Transactional
public class GameInformationServiceImpl implements GameInformationService {
	
	@Autowired
	private GameInformationRepository gameInformationRepository;
	
	
	@Override
	public List<GameInformation> listAll(GameInformation gameInfo, Direction direction, String... properties) {
		// TODO Auto-generated method stub
		Sort sort = new Sort(direction,properties);
		List<GameInformation> result = gameInformationRepository.findAll(new Specification<GameInformation>() {
			@Override
			public Predicate toPredicate(Root<GameInformation> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				  // TODO Auto-generated method stub
				  List<Predicate> list = new ArrayList<Predicate>();
				  if (gameInfo!=null&&gameInfo.getAuditStatus()!=null) {
					  list.add(cb.equal(root.get("auditStatus"),gameInfo.getAuditStatus()));
					  
				  }
				  Predicate[] p = new Predicate[list.size()];
		          return cb.and(list.toArray(p));
			}
			
		  },sort);
		return result;
	}
 
	
	@Override
	public List<GameInformation> listPage(GameInformation gameInfo, Integer page, Integer pageSize, Direction direction,
			String... properties) {
		// TODO Auto-generated method stub
		Pageable pageable = new PageRequest(page-1, pageSize, direction, properties);
		Page<GameInformation> pageGameInformation = gameInformationRepository.findAll(new Specification<GameInformation>() {
			@Override
			public Predicate toPredicate(Root<GameInformation> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				Predicate predicate =  cb.conjunction();
				if(gameInfo!=null){
					if(gameInfo.getUserInformation()!=null && StringUtil.isNotEmpty(gameInfo.getUserInformation().getUserName())){
						predicate.getExpressions().add(cb.like(root.get("userInformation").get("userName"), "%"+gameInfo.getUserInformation().getUserName()+"%"));
					}
					if(StringUtil.isNotEmpty(gameInfo.getGameName())){
						predicate.getExpressions().add(cb.like(root.get("gameName"), "%"+gameInfo.getGameName().trim()+"%"));
					}
					if(gameInfo.getAuditStatus()!=null){
						predicate.getExpressions().add(cb.equal(root.get("auditStatus"), gameInfo.getAuditStatus()));
					}
					if(gameInfo.getIsUseful()!=null){
						predicate.getExpressions().add(cb.equal(root.get("isUseful"), gameInfo.getIsUseful()));
					}
				    if(gameInfo.getDataDictionary()!=null && gameInfo.getDataDictionary().getId()!=null ){
						predicate.getExpressions().add(cb.equal(root.get("dataDictionary").get("id"),gameInfo.getDataDictionary().getId() ));
					}	
				}
				return predicate;
			}
		},pageable);
		return pageGameInformation.getContent();
	}

	@Override
	public Long getCount(GameInformation gameInfo) {
		// TODO Auto-generated method stub
		Long count = gameInformationRepository.count(new Specification<GameInformation>() {
			@Override
			public Predicate toPredicate(Root<GameInformation> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				Predicate predicate =  cb.conjunction();
				if(gameInfo!=null){
					if(gameInfo.getUserInformation()!=null && StringUtil.isNotEmpty(gameInfo.getUserInformation().getUserName())){
						predicate.getExpressions().add(cb.like(root.get("userInformation").get("userName"), "%"+gameInfo.getUserInformation().getUserName()+"%"));
					}
					if(gameInfo.getAuditStatus()!=null){
						predicate.getExpressions().add(cb.equal(root.get("auditStatus"), gameInfo.getAuditStatus()));
					}
					if(StringUtil.isNotEmpty(gameInfo.getGameName())){
						predicate.getExpressions().add(cb.like(root.get("gameName"), "%"+gameInfo.getGameName().trim()+"%"));
					}
					if(gameInfo.getIsUseful()!=null){
						predicate.getExpressions().add(cb.equal(root.get("isUseful"), gameInfo.getIsUseful()));
					}
					if(gameInfo.getDataDictionary()!=null && gameInfo.getDataDictionary().getId()!=null ){
						predicate.getExpressions().add(cb.equal(root.get("dataDictionary").get("id"),gameInfo.getDataDictionary().getId() ));
					}
				}
				return predicate;
			}
		});
		return count;
	}

	@Override
	public GameInformation getId(Integer id) {
		// TODO Auto-generated method stub
		return gameInformationRepository.getOne(id);
	}

	@Override
	public void save(GameInformation gameInfo) {
		// TODO Auto-generated method stub
		gameInformationRepository.save(gameInfo);
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		gameInformationRepository.delete(id);
	}



}
