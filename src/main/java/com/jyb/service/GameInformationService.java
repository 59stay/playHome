package com.jyb.service;

import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import com.jyb.entity.GameInformation;

public interface GameInformationService  {
  /**
   * 根据条件分页查询游戏信息
   * @param gameInfo
   * @param page
   * @param pageSize
   * @param direction
   * @param properties
   * @return
   */
  public List<GameInformation>  listPage(GameInformation gameInfo,Integer page,Integer pageSize,Direction direction,String...properties);
  
  /**
   * 根据条件查询所有游戏总记录数
   * @param gameInfo
   * @return
   */
  public Long getCount(GameInformation gameInfo);
  
  /**
   * 根据Id查找电游戏详细信息
   * @param id
   * @return
   */
  public GameInformation getId(Integer id);
  
  /**
   * 添加或者修改游戏信息
   * @param gameInfo
   */
  public void save(GameInformation gameInfo);
  
  
}
