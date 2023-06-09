package com.khh.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface MemberRepository extends JpaRepository<MemberEntity,String>{

	
	//規劃驗證使用者方法
	//登入驗證的查詢方法客製化
	@Query(value="Select UserName,Password,email from Member Where username=:username and password=:password",nativeQuery=true)
	public MemberEntity findByMember(@Param("username")String username,@Param("password")String password);
	

}
