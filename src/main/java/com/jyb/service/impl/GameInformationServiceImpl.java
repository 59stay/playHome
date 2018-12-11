package com.jyb.service.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.jyb.entity.GameInformation;
import com.jyb.repository.GameInformationRepository;
import com.jyb.service.GameInformationService;

@Service("gameInformationService")
public class GameInformationServiceImpl implements GameInformationService {
	@Autowired
	private GameInformationRepository gameInformationRepository;
	
	@Override
	public List<GameInformation> list(GameInformation gameInfo, Integer page, Integer pageSize, Direction direction,
			String... properties) {
		// TODO Auto-generated method stub
		Pageable pageable = new PageRequest(page-1, pageSize, direction, properties);
		Page<GameInformation> pageGameInformation = gameInformationRepository.findAll(new Specification<GameInformation>() {
			@Override
			public Predicate toPredicate(Root<GameInformation> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				Predicate predicate =  cb.conjunction();
				if(gameInfo!=null){
					if(gameInfo.getAuditStatus()!=null){
						predicate.getExpressions().add(cb.equal(root.get("auditStatus"), gameInfo.getAuditStatus()));
					}
				if(gameInfo.getDataDictionary()!=null && gameInfo.getDataDictionary().getDictionaryId()!=null ){
						predicate.getExpressions().add(cb.equal(root.get("dataDictionary").get("dictionaryId"),gameInfo.getDataDictionary().getDictionaryId() ));
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
					if(gameInfo.getAuditStatus()!=null){
						predicate.getExpressions().add(cb.equal(root.get("auditStatus"), gameInfo.getAuditStatus()));
					}
				}
				if(gameInfo.getDataDictionary()!=null && gameInfo.getDataDictionary().getDictionaryId()!=null ){
					predicate.getExpressions().add(cb.equal(root.get("dataDictionary").get("dictionaryId"),gameInfo.getDataDictionary().getDictionaryId() ));
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

}
