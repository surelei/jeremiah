package com.jeremiahxu.surelei.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import com.jeremiahxu.surelei.po.MenuProfile;

/**
 * sql mapper of menu info.
 * 
 * @author Jeremiah Xu
 *
 */
public interface MenuMapper {
	@Insert("INSERT INTO T_MENU_INFO(MI_ID,MI_CODE,MI_NAME,MI_LEVEL,MI_ORDER,MI_TYPE,MI_URL,MI_ID_PATH,MI_NAME_PATH,MI_PARENT_ID) " 
			+ "VALUES(#{id},#{code},#{name},#{level},#{order},#{type},#{url},#{idPath},#{namePath},#{parent.id})")
	@SelectKey(before = true, keyProperty = "id", resultType = Integer.class, statement = { "SELECT MAX(MI_ID)+1 FROM T_MENU_INFO" })
	public int insert(MenuProfile menu);
	
	@Delete("DELETE FROM T_MENU_INFO WHERE MI_ID=#{id}")
	public int delete(int id);
	
	@Update("UPDATE T_MENU_INFO SET MI_CODE=#{code},MI_NAME=#{name},MI_LEVEL=#{level},MI_ORDER=#{order},MI_TYPE=#{type},MI_URL=#{url},"
			+ "MI_ID_PATH=#{idPath},MI_NAME_PATH=#{namePath},MI_PARENT_ID=#{parent.id} WHERE MI_ID=#{id}")
	public int update(MenuProfile menu);

	@Select("SELECT * FROM T_MENU_INFO WHERE MI_PARENT_ID=#{id} ORDER BY MI_ORDER")
	@ResultMap("menuInfo")
	public List<MenuProfile> selectByParent(int id);

	@Select("SELECT * FROM T_MENU_INFO WHERE MI_ID=#{id}")
	@ResultMap("menuInfo")
	public MenuProfile selectById(int id);

	@Select("SELECT * FROM T_MENU_INFO WHERE MI_PARENT_ID IS NULL")
	@ResultMap("menuInfo")
	public MenuProfile selectRoot();
}
