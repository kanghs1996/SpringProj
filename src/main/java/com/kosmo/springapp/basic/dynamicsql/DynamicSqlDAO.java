package com.kosmo.springapp.basic.dynamicsql;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/* 컨트롤러에서 바로 DAO주입 받아서 호출하자*/
@Repository
public class DynamicSqlDAO {
	
	@Autowired
	private SqlSessionTemplate template;

	public List if1(Map map) {		
		return template.selectList("findOneMemoWithTitleLike", map);
	}////////////////////////

	public List if2(Map map) {		
		return template.selectList("findOneMemoLike", map);
	}

	public List choose(Map map) {
		return template.selectList("findOneMemoLikeChoose", map);
	}

	public List where(Map map) {
		return template.selectList("findOneMemoLikeWhere", map);
	}

	public List trim1(Map map) {
		return template.selectList("findOneMemoLikeTrim", map);
	}

	public int trim2(Map map) {
		return template.update("updateOneMemoLikeTrim", map);
	}

	public int set(Map map) {
		return template.update("updateOneMemoSet", map);
	}
	//Map사용시
	/*
	public List foreach(Map map) {//"keyno" :키, no가 저장된 리스트:밸류		
		return template.selectList("findOneMemoForeach",map);
	}*/
	public List foreach(List list) {
		return template.selectList("findOneMemoForeach",list);
	}

	public int foreachExam(int[] email) {		
		return template.delete("deleteEmail",email);
	}
	
}
